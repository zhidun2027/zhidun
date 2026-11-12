package com.utils;

public class AppZipUtil {
   private String a = "-tsa http://sha256timestamp.ws.symantec.com/sha256/timestamp ";
   private String b = "cmd.exe /c jarsigner -digestalg SHA1 -sigalg MD5withRSA ";
   private String c = this.b;

   public static void main(String[] var0) throws Exception {
      String var1 = "D:\\\\\\\\\\app签名";
      String var2 = "D:\\\\\\\\\\app签名".replaceAll("\\{1,}", "\\");
      System.out.println(var2);
      System.out.println(var2);
   }
}
