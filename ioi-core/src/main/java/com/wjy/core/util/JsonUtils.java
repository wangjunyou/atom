package com.wjy.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
    }

    public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    public static String toJson(Object obj, boolean format) {

        String json = null;

        if (obj instanceof String) return (String) obj;

        try {
            if (format)
                json = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            else
                json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("{} serialize to json faild : {}", obj.getClass().getName(), e);
        }
        return json;
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            LOG.error("json deserialize to {} faild : {}", clazz.getName(), e);
        }
        return obj;
    }

    public static <T> T toObject(String json, Class<T> clazz, Object defaultData) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return (T) defaultData;
        }
    }


}
