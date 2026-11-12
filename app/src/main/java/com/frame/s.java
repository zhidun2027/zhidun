package com.frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

final class s extends MouseAdapter {
   // $VF: synthetic field
   private a a;

   s(a var1) {
      this.a = var1;
   }

   @Override
   public final void mouseClicked(MouseEvent var1) {
      if (var1.getButton() == 3) {
         com.frame.a.a(this.a, com.frame.a.w(this.a) + 1);
         System.out.println("停止：" + com.frame.a.w(this.a));
         if (com.frame.a.w(this.a) > 7) {
            com.frame.a.a(this.a, 0);
            com.frame.a.a(this.a, false);
            String var2 = "关闭Debug模式";
            JOptionPane.showMessageDialog(null, var2);
         } else if (com.frame.a.w(this.a) >= 15) {
            com.frame.a.a(this.a, true);
            String var3 = "开启Debug模式";
            JOptionPane.showMessageDialog(null, var3);
         }

         System.out.println("----- " + com.frame.a.w(this.a));
      }

      super.mouseClicked(var1);
   }
}
