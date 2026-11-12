package com.frame;

import com.utils.ApkUtilTool;
import com.utils.FileUtils;
import com.utils.ResProguard2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JOptionPane;

public class SignShellUtils {
   private String a;
   private String b;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private y h;
   private String i;
   private String j;
   private int k;
   private boolean l = false;
   private sun.b m = new sun.b();
   private ApkUtilTool n;
   private com.utils.f o;
   private File p;
   private String q;
   private a r;

   public final File a(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      boolean var8,
      boolean var9,
      boolean var10,
      Map var11,
      boolean var12
   ) throws Exception {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.p = new File(var1);
      this.i = this.p.getParent();
      this.q = this.p.getName().substring(0, this.p.getName().lastIndexOf("."));
      this.j = System.getProperty("user.dir");
      this.l = false;
      return this.a(var8, var9, var10, var11, false);
   }

   public final File a(File var1, boolean var2) throws Exception {
      String var8;
      if (var1 != null && var1.isFile()) {
         var8 = var1.getAbsolutePath();
      } else {
         String var7 = this.a;
         if (b(this.a)) {
            this.c("没找到路径");
            return null;
         }

         if (!new File(var7).isFile()) {
            return null;
         }

         var8 = var7;
      }

      if (b(this.b) || !new File(this.b).isFile()) {
         this.c("没有找到签名文件");
      } else if (b(this.c)) {
         this.c("没有找到密钥");
      } else if (b(this.e)) {
         this.c("没有找到别名密钥");
      } else if (b(this.d)) {
         this.c("没有找到别名");
      } else {
         this.k = 800;
         File var9;
         if ((var9 = new File(var8)).isFile()) {
            String var4 = var9.getParent();
            String var5 = var9.getName().substring(0, var9.getName().lastIndexOf("."));
            this.c("\n资源混淆");
            this.c("\n" + var9.getAbsolutePath());
            this.c("\n  ↓  ");
            this.c("\n  ↓  ");
            this.c("\n  ↓  ");
            this.c("\n" + var4 + "/" + var5 + "_r.apk");
            var5 = var5.replace("_un_sign", "");
            var4 = new File(var4, var5 + "_r.apk").getPath();
            if (new File(var4).isFile()) {
               new File(var4).delete();
            }

            this.c("\n".concat(String.valueOf(var4)));
            File var6 = new File(var9.getParentFile().getParentFile(), "target");
            File var13;
            if ((var13 = new File(var6, var5 + "_res.apk")).isFile()) {
               var13.delete();
            }

            if (!var13.getParentFile().isDirectory()) {
               var13.getParentFile().mkdir();
            }

            File var10;
            if ((var10 = ResProguard2.a(var9, var13)) != null && var10.isFile()) {
               File var14 = new File(var6, "temp.apk");
               if (var2) {
                  if (!a(new File(this.j + "/tools/" + com.utils.i.ZIPALIGN), var10, var14) || !var14.isFile()) {
                     this.c("\n4字节对齐失败，可以重试");
                  }
               } else {
                  var14 = var10;
               }

               if (!this.a(var14, new File(var4), new File(this.b), this.c, this.d, this.e)) {
                  this.c("\n资源混淆失败,可以重试");
               }

               if (var10.isFile()) {
                  var10.delete();
               }

               if (var14.isFile()) {
                  var14.delete();
               }

               if (new File(var4).isFile()) {
                  this.c("\n     签名完成");
                  this.c("\n     安装文件");
                  this.c("\n       ↓  ");
                  this.c("\n       ↓  ");
                  this.c("\n       ↓  ");
                  this.c("\n".concat(String.valueOf(var4)));
                  return new File(var4);
               }

               this.c("\n资源混淆失败,可以重试");
            } else {
               this.c("\n资源混淆失败，可以重试");
            }
         }
      }

      return null;
   }

