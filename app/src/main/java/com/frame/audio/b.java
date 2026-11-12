/*
 * Decompiled with CFR 0.152.  （javazoom 混淆包已换回官方 JLayer：g -> javazoom.jl.player.Player）
 */
package com.frame.audio;

import com.frame.audio.a;
import java.io.InputStream;
import javazoom.jl.player.Player;

final class b
extends Thread {
    b() {
    }

    @Override
    public final void run() {
        try {
            Thread.sleep(2000L);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            InputStream object = a.class.getResourceAsStream("music.mp3");
            a.a(new Player(object));
            a.c().getPosition();                 // 原: a.c().c()
            a.c().play(Integer.MAX_VALUE);        // 原: ((g)object).a(Integer.MAX_VALUE)
            a.c().close();                        // 原: a.c().a()
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}
