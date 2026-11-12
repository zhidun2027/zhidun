/*
 * Decompiled with CFR 0.152.
 */
package com.youyou.shell.utils;

import java.security.MessageDigest;

public class DigestUtils {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static char[] encodeHex(byte[] byArray) {
        int n = byArray.length;
        char[] cArray = new char[n << 1];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = n3 + 1;
            cArray[n3] = DIGITS_LOWER[(byArray[n2] & 0xF0) >>> 4];
            n3 = n4 + 1;
            cArray[n4] = DIGITS_LOWER[byArray[n2] & 0xF];
            ++n2;
        }
        return cArray;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String md5(String string) {
        if (string == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            return new String(DigestUtils.encodeHex(messageDigest.digest()));
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static char[] md5Char(byte[] objectArray) {
        if (objectArray == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update((byte[])objectArray);
            return DigestUtils.encodeHex(messageDigest.digest());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String md5(byte[] object) {
        if (object == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update((byte[])object);
            return new String(DigestUtils.encodeHex(messageDigest.digest()));
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

