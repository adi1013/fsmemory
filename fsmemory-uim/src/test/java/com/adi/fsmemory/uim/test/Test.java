package com.adi.fsmemory.uim.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @org.junit.Test
    public void test1() {
        String source = "123456";
        Md5Hash md5Hash = new Md5Hash(source,"1582360665297",10);
        System.out.println(md5Hash);
    }
}
