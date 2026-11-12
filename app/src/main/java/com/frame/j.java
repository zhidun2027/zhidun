package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

final class j implements ActionListener {
   // $VF: synthetic field
   private a a;

   j(a var1) {
      this.a = var1;
   }

   @Override
   public final void actionPerformed(ActionEvent var1) {
      JFileChooser var3;
      (var3 = new JFileChooser(com.frame.a.A(this.a))).setDialogTitle("请选择文件...");
      FileNameExtensionFilter var2 = new FileNameExtensionFilter("app文件(*.setting)", "setting", "setting");
      var3.setFileFilter(var2);
      if (var3.showSaveDialog(null) == 0) {
         com.frame.a.e(this.a, var3.getSelectedFile().getPath());
         com.frame.a.c(this.a, new File(com.frame.a.C(this.a)).getParent());
         com.frame.a.h(this.a, com.frame.a.C(this.a));
      }

      var3.cancelSelection();
      var3.remove(var3);
   }
}
