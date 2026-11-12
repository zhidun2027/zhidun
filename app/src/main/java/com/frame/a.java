package com.frame;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.util.LoggingFacade;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalProgressBarUI;
import javax.swing.text.DefaultCaret;

public final class a extends JFrame {
   static String a = "3.3.7";
   private String c = "";
   private boolean d;
   private int e = 3000;
   private boolean f = false;
   private String g = null;
   private int h = 800;
   private int i = 950;
   private int j = 20;
   private int k = 20;
   private int l = 10;
   private JPanel m;
   private JButton n;
   private JTextField o;
   private JLabel p;
   private String q;
   private String r;
   private JTextField s;
   private JLabel t;
   private JScrollPane u;
   private JProgressBar v;
   private JTextArea w;
   private String x = "payload";
   private int y = 33;
   private int z = 100;
   private int A = 100;
   private int B = 5;
   JComboBox b;
   private JButton C;
   private com.utils.e D;
   private JTextField E;
   private JTextField F;
   private JButton G;
   private JTextField H;
   private JPasswordField I;
   private JButton J;
   private JButton K;
   private JPasswordField L;
   private JButton M;
   private JButton N;
   private JButton O;
   private JCheckBox P;
   private JTextField Q;
   private JTextField R;
   private int S;
   private boolean T = false;
   private String U;
   private JCheckBox V;
   private JCheckBox W;
   private JCheckBox X;
   private JCheckBox Y;
   private JCheckBox Z;
   private JCheckBox aa;
   private JCheckBox ab;
   private JCheckBox ac;
   private JCheckBox ad;
   private JCheckBox ae;
   private JCheckBox af;
   private String ag = "cls_key";
   private String ah = "fly";
   private String ai = "init.cls";
   private boolean aj;
   private Thread ak;
   private sun.b al = new sun.b();
   private SignShellUtils am;

