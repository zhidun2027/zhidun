package bin.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ZInput {
   protected final DataInputStream dis;
   protected final byte[] work;
   private int end;
   private final int length;

   public ZInput(InputStream var1) throws IOException {
      this.dis = new DataInputStream(var1);
      this.work = new byte[8];
      this.length = var1.available();
   }

   public final int getOffset() throws IOException {
      return this.length - this.dis.available();
   }

   public final int size() throws IOException {
      return this.end;
   }

   public final void skipByOffset(int var1) throws IOException {
      if ((var1 = var1 - this.getOffset()) > 0) {
         this.skipBytes(var1);
      }
   }

   public final void close() throws IOException {
      this.dis.close();
   }

   public final int available() throws IOException {
      return this.dis.available();
   }

   public final boolean readBoolean() throws IOException {
      return this.dis.readBoolean();
   }

   public final byte readByte() throws IOException {
      return this.dis.readByte();
   }

   public final char readChar() throws IOException {
      this.dis.readFully(this.work, 0, 2);
      return (char)((this.work[1] & 255) << 8 | this.work[0] & 255);
   }

   public final double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   public final int[] readIntArray(int var1) throws IOException {
      int[] var2 = new int[var1];

      for (int var3 = 0; var3 < var1; var3++) {
         var2[var3] = this.readInt();
      }

      return var2;
   }

   public final void skipInt() throws IOException {
      this.skipBytes(4);
   }

   public final void skipCheckChunkTypeInt(int var1, int var2) throws IOException {
      int var3;
      if ((var3 = this.readInt()) == var2) {
         this.skipCheckChunkTypeInt(var1, -1);
      } else if (var3 != var1) {
         throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", var1, var3));
      }
   }

   public final void skipCheckInt(int var1) throws IOException {
      int var2;
      if ((var2 = this.readInt()) != var1) {
         throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", var1, var2));
      }
   }

   public final void skipCheckShort(short var1) throws IOException {
      short var2;
      if ((var2 = this.readShort()) != var1) {
         throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", var1, var2));
      }
   }

   public final void skipCheckByte(byte var1) throws IOException {
      byte var2;
      if ((var2 = this.readByte()) != var1) {
         throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", var1, var2));
      }
   }

   public final String readNulEndedString(int var1, boolean var2) throws IOException {
      StringBuilder var3 = new StringBuilder(16);

      while (var1-- != 0) {
         short var4 = this.readShort();
         this.end += 2;
         if (var4 == 0) {
            break;
         }

         var3.append((char)var4);
      }

      if (var2) {
         this.skipBytes(var1 << 1);
         this.end += var1 << 1;
      }

      return var3.toString();
   }

   public final int read(byte[] var1, int var2, int var3) throws IOException {
      return this.dis.read(var1, var2, var3);
   }

   public final float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   public final void readFully(byte[] var1) throws IOException {
      this.dis.readFully(var1, 0, var1.length);
   }

   public final void readFully(byte[] var1, int var2, int var3) throws IOException {
      this.dis.readFully(var1, var2, var3);
   }

   public final int readInt() throws IOException {
      this.dis.readFully(this.work, 0, 4);
      return this.work[3] << 24 | (this.work[2] & 0xFF) << 16 | (this.work[1] & 0xFF) << 8 | this.work[0] & 0xFF;
   }

   public final String readLine() throws IOException {
      return this.dis.readLine();
   }

   public final long readLong() throws IOException {
      this.dis.readFully(this.work, 0, 8);
      return (long)this.work[7] << 56
         | ((long)this.work[6] & 255L) << 48
         | ((long)this.work[5] & 255L) << 40
         | ((long)this.work[4] & 255L) << 32
         | ((long)this.work[3] & 255L) << 24
         | ((long)this.work[2] & 255L) << 16
         | ((long)this.work[1] & 255L) << 8
         | (long)this.work[0] & 255L;
   }

   public final short readShort() throws IOException {
      this.dis.readFully(this.work, 0, 2);
      return (short)((this.work[1] & 255) << 8 | this.work[0] & 255);
   }

   public final String readUTF() throws IOException {
      return this.dis.readUTF();
   }

   public final int readUnsignedByte() throws IOException {
      return this.dis.readUnsignedByte();
   }

   public final int readUnsignedShort() throws IOException {
      this.dis.readFully(this.work, 0, 2);
      return (this.work[1] & 0xFF) << 8 | this.work[0] & 0xFF;
   }

   public final int skipBytes(int var1) throws IOException {
      return this.dis.skipBytes(var1);
   }

   public final String readNullEndedString(int var1, boolean var2) throws IOException {
      StringBuilder var3 = new StringBuilder(16);

      short var4;
      while (var1-- != 0 && (var4 = this.readShort()) != 0) {
         var3.append((char)var4);
      }

      if (var2) {
         this.skipBytes(var1 << 1);
      }

      return var3.toString();
   }
}
