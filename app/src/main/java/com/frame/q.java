package com.frame;

import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;

final class q extends DropTargetAdapter {
   // $VF: synthetic field
   private a a;

   q(a var1) {
      this.a = var1;
   }

   @Override
   public final void drop(DropTargetDropEvent var1) {
      File var2;
      if ((var2 = com.frame.a.a(this.a, var1, ".+\\.(key|keystore)")) != null && var2.isFile()) {
         com.frame.a.f(this.a).setText(var2.getPath());
      }
   }
}