   public final File a(File var1) throws Exception {
      this.k = 800;
      if (var1.isFile()) {
         long var4;
         if ((var4 = var1.length() / 1024L / 2024L) < 10L) {
            this.k = 600;
         } else if (var4 < 15L) {
            this.k = 1200;
         } else if (var4 < 20L) {
            this.k = 1500;
         } else if (var4 < 30L) {
            this.k = 3000;
         } else if (var4 < 70L) {
            this.k = 4500;
         } else if (var4 < 100L) {
            this.k = 5000;
         } else {
            this.k = 5500;
         }

         if (this.n == null) {
            this.n = new ApkUtilTool();
         }

         String var3 = var1.getParent();
         String var10 = var1.getName();
         File var5 = var1.getParentFile().getParentFile();
         var5 = new File(var5, "target");
         String var11 = var10.substring(0, var10.indexOf("."));
         String var6 = var3 + File.separator + var11 + "_z.apk";
         File var7 = new File(var5, var11 + "_z.apk");
         File var8 = new File(var5, var11 + "_z_z.apk");
         if (new File(var6).isFile()) {
            new File(var6).delete();
         }

         if (var8.isFile()) {
            var8.delete();
         }

         if (var7.isFile()) {
            var7.delete();
         }

         this.c("\n--------------------  app反反编译  --------------------");
         this.c("\n" + var1.getAbsolutePath());
         this.c("\n\t\t↓\t\t");
         this.c("\n\t\t↓\t\t");
         this.c("\n\t\t↓\t\t");
         this.c("\n".concat(String.valueOf(var6)));
         this.c("\n--------------------  app 512 字节拼接  --------------------");
         this.c("\n\t\t↓\t\t");
         this.c("\n\t\t↓\t\t");
         this.c("\n\t\t↓\t\t");
         this.c("\n".concat(String.valueOf(var8)));
         File var13 = new File(var3, var11 + "_z.apk");
         File var9 = new File(var3, var11 + "_z_z.apk");
         if (var13.isFile()) {
            var13.delete();
         }

         if (var9.isFile()) {
            var9.delete();
         }

         if (!var5.isDirectory()) {
            var5.mkdir();
         }

         this.n.ChangToEncryptedEntry(var1, var7);
         this.n.encry512(var7, var8);
         var8.getPath();
         File var2 = new File(var5, "_0temp.apk");
         if (var7.isFile()) {
            this.a(var7, var2);
            if (var2.isFile()) {
               var7.delete();
               var2.renameTo(var13);
            }
         }

         if (var8.isFile()) {
            this.a(var8, var2);
            if (var2.isFile()) {
               var8.delete();
               var2.renameTo(var9);
            }
         }

         return var9;
      } else {
         return null;
      }
   }

