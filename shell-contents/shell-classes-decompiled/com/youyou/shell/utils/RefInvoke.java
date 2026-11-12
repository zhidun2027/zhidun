/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.app.Instrumentation
 *  android.content.pm.ApplicationInfo
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.ArrayMap
 *  android.util.Log
 */
package com.youyou.shell.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Stack;

public class RefInvoke {
    private static Stack<Activity> activityStack = new Stack();
    private Class c = Boolean.TYPE;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static Application getApp(ApplicationInfo object, ApplicationInfo object2, Object object3, String string) {
        void var2_4;
        void var3_5;
        boolean bl = true;
        object.className = var3_5;
        ((ApplicationInfo)object4).className = var3_5;
        Class<Boolean> clazz = Boolean.TYPE;
        Object object4 = new Object[2];
        object4[0] = false;
        Application application = (Application)RefInvoke.invokeMethod("android.app.LoadedApk", "makeApplication", var2_4, new Class[]{clazz, Instrumentation.class}, (Object[])object4);
        object4 = new StringBuilder("application: is null: ");
        if (application != null) {
            bl = false;
        }
        Log.e((String)"RefInvoke", (String)((StringBuilder)object4).append(bl).append("  ").append((String)var3_5).toString());
        return application;
    }

    public static Class[] getClassArr() {
        return new Class[]{Boolean.TYPE, Instrumentation.class};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object getFieldOjbect(String object, Object object2, String string) {
        try {
            object = Class.forName((String)object).getDeclaredField(string);
            ((Field)object).setAccessible(true);
            return ((Field)object).get(object2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object[] getObjectArr() {
        Object[] objectArray = new Object[2];
        objectArray[0] = false;
        return objectArray;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object getStaticFieldOjbect(String object, String string) {
        Object var2_3 = null;
        try {
            object = Class.forName((String)object).getDeclaredField(string);
            ((Field)object).setAccessible(true);
            return ((Field)object).get(null);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return var2_3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object invokeMethod(String object, String string, Object object2, Class[] classArray, Object[] objectArray) {
        try {
            return Class.forName((String)object).getMethod(string, classArray).invoke(object2, objectArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object invokeStaticMethod(String string, String string2) {
        return RefInvoke.invokeStaticMethod(string, string2, null, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object invokeStaticMethod(String object, String string, Class[] classArray, Object[] objectArray) {
        Object var4_5 = null;
        try {
            return Class.forName((String)object).getMethod(string, classArray).invoke(null, objectArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return var4_5;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isStackNoneActivity() {
        if (activityStack.size() != 0) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void setFieldOjbect(String object, String string, Object object2, Object object3) {
        try {
            object = Class.forName((String)object).getDeclaredField(string);
            ((Field)object).setAccessible(true);
            ((Field)object).set(object2, object3);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static void setStaticOjbect(Application application) {
        if (application != null) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){

                public void onActivityCreated(Activity activity, Bundle bundle) {
                    activityStack.push(activity);
                }

                public void onActivityDestroyed(Activity activity) {
                    activityStack.remove(activity);
                }

                public void onActivityPaused(Activity activity) {
                }

                public void onActivityResumed(Activity activity) {
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public void onActivityStarted(Activity activity) {
                }

                public void onActivityStopped(Activity activity) {
                }
            });
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setStaticOjbect(String annotatedElement, String string, Object object) {
        try {
            annotatedElement = Class.forName((String)((Object)annotatedElement));
            if (annotatedElement == null) return;
            if ((annotatedElement = ((Class)annotatedElement).getDeclaredField(string)) == null) return;
            ((Field)annotatedElement).setAccessible(true);
            ((Field)annotatedElement).set(null, object);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static void setStaticOjbect2(Application application, Object object) {
        Iterator iterator = ((ArrayMap)RefInvoke.getFieldOjbect("android.app.ActivityThread", object, "mProviderMap")).values().iterator();
        while (true) {
            Object object2;
            Object e;
            block9: {
                Object object3;
                if (!iterator.hasNext()) {
                    if (application != null) {
                        application.onCreate();
                        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){

                            public void onActivityCreated(Activity activity, Bundle bundle) {
                                activityStack.push(activity);
                            }

                            public void onActivityDestroyed(Activity activity) {
                                activityStack.remove(activity);
                            }

                            public void onActivityPaused(Activity activity) {
                            }

                            public void onActivityResumed(Activity activity) {
                            }

                            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                            }

                            public void onActivityStarted(Activity activity) {
                            }

                            public void onActivityStopped(Activity activity) {
                            }
                        });
                    }
                    return;
                }
                e = iterator.next();
                object = object2 = null;
                try {
                    if (Build.VERSION.SDK_INT < 29) break block9;
                    object3 = RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord", e, "mHolder");
                    object = object2;
                    if (object3 == null) break block9;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    continue;
                }
                object = RefInvoke.getFieldOjbect("android.app.ContentProviderHolder", object3, "mLocalProvider");
            }
            object2 = object;
            if (object == null) {
                object2 = RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord", e, "mLocalProvider");
            }
            if (object2 == null) continue;
            RefInvoke.setFieldOjbect("android.content.ContentProvider", "mContext", object2, application);
            continue;
            break;
        }
    }

    public Class getClasss() {
        return Boolean.TYPE;
    }
}

