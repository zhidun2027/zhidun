package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class i {
   public static File a = new File("C:\\Users\\user\\Desktop\\appTools");
   private static String c;
   public static com.frame.a b;

   // ===== OS 适配：Windows 用 cmd.exe + .bat/.exe；其它平台用 sh + 无扩展名 =====
   public static final boolean WIN = System.getProperty("os.name", "").toLowerCase().contains("win");
   public static final String APKTOOL = WIN ? "apktool.bat" : "apktool";
   public static final String ZIPALIGN = WIN ? "zipalign.exe" : "zipalign";
   private static final String ENC = WIN ? "gbk" : "UTF-8";

   // 按平台包裹命令执行。用 ProcessBuilder 继承父进程完整环境(保留 LANG/LC_ALL，
   // 否则中文路径在子进程会乱码)；非 Windows 另把工作目录(tools)前置到 PATH 便于解析 apktool。
   private static Process run(String cmd, File dir) throws Exception {
      ProcessBuilder pb = WIN
         ? new ProcessBuilder("cmd.exe", "/c", cmd)
         : new ProcessBuilder("sh", "-c", cmd);
      if (dir != null) {
         pb.directory(dir);
         if (!WIN) {
            pb.environment().merge("PATH", dir.getAbsolutePath(),
               (oldv, add) -> add + File.pathSeparator + oldv);
         }
      }
      return pb.start();
   }

   public static boolean a(String var0, String var1, String var2) throws Exception {
      FileUtils.a(new File(var1));
      new File(var0).getParent();
      String var3 = "apktool d -f " + var0 + " -o " + var1;
      if (var2 != null) {
         var3 = "apktool d " + var2 + var0 + " -o " + var1;
      }

      if (b != null) {
         b.a("\n逆向:\n".concat(String.valueOf(var3)), true, true);
      }

      return a(run(var3, a));
   }

   public static boolean b(String var0, String var1, String var2) throws Exception {
      if (!new File(var1).getParentFile().isDirectory()) {
         new File(var1).getParentFile().mkdirs();
      }

      String var3 = "apktool b " + var0 + " -o " + var1;
      if (var2 != null) {
         var3 = "apktool b " + var2 + " " + var0 + " -o " + var1;
      }

      if (b != null) {
         b.a("\n加壳后打包 \n".concat(String.valueOf(var3)), true, true);
      }

      System.out.println("apktool");
      return a(run(var3, a));
   }

   private static boolean a(Process var0) throws Exception {
      StringBuffer var1 = new StringBuffer();
      InputStream var2 = var0.getInputStream();
      InputStream var3 = var0.getErrorStream();
      BufferedReader var4 = new BufferedReader(new InputStreamReader(var2, ENC));

      String var5;
      while ((var5 = var4.readLine()) != null) {
         var1.append(var5 + "\n");
         System.out.println(var5);
         if (b != null) {
            b.a("\n".concat(String.valueOf(var5)), true, true);
         }
      }

      var4.close();
      var2.close();
      var4 = new BufferedReader(new InputStreamReader(var3, ENC));

      while ((var5 = var4.readLine()) != null) {
         var1.append(var5 + "\n");
         System.out.println(var5);
         if (b != null) {
            b.a("\n".concat(String.valueOf(var5)), true, true);
         }
      }

      var4.close();
      var3.close();
      int var6 = var0.waitFor();
      c = var1.toString();
      var0.destroy();
      if (var6 != 0) {
         if (b != null) {
            System.err.println("error");
            System.err.println(c);
            System.out.println("apkTool ->: " + a);
            b.a("\n打包失败。。。", true, true);
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean a(String var0, File var1, File var2, String var3) throws Exception {
      if (!var1.isFile()) {
         return false;
      } else {
         String var7 = var1.getPath();
         String var9 = var2.getPath();
         var0 = var0 + " -p -f -v 4 " + var7 + " " + var9;
         if (b != null) {
            b.a("\n 4字节对齐 \n".concat(String.valueOf(var0)), true, true);
         }

         Process var5 = run(var0, a);
         g var8;
         (var8 = new g(var5.getErrorStream())).start();
         g var10;
         (var10 = new g(var5.getInputStream())).start();

         while (!var8.a()) {
            Thread.sleep(10L);
         }

         while (!var10.a()) {
            Thread.sleep(10L);
         }

         int var6;
         if ((var6 = var5.waitFor()) != 0 && b != null) {
            b.a("\n 字节对齐出错 \n" + var8.a.toString(), true, true);
         }

         return var6 == 0;
      }
   }
}
