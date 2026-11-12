package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class g implements ActionListener {
   // $VF: synthetic field
   private a a;

   g(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      new h(this).start();
   }
}
