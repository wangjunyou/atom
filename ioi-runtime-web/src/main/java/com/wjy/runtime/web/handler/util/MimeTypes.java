package com.wjy.runtime.web.handler.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * web静态资源匹配MimeType
 * */
public class MimeTypes {




    public static String getMimeTypeForExtension(String fileExtension) {
        return MIME_MAP.get(fileExtension.toLowerCase());
    }

    public static String getMimeTypeForFileName(String fileName) {
        int extensionPos = fileName.lastIndexOf('.');
        if (extensionPos >= 1 && extensionPos < fileName.length() - 1) {
            String extension = fileName.substring(extensionPos + 1);
            return getMimeTypeForExtension(extension);
        } else {
            return null;
        }
    }

    public static String getDefaultMimeType() {
        return DEFAULT_MIME_TYPE;
    }

    private MimeTypes(){}

    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

    private static final Map<String,String> MIME_MAP = new HashMap<>();

    static {
        // text types
        MIME_MAP.put("html", "text/html");
        MIME_MAP.put("htm", "text/html");
        MIME_MAP.put("css", "text/css");
        MIME_MAP.put("txt", "text/plain");
        MIME_MAP.put("log", "text/plain");
        MIME_MAP.put("out", "text/plain");
        MIME_MAP.put("err", "text/plain");
        MIME_MAP.put("xml", "text/xml");
        MIME_MAP.put("csv", "text/csv");

        // application types
        MIME_MAP.put("js", "application/javascript");
        MIME_MAP.put("json", "application/json");

        // image types
        MIME_MAP.put("png", "image/png");
        MIME_MAP.put("jpg", "image/jpeg");
        MIME_MAP.put("jpeg", "image/jpeg");
        MIME_MAP.put("gif", "image/gif");
        MIME_MAP.put("svg", "image/svg+xml");
        MIME_MAP.put("tiff", "image/tiff");
        MIME_MAP.put("tff", "image/tiff");
        MIME_MAP.put("bmp", "image/bmp");

        // fonts
        MIME_MAP.put("woff", "application/font-woff");
        MIME_MAP.put("woff2", "application/font-woff2");
        MIME_MAP.put("ttf", "font/ttf");
        MIME_MAP.put("otf", "font/opentype");
        MIME_MAP.put("eot", "font/application/vnd.ms-fontobject");
    }
}
