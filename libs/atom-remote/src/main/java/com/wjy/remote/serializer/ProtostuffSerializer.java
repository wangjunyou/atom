package com.wjy.remote.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;

/**
 * Protostuff序列化类
 * */

public class ProtostuffSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {

        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
        return ProtostuffIOUtil.toByteArray(obj, schema, buffer);

    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {

        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clz);
        T obj = schema.newMessage();
        if (null == obj) return null;
        ProtostuffIOUtil.mergeFrom(data, obj, schema);
        return obj;

    }
}
