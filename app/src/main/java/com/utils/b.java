package com.utils;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class b {
   private static File b;
   private static File c;
   private static int d = 3;
   private static int e = 5;
   public static String a = "2025-03-33";

   public static byte[] a(String var0, byte[] var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = var1.length;
      byte[] var6 = var0.getBytes();

      for (int var5 = var6.length; var2 < var4; var3++) {
         if (var3 >= var5) {
            var3 = 0;
         }

         var1[var2] ^= var6[var3];
         var1[var2] = (byte)(var1[var2] ^ d);
         var1[var2] = (byte)(var1[var2] - e);
         var2++;
      }

      return var1;
   }

   private static byte[] a(String var0, byte[] var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = var1.length;
      byte[] var7 = var0.getBytes();

      for (int var6 = var7.length; var3 < var5 && (var3 <= var2 || var2 <= 0); var4++) {
         if (var4 >= var6) {
            var4 = 0;
         }

         var1[var3] ^= var7[var4];
         var1[var3] = (byte)(var1[var3] ^ d);
         var1[var3] = (byte)(var1[var3] - e);
         var3++;
      }

      return var1;
   }

   public static byte[] b(String var0, byte[] var1) {
      int var2 = 0;
      int var3 = 0;
      byte[] var6 = var0.getBytes();
      int var4 = var1.length;

      for (int var5 = var6.length; var2 < var4; var3++) {
         if (var3 >= var5) {
            var3 = 0;
         }

         var1[var2] = (byte)(var1[var2] + e);
         var1[var2] = (byte)(var1[var2] ^ d);
         var1[var2] ^= var6[var3];
         var2++;
      }

      return var1;
   }

   public static byte[] a(File var0, String var1) throws IOException {
      b = var0;
      return !var0.isFile() ? null : a(var1, a(var0));
   }

   public static boolean a(String var0, String var1, String var2, boolean var3) throws IOException {
      b = new File(var0);
      c = new File(var1);
      if (!b.isFile()) {
         return false;
      } else {
         byte[] var4 = a(var2, a(var0));
         if (!c.isFile()) {
            c.createNewFile();
         }

         c(var1, var4);
         return true;
      }
   }

   public static boolean a(String var0, String var1, String var2, boolean var3, int var4) throws IOException {
      b = new File(var0);
      c = new File(var1);
      if (!b.isFile()) {
         return false;
      } else {
         byte[] var5 = a(var2, a(var0), 2048);
         if (!c.isFile()) {
            c.createNewFile();
         }

         c(var1, var5);
         return true;
      }
   }

   public static void c(String var0, byte[] var1) throws IOException {
      if (new File(var0).isFile()) {
         new File(var0).delete();
      }

      new File(var0).createNewFile();
      FileOutputStream var2;
      (var2 = new FileOutputStream(var0)).write(var1);
      var2.flush();
      var2.close();
   }

   public static byte[] a(String var0) throws IOException {
      return a(new File(var0));
   }

   public static byte[] a(File var0) throws IOException {
      FileInputStream var4 = new FileInputStream(var0);
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[1024];

      int var3;
      while ((var3 = var4.read(var2)) != -1) {
         var1.write(var2, 0, var3);
      }

      var4.close();
      var1.close();
      return var1.toByteArray();
   }

   public static byte[] d(String var0, byte[] var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = var1.length;
      byte[] var6 = var0.getBytes();

      for (int var5 = var6.length; var2 < var4; var3++) {
         if (var3 >= var5) {
            var3 = 0;
         }

         var1[var2] ^= var6[var3];
         var2++;
      }

      return var1;
   }
}
