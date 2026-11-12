package test;

import com.utils.b;
import java.io.IOException;

public class ShellDexUtils {
   public static void main(String[] var0) {
      try {
         byte[] var2 = b.a("D:\\example\\source\\videoShell_\\assets\\dex_key");
         byte[] var3 = b.b(b.a, var2);
         String var4 = new String(var3);
         System.out.println(var4);
      } catch (IOException var1) {
         var1.printStackTrace();
      }
   }
}
