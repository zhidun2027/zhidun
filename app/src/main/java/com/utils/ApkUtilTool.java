package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class ApkUtilTool extends k {
   private FileChannel ch;
   private FileChannel fc;

   public void FixEncryptedEntry(File var1, File var2) throws IOException {
      this.changEntry(var1, var2, true);
   }

   public void FixEncryptedEntry(String var1, String var2) throws IOException {
      this.FixEncryptedEntry(new File(var1), new File(var2));
   }

   public void ChangToEncryptedEntry(File var1, File var2) throws IOException {
      this.changEntry(var1, var2, false);
   }

   public void ChangToEncryptedEntry(String var1, String var2) throws IOException {
      this.ChangToEncryptedEntry(new File(var1), new File(var2));
   }

   private void changEntry(File var1, File var2, boolean var3) throws IOException {
      FileInputStream var6 = new FileInputStream(var1);
      FileOutputStream var7 = new FileOutputStream(var2);
      byte[] var4 = new byte[10240];

      int var5;
      while ((var5 = var6.read(var4)) != -1) {
         var7.write(var4, 0, var5);
      }

      this.ch = var6.getChannel();
      this.fc = var7.getChannel();
      this.changEntry(var3);
      this.ch.close();
      this.fc.close();
      var6.close();
      var7.close();
   }

   private void changEntry(boolean var1) throws IOException {
      a var2;
      if ((var2 = this.findEND()).c > var2.f) {
         zerror("invalid END header (bad central directory size)");
      }

      long var3;
      if ((var3 = var2.f - var2.c) - var2.d < 0L) {
         zerror("invalid END header (bad central directory offset)");
      }

      byte[] var5 = new byte[(int)(var2.c + 22L)];
      if (this.readFullyAt(var5, 0, (long)var5.length, var3) != var2.c + 22L) {
         zerror("read CEN tables failed");
      }

      int var12 = 0;
      int var6 = var5.length - 22;

      while (var12 < var6) {
         if (CENSIG(var5, var12) != CENSIG) {
            zerror("invalid CEN header (bad signature)");
         }

         int var7 = CENHOW(var5, var12);
         int var8 = CENNAM(var5, var12);
         int var9 = CENEXT(var5, var12);
         int var10 = CENCOM(var5, var12);
         if (var1) {
            if ((CEN(var5, var12) & 1) != 0) {
               byte[] var11 = Arrays.copyOfRange(var5, var12 + 46, var12 + 46 + var8);
               System.out.println("Found the encrypted entry : " + new String(var11) + ", fix...");
               var5[var12 + 8] = (byte)(var5[var12 + 8] & 254);
            }
         } else if ((CEN(var5, var12) & 1) == 0) {
            byte[] var13 = Arrays.copyOfRange(var5, var12 + 46, var12 + 46 + var8);
            System.out.println("Chang the entry : " + new String(var13) + ", Encrypted...");
            var5[var12 + 8] = (byte)(var5[var12 + 8] | 1);
         }

         if (var7 != 0 && var7 != 8) {
            zerror("invalid CEN header (unsupported compression method: " + var7 + ")");
         }

         if (var12 + 46 + var8 > var6) {
            zerror("invalid CEN header (bad header size)");
         }

         var12 += var8 + 46 + var9 + var10;
      }

      this.writeFullyAt(var5, 0, (long)var5.length, var3);
      if (var12 + 22 != var5.length) {
         zerror("invalid CEN header (bad header size)");
      }
   }

   final long readFullyAt(byte[] var1, int var2, long var3, long var5) throws IOException {
      ByteBuffer var7;
      (var7 = ByteBuffer.wrap(var1)).position(var2);
      ((Buffer)var7).limit((int)((long)var2 + var3));
      return this.readFullyAt(var7, var5);
   }

   private final long readFullyAt(ByteBuffer var1, long var2) throws IOException {
      System.err.println("FileChannel pos : ".concat(String.valueOf(var2)));
      synchronized (this.ch) {
         return (long)this.ch.position(var2).read(var1);
      }
   }

   final long writeFullyAt(byte[] var1, int var2, long var3, long var5) throws IOException {
      ByteBuffer var7;
      (var7 = ByteBuffer.wrap(var1)).position(var2);
      ((Buffer)var7).limit((int)((long)var2 + var3));
      return this.writeFullyAt(var7, var5);
   }

   private final long writeFullyAt(ByteBuffer var1, long var2) throws IOException {
      synchronized (this.fc) {
         return (long)this.fc.position(var2).write(var1);
      }
   }

   private a findEND() throws IOException {
      byte[] var1 = new byte[128];
      long var2;
      long var6 = ((var2 = this.ch.size()) - 65557L > 0L ? var2 - 65557L : 0L) - 106L;
      System.err.println(" -----------------  findEND");

      for (long var8 = var2 - 128L; var8 >= var6; var8 -= 106L) {
         int var4 = 0;
         if (var8 < 0L) {
            var4 = (int)(-var8);
            Arrays.fill(var1, 0, var4, (byte)0);
         }

         int var5 = 128 - var4;
         if (this.readFullyAt(var1, var4, (long)var5, var8 + (long)var4) != (long)var5) {
            zerror("zip END header not found 01");
         }

         for (int var21 = 106; var21 >= 0; var21--) {
            if (var1[var21] == 80
               && var1[var21 + 1] == 75
               && var1[var21 + 2] == 5
               && var1[var21 + 3] == 6
               && var8 + (long)var21 + 22L + (long)ENDCOM(var1, var21) == var2) {
               var1 = Arrays.copyOfRange(var1, var21, var21 + 22);
               a var20;
               (var20 = new a()).a = ENDSUB(var1);
               var20.b = ENDTOT(var1);
               var20.c = ENDSIZ(var1);
               var20.d = ENDOFF(var1);
               var20.e = ENDCOM(var1);
               var20.f = var8 + (long)var21;
               if (var20.c == 4294967295L || var20.d == 4294967295L || var20.b == 65535) {
                  var1 = new byte[20];
                  if (this.readFullyAt(var1, 0, 20L, var20.f - 20L) != 20L) {
                     return var20;
                  }

                  long var15 = ZIP64_LOCOFF(var1);
                  var1 = new byte[56];
                  if (this.readFullyAt(var1, 0, 56L, var15) != 56L) {
                     return var20;
                  }

                  var20.c = ZIP64_ENDSIZ(var1);
                  var20.d = ZIP64_ENDOFF(var1);
                  var20.b = (int)ZIP64_ENDTOT(var1);
                  var20.f = var15;
               }

               System.err.println(" -----------------  findEND sucess");
               return var20;
            }
         }
      }

      System.err.println(" -----------------  findEND error");
      zerror("zip END header not found 02");
      return null;
   }

   static void zerror(String var0) throws IOException {
      System.err.println("error: ".concat(String.valueOf(var0)));
   }

   public void encry512(String var1, String var2) throws IOException {
      this.encry512(new File(var1), new File(var2));
   }

   public void encry512(File var1, File var2) throws IOException {
      if (var1.isFile()) {
         FileInputStream var3 = new FileInputStream(var1);
         FileOutputStream var12 = new FileOutputStream(var2);
         long var6 = 0L;
         long var8 = var1.length() - 512L;
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         int var10;

         while ((var10 = var3.read()) != -1) {
            var6++;
            var12.write(var10);
            if (var6 >= var8) {
               var4.write(var10);
            }
         }

         byte[] var11 = var4.toByteArray();
         var12.write(var11);
         var3.close();
         var12.flush();
         var12.close();
         System.out.println("-------拼接512字节结束---------");
      }
   }

   public static void main(String[] var0) throws IOException {
      new ApkUtilTool();
      File var3 = new File("C:/Users/user/Desktop/testdir/result/2019_shell_sign_r.apk");
      File var1 = new File("C:/Users/user/Desktop/testdir/target/2019_shell_sign_r_z.apk");

      try {
         new ApkUtilTool().ChangToEncryptedEntry(var3, var1);
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   public static void main3(String[] var0) throws IOException {
      File var9 = new File("C:/Users/user/Desktop/test.apk");
      FileInputStream var1 = new FileInputStream(var9);
      FileOutputStream var2 = new FileOutputStream(new File("C:/Users/user/Desktop/test2.apk"));
      long var5 = 0L;
      long var7 = var9.length() - 512L;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      int var10;

      while ((var10 = var1.read()) != -1) {
         var5++;
         var2.write(var10);
         if (var5 >= var7) {
            var3.write(var10);
         }
      }

      byte[] var11 = var3.toByteArray();
      var2.write(var11);
      var1.close();
      var2.flush();
      var2.close();
   }

   public static void main2(String[] var0) throws IOException {
      ApkUtilTool var4 = new ApkUtilTool();
      String var1 = "C:/Users/user/Desktop/2018-09-13.apk";
      String var2 = "C:/Users/user/Desktop/2018-09-13_en.apk";

      try {
         var4.ChangToEncryptedEntry(var1, var2);
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }
}
