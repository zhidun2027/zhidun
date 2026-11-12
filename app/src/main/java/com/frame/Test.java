package com.frame;

import com.utils.ApkUtilTool;
import java.io.File;
import java.io.IOException;

public class Test {
   public static void main(String[] var0) {
      ApkUtilTool var4 = new ApkUtilTool();
      File var1 = new File("C:\\Users\\user\\Desktop\\3.2.2\\result\\Application_shell_sign_r.apk");
      File var2 = new File("C:\\Users\\user\\Desktop\\3.2.2\\result\\Application_shell_sign_r_zzzzzz.apk");

      try {
         var4.ChangToEncryptedEntry(var1, var2);
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }
}
