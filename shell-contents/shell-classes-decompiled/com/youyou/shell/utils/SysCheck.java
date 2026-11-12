/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.Application
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Proxy
 *  android.os.Build$VERSION
 *  android.os.Process
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 */
package com.youyou.shell.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SysCheck {
    private String TAG = "SysCheck";
    private Application mContext;
    private long oldTime = -1L;
    private Map<String, String> optionSetting;
    private boolean stop;

    public SysCheck(Application application) {
        this.mContext = application;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean check() {
        if (this.optionSetting == null) {
            System.exit(1);
        }
        long l = System.currentTimeMillis();
        long l2 = this.oldTime;
        this.oldTime = l;
        if (l - l2 - 2000L > 1000L) {
            System.exit(1);
        }
        String string = this.optionSetting.get("hook");
        String string2 = this.optionSetting.get("proxy");
        String string3 = this.optionSetting.get("closeProxy");
        String string4 = this.optionSetting.get("root");
        String string5 = this.optionSetting.get("debug");
        String string6 = this.optionSetting.get("vpn");
        String string7 = this.optionSetting.get("separation");
        if (string.equals("1") && this.checkHook()) {
            return false;
        }
        if (string2.equals("1") && this.isWifiProxy((Context)this.mContext)) {
            this.optionSetting = null;
            return false;
        }
        if (string3.equals("1")) {
            this.closeProxy();
        }
        if (string4.equals("1") && this.isRootSystem()) {
            this.optionSetting = null;
            return false;
        }
        if (string5.equals("1") && this.checkDebug()) {
            this.optionSetting = null;
            return false;
        }
        if (!string6.equals("1")) return true;
        if (this.check06() != 1) return true;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int check02() {
        if (!this.isWifiProxy((Context)this.mContext)) return -1;
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean findHookAppFile() {
        boolean bl;
        String string;
        Iterator iterator;
        Object object;
        boolean bl2 = true;
        try {
            object = new HashSet();
            iterator = new Iterator("/proc/");
            string = ((StringBuilder)((Object)iterator)).append(Process.myPid()).append("/maps").toString();
            FileReader fileReader = new FileReader(string);
            iterator = new Iterator(fileReader);
        }
        catch (Exception exception) {
            return false;
        }
        while (true) {
            if ((string = ((BufferedReader)((Object)iterator)).readLine()) == null) break;
            if (!string.endsWith(".so") && !string.endsWith(".jar")) continue;
            object.add(string.substring(string.lastIndexOf(" ") + 1));
        }
        ((BufferedReader)((Object)iterator)).close();
        iterator = object.iterator();
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            object = (String)iterator.next();
            bl = bl2;
            if (((String)object).contains("com.saurik.substrate")) return bl;
        } while (!(bl = ((String)object).contains("XposedBridge.jar")));
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean findHookAppName(Context object) {
        ApplicationInfo applicationInfo;
        boolean bl = true;
        object = object.getPackageManager().getInstalledApplications(128).iterator();
        do {
            if (!object.hasNext()) {
                return false;
            }
            applicationInfo = (ApplicationInfo)object.next();
            boolean bl2 = bl;
            if (applicationInfo.packageName.equals("de.robv.android.xposed.installer")) return bl2;
        } while (!applicationInfo.packageName.equals("com.saurik.substrate"));
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean findHookStack() {
        boolean bl = true;
        try {
            Exception exception = new Exception("findhook");
            throw exception;
        }
        catch (Exception exception) {
            int n = 0;
            StackTraceElement[] stackTraceElementArray = exception.getStackTrace();
            int n2 = stackTraceElementArray.length;
            int n3 = 0;
            while (true) {
                boolean bl2;
                if (n3 >= n2) {
                    return false;
                }
                StackTraceElement stackTraceElement = stackTraceElementArray[n3];
                int n4 = n;
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    n4 = n + 1;
                    bl2 = bl;
                    if (n4 == 2) return bl2;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2")) {
                    bl2 = bl;
                    if (stackTraceElement.getMethodName().equals("invoked")) return bl2;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")) {
                    bl2 = bl;
                    if (stackTraceElement.getMethodName().equals("main")) return bl2;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")) {
                    bl2 = bl;
                    if (stackTraceElement.getMethodName().equals("handleHookedMethod")) return bl2;
                }
                ++n3;
                n = n4;
            }
        }
    }

    private List<String> getPath() {
        return Arrays.asList(System.getenv("PATH").split(":"));
    }

    private boolean isOpenDevelopmentSetting() {
        boolean bl = false;
        if (Settings.Secure.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"development_settings_enabled", (int)0) != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isRootSystem1() {
        boolean bl = true;
        String[] stringArray = new String[]{"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int n = 0;
        File file = null;
        while (true) {
            block8: {
                try {
                    if (n >= stringArray.length) {
                        return false;
                    }
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(stringArray[n]));
                    file = new File(stringBuilder.append("su").toString());
                    if (file == null) break block8;
                }
                catch (Exception exception) {
                    return false;
                }
                try {}
                catch (Exception exception) {
                    return false;
                }
                if (file.exists()) {
                    boolean bl2 = file.canExecute();
                    boolean bl3 = bl;
                    if (bl2) return bl3;
                }
            }
            ++n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isRootSystem2() {
        List<String> list = this.getPath();
        int n = 0;
        File file = null;
        while (true) {
            block7: {
                boolean bl;
                try {
                    if (n >= list.size()) return false;
                    file = new File(list.get(n), "su");
                    if (file == null) break block7;
                }
                catch (Exception exception) {
                    return false;
                }
                try {}
                catch (Exception exception) {
                    return false;
                }
                if (file.exists() && (bl = file.canExecute())) {
                    return true;
                }
            }
            ++n;
        }
    }

    private boolean isUSBDebugSetting() {
        boolean bl = false;
        if (Settings.Secure.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"adb_enabled", (int)0) != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int check06() {
        int n;
        int n2 = n = -1;
        try {
            Iterator iterator = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (true) {
                n2 = n;
                if (!iterator.hasNext()) {
                    return n;
                }
                n2 = n;
                NetworkInterface networkInterface = (NetworkInterface)iterator.next();
                n2 = n;
                if (!networkInterface.getName().equals("tun0")) {
                    n2 = n;
                    boolean bl = networkInterface.getName().equals("ppp0");
                    if (!bl) continue;
                }
                n = 1;
            }
        }
        catch (Exception exception) {
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean checkActivityThread(Context object, String annotatedElement) {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo;
        boolean bl = true;
        if (!object.getFilesDir().getPath().contains("/data/user/0/" + (String)((Object)annotatedElement))) {
            return bl;
        }
        boolean bl2 = bl;
        if (new File(String.valueOf(object.getFilesDir().getParent()) + "/..").canWrite()) return bl2;
        object = ((ActivityManager)object.getSystemService("activity")).getRunningAppProcesses().iterator();
        do {
            if (!object.hasNext()) {
                try {
                    annotatedElement = Class.forName("android.app.ActivityThread");
                    object = ((Class)annotatedElement).getDeclaredMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
                    annotatedElement = ((Class)annotatedElement).getDeclaredField("sPackageManager");
                    ((Field)annotatedElement).setAccessible(true);
                    boolean bl3 = Proxy.isProxyClass(((Field)annotatedElement).get(object).getClass());
                    bl2 = bl;
                    if (bl3) return bl2;
                    return false;
                }
                catch (Exception exception) {
                    return false;
                }
            }
            runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)object.next();
        } while (!runningAppProcessInfo.processName.equals(annotatedElement) || runningAppProcessInfo.uid == Process.myUid());
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean checkDebug() {
        if (this.isOpenDevelopmentSetting()) return true;
        if (this.isUSBDebugSetting()) return true;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean checkHook() {
        boolean bl = SysCheck.findHookAppName((Context)this.mContext);
        boolean bl2 = SysCheck.findHookAppFile();
        if (bl) return true;
        if (!bl2) return false;
        if (!SysCheck.findHookStack()) return false;
        return true;
    }

    void closeProxy() {
        System.getProperties().remove("http.proxyHost");
        System.getProperties().remove("http.proxyPort");
        System.getProperties().remove("https.proxyHost");
        System.getProperties().remove("https.proxyPort");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isRootSystem() {
        if (this.isRootSystem1()) return true;
        if (!this.isRootSystem2()) return false;
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isWifiProxy(Context object) {
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
            String string = android.net.Proxy.getHost((Context)object);
            n = android.net.Proxy.getPort((Context)object);
            String string5 = string;
        }
        boolean bl2 = bl;
        if (TextUtils.isEmpty((CharSequence)var1_5)) return bl2;
        bl2 = bl;
        if (n == -1) return bl2;
        return true;
    }

    public void setSetting(Map<String, String> map) {
        this.optionSetting = map;
    }

    public void start() {
        this.oldTime = System.currentTimeMillis();
        new Thread(this){
            final SysCheck this$0;
            {
                this.this$0 = sysCheck;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                do {
                    if (this.this$0.optionSetting == null) {
                        return;
                    }
                    try {
                        Thread.sleep(2000L);
                        if (this.this$0.check()) continue;
                        System.exit(1);
                        return;
                    }
                    catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } while (!this.this$0.stop);
            }
        }.start();
    }

    public void stop() {
        this.stop = true;
    }
}

