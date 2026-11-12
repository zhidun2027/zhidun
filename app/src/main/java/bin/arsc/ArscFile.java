package bin.arsc;

import bin.io.ZInput;
import bin.io.ZOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ArscFile {
   private short typeTableNext;
   private ZInput mIn;
   private int packageCount;
   private StringDecoder mTableStrings;
   private ArrayList constantPool = new ArrayList();
   private byte[] PackageBytes;
   private Header mHeader;

   public static ArscFile decodeArsc(InputStream var0) throws IOException {
      ArscFile var1;
      (var1 = new ArscFile(new ZInput(var0))).readTable();
      return var1;
   }

   public static byte[] encodeArsc(ArscFile var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      write(new ZOutput(var1), var0);
      byte[] var2 = var1.toByteArray();
      var1.close();
      return var2;
   }

   private static void write(ZOutput var0, ArscFile var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ZOutput var3;
      (var3 = new ZOutput(var2)).writeInt(var1.packageCount);
      var1.mTableStrings.write(list2Strings(var1.constantPool), var3);
      var3.writeFully(var1.PackageBytes);
      var0.writeShort((short)2);
      var0.writeShort(var1.typeTableNext);
      var0.writeInt(var2.size() + 8);
      var0.writeFully(var2.toByteArray());
      var2.close();
   }

   public String getString(int var1) {
      return (String)this.constantPool.get(var1);
   }

   public int getStringSize() {
      return this.constantPool.size();
   }

   public void setString(int var1, String var2) {
      this.constantPool.set(var1, var2);
   }

   private ArscFile(ZInput var1) throws IOException {
      this.mIn = var1;
   }

   private void readTable() throws IOException {
      int var1 = this.mIn.readShort();
      this.checkChunk((short)var1, (short)2);
      this.typeTableNext = this.mIn.readShort();
      this.mIn.readInt();
      this.packageCount = this.mIn.readInt();
      this.mTableStrings = StringDecoder.read(this.mIn);
      var1 = this.mTableStrings.getSize();

      for (int var2 = 0; var2 < var1; var2++) {
         this.constantPool.add(this.mTableStrings.getString(var2));
      }

      this.readPackageBytes();
   }

   private void readPackageBytes() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[2048];

      int var3;
      while ((var3 = this.mIn.read(var2, 0, 2048)) != -1) {
         var1.write(var2, 0, var3);
      }

      this.mIn.close();
      this.PackageBytes = var1.toByteArray();
      var1.close();
   }

   Header nextChunk() throws IOException {
      return this.mHeader = Header.read(this.mIn);
   }

   void checkChunkType(int var1) throws IOException {
      if (this.mHeader.type != var1) {
         throw new IOException(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x", var1, this.mHeader.type));
      }
   }

   void nextChunkCheckType(int var1) throws IOException {
      this.nextChunk();
      this.checkChunkType(var1);
   }

   private void checkChunk(short var1, short var2) throws IOException {
      if (var1 != var2) {
         throw new IOException(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x", var2, var1));
      }
   }

   private static String[] list2Strings(ArrayList var0) {
      String[] var1 = new String[var0.size()];
      return (String[])var0.toArray(var1);
   }
}