   public a() {
      Dimension var1;
      int var2 = (int)(var1 = Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
      int var4 = (int)var1.getHeight();
      Toolkit.getDefaultToolkit().getScreenResolution();
      var2 = var2 / 2 - this.h / 2;
      int var5 = var4 / 2 - this.i / 2;
      this.setBounds(var2, var5, this.h, this.i);
      this.D = new com.utils.e();
      this.c = System.getProperty("user.dir");
      this.U = this.c;
      System.out.println("当前目录：" + this.U);
      this.am = new SignShellUtils();

      try {
         UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
         FlatLaf.updateUI();
      } catch (Exception var3) {
         LoggingFacade.INSTANCE.logSevere(null, var3);
      }

      File var6;
      if (!(var6 = new File(this.U, "config.dll")).isFile()) {
         System.out.println("error: " + var6.getPath());
         String var9 = "config.dll 文件未找到";
         String var7 = "错误";
         JOptionPane.showMessageDialog(null, var9, var7, 0);
      }
   }

   public final void a() {
      Image var1 = new ImageIcon(this.getClass().getResource("/com/frame/icon2.png")).getImage();
      this.setIconImage(var1);
      System.out.println("runPath: 1 " + this.U);
      String var12 = Paths.get("").toAbsolutePath().toString();
      System.out.println("runPath: 3 ".concat(String.valueOf(var12)));
      this.U = var12;
      if (new File(this.U + "/tools/" + com.utils.i.APKTOOL).isFile()) {
         com.utils.i.a = new File(this.U + "/tools");
      } else if (new File(null + "/tools/" + com.utils.i.APKTOOL).isFile()) {
         com.utils.i.a = new File(null + "/tools");
      }

      System.out.println("apkTool: " + com.utils.i.a);
      com.utils.i.b = this;
      this.setTitle("APK安全工具 ver:" + a);
      this.setDefaultCloseOperation(3);
      this.setResizable(false);
      this.m = new JPanel();
      this.setContentPane(this.m);
      this.m.setBorder(new EmptyBorder(2, 2, 2, 2));
      this.m.setLayout(null);
      this.o = new JTextField();
      this.o.setBounds(this.j, this.l, this.h - this.j - this.k - 130, this.y);
      this.o.putClientProperty("JTextField.placeholderText", "请选择apk文件...");
      this.s = new JTextField();
      this.s.setBounds(this.j, this.o.getHeight() + this.o.getX() + 10, this.h - this.j - this.k - 130, this.y);
      this.s.putClientProperty("JTextField.placeholderText", "请选择key文件...");
      this.n = new JButton("文件");
      int var13 = this.h - this.o.getWidth() - this.o.getX() - this.j - this.k;
      this.n.setBounds(this.o.getWidth() + this.j + 10, this.l, var13, this.y);
      this.C = new JButton("Key");
      this.C.setBounds(this.o.getWidth() + this.j + 10, this.s.getY(), var13, this.y);
      this.p = new JLabel();
      this.p.setBounds(this.j, this.o.getY() + this.o.getHeight(), this.h, 320);
      this.p.setBackground(Color.RED);
      JPanel var2;
      (var2 = new JPanel()).setBounds(this.j, this.s.getY() + this.s.getHeight() + 15, this.h - this.k - this.j - 10, this.i - 250);
      JLabel var3 = new JLabel();
      JLabel var4 = new JLabel();
      JLabel var5 = new JLabel();
      JLabel var6 = new JLabel();
      JLabel var7 = new JLabel();
      JLabel var8 = new JLabel();
      JLabel var9 = new JLabel();
      this.M = new JButton("停止");
      this.N = new JButton("打开配置");
      this.O = new JButton("保存配置");
      this.v = new JProgressBar();
      this.v.setStringPainted(true);
      this.v.setOrientation(0);
      this.v.setSize(var2.getWidth() - 10, 100);
      this.v.setMinimum(0);
      this.v.setMaximum(100);
      this.v.setBorderPainted(true);
      this.v.setUI(new MetalProgressBarUI());
      this.v.setStringPainted(true);
      Font var10 = this.v.getFont();
      this.v.setFont(new Font("宋体", var10.getStyle(), 11));
      var2.setLayout(null);
      var3.setText("密钥：");
      var4.setText("别名密钥：");
      var5.setText("别名：");
      var6.setText("dex密钥(可忽略)");
      var7.setText("app密钥(可忽略)");
      var8.setText("SHA1:");
      var9.setText("SHA256:");
      var3.setEnabled(false);
      var4.setEnabled(false);
      var6.setEnabled(false);
      var7.setEnabled(false);
      var5.setEnabled(false);
      var8.setEnabled(false);
      var9.setEnabled(false);
      JPanel var15;
      (var15 = new JPanel()).setLayout(null);
      this.V = new JCheckBox("Hook检查");
      this.W = new JCheckBox("HTTP代理检查");
      this.X = new JCheckBox("关闭HTTP代理");
      this.Y = new JCheckBox("ROOT检查");
      this.Z = new JCheckBox("开发者选项");
      this.aa = new JCheckBox("VPN检查");
      this.ab = new JCheckBox("4字节对齐");
      this.ab.setSelected(true);
      this.ac = new JCheckBox("关闭debuggable");
      this.ad = new JCheckBox("use-aapt2");
      this.ae = new JCheckBox("检测多开");
      this.af = new JCheckBox("RES");
      var15.add(this.V);
      var15.add(this.W);
      var15.add(this.X);
      var15.add(this.Y);
      var15.add(this.Z);
      var15.add(this.aa);
      var15.add(this.ab);
      var15.add(this.ac);
      var15.add(this.ad);
      var15.add(this.ae);
      this.af.setSelected(true);
      this.E = new JPasswordField();
      this.F = new JPasswordField();
      this.I = new JPasswordField();
      this.L = new JPasswordField();
      this.H = new JTextField();
      this.Q = new JTextField();
      this.R = new JTextField();
      this.R.setEditable(false);
      this.Q.setEditable(false);
      this.t = new JLabel("<html><a href='https://t.me/java2019'><font color=\"#FF0000\">关于作者</font></a></html>");
      this.P = new JCheckBox("music");
      this.P.setForeground(Color.RED);
      this.P.setSelected(false);
      this.t.setForeground(Color.RED);
      this.w = new JTextArea();
      this.w.setLineWrap(true);
      this.w.setWrapStyleWord(true);
      this.w.setEditable(false);
      this.am.a(this);
      ((DefaultCaret)this.w.getCaret()).setUpdatePolicy(2);
      StringBuffer var11;
      (var11 = new StringBuffer()).append("SHA 查看：keytool -list -v -keystore 文件\n");
      var11.append("\n ‘4字节对齐’   Android Studio (minSDK>27)生产的APK 建议打开");
      var11.append("\n ‘use-aapt2’   如果使用tools/apktool > 2.6.1 且 apk是Android Studio生产的，建议打开");
      var11.append("\n\n生成key文件的命令如下:\n");
      var11.append(
         "keytool -genkeypair -keyalg RSA -keysize 1024 -sigalg SHA1withRSA  -alias 别名 -keypass 别名密码 -validity 证书有效期，天单位  -storepass 获取keystore信息的密码 -keystore 文件.keystore -dname \"CN=localhost, OU=zlex,O=zlex, L=BJ, ST=BJ, C=CN\""
      );
      var11.append("\n\n日志：");
      this.w.setText(var11.toString());
      this.b = new JComboBox();
      this.u = new JScrollPane(this.w);
      var3.setBounds(0, 0, this.A, this.y);
      this.E.setBounds(var3.getWidth() + var3.getX() + 10, var3.getY(), this.z + 90, this.y);
      this.E.putClientProperty("JTextField.placeholderText", "密钥...");
      var4.setBounds(0, this.E.getY() + this.E.getHeight() + 10, this.A, this.y);
      var5.setBounds(0, var4.getY() + var4.getHeight() + 10, this.z, this.y);
      var8.setBounds(0, var5.getY() + var5.getHeight() + 15, this.A, this.y);
      var9.setBounds(0, var8.getY() + var8.getHeight() + 10, this.A, this.y);
      this.H.putClientProperty("JTextField.placeholderText", "别名...");
      this.H.setBounds(var4.getWidth() + var4.getX() + 10, var4.getY() + var4.getHeight() + 10, this.z + 90, this.y);
      this.b.setBounds(this.H.getWidth() + this.H.getX() + 20, var4.getY() + var4.getHeight() + 10, this.z + 60, this.y);
      this.N.setBounds(this.b.getWidth() + this.b.getX() + 20, this.b.getY(), this.z + 18, this.y);
      this.O.setBounds(this.N.getWidth() + this.N.getX() + 10, this.N.getY(), this.z + 18, this.y);
      this.F.setBounds(var4.getWidth() + var4.getX() + 10, var4.getY(), this.z + 90, this.y);
      this.F.putClientProperty("JTextField.placeholderText", "密钥...");
      this.Q.setBounds(var8.getWidth() + 10, var8.getY(), var2.getWidth() - var8.getWidth() - 10, this.y);
      this.R.setBounds(var9.getWidth() + 10, var9.getY() + 3, var2.getWidth() - var8.getWidth() - 10, this.y);
      var15.setBounds(0, var9.getY() + var9.getHeight() + 10, var2.getWidth(), 100);
      this.V.setBounds(var9.getX(), 0, 130, 30);
      this.W.setBounds(this.V.getX() + this.V.getWidth() + 5, 0, 160, 30);
      this.X.setBounds(this.W.getX() + this.W.getWidth() + 5, 0, 160, 30);
      this.Y.setBounds(this.X.getX() + this.X.getWidth() + 5, 0, 130, 30);
      this.aa.setBounds(this.Y.getX() + this.Y.getWidth(), this.Y.getY(), 110, 30);
      this.Z.setBounds(0, this.Y.getY() + this.Y.getHeight() + 5, 130, 30);
      this.ab.setBounds(this.Z.getX() + this.Z.getWidth() + 5, this.Z.getY(), 120, 30);
      this.ac.setBounds(this.ab.getX() + this.ab.getWidth() + 5, this.ab.getY(), 180, 30);
      this.ad.setBounds(this.ac.getX() + this.ac.getWidth() + 5, this.ac.getY(), 130, 30);
      this.ae.setBounds(this.ad.getX() + this.ad.getWidth() + 5, this.ad.getY(), 120, 30);
      this.u.setBounds(0, var15.getY() + var15.getHeight() + 1, var2.getWidth(), var2.getHeight() - var9.getY() - var9.getHeight() - var15.getHeight() - 20);
      this.w.setBorder(new EmptyBorder(3, 3, 6, 3));
      var6.setBounds(this.E.getX() + this.E.getWidth() + 40, var3.getY(), this.z + 40, this.y - 6);
      this.I.setBounds(var6.getX(), var6.getY() + var6.getHeight() + 10, this.z + 35, this.y);
      var7.setBounds(var6.getX() + var6.getWidth() + 20, var3.getY(), this.z + 40, this.y - 6);
      this.L.setBounds(var7.getX(), var7.getY() + var7.getHeight() + 10, this.z + 35, this.y);
      this.M.setBounds(var7.getWidth() + var7.getX() + 10, var6.getY() + 10, this.z, this.y + 23);
      var2.add(var3);
      var2.add(this.E);
      var2.add(var4);
      var2.add(this.F);
      var2.add(var8);
      var2.add(this.Q);
      var2.add(var9);
      var2.add(this.R);
      var2.add(var15);
      var2.add(this.u);
      var2.add(this.H);
      var2.add(this.b);
      var2.add(this.M);
      var2.add(this.N);
      var2.add(this.O);
      var2.add(var5);
      var2.add(var6);
      var2.add(var7);
      var2.add(this.I);
      var2.add(this.L);
      this.m.add(var2);
      this.G = new JButton("开始签名");
      this.K = new JButton("开始加壳");
      int var14 = var2.getWidth() / 4 - 2;
      this.v.setBounds(this.j, var2.getY() + var2.getHeight(), var2.getWidth(), 12);
      this.K.setBounds(this.j, this.v.getY() + this.v.getHeight() + 5, var14, 40);
      this.m.add(this.G);
      this.m.add(this.K);
      this.J = new JButton("选择");
      this.t.setBounds(15, this.i - 80, 100, this.y - 5);
      this.P.setBounds(this.h - 100, this.i - 80, 100, this.y - 5);
      this.m.add(this.t);
      this.m.add(this.P);
      this.m.add(this.J);
      this.a(this.w);
      this.m.add(this.v);
      this.a(this.m);
      this.m.add(this.o);
      this.m.add(this.C);
      this.m.add(this.s);
      this.m.add(this.n);
      new DropTarget(this.o, 3, new p(this));
      new DropTarget(this.s, 3, new q(this));
      this.t.addMouseListener(new r(this));
      this.M.setEnabled(false);
      this.M.addMouseListener(new s(this));
      this.M.addActionListener(new t(this));
      this.K.addActionListener(new u(this));
      this.C.addActionListener(new v(this));
      this.n.addActionListener(new c(this));
      this.b.addItemListener(new d(this));
      this.E.getDocument().addDocumentListener(new e(this));
      this.F.getDocument().addDocumentListener(new f(this));
      this.G.addActionListener(new g(this));
      this.N.addActionListener(new i(this));
      this.O.addActionListener(new j(this));
      this.V.addActionListener(var1x -> {
         if (this.V.isSelected()) {
            this.a("\n屏蔽Hook", true, true);
         } else {
            this.a("\n解除屏蔽Hook", true, true);
         }
      });
      this.W.addActionListener(var1x -> {
         if (this.W.isSelected()) {
            this.a("\n检查HTTP", true, true);
         } else {
            this.a("\n解除检查HTTP", true, true);
         }
      });
      this.X.addActionListener(var1x -> {
         if (this.X.isSelected()) {
            this.a("\n关闭此app代理，将不能被抓包", true, true);
         } else {
            this.a("\n解除关闭代理", true, true);
         }
      });
      this.Y.addActionListener(var1x -> {
         if (this.Y.isSelected()) {
            this.a("\n屏蔽ROOT", true, true);
         } else {
            this.a("\n解除屏蔽ROOT", true, true);
         }
      });
      this.Z.addActionListener(var1x -> {
         if (this.Z.isSelected()) {
            this.a("\n屏蔽开发者，打开“开发者选项”的手机不再运行", true, true);
         } else {
            this.a("\n\n解除屏蔽开发者", true, true);
         }
      });
      this.aa.addActionListener(var1x -> {
         if (this.aa.isSelected()) {
            this.a("\n屏蔽VPN，打开VPN不运行(功能暂时不可用)", true, true);
         } else {
            this.a("\n\n解除屏蔽VPN", true, true);
         }
      });
      this.ab.addActionListener(var1x -> {
         if (this.ab.isSelected()) {
            this.a("\n使用4字节对齐", true, true);
         } else {
            this.a("\n\n解除使用4字节对齐", true, true);
         }
      });
      this.ac.addActionListener(var1x -> {
         if (this.ac.isSelected()) {
            this.a("\n自动设置android:debuggable=false", true, true);
         } else {
            this.a("\n忽略android:debuggable的值", true, true);
         }
      });
      this.ad.addActionListener(var1x -> {
         if (this.ad.isSelected()) {
            this.a("\n 强制使用AAPT2 建议tools/apktool > 2.6.1 且 需要apk是Android Studio生产的", true, true);
         } else {
            this.a("\n 忽略 --use-aapt2(会自动识别)，可使用apktool <= 2.6.1版本", true, true);
         }
      });
      this.ae.addActionListener(var1x -> {
         if (this.ae.isSelected()) {
            this.a("\n 不允许多开", true, true);
         } else {
            this.a("\n 不检测多开", true, true);
         }
      });
      this.P.addActionListener(var1x -> {
         if (this.P.isSelected()) {
            this.a("\n 音乐开", true, true);
            com.frame.audio.a.a();
         } else {
            this.a("\n 音乐关", true, true);
            com.frame.audio.a.b();
         }
      });
      this.m.repaint();
   }

   private void a(boolean var1) {
      EventQueue.invokeLater(new m(this, var1));
   }

   private File a(DropTargetDropEvent var1, String var2) {
      try {
         if (var1.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            var1.acceptDrop(3);

            List<File> var3;
            for (File var4 : var3 = (List<File>)var1.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)) {
               if (var2 != null && var4.getName().matches(var2)) {
                  this.c = var4.getParent();
                  return var4;
               }

               this.c = var4.getParent();
            }

            if (var3 != null && var3.size() > 0) {
               File var7 = (File)var3.get(0);
               this.c = var7.getParent();
               return var7;
            }

            var1.dropComplete(true);
         } else {
            var1.rejectDrop();
         }
      } catch (Exception var6) {
         this.a(var6.toString(), true, true);
         var6.printStackTrace();
      }

      return null;
   }

