package com.frame;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

final class k extends DropTargetAdapter {
   // $VF: synthetic field
   private a a;
   // $VF: synthetic field
   private final Component b;

   k(a var1, Component var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public final void drop(DropTargetDropEvent var1) {
      try {
         if (!var1.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            var1.rejectDrop();
         } else {
            var1.acceptDrop(3);
            Iterator var3 = ((List)var1.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)).iterator();

            while (var3.hasNext()) {
               File var2;
               if ((var2 = (File)var3.next()).getName().matches(".+\\.(apk|APK)")) {
                  if (com.frame.a.e(this.a).getText() == null || !new File(com.frame.a.e(this.a).getText().toString()).isFile()) {
                     com.frame.a.e(this.a, var2.getPath());
                     com.frame.a.e(this.a).setText(com.frame.a.C(this.a));
                  }
               } else if (var2.getName().endsWith(".setting")) {
                  com.frame.a.g(this.a, var2.getPath());
               } else {
                  com.frame.a.d(this.a, var2.getPath());
               }

               com.frame.a.c(this.a, var2.getParent());
            }

            var1.dropComplete(true);
         }
      } catch (Exception var4) {
         this.a.a(var4.toString(), true, true);
         var4.printStackTrace();
      }
   }
}
