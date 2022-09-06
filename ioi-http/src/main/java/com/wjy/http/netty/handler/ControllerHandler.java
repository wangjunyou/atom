package com.wjy.http.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import com.wjy.api.common.cache.Cache;
import com.wjy.api.common.cache.DefaultCache;
import com.wjy.core.inject.PackageFinder;
import com.wjy.core.util.JsonUtils;
import com.wjy.http.annotation.*;
import com.wjy.http.netty.util.HandlerUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


/**
 * 未实现：
 * 动态url /com/wjy/{other}/handler
 * filter
 * 文件下载
 * ssl
 */

public class ControllerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerHandler.class);

    private static final HttpDataFactory DATA_FACTORY = new DefaultHttpDataFactory(true);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String SPEC = "/";

    private HttpPostRequestDecoder postRequestDecoder;

    private QueryStringDecoder queryStringDecoder;
    private HttpRequest request;

    private Injector injector;

    private static final Cache<String, Class<?>> pathCache = new DefaultCache(false);
    private static final Set<Class<?>> filters = new HashSet<>();

    public ControllerHandler(Injector injector, String pkg) {

        this.injector = injector;

        if (pathCache.size() == 0) {

            PackageFinder finder = new PackageFinder()
                    .toPackage(pkg);

            this.filters.addAll(finder.getTypesAnnotatedWith(Filter.class));

            Set<Class<?>> classes = finder.getTypesAnnotatedWith(Path.class);
            for (Class<?> clazz : classes) {
                Path path = clazz.getAnnotation(Path.class);
                pathCache.put(path.value(), clazz);
            }
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            if (postRequestDecoder != null) {
                postRequestDecoder.cleanFiles();
                postRequestDecoder = null;
            }

            request = (HttpRequest) msg;
            queryStringDecoder = new QueryStringDecoder(request.uri());

            if (request.method().equals(HttpMethod.POST)) {
                postRequestDecoder = new HttpPostRequestDecoder(request);
            }

        } else if (request != null && msg instanceof HttpContent) {

            boolean keepAlive = HttpUtil.isKeepAlive(request);
            Map<String, Object> attrs = new HashMap<>();

            if (request.method().equals(HttpMethod.GET)) {
                Map<String, List<String>> datas = queryStringDecoder.parameters();
                datas.forEach((k, v) -> {
                    attrs.put(k, v.get(0));
                });

            } else if (request.method().equals(HttpMethod.POST)) {

                try {
                    HttpContent content = (HttpContent) msg;
                    postRequestDecoder.offer(content);
                    if (msg instanceof LastHttpContent) {
                        List<InterfaceHttpData> datas = postRequestDecoder.getBodyHttpDatas();
                        for (InterfaceHttpData data : datas) {
                            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                                MixedFileUpload fileUpload = (MixedFileUpload) data;
                                attrs.put(fileUpload.getName(), fileUpload);
                            } else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                                MixedAttribute attr = (MixedAttribute) data;
                                attrs.put(attr.getName(), attr.getValue());
                            }
                        }
                    } else {
                        return;
                    }
                } catch (HttpPostRequestDecoder.EndOfDataDecoderException ignored) {
                    //nothing
                }

            } else {
                methodBadRequest(ctx, keepAlive);
                return;
            }


            //未实现过滤器逻辑


            Class<?> clazz = null;
            Method method = null;
            String path = queryStringDecoder.path();

            Map<String, Class<?>> classMap = getControllerClass(path);
            if (classMap != null && classMap.size() == 1)
                for (Map.Entry<String, Class<?>> entry : classMap.entrySet()) {
                    clazz = entry.getValue();
                    String mpath = path.substring(entry.getKey().length(), path.length());
                    method = getControllerMethod(clazz, request.method(), mpath);
                    break;
                }

            if (clazz == null || method == null) {
                methodNotFound(ctx, keepAlive);
                return;
            }

            Object instance = injector.getInstance(clazz);
            Parameter[] params = method.getParameters();


            if ((attrs != null && attrs.size() > 0) && (params != null && params.length > 0)) {
                if (attrs.size() == params.length) {
                    Object[] args = new Object[params.length];
                    for (int i = 0; i < params.length; i++) {
                        Name name = params[i].getAnnotation(Name.class);
                        String key = params[i].getName();
                        if (name != null)
                            key = name.value();
                        if (!attrs.containsKey(key)) {
                            methodBadRequest(ctx, keepAlive);
                            return;
                        }
                        Object arg = attrs.get(key);
                        if (arg instanceof String) {
                            Class<?> type = params[i].getType();
                            arg = JsonUtils.toObject((String) arg, type, arg);
                        }
                        args[i] = arg;
                    }

                    Object invoke = method.invoke(instance, args);

                    if (invoke instanceof File) {
                        //文件下载
                    } else {
                        String message = JsonUtils.toJson(invoke);
                        HttpResponseStatus ok = HttpResponseStatus.OK;
                        request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
                        HandlerUtils.sendResponse(ctx, keepAlive, message, ok, null);

                    }

                } else {
                    methodBadRequest(ctx, keepAlive);
                }
            } else {
                methodBadRequest(ctx, keepAlive);
            }

        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (request != null) {
            request = null;
        }
        if (postRequestDecoder != null) {
            postRequestDecoder.cleanFiles();
            postRequestDecoder = null;
        }
    }

    public Map<String, Class<?>> getControllerClass(String path) {

        if (path == null || "".equals(path)) return null;

        Class<?> clazz = pathCache.getIfPresent(path);
        if (clazz != null) {
            Map<String, Class<?>> cMap = new HashMap<>();
            cMap.put(path, clazz);
            return cMap;
        } else {
            int index = path.lastIndexOf(SPEC);
            return getControllerClass(path.substring(0, index));
        }

    }

    public Method getControllerMethod(Class clazz, HttpMethod method, String getPath) {

        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            m.setAccessible(true);
            if (HttpMethod.GET == method) {
                Get get = m.getDeclaredAnnotation(Get.class);
                if (get != null && get.value().equals(getPath)) {
                    return m;
                }
            } else if (HttpMethod.POST == method) {
                Post post = m.getDeclaredAnnotation(Post.class);
                if (post != null && post.value().equals(getPath)) {
                    return m;
                }
            }
        }
        return null;
    }

    public void methodBadRequest(ChannelHandlerContext ctx, boolean keepAlive) {
        methodErrorResponse(ctx, keepAlive, HttpResponseStatus.BAD_REQUEST, "Bad Request.");
    }

    public void methodNotFound(ChannelHandlerContext ctx, boolean keepAlive) {
        methodErrorResponse(ctx, keepAlive, HttpResponseStatus.NOT_FOUND, "Not Found.");
    }

    private void methodErrorResponse(ChannelHandlerContext ctx, boolean keepAlive, HttpResponseStatus responseStatus, String message) {
        Map headers = new HashMap();
        headers.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
        HandlerUtils.sendResponse(ctx, keepAlive, message, responseStatus, headers);
    }


}