   private void a(Component var1) {
      new DropTarget(var1, 3, new k(this, var1));
   }

   private String a(byte[] var1) throws Exception {
      return this.al.a(var1);
   }

   private void a(boolean var1, boolean var2) {
      if (!this.f && this.am != null) {
         if (var1 || var2) {
            String var3 = this.am.b();
            new o(this, var3, var1, var2).start();
         }
      }
   }

   private static boolean a(String var0) {
      return var0 == null ? true : var0.trim().length() == 0;
   }

   public final void a(String var1, boolean var2, boolean var3) {
      if (var1 != null && var1.length() > 0) {
         if (this.w != null && var3) {
            System.out.println(var1);
            SwingUtilities.invokeLater(() -> {
               if (var2) {
                  this.w.append(var1);
               } else {
                  this.w.setText(var1);
               }

               if (this.w.getParent() instanceof JScrollPane) {
                  JScrollBar var3x;
                  (var3x = ((JScrollPane)this.w.getParent()).getVerticalScrollBar()).setValue(var3x.getMaximum());
               }
            });
         } else {
            System.out.println(var1);
         }
      }
   }

   // $VF: synthetic method
   static JProgressBar a(a var0) {
      return var0.v;
   }

   // $VF: synthetic method
   static boolean b(a var0) {
      return var0.d;
   }

