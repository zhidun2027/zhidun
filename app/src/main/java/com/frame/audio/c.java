/*
 * Decompiled with CFR 0.152.
 */
package com.frame.audio;

import com.frame.audio.a;

final class c
extends Thread {
    c() {
    }

    @Override
    public final void run() {
        while (a.d() != null) {
            try {
                Thread.sleep(2000L);
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            if (a.c() != null && a.c().isComplete()) {   // \u539f: a.c().b()  -> Player.isComplete()
                a.a();
                continue;
            }
            if (a.c() == null) continue;
            System.out.println("\u672a\u64ad\u653e\u5b8c\u6210  " + a.c().getPosition());   // \u539f: a.c().c() -> getPosition()
        }
    }
}

