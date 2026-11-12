package com.frame;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final class f implements DocumentListener {
   // $VF: synthetic field
   private a a;

   f(a var1) {
      this.a = var1;
   }

   @Override
   public final void removeUpdate(DocumentEvent var1) {
      com.frame.a.d(this.a, com.frame.a.B(this.a));
   }

   @Override
   public final void insertUpdate(DocumentEvent var1) {
      com.frame.a.d(this.a, com.frame.a.B(this.a));
   }

   @Override
   public final void changedUpdate(DocumentEvent var1) {
   }
}
