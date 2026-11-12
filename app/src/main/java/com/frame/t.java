package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class t implements ActionListener {
   // $VF: synthetic field
   private a a;

   t(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      if (com.frame.a.x(this.a) != null) {
         com.frame.a.x(this.a).a();
      }

      com.frame.a.a(this.a, 0);
      com.frame.a.a(this.a, false);
      System.out.println("停止");
      com.frame.a.b(this.a, true);
      this.a.a("\n停止", true, true);
      com.frame.a.u(this.a).setEnabled(true);
      com.frame.a.v(this.a).setEnabled(true);
      com.frame.a.y(this.a);
   }
}
