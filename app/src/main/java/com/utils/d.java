package com.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public final class d extends JPanel implements PropertyChangeListener {
   private JLabel a;
   private JScrollPane b;

   public d() {
      this.setLayout(new BorderLayout());
      this.a = new JLabel((String)null);
      this.a.setBorder(BorderFactory.createLineBorder(Color.green));
      this.b = new JScrollPane(this.a);
   }

   @Override
   public final void propertyChange(PropertyChangeEvent var1) {
      if (var1.getPropertyName().equals("SelectedFileChangedProperty")) {
         File var2;
         if ((var2 = (File)var1.getNewValue()) == null) {
            return;
         }

         if (!var2.isDirectory()) {
            System.out.println(var2.getAbsolutePath());
            this.updateUI();
         }
      }
   }
}
