package sun;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;

public final class a extends c {
   private static final char[] a = new char[]{
      'A',
      'B',
      'C',
      'D',
      'E',
      'F',
      'G',
      'H',
      'I',
      'J',
      'K',
      'L',
      'M',
      'N',
      'O',
      'P',
      'Q',
      'R',
      'S',
      'T',
      'U',
      'V',
      'W',
      'X',
      'Y',
      'Z',
      'a',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'v',
      'w',
      'x',
      'y',
      'z',
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9',
      '+',
      '/'
   };
   private static final byte[] b = new byte[256];
   private byte[] c = new byte[4];

   static {
      for (int var0 = 0; var0 < 255; var0++) {
         b[var0] = -1;
      }

      for (int var1 = 0; var1 < 64; var1++) {
         b[a[var1]] = (byte)var1;
      }
   }

   @Override
   protected final void a(PushbackInputStream var1, OutputStream var2, int var3) throws Exception {
      byte var5 = -1;
      byte var6 = -1;
      byte var7 = -1;
      byte var8 = -1;
      if (var3 < 2) {
         throw new IOException("BASE64Decoder: Not enough bytes for an atom.");
      } else {
         int var4;
         while ((var4 = var1.read()) != -1) {
            if (var4 != 10 && var4 != 13) {
               this.c[0] = (byte)var4;
               if (a(var1, this.c, 1, var3 - 1) == -1) {
                  throw new IOException();
               }

               if (var3 > 3 && this.c[3] == 61) {
                  var3 = 3;
               }

               if (var3 > 2 && this.c[2] == 61) {
                  var3 = 2;
               }

               switch (var3) {
                  case 4:
                     var8 = b[this.c[3] & 255];
                  case 3:
                     var7 = b[this.c[2] & 255];
                  case 2:
                     var6 = b[this.c[1] & 255];
                     var5 = b[this.c[0] & 255];
                  default:
                     switch (var3) {
                        case 2:
                           var2.write((byte)(var5 << 2 & 252 | var6 >>> 4 & 3));
                           return;
                        case 3:
                           var2.write((byte)(var5 << 2 & 252 | var6 >>> 4 & 3));
                           var2.write((byte)(var6 << 4 & 240 | var7 >>> 2 & 15));
                           return;
                        case 4:
                           var2.write((byte)(var5 << 2 & 252 | var6 >>> 4 & 3));
                           var2.write((byte)(var6 << 4 & 240 | var7 >>> 2 & 15));
                           var2.write((byte)(var7 << 6 & 192 | var8 & 63));
                        default:
                           return;
                     }
               }
            }
         }

         throw new IOException();
      }
   }
}
