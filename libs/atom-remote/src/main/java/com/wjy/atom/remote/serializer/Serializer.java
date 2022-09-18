package com.wjy.atom.remote.serializer;

import java.io.IOException;

/**
 * 序列化类
 * */
public interface Serializer {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}
