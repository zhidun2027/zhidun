package sun;

import java.io.IOException;
import java.io.OutputStream;

public final class b extends d {
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

   @Override
   protected final void a(OutputStream var1, byte[] var2, int var3, int var4) throws IOException {
      if (var4 == 1) {
         byte var9 = var2[var3];
         var1.write(a[var9 >>> 2 & 63]);
         var1.write(a[var9 << 4 & 48]);
         var1.write(61);
         var1.write(61);
      } else if (var4 == 2) {
         byte var8 = var2[var3];
         byte var10 = var2[var3 + 1];
         var1.write(a[var8 >>> 2 & 63]);
         var1.write(a[(var8 << 4 & 48) + (var10 >>> 4 & 15)]);
         var1.write(a[var10 << 2 & 60]);
         var1.write(61);
      } else {
         byte var7 = var2[var3];
         byte var5 = var2[var3 + 1];
         byte var6 = var2[var3 + 2];
         var1.write(a[var7 >>> 2 & 63]);
         var1.write(a[(var7 << 4 & 48) + (var5 >>> 4 & 15)]);
         var1.write(a[(var5 << 2 & 60) + (var6 >>> 6 & 3)]);
         var1.write(a[var6 & 63]);
      }
   }
}
