/*
 * Decompiled with CFR 0.152.
 */
package com;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.util.SystemInfo;
import com.frame.a;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

public class ShellMain {
    private static boolean a = Boolean.parseBoolean(System.getProperty("flatlaf.demo.screenshotsMode"));

    public static void main(String[] stringArray) {
        System.setProperty("flatlaf.uiScale", "x");
        System.out.println("\u542f\u52a8\u76ee\u5f55\uff1a" + System.getProperty("user.dir"));
        SwingUtilities.invokeLater(() -> {
            System.out.println("\u542f\u52a8\u76ee\u5f55\uff1a" + System.getProperty("user.dir"));
            FlatInterFont.installLazy();
            FlatJetBrainsMonoFont.installLazy();
            FlatRobotoFont.installLazy();
            FlatRobotoMonoFont.installLazy();
            FlatLaf.setPreferredLightFontFamily("Inter Light");
            FlatLaf.registerCustomDefaultsSource("com.formdev.flatlaf.demo");
            FlatInspector.install("ctrl shift alt X");
            FlatUIDefaultsInspector.install("ctrl shift alt Y");
            a a2 = new a();
            if (a) {
                a2.setPreferredSize(SystemInfo.isJava_9_orLater ? new Dimension(830, 440) : new Dimension(1660, 880));
            }
            a2.a();
            a2.setLocationRelativeTo(null);
            a2.setVisible(true);
        });
    }
}

