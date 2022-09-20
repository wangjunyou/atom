package com.wjy.atom.server.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("atom")
public class WebUiStaticResources {

    private static final Logger LOG = LoggerFactory.getLogger(WebUiStaticResources.class);

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    private static final int HTTP_CACHE_SECONDS = 60;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);

    private File tmpFile = new File("/home/jocker/opt/workspaces/git/atom/server/atom-server/src/main/resources/web");

    @POST
    @Path("ui/{path: .*}")
    public Response postFile(@PathParam("path") String path) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getBase() {
        return Response.seeOther(URI.create("/atom/ui/")).build();
    }

    @GET
    @Path("ui")
    public Response getUi() {
        return Response.seeOther(URI.create("/atom/ui/")).build();
    }

    @GET
    @Path("ui/{path: .*}")
    public Response getFile(@PathParam("path") String path, @Context HttpHeaders headers) throws IOException {
        if (path.isEmpty()) {
            path = "index.html";
        }

        File file = new File(tmpFile, path);
        if (!file.exists()) {
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
