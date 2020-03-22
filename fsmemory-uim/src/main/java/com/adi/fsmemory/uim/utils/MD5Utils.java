package com.adi.fsmemory.uim.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Utils {

    private static final Integer HASH_ITERATIONS = 10;


    public static String md5Str(String password, String salt) {
        Md5Hash md5Hash = new Md5Hash(password, salt, HASH_ITERATIONS);
        return md5Hash.toString();
    }


}
