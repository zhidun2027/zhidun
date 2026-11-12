package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public final class e {
   private List a = new ArrayList();
   private static KeyStore b;

   public static j a(String var0) throws Exception {
      if (b == null) {
         return null;
      } else {
         j var1;
         (var1 = new j()).a(var0);
         X509Certificate var2;
         Certificate var7;
         Principal var3 = (var2 = (X509Certificate)(var7 = b.getCertificate(var0))).getIssuerDN();
         var2.getSubjectX500Principal();
         String var10 = var3.getName();
         var2.getNonCriticalExtensionOIDs();
         Date var4 = var2.getNotAfter();
         Date var5 = var2.getNotBefore();
         SimpleDateFormat var6 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
         var1.h(var6.format(var4));
         var1.i(var6.format(var5));
         var2.getSerialNumber();
         var2.getSigAlgName();
         var2.getSigAlgOID();
         PublicKey var8 = var7.getPublicKey();
         var1.j(var8.toString());
         HashMap var9 = new HashMap();
         String[] var15;
         int var14 = (var15 = var10.split(",")).length;

         for (int var13 = 0; var13 < var14; var13++) {
            String var11;
            String[] var12 = (var11 = var15[var13]).split("=");
            var9.put(var12[0], var12[1]);
         }

         var1.a(var2.getVersion());
         var1.b((String)var9.get("CN"));
         var1.c((String)var9.get(" OU"));
         var1.d((String)var9.get(" O"));
         var1.e((String)var9.get(" L"));
         var1.f((String)var9.get(" ST"));
         var1.g((String)var9.get(" C"));
         return var1;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static KeyStore a(String var0, String var1) {
      String[] var2 = new String[]{"JKS", "PKCS12", "JCEKS"};
      KeyStore var3 = null;
      String[] var5 = var2;

      for (int var4 = 0; var4 < 3; var4++) {
         String var17 = var5[var4];

         try {
            var3 = KeyStore.getInstance(var17);

            try (FileInputStream var7 = new FileInputStream(var0)) {   // 还原反编译拆坏的 try-with-resources
               var3.load(var7, var1.toCharArray());
            }

            System.out.println("检测到密钥库类型: ".concat(String.valueOf(var17)));
            b = var3;
            return var3;
         } catch (Exception var16) {
         }
      }

      System.err.println("无法检测密钥库类型");
      return var3;
   }

   public static String a(File var0, String var1, String var2, String var3) throws Exception {
      if (var0.isFile() && var1 != null) {
         try {
            KeyStore var5 = a(var0.getAbsolutePath(), var1);
            StringBuffer var8;
            if (var2 == null) {
               var8 = new StringBuffer();
            } else {
               var8 = a(var5, var2, var3);
            }

            System.out.println("\nKeystore 中的所有别名:");
            List<String> var6;
            if ((var6 = (List<String>)a(var5)) != null) {
               for (String var7 : var6) {
                  var8.append("\n- ".concat(String.valueOf(var7)));
               }
            }

            return var8.toString();
         } catch (Exception var4) {
            var4.printStackTrace();
            return "";
         }
      } else {
         return "";
      }
   }

   public static List a(KeyStore var0) throws Exception {
      if (var0 == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList();
         Enumeration var2 = var0.aliases();

         while (var2.hasMoreElements()) {
            var1.add((String)var2.nextElement());
         }

         return var1;
      }
   }

   private static StringBuffer a(KeyStore var0, String var1, String var2) throws Exception {
      StringBuffer var3;
      (var3 = new StringBuffer()).append("\n正在分析别名: ".concat(String.valueOf(var1)));
      if (!var0.containsAlias(var1)) {
         var3.append("\n错误: 别名 '" + var1 + "' 不存在于 keystore 中");
         return var3;
      } else {
         Certificate[] var4;
         if ((var4 = var0.getCertificateChain(var1)) != null && var4.length > 0) {
            var3.append("\n证书链信息:");

            for (int var5 = 0; var5 < var4.length; var5++) {
               var3.append("\n证书 #" + (var5 + 1) + ":");
               var3.append(b((X509Certificate)var4[var5]));
            }
         } else {
            System.out.println("\n该别名没有关联的证书链");
         }

         Certificate var10;
         if ((var10 = var0.getCertificate(var1)) != null) {
            var3.append("\n单独证书信息:");
            var3.append(b((X509Certificate)var10));
            var3.append("\n");
            var3.append("\n");
            String[] var7 = a((X509Certificate)var10);
            var3.append("\n指纹 SHA-1:\n" + var7[0]);
            var3.append("\n\n指纹 SHA-256:\n" + var7[1]);
            var3.append("\n----------------------------------");
         }

         if (var2 == null) {
            return var3;
         } else {
            if (var0.isKeyEntry(var1)) {
               System.out.println("\n私钥信息:");
               Key var8;
               if ((var8 = var0.getKey(var1, var2.toCharArray())) instanceof PrivateKey) {
                  PrivateKey var6 = (PrivateKey)var8;
                  var3.append("\n算法: " + var6.getAlgorithm());
                  var3.append("\n格式: " + var6.getFormat());
               }
            }

            PublicKey var9 = var0.getCertificate(var1).getPublicKey();
            var3.append("\n公钥信息:");
            var3.append("\n算法: " + var9.getAlgorithm());
            var3.append("\n格式: " + var9.getFormat());
            var3.append("\nBase64 编码: \n" + Base64.getEncoder().encodeToString(var9.getEncoded()));
            return var3;
         }
      }
   }

   public static String[] a(KeyStore var0, String var1) throws Exception {
      return a((X509Certificate)var0.getCertificate(var1));
   }

   private static String[] a(X509Certificate var0) throws Exception {
      String[] var1;
      (var1 = new String[2])[0] = a("SHA-1", var0);
      var1[1] = a("SHA-256", var0);
      return var1;
   }

   private static String b(X509Certificate var0) throws Exception {
      StringBuffer var1;
      (var1 = new StringBuffer()).append("\n主题: " + var0.getSubjectDN());
      var1.append("\n颁发者: " + var0.getIssuerDN());
      var1.append("\n序列号: " + var0.getSerialNumber().toString(16));
      var1.append("\n有效期从: " + var0.getNotBefore());
      var1.append("\n有效期至: " + var0.getNotAfter());
      var1.append("\n签名算法: " + var0.getSigAlgName());
      return var1.toString();
   }

   private static String a(String var0, X509Certificate var1) throws Exception {
      if (var1 == null) {
         return null;
      } else {
         byte[] var5 = MessageDigest.getInstance(var0).digest(var1.getEncoded());
         StringBuilder var7 = new StringBuilder();

         for (byte var6 : var5) {
            var7.append(String.format("%02X:", var6));
         }

         return var7.substring(0, var7.length() - 1);
      }
   }
}
