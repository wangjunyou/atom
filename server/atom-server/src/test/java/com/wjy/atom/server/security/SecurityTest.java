package com.wjy.atom.server.security;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

public class SecurityTest {

    @Test
    public void hashing() {
        HashCode admin = Hashing.crc32c().hashString("admin1231sfda345", Charsets.UTF_8);
        System.out.println(admin.toString());
        System.out.println(Hashing.hmacMd5("admin".getBytes()).newHasher().putBytes("123asda".getBytes()).hash().toString());
        System.out.println(Hashing.hmacMd5("admin".getBytes()).newHasher().hash().toString());
        System.out.println(Hashing.hmacSha512("admin".getBytes()).newHasher().hash().toString());
        System.out.println(Hashing.hmacSha256("admin".getBytes()).newHasher().hash().toString());
    }
}
