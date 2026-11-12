package bin.arsc;

import bin.io.ZInput;
import java.io.IOException;

public class Header {
   public final short type;
   public final int chunkSize;
   public static final short TYPE_NONE = -1;
   public static final short TYPE_TABLE = 2;
   public static final short TYPE_PACKAGE = 512;
   public static final short TYPE_TYPE = 514;
   public static final short TYPE_LIBRARY = 515;
   public static final short TYPE_CONFIG = 513;

   public Header(short var1, int var2) {
      this.type = var1;
      this.chunkSize = var2;
   }

   public static Header read(ZInput var0) throws IOException {
      short var1;
      try {
         var1 = var0.readShort();
      } catch (Exception var2) {
         return new Header((short)-1, 0);
      }

      var0.skipBytes(2);
      return new Header(var1, var0.readInt());
   }
}
