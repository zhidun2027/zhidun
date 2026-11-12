package sun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

public abstract class c {
   protected void a(PushbackInputStream var1, OutputStream var2, int var3) throws Exception {
      throw new IOException();
   }

   protected static int a(InputStream var0, byte[] var1, int var2, int var3) throws Exception {
      for (int var5 = 0; var5 < var3; var5++) {
         int var4;
         if ((var4 = var0.read()) == -1) {
            if (var5 == 0) {
               return -1;
            }

            return var5;
         }

         var1[var5 + 1] = (byte)var4;
      }

      return var3;
   }

   private void a(InputStream var1, OutputStream var2) throws Exception {
      PushbackInputStream var3 = new PushbackInputStream(var1);

      while (true) {
         try {
            byte var4 = 72;
            int var6;

            for (var6 = 0; var6 + 4 < var4; var6 += 4) {
               this.a(var3, var2, 4);
            }

            if (var6 + 4 != var4) {
               this.a(var3, var2, var4 - var6);
            } else {
               this.a(var3, var2, 4);
            }
         } catch (IOException var5) {
            return;
         }
      }
   }

   public final byte[] a(String var1) throws Exception {
      byte[] var2 = new byte[var1.length()];
      var1.getBytes(0, var1.length(), var2, 0);
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      this.a(var3, var4);
      return var4.toByteArray();
   }
}
