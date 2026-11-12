package com.frame;

import javax.swing.SwingUtilities;

final class b extends Thread {
   // $VF: synthetic field
   private a a;

   b(a var1) {
      this.a = var1;
   }

   @Override
   public final void run() {
      try {
         while (true) {
            SwingUtilities.invokeLater(() -> {
               int var1;
               if ((var1 = com.frame.a.a(this.a).getValue() + 1) <= 95) {
                  com.frame.a.a(this.a).setValue(var1);
               }
            });
            if (com.frame.a.a(this.a).getValue() + 1 <= 95) {
               if (com.frame.a.b(this.a)) {
                  com.frame.a.a(this.a).setValue(0);
                  return;
               }

               Thread.sleep((long)com.frame.a.c(this.a));
               continue;
            }
         }
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }
   }
}
