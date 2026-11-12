package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

final class c implements ActionListener {
   // $VF: synthetic field
   private a a;

   c(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      JFileChooser var4;
      (var4 = new JFileChooser(com.frame.a.A(this.a))).setDialogTitle("请选择APP...");
      FileNameExtensionFilter var2 = new FileNameExtensionFilter("app文件(*.apk;*.APK)", "apk", "APK");
      var4.setFileFilter(var2);
      com.utils.d var5 = new com.utils.d();
      var4.setAccessory(var5);
      var4.addPropertyChangeListener(var5);
      if (var4.showOpenDialog(null) == 0) {
         com.frame.a.e(this.a, var4.getSelectedFile().getPath());
         com.frame.a.e(this.a).setText(com.frame.a.C(this.a));
         com.frame.a.f(this.a, com.frame.a.C(this.a));
         com.frame.a.c(this.a, new File(com.frame.a.C(this.a)).getParent());
      }

      var4.cancelSelection();
      var5.removeAll();
      var4.remove(var4);
   }
}
