package com.frame;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

final class n extends Thread {
   // $VF: synthetic field
   private a a;
   // $VF: synthetic field
   private final String b;
   // $VF: synthetic field
   private final String c;
   // $VF: synthetic field
   private final String d;
   // $VF: synthetic field
   private final String e;
   // $VF: synthetic field
   private final String f;
   // $VF: synthetic field
   private final String g;
   // $VF: synthetic field
   private final String h;

   n(a var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
   }

   @Override
   public final void run() {
      File var1 = null;

      try {
         com.frame.a.a(this.a).setValue(10);
         this.a.a("开始加壳:", false, true);
         this.a.a("\n源APK: \n" + this.b, true, true);
         com.frame.a.b(this.a, 2000);
         var1 = com.frame.a.x(this.a)
            .a(
               this.b,
               this.c,
               this.d,
               this.e,
               this.f,
               this.g,
               this.h,
               com.frame.a.p(this.a).isSelected(),
               com.frame.a.q(this.a).isSelected(),
               com.frame.a.r(this.a).isSelected(),
               com.frame.a.F(this.a),
               false
            );
      } catch (Exception var5) {
         this.a.a("\nerror: ", true, true);
         this.a.a("\nerror: " + var5.getMessage(), true, true);
         var5.printStackTrace();
      }

      if (var1 != null && var1.isFile()) {
         com.frame.a.b(this.a, 1500);
         File var2 = null;

         try {
            com.frame.a.a(this.a).setValue(30);
            this.a.a("\n开始混淆资源:", false, true);
            var2 = com.frame.a.x(this.a).a(var1, com.frame.a.p(this.a).isSelected());
         } catch (Exception var4) {
            com.frame.a.y(this.a);
            this.a.a("error: ", true, true);
            this.a.a("error: " + var4.getMessage(), true, true);
            var4.printStackTrace();
         }

         if (var2 != null && var2.isFile()) {
            var1 = null;
            com.frame.a.b(this.a, 3000);

            try {
               com.frame.a.a(this.a).setValue(60);
               this.a.a("\n开始反反编译:", false, true);
               var1 = com.frame.a.x(this.a).a(var2);
            } catch (Exception var3) {
               com.frame.a.y(this.a);
               this.a.a("error: ", true, true);
               this.a.a("error: " + var3.getMessage(), true, true);
               var3.printStackTrace();
            }

            if (var1 != null && var1.isFile()) {
               this.a.a("\n\n成功: ", true, true);
               this.a.a("\n安装包: \n" + var1.getPath(), true, true);
               SwingUtilities.invokeLater(() -> {
                  com.frame.a.a(this.a).setValue(100);
                  com.frame.a.c(this.a, true);
                  com.frame.a.d(this.a).setEnabled(false);
                  String var2x = "成功加壳";
                  String var1x = "提示";
                  JOptionPane.showMessageDialog(null, var2x, var1x, 1);
                  this.a.a("\n\n\n", true, false);
               });
               com.frame.a.c(this.a, true);
            } else {
               SwingUtilities.invokeLater(() -> {
                  SwingUtilities.invokeLater(() -> {
                     com.frame.a.c(this.a, true);
                     String var2x = "反反编译失败";
                     String var1x = "错误";
                     JOptionPane.showMessageDialog(null, var2x, var1x, 0);
                  });
                  com.frame.a.c(this.a, true);
                  com.frame.a.d(this.a).setEnabled(false);
               });
            }

            com.frame.a.y(this.a);
         } else {
            SwingUtilities.invokeLater(() -> {
               com.frame.a.c(this.a, true);
               String var2x = "资源混淆失败";
               String var1x = "错误";
               JOptionPane.showMessageDialog(null, var2x, var1x, 0);
            });
         }
      } else {
         com.frame.a.a(this.a, true, true);
         SwingUtilities.invokeLater(() -> {
            com.frame.a.c(this.a, true);
            String var2x = "加壳失败";
            String var1x = "错误";
            JOptionPane.showMessageDialog(null, var2x, var1x, 0);
         });
      }
   }
}
