/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.Instrumentation
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Proxy
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.ArrayMap
 *  com.youyou.AppLoad
 *  com.youyou.utils.base64.BASE64Decoder
 */
package com.youyou.shell.utils;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.youyou.AppLoad;
import com.youyou.shell.utils.DigestUtils;
import com.youyou.shell.utils.RefInvoke;
import com.youyou.shell.utils.SignatureUtils;
import com.youyou.shell.utils.SysCheck;
import com.youyou.utils.base64.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

public class AppCheck {
    private String appApplication;
    private boolean appIsExst = false;
    private String appPack;
    BASE64Decoder base64Decoder;
    private long cRunTime;
    private int checkActivity;
    private String dexKey;
    private DigestUtils digesUtil;
    private Context mContext;
    private String mainActName;
    private Map<String, String> map;
    private SysCheck sysCheck;
    private SignatureUtils util;
    private String version = "3.3.7";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public AppCheck(Application application, byte[] object) {
        this.sysCheck = new SysCheck(application);
        object = (Map)this.byteToObj((byte[])object);
        if (object.isEmpty()) {
            return;
        }
        String string = (String)object.get("setting");
        this.map = object;
        this.mContext = application;
        this.digesUtil = new DigestUtils();
        this.util = new SignatureUtils((Context)application);
        this.base64Decoder = new BASE64Decoder();
        if (this.check((Map<String, String>)object)) {
            AppLoad.init((ClassLoader)application.getBaseContext().getClassLoader(), (Object)this);
            this.startThreadCheckTime();
            return;
        }
        this.map = null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean activityListener(Application application) {
        if (this.appIsExst) return false;
        if (application == null) {
            return false;
        }
        RefInvoke.setStaticOjbect(application);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] byteMap() {
        Object object = null;
        if (this.map == null) {
            return object;
        }
        try {
            Object object2 = this.map.get("setting");
            BASE64Decoder bASE64Decoder = new BASE64Decoder();
            return object2 = (Object)bASE64Decoder.decodeBuffer((String)object2);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return object;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object byteToObj(byte[] object) {
        void var1_3;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object2 = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return var1_3;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return var1_3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean ccc(Map<String, String> map) {
        if (map == null) return false;
        if (this.map == null) {
            return false;
        }
        String string = map.get("hook");
        String string2 = map.get("proxy");
        String string3 = map.get("closeProxy");
        String string4 = map.get("root");
        String string5 = map.get("debug");
        String string6 = map.get("vpn");
        String string7 = map.get("separation");
        if (string.equals("1") && this.sysCheck.checkHook()) {
            this.map = null;
            return false;
        }
        if (string2.equals("1") && this.sysCheck.isWifiProxy(this.mContext)) {
            this.map = null;
            return false;
        }
        if (string3.equals("1")) {
            this.sysCheck.closeProxy();
        }
        if (string4.equals("1") && this.sysCheck.isRootSystem()) {
            this.map = null;
            return false;
        }
        if (string5.equals("1") && this.sysCheck.checkDebug()) {
            this.map = null;
            return false;
        }
        if (string6.equals("1") && this.sysCheck.check06() == 1) {
            return false;
        }
        this.mainActName = map.get("actMain");
        if (string7.equals("1") && this.sysCheck.checkActivityThread(this.mContext, this.appPack)) {
            this.map = null;
            return false;
        }
        this.sysCheck.setSetting(map);
        this.sysCheck.start();
        this.dexKey = map.get("dexKey");
        this.appApplication = map.get("app");
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int check(byte[] byArray) {
        if (!this.util.checkApp(byArray)) return -1;
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean check(Map<String, String> map) {
        String string;
        Object object;
        String string2;
        String string3;
        block15: {
            String string4;
            block14: {
                block13: {
                    block12: {
                        if (map == null) {
                            return false;
                        }
                        String string5 = map.get("signa");
                        string3 = map.get("pckName");
                        string2 = map.get("payload");
                        String string6 = map.get("sysTime");
                        object = map.get("init_md5");
                        string4 = map.get("init_name");
                        String string7 = map.get("version");
                        string = map.get("playMd5");
                        Object object2 = map.get("setting");
                        if (string6 != null && string6.length() > 0) {
                            long l = (System.currentTimeMillis() - Long.parseLong(string6)) / 1000L / 60L / 60L / 24L;
                        }
                        if (!this.version.equals(string7)) {
                            map.clear();
                            return false;
                        }
                        object2 = this.base64Decoder.decodeBuffer(string5);
                        if (this.util.checkApp((byte[])object2)) break block12;
                        map.clear();
                        return false;
                    }
                    if (this.mContext.getPackageName().equals(string3)) break block13;
                    map.clear();
                    return false;
                }
                if (AppCheck.isFileExists(string4, this.mContext)) break block14;
                map.clear();
                return false;
            }
            if (((String)object).equals(this.check04(string4))) break block15;
            map.clear();
            return false;
        }
        if (!AppCheck.isFileExists(string2, this.mContext)) {
            map.clear();
            return false;
        }
        object = map;
        try {
            if (!string.equals(this.check04(string2))) {
                map.clear();
                return false;
            }
        }
        catch (Exception exception) {
            map.clear();
            object = null;
        }
        this.appPack = string3;
        if (object != null) return true;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int check02() {
        if (!this.isWifiProxy(this.mContext)) return -1;
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int check02(String object) {
        try {
            object = AppCheck.readAssetsFile("sign", this.mContext);
            if (object != null) {
                this.util.checkApp((byte[])object);
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.isWifiProxy(this.mContext);
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int check03(byte[] byArray) {
        int n;
        int n2 = n = -1;
        if (byArray == null) return n2;
        if (byArray.length <= 8) {
            return n;
        }
        n2 = n;
        if (byArray == null) return n2;
        n2 = n;
        if (!this.util.checkApp(byArray)) return n2;
        return 1;
    }

    private int check05() {
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean checkCCC(byte[] object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.map == null) return bl2;
        if (object == null) {
            return bl;
        }
        try {
            object = (Map)this.byteToObj((byte[])object);
            if (!this.ccc((Map<String, String>)object)) {
                this.map = null;
                return bl;
            }
        }
        catch (Exception exception) {
            return bl;
        }
        System.out.println(object);
        return true;
    }

    private int dexVerCheck(Context context, String string) {
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int dexVerCheck(String string, byte[] byArray) {
        long l;
        block5: {
            if (byArray == null) {
                return 1;
            }
            String string2 = new String(byArray);
            System.currentTimeMillis();
            string = this.mContext.getSharedPreferences(string, 0);
            long l2 = string.getLong("CkeyId_C_C", -1L);
            if (byArray == null) {
                return 1;
            }
            try {
                l = Long.parseLong(string2);
                if (l != l2) break block5;
                return 0;
            }
            catch (Exception exception) {
                return 1;
            }
        }
        string.edit().putLong("CkeyId_C_C", l).commit();
        return 1;
    }

    private String getAppName() {
        return this.appApplication;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getAppName(Context object) {
        block6: {
            try {
                object = object.getPackageManager().getApplicationInfo((String)object.getPackageName(), (int)128).metaData;
                if (object == null) break block6;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return null;
            }
            if (!object.containsKey("APPLICATION_CLASS_NAME") || (object = object.getString("APPLICATION_CLASS_NAME")) == null) break block6;
            int n = ((String)object).length();
            if (n <= 0) {
                // empty if block
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getDex() {
        if (this.map != null) return this.map.get("payload");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getKey() {
        if (this.map != null) return this.dexKey;
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getPlayName() {
        if (this.map != null) return this.map.get("payload");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getSett() {
        if (this.map != null) return this.map.get("setting");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getTime(long l) {
        int n = -1;
        if (Math.abs(System.currentTimeMillis() - l) > 1500L) {
            return n;
        }
        long l2 = this.cRunTime;
        if (this.cRunTime > 0L && l - l2 > 2500L) {
            System.exit(1);
            return n;
        }
        this.cRunTime = l;
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getVer() {
        if (this.map != null) return this.map.get("version");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isFileExists(String string, Context stringArray) {
        int n;
        boolean bl = false;
        if (stringArray == null) {
            return bl;
        }
        stringArray = stringArray.getResources().getAssets();
        try {
            stringArray = stringArray.list("");
            n = 0;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return bl;
        }
        while (true) {
            boolean bl2 = bl;
            if (n >= stringArray.length) return bl2;
            bl2 = stringArray[n].equals(string.trim());
            if (bl2) {
                return true;
            }
            ++n;
            continue;
            break;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isUp() {
        boolean bl = false;
        if (this.map == null) {
            return bl;
        }
        String string = this.map.get("time");
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("app_k_y", 0);
        long l = sharedPreferences.getLong("CkeyId_C_C", -1L);
        long l2 = Long.parseLong(string);
        if (l2 == l) return bl;
        sharedPreferences.edit().putLong("CkeyId_C_C", l2).commit();
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isWifiProxy(Context object) {
        void var1_5;
        int n;
        boolean bl = false;
        try {
            NetworkInfo networkInfo = ((ConnectivityManager)object.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo == null) {
                return bl;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((n = Build.VERSION.SDK_INT >= 14 ? 1 : 0) != 0) {
            void var1_3;
            String string = System.getProperty("http.proxyHost");
            String string2 = System.getProperty("http.proxyPort");
            if (string2 == null) {
                String string3 = "-1";
            }
            n = Integer.parseInt((String)var1_3);
            String string4 = string;
        } else {
            String string = Proxy.getHost((Context)object);
            n = Proxy.getPort((Context)object);
            String string5 = string;
        }
        boolean bl2 = bl;
        if (TextUtils.isEmpty((CharSequence)var1_5)) return bl2;
        bl2 = bl;
        if (n == -1) return bl2;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean onCreate(Application object, String object2) {
        Object object3 = object2;
        if (object2 == null) {
            object3 = this.appApplication;
        }
        if (object == null) {
            RefInvoke.setStaticOjbect((Application)object);
            return false;
        }
        if (object3 == null) return false;
        if (((String)object3).trim().length() <= 0) {
            return false;
        }
        object = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[0], new Object[0]);
        Object object4 = RefInvoke.getFieldOjbect("android.app.ActivityThread", object, "mBoundApplication");
        object2 = RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", object4, "info");
        RefInvoke.setFieldOjbect("android.app.LoadedApk", "mApplication", object2, null);
        Object object5 = RefInvoke.getFieldOjbect("android.app.ActivityThread", object, "mInitialApplication");
        ((ArrayList)RefInvoke.getFieldOjbect("android.app.ActivityThread", object, "mAllApplications")).remove(object5);
        object5 = (ApplicationInfo)RefInvoke.getFieldOjbect("android.app.LoadedApk", object2, "mApplicationInfo");
        object4 = (ApplicationInfo)RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", object4, "appInfo");
        ((ApplicationInfo)object5).className = object3;
        ((ApplicationInfo)object4).className = object3;
        object4 = Boolean.TYPE;
        object3 = new Object[2];
        object3[0] = false;
        object2 = (Application)RefInvoke.invokeMethod("android.app.LoadedApk", "makeApplication", object2, new Class[]{object4, Instrumentation.class}, (Object[])object3);
        if (object2 == null) {
            return false;
        }
        RefInvoke.setFieldOjbect("android.app.ActivityThread", "mInitialApplication", object, object2);
        object = ((ArrayMap)RefInvoke.getFieldOjbect("android.app.ActivityThread", object, "mProviderMap")).values().iterator();
        while (true) {
            if (!object.hasNext()) {
                if (object2 == null) return true;
                this.appIsExst = true;
                object2.onCreate();
                RefInvoke.setStaticOjbect(object2);
                return true;
            }
            object3 = object.next();
            try {
                object4 = object3.getClass().getDeclaredField("mLocalProvider");
                ((Field)object4).setAccessible(true);
                ((Field)object4).set(object3, object2);
                object4 = ((Field)object4).get(object3);
                object5 = System.out;
                StringBuilder stringBuilder = new StringBuilder("name = ");
                ((PrintStream)object5).println(stringBuilder.append(object4).toString());
                RefInvoke.setFieldOjbect("android.content.ContentProvider", "mContext", RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord", object3, "mLocalProvider"), object2);
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
            break;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static byte[] readAssetsFile(String object, Context object2) throws IOException {
        if (!AppCheck.isFileExists((String)object, object2)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        object = object2.getResources().getAssets().open((String)object);
        byte[] byArray = new byte[1024];
        while (true) {
            int n;
            if ((n = ((InputStream)object).read(byArray)) == -1) {
                ((InputStream)object).close();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(byArray, 0, n);
        }
    }

    private void startThreadCheckTime() {
        new Thread(this){
            final AppCheck this$0;
            {
                this.this$0 = appCheck;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                do {
                    long l = System.currentTimeMillis();
                    long l2 = this.this$0.cRunTime;
                    if (this.this$0.cRunTime > 0L && l - l2 > 2500L) {
                        System.exit(1);
                        break;
                    }
                    try {
                        1.sleep(1500L);
                    }
                    catch (InterruptedException interruptedException) {}
                    AppLoad.time((Object)this, (long)System.currentTimeMillis());
                    if (!RefInvoke.isStackNoneActivity() || !this.this$0.appIsExst) continue;
                    AppCheck appCheck = this.this$0;
                    appCheck.checkActivity = appCheck.checkActivity + 1;
                } while (this.this$0.checkActivity <= 4);
                this.this$0.map.clear();
                if (this.this$0.sysCheck != null) {
                    this.this$0.sysCheck.stop();
                }
            }
        }.start();
    }

    /*
     * Exception decompiling
     */
    private ByteBuffer[] zipDex(byte[] var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 12[TRYBLOCK] [14 : 155->162)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private byte[][] zipDex2(byte[] var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 13[TRYBLOCK] [15 : 238->245)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean cc() {
        if (this.map == null) {
            return false;
        }
        Object object = this.map.get("sysTime");
        if (object != null && ((String)object).length() > 0 && 540L < (System.currentTimeMillis() - Long.parseLong((String)object)) / 1000L / 60L / 60L / 24L) {
            this.map = null;
            return false;
        }
        object = this.map.get("signa");
        try {
            object = this.base64Decoder.decodeBuffer((String)object);
            if (!this.util.checkApp((byte[])object)) {
                this.map = null;
                return false;
            }
        }
        catch (Exception exception) {
            return false;
        }
        object = this.map.get("pckName");
        if (!this.mContext.getPackageName().equals(object)) {
            this.map = null;
            return false;
        }
        object = this.map.get("init_name");
        if (!AppCheck.isFileExists((String)object, this.mContext)) {
            this.map = null;
            return false;
        }
        if (!this.map.get("init_md5").equals(this.check04((String)object))) {
            this.map = null;
            return false;
        }
        object = this.map.get("payload");
        if (!AppCheck.isFileExists((String)object, this.mContext)) {
            this.map = null;
            return false;
        }
        if (this.map.get("playMd5").equals(this.check04((String)object))) return true;
        this.map = null;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String check04(String object) {
        if (this.digesUtil == null) {
            this.digesUtil = new DigestUtils();
        }
        try {
            object = AppCheck.readAssetsFile((String)object, this.mContext);
            return this.digesUtil.md5((byte[])object);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getValue(String string) {
        if (this.map != null) return this.map.get(string);
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setCheck(Object object) {
        if (object == null) return false;
        if (!(object instanceof Map)) return false;
        return true;
    }
}

