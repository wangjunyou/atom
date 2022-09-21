package com.wjy.atom.server.resources;


import com.wjy.atom.config.annotation.Config;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("")
public class WebUiStaticResources {

    private static final Logger LOG = LoggerFactory.getLogger(WebUiStaticResources.class);

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    private static final int HTTP_CACHE_SECONDS = 60;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);

    private static final String WEB_DASHBOARD = "web-dashboard";

    @Inject
    @Config("atom.root_path")
    private String rootPath;

    @Inject
    @Config("atom.tmp.dirs")
    private String tmpDirs;

    @POST
    @Path("ui/{path: .*}")
    public Response postFile(@PathParam("path") String path) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getBase(@Context HttpRequest request) {
        return Response.seeOther(
                URI.create((rootPath == null ? "" : rootPath) + "/ui/index.html")
        ).build();
    }

    @GET
    @Path("ui")
    public Response getFile() {
        return Response.seeOther(URI.create("/ui/index.html")).build();
    }

    @GET
    @Path("ui/{path: .*}")
    public Response getFile(@PathParam("path") String path, @Context HttpHeaders headers) throws IOException {
        if (path.isEmpty()) {
            path = "index.html";
        }

        String reqPath = WEB_DASHBOARD + "/" + path;
        File file = new File(tmpDirs, reqPath);
        if (!file.exists() && !loadFile(file, reqPath)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (cacheValidation(headers, file)) {
            Response response = Response.status(Response.Status.NOT_MODIFIED).build();
            setDateHeader(response);
            return response;
        } else {
            String mimeType = Files.probeContentType(file.toPath());
            Response response = Response.ok(file, mimeType).build();
            setCacheHeader(response, file);
            setDateHeader(response);
            return response;
        }

    }


    public boolean loadFile(File file, String reqPath) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        boolean success = false;

        try (InputStream resource = cl.getResourceAsStream(reqPath)) {
            if (resource != null) {
                URL root = cl.getResource(WEB_DASHBOARD);
                URL requested = cl.getResource(reqPath);

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
        return success;
    }

    public boolean cacheValidation(HttpHeaders headers, File file) {

        try {
            String ifModifiedSince = headers.getHeaderString(HttpHeaders.IF_MODIFIED_SINCE);
            if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
                Date ifModifiedSinceDate = DATE_FORMAT.parse(ifModifiedSince);
                long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
                long fileLastModifiedSeconds = file.lastModified() / 1000;
                if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) return true;
            }
        } catch (Exception e) {
            LOG.error("Cache Validation faild : {}", e);
        }
        return false;
    }

    public void setDateHeader(Response response) {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar time = new GregorianCalendar();
        response.getHeaders().putSingle(HttpHeaders.DATE, DATE_FORMAT.format(time.getTime()));
    }

    public void setCacheHeader(Response response, File file) {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar time = new GregorianCalendar();
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.getHeaders().putSingle(HttpHeaders.EXPIRES, DATE_FORMAT.format(time.getTime()));
        response.getHeaders().putSingle(HttpHeaders.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.getHeaders().putSingle(HttpHeaders.LAST_MODIFIED, DATE_FORMAT.format(new Date(file.lastModified())));
    }
}
