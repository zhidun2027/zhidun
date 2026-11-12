/*
 * Decompiled with CFR 0.152.  （javazoom 混淆包已换回官方 JLayer：g -> javazoom.jl.player.Player）
 */
package com.frame.audio;

import com.frame.audio.b;
import com.frame.audio.c;
import javazoom.jl.player.Player;

public class a {
    private static Player a;
    private static Thread b;

    public static void a() {
        new b().start();
        if (b == null) {
            b = new c();
        }
        b.start();
    }

    public static void b() {
        if (a != null) {
            a.close();          // 原: a.a()  -> Player.close()
            a = null;
            b = null;
        }
    }

    static /* synthetic */ void a(Player g2) {
        a = g2;
    }

    static /* synthetic */ Player c() {
        return a;
    }

    static /* synthetic */ Thread d() {
        return b;
    }
}