   private File a(boolean var1, boolean var2, boolean var3, Map var4, boolean var5) throws Exception {
      if (!this.p.isFile()) {
         this.c("\n没有找到apk");
         this.c("\n" + this.p.getPath());
         return null;
      } else if (b(this.c)) {
         this.c("\n没有找到密钥");
         return null;
      } else if (b(this.e)) {
         this.c("\n没有找到别名密钥");
         return null;
      } else if (b(this.d)) {
         this.c("\n没有找到别名");
         return null;
      } else if (!b(this.b) && new File(this.b).isFile()) {
         String var6 = this.i + File.separator + "result";
         String var7 = this.i + File.separator + "source";
         String var8 = this.i + File.separator + "target";
         String var9 = var7 + File.separator + this.q + "_";
         String var10 = var7 + File.separator + this.q + "_shell";
         String var11 = var7 + File.separator + this.q;
         String var12 = var8 + File.separator + this.q + "_un_sign.apk";
         a(new File(var7), this.q);
         a(new File(var8), this.q);
         var7 = var6 + File.separator + this.q + "_shell_sign.apk";
         HashMap var84 = new HashMap();
         this.c("\n编译目录：" + this.i);
         File var13;
         if (!(var13 = new File(this.j, "config.dll")).isFile()) {
            var13 = new File(this.i, "config.dll");
         }

         File var14 = new File(this.i, "tools/" + com.utils.i.APKTOOL);
         File var15 = new File(this.j, "tools/" + com.utils.i.APKTOOL);
         if (!var14.isFile() && !var15.isFile()) {
            this.c("\n未找到-> " + var14.getAbsolutePath());
            this.c("\n   或  ");
            this.c("\n未找到-> " + var15.getAbsolutePath());
            return null;
         } else if (var1 && !new File(this.j, "tools/" + com.utils.i.ZIPALIGN).isFile()) {
            this.c("\n未找到->tools/zipalign.exe");
            return null;
         } else {
            try {
               long var18 = FileUtils.b(var13);
               long var20 = System.currentTimeMillis() - var18;
               System.out.println(var20 / 1000L / 60L / 60L / 24L);
               if (540L < var20 / 1000L / 60L / 60L / 24L) {
                  JOptionPane.showMessageDialog(null, "config.dll 配置文件已过期，可重新下载后使用", "过期提示", 1);
                  return null;
               }
            } catch (IOException var67) {
               var67.printStackTrace();
            }

            if (var15.isFile()) {
               com.utils.i.a = var15.getParentFile();
            } else {
               com.utils.i.a = var14.getParentFile();
            }

            this.c("\n\n日志：");
            long var102;
            if ((var102 = new File(this.a).length() / 1024L / 2024L) < 15L) {
               this.k = 600;
            } else if (var102 < 40L) {
               this.k = 1000;
            } else if (var102 < 80L) {
               this.k = 1500;
            } else {
               this.k = 2000;
            }

            int var17 = this.k;
            if (this.h != null) {
               this.h.a(var17);
            }

            this.c("\n apkTool:");
            this.c("\n " + com.utils.i.a);
            this.c("\n\n---------------  源逆向  ---------------");
            this.c("\n---------------   ↓  ---------------\n");
            if (this.l) {
               return null;
            } else {
               this.c(var9);
               boolean var106;
               if (var5) {
                  var106 = com.utils.i.a(this.a, var9, " -r ");
               } else {
                  var106 = com.utils.i.a(this.a, var9, null);
               }

               this.c("\n逆向完成：".concat(String.valueOf(var9)));
               if (var106 && !this.l) {
                  this.c("\n---------------  解压  ---------------");
                  this.c("\n---------------   ↓  ---------------");
                  this.c("\n---------------   ↓  ---------------");
                  this.c("\n".concat(String.valueOf(var11)));
                  File var100 = new File(this.a);
                  com.utils.j.a(var11, new ZipInputStream(new FileInputStream(var100)));
                  this.c("\n解压完毕：".concat(String.valueOf(var11)));
                  if (this.l) {
                     return null;
                  } else {
                     if (new File(var9 + "/unknown").isDirectory()) {
                        this.c("\n复制文件 unknown");
                        FileUtils.b(var9 + "/unknown", var10 + "/unknown");
                        this.c("\n" + var9 + "/unknown");
                        this.c("\n" + var10 + "/unknown");
                     }

                     File var82;
                     if ((var82 = new File(var9 + "/original")).isDirectory()) {
                        this.c("\n复制文件 unknown");
                        FileUtils.b(var9 + "/original", var10 + "/original");
                        this.c("\n" + var9 + "/original");
                        this.c("\n" + var10 + "/original");
                     }

                     if (this.l) {
                        return null;
                     } else {
                        if (new File(var9 + File.separator + "assets").isDirectory()) {
                           this.c("\n---------------  复制 assets  ---------------");
                           FileUtils.b(var9 + File.separator + "assets", var10 + File.separator + "assets");
                        }

                        this.c("\n---------------  复制 libs  ---------------");
                        a(var9, var10);
                        if (this.l) {
                           return null;
                        } else {
                           FileUtils.a(var9 + "/apktool.yml", var10 + "/apktool.yml");
                           FileUtils.a(new File(var10 + "/res"));
                           FileUtils.b(var9 + File.separator + "res", var10 + File.separator + "res");
                           if (!new File(var10 + File.separator + "assets").isDirectory()) {
                              new File(var10 + File.separator + "assets").mkdirs();
                           }

                           if (this.l) {
                              return null;
                           } else {
                              this.c("\n---------------  dex移动到壳中  ---------------");
                              this.c("\n--------------- 加密dex ---------------");
                              String var90 = a(3, 10);
                              var84.put("payload", var90);
                              var15 = new File(var10, "assets/".concat(String.valueOf(var90)));
                              System.out.println("check: " + var4.toString());
                              System.out.println("dex 文件压缩包：".concat(String.valueOf(var15)));
                              File var10001 = new File(var11);
                              String var19 = this.g;
                              File var101 = var10001;
                              boolean var10000;
                              if (!var101.isDirectory()) {
                                 var10000 = false;
                              } else {
                                 label245: {
                                    String var65 = var101.getPath();
                                    File[] var66 = new File(var65).listFiles(new x(this));
                                    com.utils.j.a(new File(var65 + File.separator + "dex.zip"), var66);
                                    String var21 = var65 + File.separator + "dex.zip";
                                    String var22 = "fly";
                                    if (b(var19)) {
                                       var19 = "z" + System.currentTimeMillis() + "z";
                                    }

                                    if (!b(var19)) {
                                       long var61 = System.currentTimeMillis();
                                       var19 = var19 + var61;
                                       new File(var15, var22);
                                       if (!com.utils.b.a(var21, var15.getPath(), var19 + "_" + com.frame.a.a, false, 2048)) {
                                          this.c("\ndex文件保存失败");
                                          var10000 = false;
                                          break label245;
                                       }

                                       this.c("\ndex文件保存成功");
                                       var4.put("dexKey", this.a(com.utils.b.a(com.utils.b.a, var19.getBytes())));
                                       var4.put("time", String.valueOf(System.currentTimeMillis()));
                                    }

                                    var10000 = true;
                                 }
                              }

                              if (!var10000) {
                                 return null;
                              } else {
                                 String[] var91 = com.utils.e.a(com.utils.e.a(this.b, this.c), this.d);
                                 String var92;
                                 if ((var92 = var91[0].trim() + var91[1].trim()) != null && var92.trim().length() > 8) {
                                    this.c("\n---------------  保存SHA  ---------------");
                                    String var16 = var92.substring(5, var92.length() - 5);
                                    var16 = new StringBuilder(var16).reverse().toString();
                                    System.out.println(var16);
                                    byte[] var98 = com.utils.b.d(var16, var92.getBytes());
                                    String var93 = this.a(var98);
                                    var84.put("signa", var93);
                                 }

                                 b(var13, new File(var10), new File(var11));
                                 System.out.println("config.dll  释放成功");
                                 this.c("\n--------------  重写AndroidManifest  ---------------");
                                 if (this.o == null) {
                                    this.o = new com.utils.f();
                                 }

                                 String var99 = this.o
                                    .a(
                                       new File(var10 + "/AndroidManifest.xml"),
                                       new File(var9 + "/AndroidManifest.xml"),
                                       var10 + "/AndroidManifest.xml",
                                       var2
                                    );
                                 String var94 = this.o.c();
                                 String var70 = this.o.a();
                                 var9 = this.o.b();
                                 if (var94.startsWith(".")) {
                                    var94 = var70 + var94;
                                 }

                                 var4.put("actMain", var94);
                                 var4.put("package", var70);
                                 var4.put("app", var9);
                                 if (var99 == null) {
                                    this.c("\nAndroidManifest.xml 合并失败");
                                    this.c("\n可以重试");
                                    return null;
                                 } else if (this.l) {
                                    return null;
                                 } else {
                                    File var71 = new File(var10, "init");
                                    String var95;
                                    var9 = var95 = a(3, 10) + ".cls";
                                    var84.put("init_name", var95);
                                    File var87 = new File(var10, "assets/".concat(String.valueOf(var95)));
                                    if (var71.isFile()) {
                                       var19 = this.f;
                                       String var109;
                                       if (!var71.isFile()) {
                                          System.err.print("没有找到加密工具init");
                                          var109 = null;
                                       } else {
                                          if (b(var19)) {
                                             String var107 = a(10, 15);
                                             var19 = "z " + var107 + " z";
                                          } else {
                                             var19 = var19 + System.currentTimeMillis();
                                          }

                                          byte[] var108 = com.utils.b.a(com.utils.b.a, var19.getBytes());
                                          var4.put("init_pass", this.a(var108));
                                          com.utils.b.a(var71.getPath(), var87.getPath(), var99 + var19, false);
                                          var109 = var19;
                                       }

                                       String var89 = var109;
                                       var71.delete();
                                       String var72 = com.utils.c.a(a(var87.getPath()));
                                       var11 = com.utils.c.a(a(var15.getPath()));
                                       var84.put("pckName", var99);
                                       var84.put("version", com.frame.a.a);
                                       var84.put("init_md5", var72);
                                       var84.put("playMd5", var11);
                                       var84.put("pckName", var99);
                                       var84.put("sysTime", String.valueOf(System.currentTimeMillis()));
                                       if (this.l) {
                                          return null;
                                       } else {
                                          String var73 = this.a(var4, com.utils.b.a + var99 + var89);
                                          var84.put("setting", var73);
                                          String var74 = this.a(var84, com.utils.b.a + var99);
                                          byte[] var80 = com.utils.b.a(var99 + com.utils.b.a, var89.getBytes());
                                          String var81 = this.a(var80);
                                          String var75 = this.a(var9.getBytes()) + "\n\n" + var81 + "\n\n" + var74 + "\n\n" + this.a(var99.getBytes());
                                          com.utils.b.c(var10 + "/assets/cls_key", com.utils.b.a(com.utils.b.a, var75.getBytes()));
                                          if (this.l) {
                                             return null;
                                          } else {
                                             this.c("\n---------------  打包带壳的apk  ---------------");
                                             String var76 = this.i + "/target/" + this.q + "_un_sign.apk";
                                             if (var3) {
                                                var3 = com.utils.i.b(var10, var76, "--use-aapt2");
                                             } else {
                                                var3 = com.utils.i.b(var10, var76, null);
                                             }

                                             if (!var3) {
                                                this.c("\n".concat(String.valueOf(var10)));
                                                this.c("\n---------------  |  ---------------");
                                                this.c("\n".concat(String.valueOf(var76)));
                                                this.c("\n\n---------------  可开启/关闭“use-aapt2”后重试  ---------------");
                                                this.c("\n---------------  或者升级APKTool(tool目录下)  ---------------");
                                                this.c("\n---------------       出现错误            ---------------");
                                                return null;
                                             } else {
                                                this.c("\n打包完成：".concat(String.valueOf(var10)));
                                                if (this.l) {
                                                   return null;
                                                } else if (this.l) {
                                                   return null;
                                                } else {
                                                   this.c("开始签名：".concat(String.valueOf(var76)));
                                                   String var77 = this.i + "/result/" + this.q + "_shell_sign.apk";
                                                   if (new File(var77).isFile()) {
                                                      new File(var77).delete();
                                                   }

                                                   if (!new File(var6).isDirectory()) {
                                                      new File(var6).mkdirs();
                                                   }

                                                   if (this.l) {
                                                      return null;
                                                   } else {
                                                      File var78;
                                                      if ((var78 = new File(this.j + "/tools/" + com.utils.i.ZIPALIGN)).isFile() && (var82.isDirectory() || var1)) {
                                                         this.c("\n--------------  签名apk  -------------");
                                                         String var69 = this.i + "/target/" + this.q + "_un_sign_align.apk";
                                                         if (!a(var78, new File(var12), new File(var69))) {
                                                            this.c("\n---4字节对齐失败，可以重试---");
                                                            var1 = false;
                                                         } else {
                                                            this.c("\n--- 使用4了字节对齐---");
                                                            var1 = this.a(new File(var69), new File(var7), new File(this.b), this.c, this.d, this.e);
                                                         }
                                                      } else {
                                                         this.c("\n--- 未使用4字节对齐---");
                                                         var1 = this.a(new File(var12), new File(var7), new File(this.b), this.c, this.d, this.e);
                                                      }

                                                      if (!var1) {
                                                         this.c("\n---加壳失败，可以重试---");
                                                      } else {
                                                         this.c("\n------------  加壳结束  --------------");
                                                         this.c("\n---------------  安装  ---------------");
                                                         this.c("\n---------------   ↓  ---------------");
                                                         this.c("\n---------------   ↓  ---------------");
                                                         this.c("\n---------------   ↓  ---------------");
                                                         this.c("\n".concat(String.valueOf(var7)));
                                                      }

                                                      this.c("/nn签名结束：\n".concat(String.valueOf(var6)));
                                                      return new File(var7);
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    } else {
                                       if (this.h != null) {
                                          this.h.a("dll配置不完整");
                                       }

                                       System.out.println("未找到init文件");
                                       this.c("\n 文件初始化失败");
                                       return null;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               } else {
                  return null;
               }
            }
         }
      } else {
         this.c("\n未到找签名文件");
         return null;
      }
   }

   private boolean a(File var1, File var2) throws Exception {
      File var3 = new File(this.a);
      if (var2.isFile()) {
         var2.delete();
      }

      if (!var3.isFile()) {
         this.c("\n没有找到apk");
         this.c("\n" + var3.getPath());
         return false;
      } else if (b(this.c)) {
         this.c("\n没有找到密钥");
         return false;
      } else if (b(this.e)) {
         this.c("\n没有找到别名密钥");
         return false;
      } else if (b(this.d)) {
         this.c("\n没有找到别名");
         return false;
      } else if (!b(this.b) && new File(this.b).isFile()) {
         return this.a(var1, var2, new File(this.b), this.c, this.d, this.e);
      } else {
         this.c("\n未到找签名文件");
         return false;
      }
   }

   private boolean a(File var1, File var2, File var3, String var4, String var5, String var6) throws Exception {
      var1.getParentFile();
      this.c("\n---------------  签名  ---------------");
      this.c("\n---------------   ↓  ---------------");
      this.c("\n  apk: \n" + var1.getPath());
      this.c("\n  out: \n" + var2.getPath());
      this.c("\n  keyFile: \n" + var3.getPath());
      if (var2.isFile()) {
         var2.delete();
      }

      if (!var2.getParentFile().isDirectory()) {
         var2.getParentFile().mkdirs();
      }

      ArrayList var7;
      (var7 = new ArrayList()).add("--v1-signing-enabled");
      var7.add("true");
      var7.add("--v2-signing-enabled");
      var7.add("true");
      var7.add("--v3-signing-enabled");
      var7.add("true");
      var7.add("--v4-signing-enabled");
      var7.add("false");
      String[] var9 = (String[])var7.toArray(new String[0]);

      try {
         com.utils.j.a(var1, var2, var3, var4, var5, var6, var9);
         return true;
      } catch (Exception var8) {
         this.c("\n签名失败1");
         this.c("\n签名失败1");
         this.c("\n" + var8.getMessage());
         var8.printStackTrace();
         return false;
      }
   }

   public static void main(String[] var0) throws Exception {
      SignShellUtils var7 = new SignShellUtils();
      File var1 = new File("C:/Users/user/Desktop/testdir/target/2019_un_sign_align.apk");
      File var2 = new File("C:/Users/user/Desktop/testdir/result/2019_shell_sign.apk");
      File var3 = new File("C:/Users/user/Desktop/testdir/bj.keystore");
      String var4 = "111111";
      String var5 = "test";
      String var6 = "11111111";
      var7.a(var1, var2, var3, var4, var5, var6);
   }

   private static boolean a(File var0, File var1, File var2) throws Exception {
      return !var0.isFile() ? false : com.utils.i.a(var0.getPath(), var1, var2, null);
   }

   private static byte[] a(String var0) throws Exception {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      FileInputStream var4 = new FileInputStream(var0);
      byte[] var2 = new byte[1024];

      int var3;
      while ((var3 = var4.read(var2)) != -1) {
         var1.write(var2, 0, var3);
      }

      var4.close();
      return var1.toByteArray();
   }

   private String a(Object var1, String var2) throws Exception {
      try {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         ObjectOutputStream var4;
         (var4 = new ObjectOutputStream(var3)).writeObject(var1);
         var4.close();
         var3.close();
         var1 = var3.toByteArray();
         return this.a(com.utils.b.a(var2, (byte[])var1));
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }
   }

   private static boolean b(File var0, File var1, File var2) throws Exception {
      var2 = new File(var2, "lib");
      byte[] var11 = com.utils.b.a(var0);
      byte[] var12 = com.utils.b.b("2025_09", var11);

      try {
         String[] var3 = null;
         if (var2.isDirectory()) {
            var3 = var2.list();
         }

         ByteArrayInputStream var13 = new ByteArrayInputStream(var12);
         ZipInputStream var14;
         ZipEntry var17 = (var14 = new ZipInputStream(var13)).getNextEntry();
         if (!(var1 = var1).exists() && !var1.mkdirs()) {
            throw new Exception("创建解压目录 \"" + var1.getAbsolutePath() + "\" 失败");
         }

         while (var17 != null) {
            String var18 = var17.getName().replace('\\', '/');   // config.dll 条目名为 Windows 反斜杠，归一化为 / 才能正确建目录并命中 lib/ 判断
            File var4;
            if ((var4 = new File(var1, var18)).isFile()) {
               var17 = var14.getNextEntry();
            } else {
               boolean var5 = false;
               if (var3 != null && var3.length > 0 && var18.startsWith("lib/")) {
                  var5 = true;

                  for (String var6 : var3) {
                     if (var18.startsWith("lib/".concat(String.valueOf(var6)))) {
                        var5 = false;
                        break;
                     }
                  }
               }

               if (var5) {
                  var17 = var14.getNextEntry();
               } else {
                  File var19;
                  if (!(var19 = var4.getParentFile()).isDirectory()) {
                     var19.mkdirs();
                  }

                  FileOutputStream var20 = new FileOutputStream(var4);
                  byte[] var21 = new byte[1024];

                  int var22;
                  while ((var22 = var14.read(var21)) > 0) {
                     var20.write(var21, 0, var22);
                  }

                  var20.flush();
                  var20.close();
                  var17 = var14.getNextEntry();
               }
            }
         }

         var14.close();
      } catch (IOException var10) {
         var10.printStackTrace();
      }

      return true;
   }

   private String a(byte[] var1) throws Exception {
      return this.m.a(var1);
   }

   private static String a(int var0, int var1) throws Exception {
      int[] var3 = new int[new Random().nextInt(var1 - var0) + var0];
      String var4 = "";

      for (int var2 = 0; var2 < var3.length; var2++) {
         var3[var2] = new Random().nextInt(26) + 97;
         var4 = var4 + String.format("%c", var3[var2]);
      }

      System.out.println("随机数据：".concat(String.valueOf(var4)));
      return var4;
   }

   private static void a(String var0, String var1) throws Exception {
      String var2 = var0 + File.separator + "lib";
      String var3 = var1 + File.separator + "lib";
      if (!new File(var2).isDirectory()) {
         var2 = var0 + File.separator + "libs";
      }

      if (!new File(var2).isDirectory()) {
         FileUtils.b(var0 + File.separator + "lib", var1 + File.separator + "lib");
      } else {
         File[] var7;
         if ((var7 = new File(var2).listFiles()) != null && var7.length > 0) {
            ArrayList var10 = new ArrayList();

            for (File var8 : var7) {
               var10.add(var8.getName());
            }

            if (!new File(var3).isDirectory()) {
               new File(var3).mkdir();
            }

            File[] var13;
            for (File var9 : var13 = new File(var3).listFiles()) {
               if (!var10.contains(var9.getName())) {
                  FileUtils.a(var9);
               }
            }

            FileUtils.b(var2, var3);
         }
      }
   }

   private static boolean b(String var0) throws Exception {
      return var0 == null ? true : var0.trim().length() == 0;
   }

   private void c(String var1) throws Exception {
      if (this.r != null) {
         this.r.a(var1, true, true);
      }

      if (this.h != null) {
         this.h.a(var1);
      }
   }

   public final void a(y var1) {
      this.h = var1;
   }

   private static void a(File var0, String var1) throws Exception {
      if (var0.isFile()) {
         var0.delete();
      } else {
         if (var0.isDirectory()) {
            File[] var4;
            int var3 = (var4 = var0.listFiles()).length;

            for (int var2 = 0; var2 < var3; var2++) {
               if ((var0 = var4[var2]).getName().startsWith(var1)) {
                  FileUtils.a(var0);
               }
            }
         }
      }
   }

   public final void a() {
      this.l = true;
   }

   public final String b() {
      return this.i;
   }

   public final void a(a var1) {
      this.r = var1;
   }
}
