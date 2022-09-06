package com.wjy.remote.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

public class SerializerTest {

    @Test
    public void serializer() {
        User user = new User();
        user.setId(1);
        user.setName("zhangsan");
        user.setBirths(new Date());
        user.setAddr("beijing");
        user.setDataType(DataType.ONE);

        Sdata sdata = new Sdata();
        sdata.setPerson(user);

        RuntimeSchema<Sdata> from = RuntimeSchema.createFrom(Sdata.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        byte[] bytes = ProtostuffIOUtil.toByteArray(sdata, from, buffer);
        Sdata person = from.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes,person,from);
        person.sOut();
    }

    @Test
    public void protostaffSerializer() throws IOException {
        User user = new User();
        user.setId(1);
        user.setName("zhangsan");
        user.setBirths(new Date());
        user.setAddr("beijing");
        user.setDataType(DataType.ONE);

        Sdata sdata = new Sdata();
        sdata.setPerson(user);

        ProtostuffSerializer serializer = new ProtostuffSerializer();
        byte[] serialize = serializer.serialize(sdata);
        Sdata deserialize = serializer.deserialize(serialize, Sdata.class);
        deserialize.sOut();
    }
}
