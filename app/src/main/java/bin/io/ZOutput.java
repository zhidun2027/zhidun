package bin.io;

import java.io.IOException;
import java.io.OutputStream;

public final class ZOutput {
   private int written = 0;
   private OutputStream dos;

   public ZOutput(OutputStream var1) throws IOException {
      this.dos = var1;
   }

   public final void writeShort(short var1) throws IOException {
      this.dos.write(var1 & 255);
      this.written++;
      this.dos.write(var1 >>> 8 & 0xFF);
      this.written++;
   }

   public final void close() throws IOException {
      this.dos.close();
   }

   public final int size() throws IOException {
      return this.written;
   }

   public final void writeChar(char var1) throws IOException {
      this.dos.write(var1 & 255);
      this.written++;
      this.dos.write(var1 >>> '\b' & 0xFF);
      this.written++;
   }

   public final void writeCharArray(char[] var1) throws IOException {
      for (char var5 : var1) {
         this.writeChar(var5);
      }
   }

   public final void writeByte(byte var1) throws IOException {
      this.dos.write(var1);
      this.written++;
   }

   public final void writeByte(int var1) throws IOException {
      this.dos.write(var1);
      this.written++;
   }

   public final void writeFully(byte[] var1) throws IOException {
      this.dos.write(var1, 0, var1.length);
      this.written += var1.length;
   }

   public final void writeFully(byte[] var1, int var2, int var3) throws IOException {
      this.dos.write(var1, var2, var3);
      this.written += var3;
   }

   public final void writeInt(int var1) throws IOException {
      this.dos.write(var1 & 0xFF);
      this.written++;
      this.dos.write(var1 >>> 8 & 0xFF);
      this.written++;
      this.dos.write(var1 >>> 16 & 0xFF);
      this.written++;
      this.dos.write(var1 >>> 24);
      this.written++;
   }

   public final void writeIntArray(int[] var1, int var2, int var3) throws IOException {
      while (var2 < var3) {
         this.writeInt(var1[var2]);
         var2++;
      }
   }

   public final void writeIntArray(int[] var1) throws IOException {
      this.writeIntArray(var1, 0, var1.length);
   }

   public final void writeNulEndedString(String var1, int var2, boolean var3) throws IOException {
      char[] var5 = var1.toCharArray();
      int var4 = 0;

      while (var4 < var5.length && var2 != 0) {
         this.writeChar(var5[var4++]);
         var2--;
      }

      if (var3) {
         for (int var6 = 0; var6 < var2 << 1; var6++) {
            this.dos.write(0);
            this.written++;
         }
      }
   }
}