   // $VF: synthetic method
   static int c(a var0) {
      return var0.e;
   }

   // $VF: synthetic method
   static JButton d(a var0) {
      return var0.M;
   }

   // $VF: synthetic method
   static JTextField e(a var0) {
      return var0.o;
   }

   // $VF: synthetic method
   static JTextField f(a var0) {
      return var0.s;
   }

   // $VF: synthetic method
   static JButton g(a var0) {
      return var0.n;
   }

   // $VF: synthetic method
   static JButton h(a var0) {
      return var0.C;
   }

   // $VF: synthetic method
   static JTextField i(a var0) {
      return var0.H;
   }

   // $VF: synthetic method
   static JCheckBox j(a var0) {
      return var0.V;
   }

   // $VF: synthetic method
   static JCheckBox k(a var0) {
      return var0.W;
   }

   // $VF: synthetic method
   static JCheckBox l(a var0) {
      return var0.X;
   }

   // $VF: synthetic method
   static JCheckBox m(a var0) {
      return var0.Y;
   }

   // $VF: synthetic method
   static JCheckBox n(a var0) {
      return var0.Z;
   }

   // $VF: synthetic method
   static JCheckBox o(a var0) {
      return var0.aa;
   }

   // $VF: synthetic method
   static JCheckBox p(a var0) {
      return var0.ab;
   }

