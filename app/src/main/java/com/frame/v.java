package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

final class v implements ActionListener {
   // $VF: synthetic field
   private a a;

   v(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      JFileChooser var4;
      (var4 = new JFileChooser(com.frame.a.A(this.a))).setDialogTitle("请选择签名文件...");
      FileNameExtensionFilter var2 = new FileNameExtensionFilter("key文件(*.keystore;*.key)", "keystore", "key");
      var4.setFileFilter(var2);
      com.utils.d var5 = new com.utils.d();
      var4.setAccessory(var5);
      var4.addPropertyChangeListener(var5);
      if (var4.showOpenDialog(null) == 0) {
         com.frame.a.b(this.a, var4.getSelectedFile().getPath());
         com.frame.a.c(this.a, new File(com.frame.a.B(this.a)).getParent());
         com.frame.a.f(this.a).setText(com.frame.a.B(this.a));
         new w(this).start();
      }

      var4.cancelSelection();
      var5.removeAll();
      var4.remove(var4);
   }

   // $VF: synthetic method
   static a a(v var0) {
      return var0.a;
   }
}
