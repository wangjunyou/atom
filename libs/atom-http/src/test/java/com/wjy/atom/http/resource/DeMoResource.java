package com.wjy.atom.http.resource;


import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.net.URI;
import java.nio.file.Paths;

@Singleton
@Path("demo")
public class DeMoResource {

    private String value;

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getDeMo() {
        value += "1";
        return "get Demo: " + value;
    }

    @POST
    @Path("upload")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUpload(
            @FormDataParam("name") String name,
            @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDisposition
    ) throws IOException {
        System.out.println("name: " + name);
        System.out.println(fileDisposition.getFileName());
        File out = new File("/home/jocker/opt/workspaces/git/atom/libs/atom-http/src/test/resources/" + name);
        FileOutputStream outputStream = new FileOutputStream(out);
        ByteStreams.copy(inputStream, outputStream);
        return name;
    }

    @GET
    @Path("download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDownload(@QueryParam("name") String name) throws FileNotFoundException {
        File out = new File("/home/jocker/opt/workspaces/git/atom/libs/atom-http/src/test/resources/" + name);
        Response.ResponseBuilder builder = Response.ok(out)
                .header("Content-Disposition", "attachment; filename=" + out.getName());
        return builder.build();
    }

    @GET
    @Path("downstream")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDownstream(@QueryParam("name") String name) throws FileNotFoundException {
        File out = new File("/home/jocker/opt/workspaces/git/atom/libs/atom-http/src/test/resources/" + name);
        FileInputStream inputStream = new FileInputStream(out);
        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                ByteStreams.copy(inputStream, output);
            }
        };
        Response.ResponseBuilder builder = Response.ok(streamingOutput)
                .header("Content-Disposition", "attachment; filename=" + out.getName());
        return builder.build();
    }
}
