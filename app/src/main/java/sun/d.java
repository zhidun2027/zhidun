package sun;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class d {
   private PrintStream a;

   protected abstract void a(OutputStream var1, byte[] var2, int var3, int var4) throws IOException;

   private static int a(InputStream var0, byte[] var1) throws Exception {
      for (int var2 = 0; var2 < var1.length; var2++) {
         int var3;
         if ((var3 = var0.read()) == -1) {
            return var2;
         }

         var1[var2] = (byte)var3;
      }

      return var1.length;
   }

   private void a(InputStream var1, OutputStream var2) throws Exception {
      byte[] var5 = new byte[57];
      this.a = new PrintStream(var2);

      int var4;
      while ((var4 = a(var1, var5)) != 0) {
         for (byte var3 = 0; var3 < var4; var3 += 3) {
            if (var3 + 3 <= var4) {
               this.a(var2, var5, var3, 3);
            } else {
               this.a(var2, var5, var3, var4 - var3);
            }
         }

         if (var4 < 57) {
            break;
         }

         this.a.println();
      }
   }

   public final String a(byte[] var1) throws Exception {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ByteArrayInputStream var4 = new ByteArrayInputStream(var1);

      try {
         this.a(var4, var2);
         return var2.toString("8859_1");
      } catch (Exception var3) {
         throw new Error("CharacterEncoder.encode internal error");
      }
   }
}
