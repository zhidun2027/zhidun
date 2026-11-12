package com.frame;

import java.io.File;
import java.io.FilenameFilter;

final class x implements FilenameFilter {
   // $VF: synthetic field
   private SignShellUtils a;

   x(SignShellUtils var1) {
      this.a = var1;
   }

   @Override
   public final boolean accept(File var1, String var2) {
      return var2.toLowerCase().matches("^classes.*\\.dex$");
   }
}
