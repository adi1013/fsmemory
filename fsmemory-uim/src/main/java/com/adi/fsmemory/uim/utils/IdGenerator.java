package com.adi.fsmemory.uim.utils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IdGenerator {

    private static final Random random = new Random();

    public static String uuidStr() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }



    /**
     * 生成随机活动搜索id,5位字母+3位数字
     * @param ids
     * @return
     */
    public static String randomSearchId(Collection ids) {
        StringBuffer sb = null;
        do {
            sb = new StringBuffer();
            for (int i = 0; i < 8; i++) {   //5位随机大小写字母
                switch (switchStrategy()) {
                    case 1:sb.append(getUpper());break;
                    case 2:sb.append(getLetter());break;
                    case 3:sb.append(getNumber());break;
                }
            }
        } while (sb.length() < 8 || ids.contains(sb.toString()));
        return sb.toString();
    }

    private static int switchStrategy(){
        return getRandomInt(1,3);
    }

    private static int getNumber() {
        return getRandomInt(0,9);
    }

    private static char getUpper() {
        return (char)getRandomInt(65, 90);
    }

    private static char getLetter() {
        return (char)getRandomInt(97, 122);
    }

    public static int getRandomInt(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }


}
