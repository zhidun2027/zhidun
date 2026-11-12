package com.frame;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final class e implements DocumentListener {
   // $VF: synthetic field
   private a a;

   e(a var1) {
      this.a = var1;
   }

   @Override
   public final void removeUpdate(DocumentEvent var1) {
      System.out.println("removeUpdate   " + com.frame.a.E(this.a).getText());
      com.frame.a.b(this.a, com.frame.a.f(this.a).getText());
      com.frame.a.d(this.a, com.frame.a.B(this.a));
   }

   @Override
   public final void insertUpdate(DocumentEvent var1) {
      com.frame.a.b(this.a, com.frame.a.f(this.a).getText());
      com.frame.a.d(this.a, com.frame.a.B(this.a));
      System.out.println("insertUpdate  " + com.frame.a.E(this.a).getText());
   }

   @Override
   public final void changedUpdate(DocumentEvent var1) {
      System.out.println("changedUpdate   " + com.frame.a.E(this.a).getText());
   }
}