   // $VF: synthetic method
   static JCheckBox q(a var0) {
      return var0.ac;
   }

   // $VF: synthetic method
   static JCheckBox r(a var0) {
      return var0.ad;
   }

   // $VF: synthetic method
   static JCheckBox s(a var0) {
      return var0.ae;
   }

   // $VF: synthetic method
   static JButton t(a var0) {
      return var0.N;
   }

   // $VF: synthetic method
   static JButton u(a var0) {
      return var0.K;
   }

   // $VF: synthetic method
   static JButton v(a var0) {
      return var0.G;
   }

   // $VF: synthetic method
   static File a(a var0, DropTargetDropEvent var1, String var2) {
      return var0.a(var1, var2);
   }

   // $VF: synthetic method
   static int w(a var0) {
      return var0.S;
   }

   // $VF: synthetic method
   static void a(a var0, int var1) {
      var0.S = var1;
   }

   // $VF: synthetic method
   static void a(a var0, boolean var1) {
      var0.f = var1;
   }

   // $VF: synthetic method
   static SignShellUtils x(a var0) {
      return var0.am;
   }

   // $VF: synthetic method
   static void b(a var0, boolean var1) {
      var0.d = true;
   }

   // $VF: synthetic method
   static void y(a var0) {
      a var2;
      (var2 = var0).d = true;
      var2.v.setValue(100);
      var2.a(true, true);
      var2.a(true);
      if (var2.ak != null) {
         try {
            var2.ak.stop();
         } catch (Exception var1) {
         }
      }

      var2.ak = null;
   }

