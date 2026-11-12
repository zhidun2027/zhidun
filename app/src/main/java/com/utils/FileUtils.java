package com.utils;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {
   private static void c(String var0, String var1) throws IOException {
      File var5;
      if ((var5 = new File(var0)).isFile() || var5.isDirectory()) {
         File[] var6;
         if ((var6 = var5.listFiles()) != null && var6.length > 0) {
            File var2;
            if (!(var2 = new File(var1)).exists()) {
               var2.mkdirs();
            }

            File[] var4 = var6;
            int var3 = var6.length;

            for (int var8 = 0; var8 < var3; var8++) {
               File var7;
               if ((var7 = var4[var8]).isFile()) {
                  a(var7.getPath(), var1 + "/" + var7.getName());
               } else if (var7.isDirectory()) {
                  c(var7.getPath(), var1 + "/" + var7.getName());
               }
            }
         }
      }
   }

   public static void a(String var0, String var1) throws IOException {
      if (new File(var1).isFile()) {
         new File(var1).delete();
      }

      BufferedInputStream var4 = new BufferedInputStream(new FileInputStream(var0));
      BufferedOutputStream var5 = new BufferedOutputStream(new FileOutputStream(var1));
      byte[] var3 = new byte[2014];

      int var2;
      while ((var2 = var4.read(var3)) != -1) {
         var5.write(var3, 0, var2);
      }

      var5.close();
      var4.close();
   }

   public static void a(String var0, byte[] var1) throws IOException {
      if (!new File(var0).getParentFile().isDirectory()) {
         new File(var0).getParentFile().mkdirs();
      }

      if (new File(var0).isFile()) {
         new File(var0).delete();
      }

      FileOutputStream var2;
      (var2 = new FileOutputStream(var0)).write(var1);
      var2.close();
   }

   public static void a(File... var0) throws IOException {
      if (var0 != null) {
         File[] var3 = var0;
         int var2 = var0.length;

         for (int var1 = 0; var1 < var2; var1++) {
            File var4;
            a(var4 = var3[var1]);
         }
      }
   }

   public static void a(File var0) throws IOException {
      if (!var0.isDirectory()) {
         var0.delete();
      } else {
         File[] var1;
         if ((var1 = var0.listFiles()) != null) {
            File[] var4 = var1;
            int var3 = var1.length;

            for (int var2 = 0; var2 < var3; var2++) {
               File var5;
               a(var5 = var4[var2]);
            }
         }

         var0.delete();
      }
   }

   public static void b(String var0, String var1) throws IOException {
      c(var0, var1);
   }

   public static long b(File var0) throws IOException {
      return Files.readAttributes(Paths.get(var0.getAbsolutePath()), BasicFileAttributes.class).creationTime().toMillis();
   }

   public static void main(String[] var0) throws IOException {
      try {
         c("C:/Users/user/Desktop/test/source/MyApp_1.0_/smali_classes2", "C:/Users/user/Desktop/test/source/MyApp_1.0_shell/smali_classes2");
      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }
}
