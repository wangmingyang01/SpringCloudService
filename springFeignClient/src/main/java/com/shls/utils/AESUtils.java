package com.shls.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

public class AESUtils {
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String key = "HEXIN9527ADMIN01";
    private static String ivParameter = "ADMIN01HEXIN9527";

    private AESUtils() {
    }

    /**
     * 加密
     */
    public static String encrypt(String src){
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 此处使用BASE64做转码。
        return result;

    }

    /**
     * 解密
     */
    public static String decrypt(String src){
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(src);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 随机字符串
     * @param length
     */
    public static String randomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKMNLOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer password = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = Math.abs(random.nextInt(base.length()));
            password.append(base.charAt(number));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        //System.out.println(AESUtils.encrypt("12345678"));
        System.out.println(AESUtils.decrypt("AzdZaCdLfS2KqqCBYPe6+WNpLuqF3oLDPH1/V4xKY7I="));
    }

}


