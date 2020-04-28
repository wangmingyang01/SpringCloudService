package com.shls.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Utils {

    public static String encode(String str) {
        if (str == null) {
            throw new NullPointerException("加密串不能为空");
        } else {
            String totleStr = "QmVpamluZyBDaGluYSBGaW5hbmNlIENsb3VkIElubm92YXRpb24gU29mdHdhcmUgQ28uLCBMdGQu";
            str = str.replace("0", "~").replace("1", "$").replace("2", "!").replace("3", "@").replace("4", ":").replace("5", "]").replace("6", "[").replace("7", "{").replace("8", "}").replace("9", "`");
            totleStr = totleStr.substring(0, totleStr.length() - str.length());
            str = totleStr + str;
            StringBuilder sb = new StringBuilder();

            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(str.getBytes());
                byte[] data = md5.digest();
                byte[] var5 = data;
                int var6 = data.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    byte b = var5[var7];
                    sb.append(String.format("%02X", b));
                }

                return sb.toString();
            } catch (NoSuchAlgorithmException var9) {
                var9.printStackTrace();
                return null;
            }
        }
    }
}