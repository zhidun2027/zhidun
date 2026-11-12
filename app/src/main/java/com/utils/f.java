package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class f {
   private static String a = "APPLICATION_CLASS_NAME";
   private static String b = "ACTIVITY_MAIN_CLASS_NAME";
   private String c;
   private String d;
   private String e;

   public final String a(File var1, File var2, String var3, boolean var4) throws Exception {
      DocumentBuilderFactory var5 = DocumentBuilderFactory.newInstance();
      if (var1.isFile() && var2.isFile()) {
         DocumentBuilder var6;
         Element var10;
         if ((var10 = b((var6 = var5.newDocumentBuilder()).parse(var1))) == null) {
            return null;
         } else {
            String var8 = var10.getAttribute("android:name");
            var5.setAttribute("http://www.oracle.com/xml/jaxp/properties/maxXMLNameLimit", "10000");
            var5.setAttribute("http://www.oracle.com/xml/jaxp/properties/maxXMLNameLimit", "20000");
            Document var7;
            String var11 = ((Element)(var7 = var6.parse(var2)).getElementsByTagName("manifest").item(0)).getAttribute("package");
            Element var12;
            String var20 = (var12 = b(var7)).getAttribute("android:name");
            var12.setAttribute("android:name", var8);
            var12.getAttribute("android:extractNativeLibs");
            var12.getAttribute("android:debuggable");
            if (var4) {
               var12.setAttribute("android:debuggable", "false");
            }

            var12.setAttribute("android:extractNativeLibs", "true");
            Element var13;
            Node var17 = (var13 = a(var7)).getParentNode();
            String var14 = var13.getAttribute("android:name");
            Element var21;
            (var21 = var7.createElement("meta-data")).setAttribute("android:name", a);
            var21.setAttribute("android:value", var20);
            var17.appendChild(var21);
            Element var22;
            (var22 = var7.createElement("meta-data")).setAttribute("android:name", b);
            var22.setAttribute("android:value", var14);
            var17.appendChild(var22);
            if (new File(var3).isFile()) {
               new File(var3).delete();
            }

            Document var18 = var7;
            var3 = var3;
            TransformerFactory var23 = TransformerFactory.newInstance();

            try {
               Transformer var24;
               (var24 = var23.newTransformer()).setOutputProperty("indent", "yes");
               DOMSource var25;
               (var25 = new DOMSource()).setNode(var18);
               StreamResult var19 = new StreamResult();
               FileOutputStream var16 = new FileOutputStream(var3);
               var19.setOutputStream(var16);
               var24.transform(var25, var19);
               var16.flush();
               var16.close();
            } catch (Exception var9) {
               var9.printStackTrace();
            }

            this.c = var11;
            this.d = var20;
            this.e = var14;
            return var11;
         }
      } else {
         throw new FileNotFoundException(var1 + "\n" + var2 + "\n" + var3);
      }
   }

   private static Element a(Document var0) {
      NodeList var11 = var0.getElementsByTagName("application");

      for (int var1 = 0; var1 < var11.getLength(); var1++) {
         for (Node var2 = ((Element)var11.item(var1)).getFirstChild(); var2 != null; var2 = var2.getNextSibling()) {
            if (var2.getNodeType() == 1) {
               Element var3;
               Element var10000;
               if ((var3 = var3 = (Element)var2) == null) {
                  var10000 = null;
               } else {
                  var3.getAttribute("android:name");
                  var3.getNodeName();
                  var3.getAttributes();
                  var3.getNodeValue();
                  var3.getNextSibling();
                  NodeList var4;
                  int var5 = (var4 = var3.getElementsByTagName("intent-filter")).getLength();
                  int var6 = 0;

                  label46:
                  while (true) {
                     if (var6 >= var5) {
                        var10000 = null;
                        break;
                     }

                     NodeList var7;
                     int var8 = (var7 = ((Element)var4.item(var6)).getElementsByTagName("action")).getLength();

                     for (int var9 = 0; var9 < var8; var9++) {
                        String var10;
                        if ((var10 = ((Element)var7.item(var9)).getAttribute("android:name")) != null && var10.equals("android.intent.action.MAIN")) {
                           var10000 = var3;
                           break label46;
                        }
                     }

                     var6++;
                  }
               }

               var3 = var10000;
               if (var10000 != null) {
                  var3.getAttribute("android:name");
                  return var3;
               }
            }
         }
      }

      return null;
   }

   private static Element b(Document var0) {
      NodeList var1 = var0.getElementsByTagName("application");
      if (0 < var1.getLength()) {
         Element var2;
         (var2 = (Element)var1.item(0)).getAttribute("android:name");
         return var2;
      } else {
         return null;
      }
   }

   public final String a() {
      return this.c;
   }

   public final String b() {
      return this.d;
   }

   public final String c() {
      return this.e;
   }
}
