package com.frame;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

final class r extends MouseAdapter {
   // $VF: synthetic field
   private a a;

   r(a var1) {
      this.a = var1;
   }

   @Override
   public final void mouseClicked(MouseEvent var1) {
      String var4 = "https://t.me/java2019";

      try {
         Desktop.getDesktop().browse(new URI(var4));
      } catch (IOException var2) {
         var2.printStackTrace();
      } catch (URISyntaxException var3) {
         var3.printStackTrace();
      }
   }
}