   // $VF: synthetic method
   static JTextArea z(a var0) {
      return var0.w;
   }

   // $VF: synthetic method
   static void a(a var0, String var1) {
      if (var0.am == null) {
         var0.am = new SignShellUtils();
         var0.am.a(var0);
         var0.am.a(new l(var0));
      }

      var1 = var0.s.getText().toString();
      String var2 = var0.o.getText();
      if (!new File(var1).isFile()) {
         var1 = "没有Key文件";
         String var13 = "错误";
         JOptionPane.showMessageDialog(null, var1, var13, 0);
      } else if (!new File(var2).isFile()) {
         var1 = "没有APK文件";
         String var12 = "错误";
         JOptionPane.showMessageDialog(null, var1, var12, 0);
      } else if (!var0.aj) {
         var1 = "密钥不正确";
         String var11 = "错误";
         JOptionPane.showMessageDialog(null, var1, var11, 0);
      } else {
         File var3;
         if (!(var3 = new File(var0.U, "config.dll")).isFile()) {
            System.out.println("error: " + var3.getPath());
            var1 = "config.dll 文件未找到";
            String var10 = "错误";
            JOptionPane.showMessageDialog(null, var1, var10, 0);
         } else {
            var0.d = false;
            String var21 = var0.E.getText();
            String var4 = var0.H.getText();
            String var5 = var0.F.getText();
            String var6 = var0.I.getText().toString();
            String var7 = var0.L.getText().toString();
            if (a(var21)) {
               var1 = "密钥不正确";
               String var9 = "错误";
               JOptionPane.showMessageDialog(null, var1, var9, 0);
            } else if (a(var4)) {
               var1 = "别名不正确";
               String var8 = "错误";
               JOptionPane.showMessageDialog(null, var1, var8, 0);
            } else {
               com.utils.i.b = var0;
               var0.a(false);
               var0.d = false;
               var0.ak = new n(var0, var2, var1, var21, var4, var5, var6, var7);
               var0.ak.start();
               var0.M.setEnabled(true);
               new b(var0).start();
            }
         }
      }
   }

   // $VF: synthetic method
   static String A(a var0) {
      return var0.c;
   }

   // $VF: synthetic method
   static void b(a var0, String var1) {
      var0.r = var1;
   }

   // $VF: synthetic method
   static String B(a var0) {
      return var0.r;
   }

   // $VF: synthetic method
   static void c(a var0, String var1) {
      var0.c = var1;
   }

