package com.frame;

import com.utils.FileUtils;
import java.io.File;

final class o extends Thread {
   // $VF: synthetic field
   private a a;
   // $VF: synthetic field
   private final String b;
   // $VF: synthetic field
   private final boolean c;
   // $VF: synthetic field
   private final boolean d;

   o(a var1, String var2, boolean var3, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   @Override
   public final void run() {
      try {
         File var1 = new File(this.b, "source");
         if (this.c && var1.isDirectory()) {
            FileUtils.a(var1.listFiles());
         }

         var1 = new File(this.b, "target");
         if (this.d && var1.isDirectory()) {
            FileUtils.a(var1.listFiles());
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
