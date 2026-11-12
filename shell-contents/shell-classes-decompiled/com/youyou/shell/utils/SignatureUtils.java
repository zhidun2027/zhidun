/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.youyou.shell.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class SignatureUtils {
    private static final String TAG = "SignatureUtils";
    private Context mContext;

    public SignatureUtils(Context context) {
        this.mContext = context;
    }

    private static String byte2HexFormatted(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder(byArray.length * 2);
        int n = 0;
        while (n < byArray.length) {
            String string = Integer.toHexString(byArray[n]);
            int n2 = string.length();
            String string2 = string;
            if (n2 == 1) {
                string2 = "0" + string;
            }
            string = string2;
            if (n2 > 2) {
                string = string2.substring(n2 - 2, n2);
            }
            stringBuilder.append(string.toUpperCase());
            if (n < byArray.length - 1) {
                stringBuilder.append(':');
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    private static byte[] encryptSimpl(String object, byte[] byArray) {
        int n = 0;
        int n2 = 0;
        int n3 = byArray.length;
        object = ((String)object).getBytes();
        int n4 = ((Object)object).length;
        while (n < n3) {
            int n5 = n2;
            if (n2 >= n4) {
                n5 = 0;
            }
            byArray[n] = (byte)(byArray[n] ^ object[n5]);
            ++n;
            n2 = n5 + 1;
        }
        return byArray;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static String getMessageDigest(byte[] var0, String var1_3, String var2_4) {
        var3_5 = null;
        if (var2_4 == null) {
            var1_3 = MessageDigest.getInstance((String)var1_3);
lbl5:
            // 2 sources

            while (true) {
                var1_3.update(var0 /* !! */ );
                var0 /* !! */  = (byte[])SignatureUtils.byte2HexFormatted(var1_3.digest());
lbl8:
                // 3 sources

                return var0 /* !! */ ;
            }
        }
        try {
            var1_3 = MessageDigest.getInstance((String)var1_3, var2_4);
            ** continue;
        }
        catch (NoSuchAlgorithmException var0_1) {
            var0_1.printStackTrace();
            var0 /* !! */  = var3_5;
            ** GOTO lbl8
        }
        catch (NoSuchProviderException var0_2) {
            var0_2.printStackTrace();
            var0 /* !! */  = var3_5;
            ** continue;
        }
    }

    private static String getSHA1(byte[] byArray) {
        String string;
        block3: {
            block2: {
                String string2 = SignatureUtils.getMessageDigest(byArray, "SHA1", null);
                if (string2 == null) break block2;
                string = string2;
                if (string2.trim().length() > 0) break block3;
            }
            string = SignatureUtils.getMessageDigest(byArray, "SHA1", "BC");
        }
        return string;
    }

    private static String getSHA256(byte[] byArray) {
        return SignatureUtils.getMessageDigest(byArray, "SHA-256", null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String[] getSha1And256(Context stringArray) throws PackageManager.NameNotFoundException {
        String[] stringArray2 = new String[2];
        stringArray = stringArray.getPackageManager().getPackageInfo(stringArray.getPackageName(), 64);
        if (stringArray.signatures == null) return null;
        if (stringArray.signatures.length <= 0) return null;
        stringArray2[0] = SignatureUtils.getSHA1(stringArray.signatures[0].toByteArray());
        stringArray2[1] = SignatureUtils.getSHA256(stringArray.signatures[0].toByteArray());
        return stringArray2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean checkApp(byte[] byArray) {
        boolean bl = true;
        try {
            Object object = SignatureUtils.getSha1And256(this.mContext);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(object[0]));
            String string = stringBuilder.append(object[1]).toString();
            String string2 = string.substring(5, string.length() - 5);
            object = new StringBuilder(string2);
            boolean bl2 = Arrays.equals(byArray, SignatureUtils.encryptSimpl(((StringBuilder)object).reverse().toString(), string.getBytes()));
            if (!bl2) return false;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            return false;
        }
        return bl;
    }

    public byte[] getData(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[2048];
        int n;
        while ((n = inputStream.read(byArray)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }
}

