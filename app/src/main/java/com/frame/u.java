package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class u implements ActionListener {
   // $VF: synthetic field
   private a a;

   u(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      com.frame.a.z(this.a).setText("启动线程中...");
      com.frame.a.a(this.a, com.frame.a.e(this.a).getText().toString());
   }
}
