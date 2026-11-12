package com.frame;

final class l implements y {
   // $VF: synthetic field
   private a a;

   l(a var1) {
      this.a = var1;
   }

   @Override
   public final void a(int var1) {
      System.out.println("progress: ".concat(String.valueOf(var1)));
   }

   @Override
   public final void a(String var1) {
      this.a.a(var1, true, true);
   }
}
