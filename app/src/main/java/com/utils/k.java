package com.utils;

public class k {
   static final int METHOD_STORED = 0;
   static final int METHOD_DEFLATED = 8;
   static final int METHOD_DEFLATED64 = 9;
   static final int METHOD_BZIP2 = 12;
   static final int METHOD_LZMA = 14;
   static final int METHOD_LZ77 = 19;
   static final int METHOD_AES = 99;
   static final int FLAG_ENCRYPTED = 1;
   static final int FLAG_DATADESCR = 8;
   static final int FLAG_EFS = 2048;
   static long LOCSIG = 67324752L;
   static long EXTSIG = 134695760L;
   static long CENSIG = 33639248L;
   static long ENDSIG = 101010256L;
   static final int LOCHDR = 30;
   static final int EXTHDR = 16;
   static final int CENHDR = 46;
   static final int ENDHDR = 22;
   static final int LOCVER = 4;
   static final int LOCHOW = 8;
   static final int LOCTIM = 10;
   static final int LOCCRC = 14;
   static final int LOCSIZ = 18;
   static final int LOCLEN = 22;
   static final int LOCNAM = 26;
   static final int LOCEXT = 28;
   static final int EXTCRC = 4;
   static final int EXTSIZ = 8;
   static final int EXTLEN = 12;
   static final int CENVEM = 4;
   static final int CENVER = 6;
   static final int CENHOW = 10;
   static final int CENTIM = 12;
   static final int CENCRC = 16;
   static final int CENSIZ = 20;
   static final int CENLEN = 24;
   static final int CENNAM = 28;
   static final int CENEXT = 30;
   static final int CENCOM = 32;
   static final int CENDSK = 34;
   static final int CENATT = 36;
   static final int CENATX = 38;
   static final int CENOFF = 42;
   static final int ENDSUB = 8;
   static final int ENDTOT = 10;
   static final int ENDSIZ = 12;
   static final int ENDOFF = 16;
   static final int ENDCOM = 20;
   static final long ZIP64_ENDSIG = 101075792L;
   static final long ZIP64_LOCSIG = 117853008L;
   static final int ZIP64_ENDHDR = 56;
   static final int ZIP64_LOCHDR = 20;
   static final int ZIP64_EXTHDR = 24;
   static final int ZIP64_EXTID = 1;
   static final int ZIP64_MINVAL32 = 65535;
   static final long ZIP64_MINVAL = 4294967295L;
   static final int ZIP64_ENDLEN = 4;
   static final int ZIP64_ENDVEM = 12;
   static final int ZIP64_ENDVER = 14;
   static final int ZIP64_ENDNMD = 16;
   static final int ZIP64_ENDDSK = 20;
   static final int ZIP64_ENDTOD = 24;
   static final int ZIP64_ENDTOT = 32;
   static final int ZIP64_ENDSIZ = 40;
   static final int ZIP64_ENDOFF = 48;
   static final int ZIP64_ENDEXT = 56;
   static final int ZIP64_LOCDSK = 4;
   static final int ZIP64_LOCOFF = 8;
   static final int ZIP64_LOCTOT = 16;
   static final int ZIP64_EXTCRC = 4;
   static final int ZIP64_EXTSIZ = 8;
   static final int ZIP64_EXTLEN = 16;
   static final int EXTID_ZIP64 = 1;
   static final int EXTID_NTFS = 10;
   static final int EXTID_UNIX = 13;
   static final int EXTID_EFS = 23;
   static final int EXTID_EXTT = 21589;
   static final long END_MAXLEN = 65557L;
   static final int READBLOCKSZ = 128;

   static final int CH(byte[] var0, int var1) {
      return var0[var1] & 0xFF;
   }

   static final int SH(byte[] var0, int var1) {
      return var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8;
   }

   static final long LG(byte[] var0, int var1) {
      return (long)(SH(var0, var1) | SH(var0, var1 + 2) << 16) & 4294967295L;
   }

