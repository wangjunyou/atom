package com.wjy.http.netty.handler;

import com.wjy.http.netty.util.HandlerUtils;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 *
 * */
public class HttpServerStaticFileHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerStaticFileHandler.class);

    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String SEP = "/";
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;

    private final File tmpFile;
    private HttpRequest request;

    public HttpServerStaticFileHandler(File tmpFile) {
        this.tmpFile = tmpFile;
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        try {

            if (msg instanceof HttpRequest) {
                request = (HttpRequest) msg;

                boolean keepAlive = HttpUtil.isKeepAlive(request);

                if (request.method().equals(HttpMethod.GET)) {

                    QueryStringDecoder qsd = new QueryStringDecoder(request.uri());
                    Map<String, List<String>> parameters = qsd.parameters();
                    if (parameters != null && parameters.size() > 0) {
                        request = null;
                        ctx.fireChannelRead(msg);
                        return;
                    }

                    String reqPath = qsd.path();
                    if (reqPath.endsWith(SEP))
                        reqPath += SEP + "index.html";

                    final File file = new File(tmpFile, reqPath);

                    if (!file.exists()) {

                        ClassLoader cl = HttpServerStaticFileHandler.class.getClassLoader();
                        boolean success = false;

                        try (InputStream resource = cl.getResourceAsStream("web" + reqPath);) {
                            if (resource != null) {
                                URL root = cl.getResource("web");
                                URL requested = cl.getResource("web" + reqPath);

                                if (root != null && requested != null) {
                                    URI rootURI = new URI(root.getPath()).normalize();
                                    URI requestedURI = new URI(requested.getPath()).normalize();

                                    // Check that we don't load anything from outside of the
                                    // expected scope.
                                    if (!rootURI.relativize(requestedURI).equals(requestedURI)) {
                                        LOG.debug("Loading missing file from classloader: {}", reqPath);
                                        // ensure that directory to file exists.
                                        file.getParentFile().mkdirs();
                                        Files.copy(resource, file.toPath());

                                        success = true;
                                    }
                                }
                            }
                        } catch (Throwable t) {
                            LOG.error("error while responding", t);
                        } finally {
                            if (!success) {
                                LOG.debug("Unable to load requested file {} from classloader", reqPath);
                                throw new NotFoundException("File not found.");
                            }
                        }
                    }

                    boolean cache = cacheValidation(ctx, file);
                    if (cache) return;

                    final RandomAccessFile raf;
                    try {
                        raf = new RandomAccessFile(file, "r");
                    } catch (FileNotFoundException e) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Could not find file {}.", file.getAbsolutePath());
                        }

                        Map headers = new HashMap();
                        headers.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
                        HandlerUtils.sendResponse(ctx, keepAlive, "File not found.", HttpResponseStatus.NOT_FOUND, headers);
                        return;
                    }

                    final long fileLength = raf.length();
                    final FileChannel fileChannel = raf.getChannel();

                    try {
                        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);

                        setDateAndCacheHeaders(response, file);

                        String mimeType = Files.probeContentType(Path.of(file.toURI()));
                        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeType);
                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileChannel);

                        if (keepAlive) {
                            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                        }

                        // write the initial line and the header.
                        ctx.write(response);

                        // write the content.
                        final ChannelFuture lastContentFuture;
                        final GenericFutureListener<Future<? super Void>> completionListener =
                                future -> {
                                    fileChannel.close();
                                    raf.close();
                                };

                        if (ctx.pipeline().get(SslHandler.class) == null) {
                            ctx.write(
                                            new DefaultFileRegion(fileChannel, 0, fileLength),
                                            ctx.newProgressivePromise())
                                    .addListener(completionListener);
                            lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

                        } else {
                            lastContentFuture =
                                    ctx.writeAndFlush(
                                                    new HttpChunkedInput(
                                                            new ChunkedFile(
                                                                    raf, 0, fileLength, 8192)),
                                                    ctx.newProgressivePromise())
                                            .addListener(completionListener);

                            // HttpChunkedInput will write the end marker (LastHttpContent) for us.
                        }

                        // close the connection, if no keep-alive is needed
                        if (!keepAlive) {
                            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
                        }
                    } catch (IOException e) {
                        fileChannel.close();
                        raf.close();
                        throw e;
                    }

                } else {
                    request = null;
                    ctx.fireChannelRead(msg);
                }

            } else if (request == null & msg instanceof HttpContent) {
                ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
            }

        } catch (Throwable t) {

            boolean keepAlive = HttpUtil.isKeepAlive(request);

            request = null;

            HttpResponseStatus responseStatus = HttpResponseStatus.NOT_FOUND;
            Map headers = new HashMap();
            headers.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
            HandlerUtils.sendResponse(ctx, keepAlive, t.getMessage(), responseStatus, headers);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx.channel().isActive()) {
            LOG.error("Caught exception", cause);

            Map headers = new HashMap();
            headers.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
            HandlerUtils.sendResponse(
                    ctx,
                    false,
                    "Internal server error.",
                    HttpResponseStatus.INTERNAL_SERVER_ERROR,
                    headers);
        }
    }

    private boolean cacheValidation(ChannelHandlerContext ctx, File file) {
        try {
            String ifModifiedSince = request.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE);
            if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
                Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

                // Only compare up to the second because the datetime format we send to the client
                // does not have milliseconds
                long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
                long fileLastModifiedSeconds = file.lastModified() / 1000;
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

    private void setDateAndCacheHeaders(HttpResponse response, File file) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar time = new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
        response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.headers().set(
                HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(file.lastModified())));
    }

}
