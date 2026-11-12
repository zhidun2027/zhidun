/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksigner;

import java.nio.ByteBuffer;

class HexEncoding {
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private HexEncoding() {
    }

    public static String encode(byte[] data, int offset, int length) {
        StringBuilder result = new StringBuilder(length * 2);
        for (int i2 = 0; i2 < length; ++i2) {
            byte b2 = data[offset + i2];
            result.append(HEX_DIGITS[b2 >>> 4 & 0xF]);
            result.append(HEX_DIGITS[b2 & 0xF]);
        }
        return result.toString();
    }

    public static String encode(byte[] data) {
        return HexEncoding.encode(data, 0, data.length);
    }

    public static String encodeRemaining(ByteBuffer data) {
        return HexEncoding.encode(data.array(), data.arrayOffset() + data.position(), data.remaining());
    }
}