   // $VF: synthetic method
   static void d(a var0, String var1) {
      var1 = var1;
      var0 = var0;
      if (var1 != null && new File(var1).isFile()) {
         var0.r = var1;
         var0.s.setText(var1);
         String var2 = var0.E.getText();
         String var3 = var0.F.getText();
         var0.b.removeAllItems();
         if (var2 != null && var2 != "" && var2.trim().length() > 0) {
            try {
               KeyStore var4;
               List<String> var5;
               if ((var5 = (List<String>)com.utils.e.a(var4 = com.utils.e.a(var1, var2))) != null) {
                  for (String var6 : var5) {
                     var0.b.addItem(var6);
                  }

                  String[] var11;
                  if ((var11 = com.utils.e.a(var4, (String)var5.get(0))) != null) {
                     var0.Q.setText(var11[0].trim());
                     var0.R.setText(var11[1].trim());
                  }

                  if (var3.length() > 0) {
                     String var12;
                     if (!a(var12 = com.utils.e.a(new File(var1), var2, (String)var5.get(0), var3))) {
                        var0.aj = true;
                     }

                     var0.a(var12, false, true);
                     return;
                  }
               }
            } catch (Exception var8) {
               var0.aj = false;
               var0.F.setText("");
               var0.H.setText("");
               var0.b.removeAllItems();
               var0.Q.setText("");
               var0.R.setText("");
               System.out.println("Key 不正确");
            }
         }
      }
   }

   // $VF: synthetic method
   static void e(a var0, String var1) {
      var0.q = var1;
   }

   // $VF: synthetic method
   static String C(a var0) {
      return var0.q;
   }

   // $VF: synthetic method
   static void f(a var0, String var1) {
      var0.g = var1;
   }

   // $VF: synthetic method
   static com.utils.e D(a var0) {
      return var0.D;
   }

   // $VF: synthetic method
   static JTextField E(a var0) {
      return var0.E;
   }

