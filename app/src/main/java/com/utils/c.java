package com.utils;

import java.security.MessageDigest;

public final class c {
   private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   private static char[] b(byte[] var0) throws Exception {
      int var1;
      char[] var2 = new char[(var1 = var0.length) << 1];
      int var3 = 0;

      for (int var4 = 0; var3 < var1; var3++) {
         var2[var4++] = a[(240 & var0[var3]) >>> 4];
         var2[var4++] = a[15 & var0[var3]];
      }

      return var2;
   }

   public static String a(byte[] var0) throws Exception {
      if (var0 == null) {
         return null;
      } else {
         try {
            MessageDigest var1;
            (var1 = MessageDigest.getInstance("MD5")).update(var0);
            return new String(b(var1.digest()));
         } catch (Exception var2) {
            throw new RuntimeException(var2);
         }
      }
   }
}
