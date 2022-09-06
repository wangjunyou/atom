package com.wjy.http.netty.handler;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.wjy.api.common.cache.DefaultCache;
import com.wjy.api.common.cache.KeyEntry;
import com.wjy.http.netty.util.HandlerUtils;
import com.wjy.http.netty.util.MimeTypes;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * web静态页面handler
 */
public class StaticFileHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(StaticFileHandler.class);

    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;

    /**
     * 静态页面缓存器
     */
    private static final DefaultCache<String, byte[]> tempFileCache = new DefaultCache(TimeUnit.MINUTES, 10);
    private HttpRequest request;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {

        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            if (request.method().equals(HttpMethod.GET)) {

                QueryStringDecoder qsd = new QueryStringDecoder(request.uri());
                if (qsd.parameters().isEmpty()) {

                    boolean keepAlive = HttpUtil.isKeepAlive(request);

                    String path = qsd.path();
                    final String filePath = path.endsWith("/") ? path + "index.html" : path;
                    HttpResponseStatus respStatus = HttpResponseStatus.OK;
                    Map<String, String> headers = new HashMap<>();

                    byte[] sfd = getStaticFile(path);

                    KeyEntry<String> entry = tempFileCache.getKeyEntry(path);
                    boolean cache = cacheValidation(ctx, entry);
                    if (cache) return;

                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, respStatus);

                    if (sfd.length > 0) {

                        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                        String mimeType = MimeTypes.getMimeTypeForFileName(fileName);
                        mimeType = mimeType == null ? MimeTypes.getDefaultMimeType() : mimeType;
                        headers.put(HttpHeaderNames.CONTENT_TYPE.toString(), mimeType);

                        setDateAndCacheHeaders(response, entry);

                        if (headers != null)
                            for (Map.Entry<String, String> header : headers.entrySet()) {
                                response.headers().set(header.getKey(), header.getValue());
                            }

                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, sfd.length);
                        response.content().writeBytes(sfd);

                        HandlerUtils.sendResponse(ctx, keepAlive, response);

                    } else {

                        respStatus = HttpResponseStatus.NOT_FOUND;
                        headers.put(HttpHeaderNames.CONTENT_TYPE.toString(), HttpHeaderValues.TEXT_PLAIN.toString());
                        HandlerUtils.sendResponse(ctx, keepAlive, "Not Found.", respStatus, headers);

                    }
                } else {
                    request = null;
                    ctx.fireChannelRead(msg);
                }

            } else {
                request = null;
                ctx.fireChannelRead(msg);
            }

        } else if (request == null && msg instanceof HttpContent) {
            ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
        }

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (request != null) {
            request = null;
        }
    }

    public byte[] getStaticFile(String path) {
        byte[] data = tempFileCache.getIfPresent(path, () -> {
            ClassLoader classLoader = StaticFileHandler.class.getClassLoader();
            InputStream resource = null;
            try {
                resource = classLoader.getResourceAsStream("web" + path);
                byte[] d = new byte[0];
                if (resource != null) {
                    d = ByteStreams.toByteArray(resource);
                }
                tempFileCache.put(path, d);
                return d;
            } catch (IOException e) {
                LOG.error("Get web static file fiald: {}", e);
                throw new IOException(e);
            } finally {
                if (resource != null)
                    resource.close();
            }
        });
        return data;
    }

    private boolean cacheValidation(ChannelHandlerContext ctx, KeyEntry entry) {
        try {
            String ifModifiedSince = request.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE);
            if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
                Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

                // Only compare up to the second because the datetime format we send to the client
                // does not have milliseconds
                long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
                long fileLastModifiedSeconds = entry.getCreateTime() / 1000;
                if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                    sendNotModified(ctx);
                    return true;
                }
            }
        } catch (ParseException e) {
            LOG.error("Cache Validation faild : {}", e);
        }
        return false;
    }

    private void sendNotModified(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_MODIFIED);
        setDateHeader(response);

        boolean keepAlive = HttpUtil.isKeepAlive(request);

        HandlerUtils.sendResponse(ctx, keepAlive, response);
    }

    private static void setDateHeader(HttpResponse response) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        Calendar time = new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));
    }

    private void setDateAndCacheHeaders(HttpResponse response, KeyEntry entry) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar time = new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
        response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.headers().set(
                HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(entry.getCreateTime())));
    }

}