   static final long LL(byte[] var0, int var1) {
      return LG(var0, var1) | LG(var0, var1 + 4) << 32;
   }

   static final long GETSIG(byte[] var0) {
      return LG(var0, 0);
   }

   static final long LOCSIG(byte[] var0) {
      return LG(var0, 0);
   }

   static final int LOCVER(byte[] var0) {
      return SH(var0, 4);
   }

   static final int LOCHOW(byte[] var0) {
      return SH(var0, 8);
   }

   static final long LOCTIM(byte[] var0) {
      return LG(var0, 10);
   }

   static final long LOCCRC(byte[] var0) {
      return LG(var0, 14);
   }

   static final long LOCSIZ(byte[] var0) {
      return LG(var0, 18);
   }

   static final long LOCLEN(byte[] var0) {
      return LG(var0, 22);
   }

   static final int LOCNAM(byte[] var0) {
      return SH(var0, 26);
   }

   static final int LOCEXT(byte[] var0) {
      return SH(var0, 28);
   }

   static final long EXTCRC(byte[] var0) {
      return LG(var0, 4);
   }

   static final long EXTSIZ(byte[] var0) {
      return LG(var0, 8);
   }

   static final long EXTLEN(byte[] var0) {
      return LG(var0, 12);
   }

   static final int ENDSUB(byte[] var0) {
      return SH(var0, 8);
   }

   static final int ENDTOT(byte[] var0) {
      return SH(var0, 10);
   }

   static final long ENDSIZ(byte[] var0) {
      return LG(var0, 12);
   }

   static final long ENDOFF(byte[] var0) {
      return LG(var0, 16);
   }

   static final int ENDCOM(byte[] var0) {
      return SH(var0, 20);
   }

   static final int ENDCOM(byte[] var0, int var1) {
      return SH(var0, var1 + 20);
   }

   static final long ZIP64_ENDTOD(byte[] var0) {
      return LL(var0, 24);
   }

   static final long ZIP64_ENDTOT(byte[] var0) {
      return LL(var0, 32);
   }

   static final long ZIP64_ENDSIZ(byte[] var0) {
      return LL(var0, 40);
   }

   static final long ZIP64_ENDOFF(byte[] var0) {
      return LL(var0, 48);
   }

   static final long ZIP64_LOCOFF(byte[] var0) {
      return LL(var0, 8);
   }

   static final long CENSIG(byte[] var0, int var1) {
      return LG(var0, var1);
   }

   static final int CENVEM(byte[] var0, int var1) {
      return SH(var0, var1 + 4);
   }

   static final int CENVER(byte[] var0, int var1) {
      return SH(var0, var1 + 6);
   }

   static final int CENHOW(byte[] var0, int var1) {
      return SH(var0, var1 + 10);
   }

   static final long CENTIM(byte[] var0, int var1) {
      return LG(var0, var1 + 12);
   }

   static final long CENCRC(byte[] var0, int var1) {
      return LG(var0, var1 + 16);
   }

   static final long CENSIZ(byte[] var0, int var1) {
      return LG(var0, var1 + 20);
   }

   static final long CENLEN(byte[] var0, int var1) {
      return LG(var0, var1 + 24);
   }

   static final int CENNAM(byte[] var0, int var1) {
      return SH(var0, var1 + 28);
   }

   static final int CENEXT(byte[] var0, int var1) {
      return SH(var0, var1 + 30);
   }

   static final int CENCOM(byte[] var0, int var1) {
      return SH(var0, var1 + 32);
   }

   static final int CENDSK(byte[] var0, int var1) {
      return SH(var0, var1 + 34);
   }

   static final int CENATT(byte[] var0, int var1) {
      return SH(var0, var1 + 36);
   }

   static final long CENATX(byte[] var0, int var1) {
      return LG(var0, var1 + 38);
   }

   static final long CENOFF(byte[] var0, int var1) {
      return LG(var0, var1 + 42);
   }

   static final int CEN(byte[] var0, int var1) {
      return SH(var0, var1 + 8);
   }
}
