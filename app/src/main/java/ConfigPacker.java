import com.utils.FileUtils;
import com.utils.b;
import com.utils.j;
import java.io.File;

public class ConfigPacker {
   private static String a = "D:\\path\\to\\Config";

   public static void main(String[] var0) {
      File var4 = new File(a, "config.zip");
      File var1 = new File(a);
      String var2 = a + "\\config.dll";
      var4.delete();
      new File(var2).delete();

      try {
         System.out.println("开始打包");
         j.a(var4, var1.listFiles());
         byte[] var5 = b.a(var4, "2025_09");
         FileUtils.a(var2, var5);
         byte[] var6 = b.a(new File(var2));
         b.b("2023_09", var6);
         System.out.println("生成：".concat(String.valueOf(var2)));
         System.out.println("打包完成");
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }
}
