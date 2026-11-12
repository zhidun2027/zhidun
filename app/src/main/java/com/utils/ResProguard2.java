package com.utils;

import bin.arsc.ArscFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ResProguard2 {
   public static void main(String[] var0) throws Exception {
      File var2 = new File("C:/Users/user/Desktop/testdir/1/app-debug.apk");
      File var1 = new File("C:/Users/user/Desktop/testdir/1/app-debug_temp.apk");
      a(var2, var1);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static File a(File var0, File var1) throws Exception {
      File var2 = null;
      FileOutputStream var3 = null;
      boolean var14 = false /* VF: Semaphore variable */;

      label234: {
         try {
            var14 = true;
            HashMap var4 = new HashMap();
            FileInputStream var5 = new FileInputStream(var0);
            ZipInputStream var6 = new ZipInputStream(var5);
            var4.clear();

            ZipEntry var7;
            byte[] var8;
            for (var8 = null; (var7 = var6.getNextEntry()) != null; var6.closeEntry()) {
               if (!var7.isDirectory()) {
                  String var24;
                  if ((var24 = var7.getName()).startsWith("res/")) {
                     var4.put(var24, var24);
                  }

                  byte[] var9 = new byte[4096];
                  if (var24.equals("resources.arsc")) {
                     ByteArrayOutputStream var29 = new ByteArrayOutputStream();

                     int var10;
                     while ((var10 = var6.read(var9)) > 0) {
                        var29.write(var9, 0, var10);
                     }

                     var8 = var29.toByteArray();
                  }
               }
            }

            var6.close();
            var5.close();
            ArscFile var25 = ArscFile.decodeArsc(new ByteArrayInputStream(var8));
            h var36 = new h((byte)0);

            for (int var30 = 0; var30 < var25.getStringSize(); var30++) {
               String var37;
               if ((var37 = var25.getString(var30)).startsWith("res/") && var4.containsKey(var37)) {
                  String var19 = "r/" + var36.a();
                  if (var37.toLowerCase().endsWith(".9.png")) {
                     var19 = var19 + ".9.png";
                  } else {
                     int var21;
                     if ((var21 = var37.lastIndexOf(46)) != -1) {
                        var19 = var19 + var37.substring(var21);
                     }
                  }

                  var36.b();
                  var25.setString(var30, var19);
                  var4.put(var37, var19);
               }
            }

            String var31;
            int var38;
            if ((var38 = (var31 = var0.getName()).lastIndexOf(46)) != -1) {
               var2 = new File(var0.getParentFile(), var31.substring(0, var38) + "_mapping.txt");
               new StringBuilder(String.valueOf(var31.substring(0, var38))).append("_res").append(var31.substring(var38));
            } else {
               var2 = new File(var0.getParentFile(), var31 + "_mapping.txt");
               new StringBuilder(String.valueOf(var31)).append("_res");
            }

            ZipOutputStream var20 = new ZipOutputStream(new FileOutputStream(var1));
            ZipEntry var22;
            (var22 = new ZipEntry("resources.arsc")).setMethod(0);
            byte[] var26 = ArscFile.encodeArsc(var25);
            CRC32 var32;
            (var32 = new CRC32()).update(var26);
            var22.setSize((long)var26.length);
            var22.setCompressedSize((long)var26.length);
            var22.setCrc(var32.getValue());
            var20.putNextEntry(var22);
            var20.write(var26);
            var20.closeEntry();
            ZipInputStream var18 = new ZipInputStream(new FileInputStream(var0));
            var3 = new FileOutputStream(var2);
            byte[] var23 = new byte[8192];

            while ((var7 = var18.getNextEntry()) != null) {
               if (var7.isDirectory()) {
                  var20.closeEntry();
               } else {
                  String var28;
                  if (!(var28 = var7.getName()).equals("resources.arsc") && !var28.startsWith("META-INF/")) {
                     String var33 = var28;
                     if (var4.containsKey(var28)) {
                        String var34 = (String)var4.get(var28);
                        var3.write((var28 + " -> " + var34 + "\r\n").getBytes());
                        var33 = var34;
                     }

                     var20.putNextEntry(new ZipEntry(var33));

                     int var35;
                     while ((var35 = var18.read(var23)) != -1) {
                        var20.write(var23, 0, var35);
                     }

                     var20.closeEntry();
                     var18.closeEntry();
                  }
               }
            }

            var18.close();
            var20.close();
            var3.close();
            var14 = false;
            break label234;
         } catch (Throwable var16) {
            var16.printStackTrace();

            try {
               if (var3 != null) {
                  var3.close();
               }
            } catch (IOException var15) {
            }

            if (var1.exists()) {
               var1.delete();
               var14 = false;
            } else {
               var14 = false;
            }
         } finally {
            if (var14) {
               if (var2 != null && var2.exists()) {
                  var2.delete();
               }
            }
         }

         if (var2 != null && var2.exists()) {
            var2.delete();
         }

         return var1;
      }

      if (var2.exists()) {
         var2.delete();
      }

      return var1;
   }
}
