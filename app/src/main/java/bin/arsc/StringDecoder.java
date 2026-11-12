package bin.arsc;

import bin.io.ZInput;
import bin.io.ZOutput;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

public class StringDecoder {
   private int[] m_stringOffsets;
   private String[] m_strings;
   private int[] m_styleOffsets;
   private int[] m_styles;
   private boolean m_isUTF8;
   private int styleOffsetCount;
   private int stylesOffset;
   private int stringsOffset;
   private int flags;
   private int chunkSize;
   private int m_strings_size;
   private static final CharsetDecoder UTF16LE_DECODER = Charset.forName("UTF-16LE").newDecoder();
   private static final CharsetDecoder UTF8_DECODER = Charset.forName("UTF-8").newDecoder();
   private static final int CHUNK_STRINGPOOL_TYPE = 1835009;
   private static final int CHUNK_NULL_TYPE = 0;
   public static final int IS_UTF8 = 256;

   public static StringDecoder read(ZInput var0) throws IOException {
      var0.skipCheckChunkTypeInt(1835009, 0);
      StringDecoder var1;
      int var2 = (var1 = new StringDecoder()).chunkSize = var0.readInt();
      int var3 = var0.readInt();
      int var4 = var1.styleOffsetCount = var0.readInt();
      int var5 = var1.flags = var0.readInt();
      int var6 = var1.stringsOffset = var0.readInt();
      int var7 = var1.stylesOffset = var0.readInt();
      var1.m_isUTF8 = (var5 & 256) != 0;
      var1.m_stringOffsets = var0.readIntArray(var3);
      if (var4 != 0) {
         var1.m_styleOffsets = var0.readIntArray(var4);
      }

      byte[] var16 = new byte[var3 = (var7 == 0 ? var2 : var7) - var6];
      var0.readFully(var16);
      var1.m_strings_size = var3;
      if (var7 != 0) {
         var3 = var2 - var7;
         var1.m_styles = var0.readIntArray(var3 / 4);
         if ((var2 = var3 % 4) > 0) {
            while (var2-- > 0) {
               var0.readByte();
            }
         }
      }

      var2 = 0;
      var1.m_strings = new String[var1.m_stringOffsets.length];

      for (int var8 : var1.m_stringOffsets) {
         int var9;
         if (!var1.m_isUTF8) {
            var7 = getShort(var16, var8) << 1;
            var9 = var8 + 2;
         } else {
            int var10 = var8 + getVarint(var16, var8)[1];
            int[] var20 = getVarint(var16, var10);
            var9 = var10 + var20[1];
            var7 = var20[0];
         }

         var1.m_strings[var2++] = decodeString(var9, var7, var1.m_isUTF8, var16);
      }

      return var1;
   }

   public int find(String var1) {
      if (var1 == null) {
         return -1;
      } else {
         for (int var2 = 0; var2 != this.m_stringOffsets.length; var2++) {
            if (this.getString(var2).equals(var1)) {
               return var2;
            }
         }

         return -1;
      }
   }

   public void getStrings(List var1) {
      int var2 = this.getSize();

      for (int var3 = 0; var3 < var2; var3++) {
         var1.add(this.getString(var3));
      }
   }

   public void write(ZOutput var1) throws IOException {
      this.write(this.m_strings, var1);
   }

   public void write(String[] var1, ZOutput var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      ZOutput var4 = new ZOutput(var3);
      int var5;
      int[] var6 = new int[var5 = var1.length];
      int var19 = 0;
      ByteArrayOutputStream var8 = new ByteArrayOutputStream();
      ZOutput var9 = new ZOutput(var8);
      if (!this.m_isUTF8) {
         for (int var10 = 0; var10 < var5; var10++) {
            var6[var10] = var19;
            String var11;
            char[] var12 = (var11 = var1[var10]).toCharArray();
            var9.writeShort((short)var12.length);
            var9.writeCharArray(var12);
            var9.writeShort((short)0);
            var19 += (var12.length << 1) + 4;
         }
      } else {
         for (int var20 = 0; var20 < var5; var20++) {
            var6[var20] = var19;
            String var22;
            byte[] var13 = getVarbytes((var22 = var1[var20]).toCharArray().length);
            var9.writeFully(var13);
            var19 += var13.length;
            byte[] var23;
            var13 = getVarbytes((var23 = var22.getBytes("UTF-8")).length);
            var9.writeFully(var13);
            var19 += var13.length;
            var9.writeFully(var23);
            var19 += var23.length;
            var9.writeByte(0);
            var19++;
         }
      }

      int var21;
      int var24;
      if ((var24 = (var21 = var8.size()) % 4) != 0) {
         for (int var27 = 0; var27 < 4 - var24; var27++) {
            var8.write(0);
         }

         var21 += 4 - var24;
      }

      byte[] var28 = var8.toByteArray();
      var4.writeInt(var5);
      var4.writeInt(this.styleOffsetCount);
      var4.writeInt(this.flags);
      var4.writeInt(this.stringsOffset);
      int var30 = var21 - this.m_strings_size;
      var4.writeInt(this.stylesOffset == 0 ? 0 : this.stylesOffset + var30);
      var4.writeIntArray(var6);
      if (this.styleOffsetCount != 0) {
         for (int var25 : this.m_styleOffsets) {
            var4.writeInt(var25 + var30);
         }
      }

      var4.writeFully(var28);
      if (this.m_styles != null) {
         var4.writeIntArray(this.m_styles);
      }

      var2.writeInt(1835009);
      byte[] var26 = var3.toByteArray();
      var3.close();
      var4.close();
      var2.writeInt(var26.length + 8);
      var2.writeFully(var26);
   }

   public int getChunkSize() {
      return this.chunkSize;
   }

   public void setString(int var1, String var2) {
      this.m_strings[var1] = var2;
   }

   public String getString(int var1) {
      return var1 >= 0 ? this.m_strings[var1] : null;
   }

   private static int[] getVarint(byte[] var0, int var1) {
      return (var0[var1] & 128) == 0 ? new int[]{var0[var1] & 127, 1} : new int[]{(var0[var1] & 127) << 8 | var0[var1 + 1] & 255, 2};
   }

   private static byte[] getVarbytes(int var0) {
      if ((var0 & 127) == var0) {
         return new byte[]{(byte)var0};
      } else {
         byte[] var1;
         (var1 = new byte[2])[0] = (byte)(var0 >>> 8 | 128);
         var1[1] = (byte)var0;
         return var1;
      }
   }

   public int getSize() {
      return this.m_stringOffsets != null ? this.m_stringOffsets.length : 0;
   }

   private static String decodeString(int var0, int var1, boolean var2, byte[] var3) {
      try {
         return (var2 ? UTF8_DECODER : UTF16LE_DECODER).decode(ByteBuffer.wrap(var3, var0, var1)).toString();
      } catch (CharacterCodingException var4) {
         return null;
      }
   }

   private static int getShort(byte[] var0, int var1) {
      return (var0[var1 + 1] & 0xFF) << 8 | var0[var1] & 0xFF;
   }
}
