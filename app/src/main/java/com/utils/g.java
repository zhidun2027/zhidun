package com.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

final class g extends Thread {
   private InputStream b;
   private boolean c;
   StringBuffer a = new StringBuffer();

   g(InputStream var1) {
      this.b = var1;
      this.c = false;
   }

   public final boolean a() throws Exception {
      return this.c;
   }

   @Override
   public final void run() {
      if (this.b == null) {
         this.c = true;
      } else {
         try {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(this.b, "gbk"));

            String var2;
            while ((var2 = var1.readLine()) != null) {
               if (var2 != null && var2.trim().length() > 0) {
                  this.a.append(var2 + "\n");
                  System.out.println(var2);
                  if (i.b != null) {
                     i.b.a("\n".concat(String.valueOf(var2)), true, true);
                  }
               }
            }

            var1.close();
            this.b.close();
            return;
         } catch (Exception var5) {
            this.c = true;
         } finally {
            this.c = true;
         }
      }
   }
}
