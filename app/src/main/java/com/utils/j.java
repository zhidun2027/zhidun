package com.utils;

import com.android.apksigner.ApkSignerTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class j {
   private String a;
   private String b;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;
   private String j;
   private String k;

   public static boolean a(File var0, File var1, File var2, String var3, String var4, String var5, String[] var6) throws Exception {
      ArrayList var7;
      (var7 = new ArrayList()).add("sign");
      var7.add("--ks");
      var7.add(var2.getAbsolutePath());
      var7.add("--ks-key-alias");
      var7.add(var4);
      var7.add("--ks-pass");
      var7.add("pass:".concat(String.valueOf(var3)));
      var7.add("--key-pass");
      var7.add("pass:".concat(String.valueOf(var5)));
      if (var6 != null && var6.length > 0) {
         for (String var8 : var6) {
            var7.add(var8);
         }
      }

      if (var1 != null) {
         var7.add("--out");
         var7.add(var1.getAbsolutePath());
      }

      var7.add(var0.getAbsolutePath());
      ApkSignerTool.main((String[])var7.toArray(new String[0]));
      return true;
   }

   public static boolean a(String var0, ZipInputStream var1) throws Exception {
      boolean var2 = true;

      try {
         ZipEntry var3 = var1.getNextEntry();
         File var9;
         if (!(var9 = new File(var0)).exists() && !var9.mkdirs()) {
            throw new Exception("创建解压目录 \"" + var9.getAbsolutePath() + "\" 失败");
         }

         while (var3 != null) {
            String var4 = var3.getName().replace('\\', '/');   // config.dll 条目名为 Windows 反斜杠，归一化为 / 以便跨平台正确建目录
            long var6 = var3.getSize();
            if (var4 == null || var4.length() <= 0) {
               var4 = "1111.dex";
               System.err.println("name: " + var4 + " size: " + var6);
               if (var6 <= 0L) {
                  var3 = var1.getNextEntry();
                  continue;
               }
            }

            File var10;
            if (!(var10 = new File(var9, var4)).getParentFile().isDirectory()) {
               var10.getParentFile().mkdirs();
            }

            FileOutputStream var11 = new FileOutputStream(var10);
            byte[] var12 = new byte[10240];

            int var5;
            while ((var5 = var1.read(var12)) > 0) {
               var11.write(var12, 0, var5);
            }

            var11.flush();
            var11.close();
            var3 = var1.getNextEntry();
         }

         var1.close();
      } catch (IOException var8) {
         var2 = false;
         var8.printStackTrace();
      }

      return var2;
   }

   public static void a(File var0, File... var1) throws IOException {
      FileOutputStream var6 = new FileOutputStream(var0);
      ZipOutputStream var2 = new ZipOutputStream(var6);
      File[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         File var7;
         (var7 = var5[var3]).isDirectory();
         a(var2, var7.getParentFile(), var7);
      }

      var2.finish();
      var6.close();
      var2.close();
   }

   private static void a(ZipOutputStream var0, File var1, File var2) throws IOException {
      if (var2.isFile()) {
         String var9 = var1.getPath() + File.separator;
         var9 = var2.getPath().replace(var9, "");
         var0.putNextEntry(new ZipEntry(var9));
         FileInputStream var8 = new FileInputStream(var2);
         byte[] var11 = new byte[1048576];
         int var6;

         while ((var6 = var8.read(var11)) != -1) {
            var0.write(var11, 0, var6);
         }

         var0.closeEntry();
         var8.close();
      } else {
         if (var2.isDirectory()) {
            File[] var5;
            for (File var4 : var5 = var2.listFiles()) {
               a(var0, var1, var4);
            }
         }
      }
   }

   public void a(String var1) {
      this.a = var1;
   }

   public void b(String var1) {
      this.b = var1;
   }

   public void c(String var1) {
      this.c = var1;
   }

   public void d(String var1) {
      this.d = var1;
   }

   public void e(String var1) {
      this.e = var1;
   }

   public void f(String var1) {
      this.f = var1;
   }

   public void g(String var1) {
      this.g = var1;
   }

   public void h(String var1) {
      this.h = var1;
   }

   public void i(String var1) {
      this.i = var1;
   }

   public void j(String var1) {
      this.j = var1;
   }

   @Override
   public String toString() {
      StringBuffer var1;
      (var1 = new StringBuffer())
         .append(this.k == null ? "" : "\n\n版本： " + this.k)
         .append(this.a == null ? "" : "\n别名:  " + this.a)
         .append(this.b == null ? "" : "\n名字:  " + this.b)
         .append(this.c == null ? "" : "\n组织单位:  " + this.c)
         .append(this.d == null ? "" : "\n组织:  " + this.d)
         .append(this.e == null ? "" : "\n城市:  " + this.e)
         .append(this.f == null ? "" : "\n州或省:  " + this.f)
         .append(this.g == null ? "" : "\n国家编码:  " + this.g)
         .append(this.h == null ? "" : "\n失效日期:  " + this.h)
         .append(this.i == null ? "" : "\n创建日期:  " + this.i)
         .append(this.j == null ? "" : "公钥： " + this.j);
      return var1.toString();
   }

   public void a(int var1) {
      this.k = String.valueOf(var1);
   }
}
