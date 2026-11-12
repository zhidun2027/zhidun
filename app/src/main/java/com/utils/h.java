package com.utils;

final class h {
   private h a = null;
   private char b = '0';

   private h() throws Exception {
   }

   final String a() throws Exception {
      return this.a == null ? String.valueOf(this.b) : this.a.a() + this.b;
   }

   final void b() throws Exception {
      if (this.b == '9') {
         this.b = 'A';
      } else if (this.b == 'Z') {
         this.b = 'a';
      } else if (this.b == 'z') {
         this.b = '0';
         if (this.a == null) {
            this.a = new h();
         } else {
            this.a.b();
         }
      } else {
         this.b++;
      }
   }

   // $VF: synthetic method
   h(byte var1) throws Exception {
      this();
   }
}