   // $VF: synthetic method
   static void g(a var0, String var1) {
      var0 = var0;
      File var8;
      if ((var8 = new File(var1)).isFile()) {
         sun.a var2 = new sun.a();

         try {
            FileReader var9 = new FileReader(var8);
            BufferedReader var3 = new BufferedReader(var9);

            String var4;
            while ((var4 = var3.readLine()) != null) {
               String[] var10;
               if (var4.contains("$=") && (var10 = var4.split("\\$=")).length > 1) {
                  String var5 = var10[0];
                  var4 = var10[1];
                  switch (var5.hashCode()) {
                     case -2116852922:
                        if (!var5.equals("separation")) {
                           continue;
                        }
                        break;
                     case -1925383738:
                        if (var5.equals("aliasePass") && var0.f) {
                           byte[] var16 = var2.a(var4);
                           byte[] var17 = com.utils.b.b("2026-08", var16);
                           var0.F.setText(new String(var17, "utf-8"));
                        }
                        continue;
                     case -1833406514:
                        if (!var5.equals("debuggable")) {
                           continue;
                        }

                        var0.ac.setSelected("true".equals(var4));
                        break;
                     case -1491173820:
                        if (var5.equals("signSha256")) {
                           var0.R.setText(var4);
                        }
                        continue;
                     case -1383292981:
                        if (var5.equals("cProxy")) {
                           var0.X.setSelected("true".equals(var4));
                        }
                        continue;
                     case -809949129:
                        if (var5.equals("vpnBox")) {
                           var0.aa.setSelected("true".equals(var4));
                        }
                        continue;
                     case -794367171:
                        if (var5.equals("appFile")) {
                           var4 = new String(Base64.getDecoder().decode(var4), "utf-8");
                           var0.g = var4;
                           var0.o.setText(var4);
                        }
                        continue;
                     case 3208483:
                        if (var5.equals("hook")) {
                           var0.V.setSelected("true".equals(var4));
                        }
                        continue;
                     case 95458899:
                        if (var5.equals("debug")) {
                           var0.Z.setSelected("true".equals(var4));
                        }
                        continue;
                     case 106941038:
                        if (var5.equals("proxy")) {
                           var0.W.setSelected("true".equals(var4));
                        }
                        continue;
                     case 310911193:
                        if (var5.equals("signFile")) {
                           var4 = new String(Base64.getDecoder().decode(var4), "utf-8");
                           var0.s.setText(var4);
                        }
                        continue;
                     case 505236532:
                        if (var5.equals("singSha1")) {
                           var0.Q.setText(var4);
                        }
                        continue;
                     case 1691842706:
                        if (var5.equals("storePass") && var0.f) {
                           byte[] var12 = var2.a(var4);
                           byte[] var13 = com.utils.b.b("2026-08", var12);
                           var0.E.setText(new String(var13, "utf-8"));
                        }
                        continue;
                     case 1767875043:
                        if (var5.equals("alignment")) {
                           var0.ab.setSelected("true".equals(var4));
                        }
                        continue;
                     case 1802542709:
                        if (!var5.equals("shellPath")) {
                        }
                     default:
                        continue;
                  }

                  var0.ae.setSelected("true".equals(var4));
               }
            }

            var3.close();
            var9.close();
            return;
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }
   }

   // $VF: synthetic method
   static void h(a var0, String var1) {
      var1 = var1;
      var0 = var0;
      if (!var1.endsWith(".setting")) {
         var1 = var1 + ".setting";
      }

      if (new File(var1).isFile()) {
         var1 = var1 + " 文件已存在";
         String var11 = "错误";
         JOptionPane.showMessageDialog(null, var1, var11, 0);
      } else {
         String var2 = var0.o.getText().toString();
         String var3 = var0.s.getText().toString();
         String var4 = var0.Q.getText();
         String var5 = var0.R.getText();
         if (!new File(var2).isFile()) {
            var1 = "未找到文件";
            String var10 = "错误";
            JOptionPane.showMessageDialog(null, var1, var10, 0);
         } else {
            System.out.println("appFile  ".concat(String.valueOf(var2)));
            System.out.println("singFile  ".concat(String.valueOf(var3)));
            System.out.println("shellPath  " + null);
            System.out.println("singSha1  ".concat(String.valueOf(var4)));
            System.out.println("signSha256  ".concat(String.valueOf(var5)));
            File var13 = new File(var1);
            String var6 = var0.E.getText();
            String var7 = var0.F.getText();

            try {
               FileWriter var14;
               (var14 = new FileWriter(var13)).write("appFile$=" + Base64.getEncoder().encodeToString(var2.getBytes("utf-8")));
               var14.write("\nsignFile$=" + Base64.getEncoder().encodeToString(var3.getBytes("utf-8")));
               var14.write("\nshellPath$=" + null);
               var14.write("\nsingSha1$=".concat(String.valueOf(var4)));
               var14.write("\nsignSha256$=".concat(String.valueOf(var5)));
               var14.write("\nhook$=" + var0.V.isSelected());
               var14.write("\nproxy$=" + var0.W.isSelected());
               var14.write("\ncProxy$=" + var0.X.isSelected());
               var14.write("\ndebug$=" + var0.Z.isSelected());
               var14.write("\nvpn$=" + var0.aa.isSelected());
               var14.write("\nseparation$=" + var0.ae.isSelected());
               var14.write("\nalignment$=" + var0.ab.isSelected());
               var14.write("\ndebuggable$=" + var0.ac.isSelected());
               if (!a(var6) || !a(var7)) {
                  var14.write("\nstorePass$=" + var0.a(com.utils.b.a("2026-08", var6.getBytes("utf-8"))));
                  var14.write("\naliasePass$=" + var0.a(com.utils.b.a("2026-08", var7.getBytes("utf-8"))));
               }

               var14.flush();
               var14.close();
            } catch (Exception var8) {
               var8.printStackTrace();
            }
         }
      }
   }

   // $VF: synthetic method
   static void b(a var0, int var1) {
      var0.e = var1;
   }

   // $VF: synthetic method
   static Map F(a var0) {
      HashMap var1 = new HashMap();
      if (var0.V.isSelected()) {
         var1.put("hook", "1");
      } else {
         var1.put("hook", "0");
      }

      if (var0.W.isSelected()) {
         var1.put("proxy", "1");
      } else {
         var1.put("proxy", "0");
      }

      if (var0.X.isSelected()) {
         var1.put("closeProxy", "1");
      } else {
         var1.put("closeProxy", "0");
      }

      if (var0.Y.isSelected()) {
         var1.put("root", "1");
      } else {
         var1.put("root", "0");
      }

      if (var0.Z.isSelected()) {
         var1.put("debug", "1");
      } else {
         var1.put("debug", "0");
      }

      if (var0.aa.isSelected()) {
         var1.put("vpn", "1");
      } else {
         var1.put("vpn", "0");
      }

      if (var0.ae.isSelected()) {
         var1.put("separation", "1");
      } else {
         var1.put("separation", "0");
      }

      return var1;
   }

   // $VF: synthetic method
   static void a(a var0, boolean var1, boolean var2) {
      var0.a(true, true);
   }

   // $VF: synthetic method
   static void c(a var0, boolean var1) {
      var0.a(true);
   }
}
