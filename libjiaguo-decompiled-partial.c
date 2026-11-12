
// ===== FUN_0010a80c @ 0010a80c =====

void FUN_0010a80c(void)

{
  long lVar1;
  bool bVar2;
  int iVar3;
  FILE *__stream;
  char *pcVar4;
  char acStack_138 [10];
  char acStack_12e [246];
  long local_38;
  
  lVar1 = tpidr_el0;
  local_38 = *(long *)(lVar1 + 0x28);
  __stream = fopen("/proc/self/status","r");
  if (__stream == (FILE *)0x0) {
LAB_0010a8a4:
    bVar2 = false;
  }
  else {
    do {
      pcVar4 = fgets(acStack_138,0x100,__stream);
      if (pcVar4 == (char *)0x0) {
        fclose(__stream);
        goto LAB_0010a8a4;
      }
      iVar3 = strncmp(acStack_138,"TracerPid",9);
    } while (iVar3 != 0);
    iVar3 = atoi(acStack_12e);
    fclose(__stream);
    bVar2 = iVar3 != 0;
  }
  lVar1 = tpidr_el0;
  if (*(long *)(lVar1 + 0x28) != local_38) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail(bVar2);
  }
  return;
}


// ===== FUN_0010a8d4 @ 0010a8d4 =====

undefined8 FUN_0010a8d4(void)

{
  bool bVar1;
  int iVar2;
  int iVar3;
  ulong uVar4;
  undefined8 uVar5;
  undefined *puVar6;
  long lVar7;
  long *local_80;
  timeval local_78;
  long local_68;
  
  lVar7 = tpidr_el0;
  local_68 = *(long *)(lVar7 + 0x28);
  lVar7 = 0;
  do {
    __android_log_print(4,"(^_^)","%s",*(undefined8 *)((long)&PTR_DAT_00158008 + lVar7));
    lVar7 = lVar7 + 8;
  } while (lVar7 != 0x138);
  do {
    local_78.tv_sec = 0;
    local_78.tv_usec = 0;
    iVar3 = gettimeofday(&local_78,(__timezone_ptr_t)0x0);
    lVar7 = DAT_00158140;
    if ((local_78.tv_usec / 1000 + local_78.tv_sec * 1000) - DAT_00158140 < 0x1389) {
      local_78.tv_sec = 0;
      local_78.tv_usec = 0;
      iVar3 = gettimeofday(&local_78,(__timezone_ptr_t)0x0);
      if (lVar7 < local_78.tv_usec / 1000 + local_78.tv_sec * 1000 + -5000) goto LAB_0010a9e8;
    }
    else {
LAB_0010a9e8:
      iVar2 = DAT_001583a8 + 1;
      bVar1 = 4 < DAT_001583a8;
      DAT_001583a8 = iVar2;
      if (bVar1) {
                    /* WARNING: Subroutine does not return */
        _exit(1);
      }
    }
    uVar4 = FUN_0010a80c(iVar3);
    if ((uVar4 & 1) != 0) {
      puVar6 = &DAT_0013e4d8;
LAB_0010ab28:
      __android_log_print(6,"(>_<)",puVar6);
                    /* WARNING: Subroutine does not return */
      _exit(1);
    }
    local_80 = (long *)0x0;
    iVar3 = (**(code **)(*DAT_001583b0 + 0x20))(DAT_001583b0,&local_80,0);
    if (iVar3 != 0) goto LAB_0010aac4;
    if (DAT_001583b8 != 0) {
      uVar5 = (**(code **)(*local_80 + 0xf8))();
      lVar7 = (**(code **)(*local_80 + 0x108))(local_80,uVar5,"getTime",&DAT_0013e528);
      if (lVar7 == 0) {
        (**(code **)(*DAT_001583b0 + 0x28))();
LAB_0010aac4:
        lVar7 = tpidr_el0;
        if (*(long *)(lVar7 + 0x28) != local_68) {
                    /* WARNING: Subroutine does not return */
          __stack_chk_fail();
        }
        return 0;
      }
      local_78.tv_sec = 0;
      local_78.tv_usec = 0;
      gettimeofday(&local_78,(__timezone_ptr_t)0x0);
      iVar3 = FUN_0010ab3c(local_80,DAT_001583b8,lVar7,
                           local_78.tv_usec / 1000 + local_78.tv_sec * 1000);
      if (iVar3 == -1) {
        puVar6 = &DAT_0013e52d;
        goto LAB_0010ab28;
      }
      (**(code **)(*DAT_001583b0 + 0x28))();
    }
    sleep(1);
  } while( true );
}


// ===== JNI_OnLoad @ 0010abdc =====

void JNI_OnLoad(long *param_1)

{
  __time_t _Var1;
  int iVar2;
  uint uVar3;
  ulong uVar4;
  long lVar5;
  timeval local_38;
  long local_28;
  
  lVar5 = tpidr_el0;
  local_28 = *(long *)(lVar5 + 0x28);
  local_38.tv_sec = 0;
  local_38.tv_usec = 0;
  gettimeofday(&local_38,(__timezone_ptr_t)0x0);
  DAT_00158140 = local_38.tv_usec / 1000 + local_38.tv_sec * 1000;
  iVar2 = pthread_create((pthread_t *)&DAT_00158380,(pthread_attr_t *)0x0,FUN_0010a8d4,(void *)0x0);
  DAT_001583b0 = param_1;
  uVar4 = FUN_0010a80c(iVar2);
  if ((uVar4 & 1) != 0) {
    __android_log_print(6,"(>_<)",&DAT_0013e56a);
                    /* WARNING: Subroutine does not return */
    _exit(1);
  }
  iVar2 = (**(code **)(*param_1 + 0x30))(param_1,&local_38,0x10006);
  _Var1 = local_38.tv_sec;
  if (iVar2 == 0) {
    lVar5 = (**(code **)(*(long *)local_38.tv_sec + 0x30))(local_38.tv_sec,"com/youyou/AppLoad");
    if (lVar5 != 0) {
      iVar2 = (**(code **)(*(long *)_Var1 + 0x6b8))(_Var1,lVar5,&DAT_00158148,4);
      uVar3 = iVar2 >> 0x1f | 0x10006;
      goto LAB_0010ace0;
    }
  }
  uVar3 = 0xffffffff;
LAB_0010ace0:
  lVar5 = tpidr_el0;
  if (*(long *)(lVar5 + 0x28) == local_28) {
    return;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail(uVar3);
}


// ===== FUN_0010dbb8 @ 0010dbb8 =====

undefined8 FUN_0010dbb8(long *param_1,long param_2,char *param_3,int param_4,int param_5)

{
  long lVar1;
  char *__s;
  byte bVar2;
  size_t sVar3;
  undefined8 uVar4;
  long lVar5;
  int iVar6;
  
  __s = "2025-03-33";
  if (*param_3 != '\0') {
    __s = param_3;
  }
  sVar3 = strlen(__s);
  if (0 < param_4) {
    lVar5 = 0;
    iVar6 = 0;
    do {
      if ((0 < param_5) && (param_5 < lVar5)) break;
      if ((int)sVar3 <= iVar6) {
        iVar6 = 0;
      }
      bVar2 = *(char *)(param_2 + lVar5) + (char)DAT_001581b4;
      *(byte *)(param_2 + lVar5) = bVar2;
      bVar2 = bVar2 ^ (byte)DAT_001581b0;
      *(byte *)(param_2 + lVar5) = bVar2;
      lVar1 = (long)iVar6;
      iVar6 = iVar6 + 1;
      *(byte *)(param_2 + lVar5) = __s[lVar1] ^ bVar2;
      lVar5 = lVar5 + 1;
    } while (lVar5 < param_4);
  }
  uVar4 = (**(code **)(*param_1 + 0x580))(param_1,param_4);
  (**(code **)(*param_1 + 0x680))(param_1,uVar4,0,param_4,param_2);
  return uVar4;
}


// ===== FUN_0010dfa4 @ 0010dfa4 =====

bool FUN_0010dfa4(long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4)

{
  int iVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  
  uVar2 = (**(code **)(*param_1 + 0x108))(param_1,param_4,"check","(Ljava/lang/String;[B)I");
  uVar3 = (**(code **)(*param_1 + 0x538))(param_1,"app_k_y");
  iVar1 = (**(code **)(*param_1 + 0x188))(param_1,param_3,uVar2,uVar3,param_2);
  return 0 < iVar1;
}


// ===== Java_com_hello_utils_FileUtils_n001___3B @ 0010e850 =====

undefined8
Java_com_hello_utils_FileUtils_n001___3B(long *param_1,undefined8 param_2,undefined8 param_3)

{
  size_t __n;
  int iVar1;
  undefined8 uVar2;
  void *__src;
  void *__dest;
  ulong uVar3;
  size_t sVar4;
  size_t __n_00;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    uVar2 = (**(code **)(*param_1 + 0x5c0))(param_1,param_3,0);
    iVar1 = (**(code **)(*param_1 + 0x558))(param_1,param_3);
    __src = (void *)FUN_0010dbb8(uVar2,PTR_s_2025_03_33_001581b8,iVar1,0xffffffff);
    __n_00 = (size_t)iVar1;
    uVar3 = (ulong)(iVar1 + 1);
    sVar4 = uVar3 - __n_00;
    if (iVar1 < -1) {
      uVar3 = 0xffffffffffffffff;
    }
    __n = 0;
    if (iVar1 != -1) {
      __n = sVar4;
    }
    __dest = operator_new__(uVar3);
    memset((void *)((long)__dest + __n_00),0,__n);
    memcpy(__dest,__src,__n_00);
    DAT_001583e0 = __dest;
    (**(code **)(*param_1 + 0x600))(param_1,param_3,uVar2,2);
  }
  return 1;
}


// ===== Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2 @ 0010e95c =====

undefined8
Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4)

{
  undefined4 uVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  char *__s;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    uVar2 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/base64/BASE64Decoder");
    uVar3 = (**(code **)(*param_1 + 0x548))(param_1,param_3,0);
    __android_log_print(6,"(>_<)","char :%s",uVar3);
    uVar3 = (**(code **)(*param_1 + 0x108))(param_1,param_2,"<init>",&DAT_0013e63a);
    uVar4 = (**(code **)(*param_1 + 0x108))(param_1,uVar2,"decodeBuffer","(Ljava/lang/String;)[B");
    uVar2 = FUN_0010ba34(param_1,uVar2,uVar3);
    uVar2 = FUN_0010c24c(param_1,uVar2,uVar4,param_3);
    uVar3 = (**(code **)(*param_1 + 0x5c0))(param_1,uVar2,0);
    uVar4 = (**(code **)(*param_1 + 0x548))(param_1,param_4,0);
    __s = operator_new__(100);
    sprintf(__s,"%s%s",uVar4,PTR_s_2025_03_33_001581b8);
    uVar1 = (**(code **)(*param_1 + 0x558))(param_1,uVar2);
    uVar2 = FUN_0010dbb8(uVar3,__s,uVar1,0xffffffff);
    uVar3 = (**(code **)(*param_1 + 0x580))(param_1,uVar1);
    (**(code **)(*param_1 + 0x680))(param_1,uVar3,0,uVar1,uVar2);
    uVar2 = (**(code **)(*param_1 + 0x30))(param_1,"java/io/ByteArrayInputStream");
    uVar4 = (**(code **)(*param_1 + 0x108))(param_1,uVar2,"<init>","([B)V");
    uVar3 = FUN_0010ba34(param_1,uVar2,uVar4,uVar3);
    uVar4 = (**(code **)(*param_1 + 0x30))(param_1,"java/io/ObjectInputStream");
    uVar2 = (**(code **)(*param_1 + 0x108))(param_1,uVar2,"<init>","(Ljava/io/InputStream;)V");
    FUN_0010ba34(param_1,uVar4,uVar2,uVar3);
    (**(code **)(*param_1 + 0x108))(param_1,uVar4,"<init>","()Ljava/lang/Object");
  }
  return 1;
}


// ===== Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2 @ 0010ec0c =====

undefined8
Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4,undefined8 param_5
          )

{
  undefined4 uVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  char *__s;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    DAT_00158420 = param_5;
    uVar2 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/base64/BASE64Decoder");
    (**(code **)(*param_1 + 0x548))(param_1,param_3,0);
    uVar3 = (**(code **)(*param_1 + 0x108))(param_1,uVar2,"<init>",&DAT_0013e63a);
    uVar4 = (**(code **)(*param_1 + 0x108))(param_1,uVar2,"decodeBuffer","(Ljava/lang/String;)[B");
    uVar2 = FUN_0010ba34(param_1,uVar2,uVar3);
    uVar2 = FUN_0010c24c(param_1,uVar2,uVar4,param_3);
    uVar1 = (**(code **)(*param_1 + 0x558))(param_1,uVar2);
    uVar3 = (**(code **)(*param_1 + 0x5c0))(param_1,uVar2,0);
    __s = operator_new__(100);
    uVar4 = (**(code **)(*param_1 + 0x548))(param_1,DAT_00158420,0);
    sprintf(__s,"%s%s",uVar4,PTR_s_2025_03_33_001581b8);
    FUN_0010dbb8(uVar3,__s,uVar1,0xffffffff);
    (**(code **)(*param_1 + 0x558))(param_1,uVar2);
  }
  return 1;
}


// ===== Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2 @ 0010eda8 =====

undefined8
Java_com_hello_utils_FileUtils_n001__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4,undefined8 param_5
          ,undefined8 param_6)

{
  size_t __n;
  undefined4 uVar1;
  int iVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  undefined8 uVar5;
  undefined8 uVar6;
  char *pcVar7;
  void *__src;
  void *__dest;
  ulong uVar8;
  size_t sVar9;
  size_t __n_00;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    DAT_00158480 = param_5;
    DAT_00158478 = (**(code **)(*param_1 + 0x528))(param_1,param_5,0);
    DAT_00158458 = (**(code **)(*param_1 + 0x548))(param_1,param_3,0);
    DAT_00158420 = param_6;
    uVar3 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/base64/BASE64Decoder");
    (**(code **)(*param_1 + 0x548))(param_1,param_4,0);
    uVar4 = (**(code **)(*param_1 + 0x108))(param_1,uVar3,"<init>",&DAT_0013e63a);
    uVar5 = (**(code **)(*param_1 + 0x108))(param_1,uVar3,"decodeBuffer","(Ljava/lang/String;)[B");
    uVar3 = FUN_0010ba34(param_1,uVar3,uVar4);
    DAT_00158450 = (**(code **)(*param_1 + 0xa8))(param_1,uVar3);
    uVar4 = FUN_0010c24c(param_1,uVar3,uVar5,param_4);
    uVar1 = (**(code **)(*param_1 + 0x558))(param_1,uVar4);
    uVar6 = (**(code **)(*param_1 + 0x5c0))(param_1,uVar4,0);
    pcVar7 = operator_new__(100);
    sprintf(pcVar7,"%s%s","com.marquee",PTR_s_2025_03_33_001581b8);
    __src = (void *)FUN_0010dbb8(uVar6,pcVar7,uVar1,0xffffffff);
    DAT_00158468 = __src;
    iVar2 = (**(code **)(*param_1 + 0x558))(param_1,uVar4);
    __n_00 = (size_t)iVar2;
    uVar8 = (ulong)(iVar2 + 1);
    sVar9 = uVar8 - __n_00;
    if (iVar2 < -1) {
      uVar8 = 0xffffffffffffffff;
    }
    __n = 0;
    if (iVar2 != -1) {
      __n = sVar9;
    }
    __dest = operator_new__(uVar8);
    memset((void *)((long)__dest + __n_00),0,__n);
    memcpy(__dest,__src,__n_00);
    DAT_00158460 = __dest;
    (**(code **)(*param_1 + 0x548))(param_1,DAT_00158420,0);
    pcVar7 = operator_new__(100);
    sprintf(pcVar7,"%s%s","com.marquee",DAT_00158460);
    uVar3 = FUN_0010c24c(param_1,uVar3,uVar5,param_5);
    uVar4 = (**(code **)(*param_1 + 0x5c0))(param_1,uVar3,0);
    uVar1 = (**(code **)(*param_1 + 0x558))(param_1,uVar3);
    uVar3 = FUN_0010dbb8(uVar4,"2018-08-08com.marquee",uVar1,0xffffffff);
    DAT_00158470 = (**(code **)(*param_1 + 0x580))(param_1,uVar1);
    (**(code **)(*param_1 + 0x680))(param_1,DAT_00158470,0,uVar1,uVar3);
    uVar3 = (**(code **)(*param_1 + 0x388))(param_1,param_2,"byteToString","([B)V");
    FUN_0010f128(param_1,param_2,uVar3,DAT_00158470);
  }
  return 1;
}


// ===== Java_com_hello_utils_FileUtils_n002 @ 0010f1c8 =====

/* Java_com_hello_utils_FileUtils_n002(_JNIEnv*, _jclass*) */

void Java_com_hello_utils_FileUtils_n002(_JNIEnv *param_1,_jclass *param_2)

{
  return;
}


// ===== Java_com_hello_utils_FileUtils_n003 @ 0010f1cc =====

long * Java_com_hello_utils_FileUtils_n003(long *param_1,undefined8 param_2,undefined8 param_3)

{
  int iVar1;
  uint uVar2;
  void *__ptr;
  FILE *__s;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    iVar1 = (**(code **)(*param_1 + 0x558))(param_1,param_3);
    __ptr = (void *)(**(code **)(*param_1 + 0x5c0))(param_1,param_3,0);
    __s = fopen("/storage/emulated/0/data.dex","w");
    param_1 = (long *)0x0;
    if (__s != (FILE *)0x0) {
      fwrite(__ptr,(long)iVar1,1,__s);
      uVar2 = fclose(__s);
      return (long *)(ulong)uVar2;
    }
  }
  return param_1;
}


// ===== Java_com_hello_utils_FileUtils_n004 @ 0010f284 =====

void Java_com_hello_utils_FileUtils_n004(long *param_1,undefined8 param_2,undefined8 param_3)

{
  long lVar1;
  long lVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  long lVar5;
  undefined8 uVar6;
  undefined8 uVar7;
  undefined8 uVar8;
  
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    lVar1 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/utils/RefInvoke");
    if ((lVar1 != 0) &&
       (lVar2 = (**(code **)(*param_1 + 0x388))
                          (param_1,lVar1,"invokeStaticMethod",
                           "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;"), lVar2 != 0))
    {
      uVar3 = (**(code **)(*param_1 + 0x538))(param_1,"android.app.ActivityThread");
      uVar4 = (**(code **)(*param_1 + 0x538))(param_1,"currentActivityThread");
      lVar2 = FUN_0010cda4(param_1,lVar1,lVar2,uVar3,uVar4);
      if ((lVar2 != 0) &&
         (lVar5 = (**(code **)(*param_1 + 0x388))
                            (param_1,lVar1,"getFieldOjbect",
                             "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;"
                            ), lVar5 != 0)) {
        uVar4 = (**(code **)(*param_1 + 0x538))(param_1,"mPackages");
        lVar2 = FUN_0010cda4(param_1,lVar1,lVar5,uVar3,lVar2,uVar4);
        if ((lVar2 != 0) && (lVar5 = (**(code **)(*param_1 + 0xf8))(param_1,lVar2), lVar5 != 0)) {
          uVar3 = (**(code **)(*param_1 + 0x108))
                            (param_1,lVar5,&DAT_0013ede0,"(Ljava/lang/Object;)Ljava/lang/Object;");
          uVar4 = (**(code **)(*param_1 + 0x538))(param_1,"com.test");
          uVar3 = FUN_0010c24c(param_1,lVar2,uVar3,uVar4);
          uVar4 = (**(code **)(*param_1 + 0xf8))(param_1,uVar3);
          uVar4 = (**(code **)(*param_1 + 0x108))
                            (param_1,uVar4,&DAT_0013ede0,"()Ljava/lang/Object;");
          uVar3 = FUN_0010c24c(param_1,uVar3,uVar4);
          uVar4 = (**(code **)(*param_1 + 0x30))(param_1,"dalvik/system/PathClassLoader");
          uVar6 = (**(code **)(*param_1 + 0x108))
                            (param_1,uVar4,"<init>",
                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
          uVar7 = (**(code **)(*param_1 + 0x538))
                            (param_1,"/data/user/0/com.test/app_payload_odex/payload");
          uVar8 = (**(code **)(*param_1 + 0x538))
                            (param_1,"/data/app/com.test-F10QYtCbc13K2eW3sVuWBw==/lib/arm64");
          uVar4 = FUN_0010ba34(param_1,uVar4,uVar6,uVar7,uVar8,param_3);
          uVar6 = (**(code **)(*param_1 + 0x388))
                            (param_1,lVar1,"setFieldOjbect",
                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
                            );
          uVar7 = (**(code **)(*param_1 + 0x538))(param_1,"android.app.LoadedApk");
          uVar8 = (**(code **)(*param_1 + 0x538))(param_1,"mClassLoader");
          FUN_0010f128(param_1,lVar1,uVar6,uVar7,uVar8,uVar3,uVar4);
          return;
        }
      }
    }
  }
  return;
}


// ===== Java_com_hello_utils_FileUtils_n006 @ 0010f5c4 =====

void Java_com_hello_utils_FileUtils_n006(long *param_1,undefined8 param_2,undefined8 param_3)

{
  undefined8 uVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  
  if (DAT_001583d0 != '\0') {
    return;
  }
  DAT_001583cc = 1;
  uVar1 = (**(code **)(*param_1 + 0x30))(param_1,"dalvik/system/PathClassLoader");
  uVar2 = (**(code **)(*param_1 + 0x108))
                    (param_1,uVar1,"<init>",
                     "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
  uVar3 = (**(code **)(*param_1 + 0x538))(param_1,&DAT_0013ef69);
  FUN_0010ba34(param_1,uVar1,uVar2,uVar3,uVar3,param_3);
  return;
}


// ===== Java_com_hello_utils_FileUtils_n007 @ 0010f688 =====

/* Java_com_hello_utils_FileUtils_n007(_JNIEnv*, _jclass*, _jobject*) */

undefined8 Java_com_hello_utils_FileUtils_n007(_JNIEnv *param_1,_jclass *param_2,_jobject *param_3)

{
  FILE *__stream;
  long lVar1;
  long lVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  long lVar5;
  undefined8 uVar6;
  undefined8 uVar7;
  undefined8 uVar8;
  
  __stream = fopen(DAT_00158410,"r");
  if (__stream == (FILE *)0x0) {
    return 0;
  }
  if (DAT_001583d0 != '\0') {
    return 0;
  }
  fclose(__stream);
  lVar1 = (**(code **)(*(long *)param_1 + 0x30))(param_1,"com/hello/utils/RefInvoke");
  if (lVar1 != 0) {
    lVar2 = (**(code **)(*(long *)param_1 + 0x388))
                      (param_1,lVar1,"invokeStaticMethod",
                       "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
    if (lVar2 == 0) {
      return 0;
    }
    uVar3 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"android.app.ActivityThread");
    uVar4 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"currentActivityThread");
    lVar2 = FUN_0010cda4(param_1,lVar1,lVar2,uVar3,uVar4);
    if (lVar2 == 0) {
      return 0;
    }
    lVar5 = (**(code **)(*(long *)param_1 + 0x388))
                      (param_1,lVar1,"getFieldOjbect",
                       "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;")
    ;
    if (lVar5 != 0) {
      uVar4 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"mPackages");
      lVar2 = FUN_0010cda4(param_1,lVar1,lVar5,uVar3,lVar2,uVar4);
      if ((lVar2 != 0) &&
         (lVar5 = (**(code **)(*(long *)param_1 + 0xf8))(param_1,lVar2), lVar5 != 0)) {
        uVar3 = (**(code **)(*(long *)param_1 + 0x108))
                          (param_1,lVar5,&DAT_0013ede0,"(Ljava/lang/Object;)Ljava/lang/Object;");
        uVar4 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"com.test");
        uVar3 = FUN_0010c24c(param_1,lVar2,uVar3,uVar4);
        uVar4 = (**(code **)(*(long *)param_1 + 0xf8))(param_1,uVar3);
        uVar4 = (**(code **)(*(long *)param_1 + 0x108))
                          (param_1,uVar4,&DAT_0013ede0,"()Ljava/lang/Object;");
        uVar3 = FUN_0010c24c(param_1,uVar3,uVar4);
        uVar4 = (**(code **)(*(long *)param_1 + 0x30))(param_1,"dalvik/system/PathClassLoader");
        uVar6 = (**(code **)(*(long *)param_1 + 0x108))
                          (param_1,uVar4,"<init>",
                           "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
        uVar7 = (**(code **)(*(long *)param_1 + 0x538))
                          (param_1,"/data/user/0/com.test/app_payload_odex/payload");
        uVar8 = (**(code **)(*(long *)param_1 + 0x538))
                          (param_1,"/data/app/com.test-F10QYtCbc13K2eW3sVuWBw==/lib/arm64");
        uVar4 = FUN_0010ba34(param_1,uVar4,uVar6,uVar7,uVar8,param_3);
        uVar6 = (**(code **)(*(long *)param_1 + 0x388))
                          (param_1,lVar1,"setFieldOjbect",
                           "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
                          );
        uVar7 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"android.app.LoadedApk");
        uVar8 = (**(code **)(*(long *)param_1 + 0x538))(param_1,"mClassLoader");
        FUN_0010f128(param_1,lVar1,uVar6,uVar7,uVar8,uVar3,uVar4);
        return uVar4;
      }
    }
  }
  return 0;
}


// ===== Java_com_hello_utils_FileUtils_n005 @ 0010f9d8 =====

void Java_com_hello_utils_FileUtils_n005(long *param_1)

{
  undefined8 uVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  
  if (DAT_001583d0 != '\0') {
    return;
  }
  DAT_001583cc = 1;
  uVar1 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/utils/RefInvoke");
  uVar2 = (**(code **)(*param_1 + 0x108))
                    (param_1,uVar1,"<init>",
                     "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
  uVar3 = (**(code **)(*param_1 + 0x538))(param_1,&DAT_0013ef69);
  FUN_0010ba34(param_1,uVar1,uVar2,uVar3,uVar3);
  return;
}


// ===== FUN_0010fa94 @ 0010fa94 =====

bool FUN_0010fa94(long *param_1,long param_2,undefined8 param_3,char *param_4)

{
  int iVar1;
  void *__ptr;
  FILE *__s;
  size_t sVar2;
  bool bVar3;
  
  bVar3 = false;
  if ((param_2 != 0) && (param_4 != (char *)0x0)) {
    (**(code **)(*param_1 + 0x558))(param_1,param_2);
    iVar1 = (**(code **)(*param_1 + 0x558))(param_1,param_2);
    __ptr = (void *)(**(code **)(*param_1 + 0x5c0))(param_1,param_2,0);
    __s = fopen(param_4,"w");
    if (__s == (FILE *)0x0) {
      bVar3 = false;
    }
    else {
      sVar2 = fwrite(__ptr,(long)iVar1,1,__s);
      bVar3 = 0 < (int)sVar2;
      fclose(__s);
    }
  }
  return bVar3;
}


// ===== Java_com_hello_utils_FileUtils_n007__Ljava_lang_String_2Ljava_lang_String_2 @ 0011049c =====

undefined8
Java_com_hello_utils_FileUtils_n007__Ljava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4)

{
  DAT_001583f8 = (**(code **)(*param_1 + 0x548))(param_1,param_3,0);
  DAT_00158400 = (**(code **)(*param_1 + 0x548))(param_1,param_4,0);
  return 1;
}


// ===== Java_com_hello_utils_FileUtils_n007___3BZ @ 00110500 =====

uint Java_com_hello_utils_FileUtils_n007___3BZ
               (undefined8 param_1,undefined8 param_2,undefined8 param_3)

{
  uint uVar1;
  FILE *pFVar2;
  
  uVar1 = 1;
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    pFVar2 = fopen(DAT_00158410,"r");
    if ((pFVar2 == (FILE *)0x0) || (uVar1 = 0, DAT_001583e8 != '\0')) {
      uVar1 = FUN_0010fa94(param_1,param_3,0,DAT_00158410);
      uVar1 = uVar1 & 1;
    }
  }
  return uVar1;
}


// ===== Java_com_hello_utils_FileUtils_n008__Ljava_lang_Object_2 @ 00110584 =====

undefined1 Java_com_hello_utils_FileUtils_n008__Ljava_lang_Object_2(undefined8 param_1)

{
  undefined1 uVar1;
  
  uVar1 = 1;
  if (DAT_001583d0 == '\0') {
    DAT_001583cc = 1;
    FUN_0010dfa4(param_1,0,0,0);
    uVar1 = DAT_001583e8;
  }
  return uVar1;
}


// ===== Java_com_hello_utils_FileUtils_n009__Ljava_lang_Object_2Ljava_lang_Object_2 @ 00111100 =====

undefined8
Java_com_hello_utils_FileUtils_n009__Ljava_lang_Object_2Ljava_lang_Object_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4)

{
  size_t sVar1;
  int iVar2;
  int iVar3;
  long lVar4;
  undefined8 uVar5;
  undefined8 uVar6;
  undefined8 uVar7;
  char *__s;
  FILE *pFVar8;
  long lVar9;
  void *pvVar10;
  undefined *__dest;
  void *pvVar11;
  long lVar12;
  void *__dest_00;
  ulong uVar13;
  size_t sVar14;
  size_t sVar15;
  
  if (DAT_001583d0 != '\0') {
    return 1;
  }
  DAT_001583cc = 1;
  DAT_00158428 = param_3;
  lVar4 = FUN_0010e318(param_1,DAT_00158408,"com.init.ApplicationUtils",param_4,param_3);
  if (lVar4 == 0) {
LAB_001115c0:
    uVar5 = 0;
  }
  else {
    uVar5 = (**(code **)(*param_1 + 0xf8))(param_1,lVar4);
    uVar6 = FUN_0010e318(param_1,DAT_00158408,"com/dex/DexUtils",param_4,param_3);
    uVar7 = (**(code **)(*param_1 + 0xf8))(param_1,uVar6);
    uVar7 = (**(code **)(*param_1 + 0x108))
                      (param_1,uVar7,"getAppName","(Landroid/content/Context;)Ljava/lang/String;");
    DAT_00158420 = FUN_0010c24c(param_1,uVar6,uVar7,param_3);
    if (DAT_00158420 != 0) {
      DAT_00158448 = (**(code **)(*param_1 + 0x548))(param_1,DAT_00158420,0);
    }
    FUN_00110924(param_1,param_3,param_4,lVar4,uVar5,uVar6);
    __s = operator_new__(100);
    if (DAT_00158418 == 0) {
      sprintf(__s,"%s%s%s","/data/user/0/",DAT_001583f8,"/.zip");
    }
    else {
      sprintf(__s,"%s%s",DAT_00158418,&DAT_0013f2e3);
    }
    pFVar8 = fopen(__s,"r");
    if (pFVar8 == (FILE *)0x0) {
      DAT_001583e8 = '\x01';
LAB_001112d4:
      lVar9 = FUN_001105c8(param_1,uVar5,lVar4,"cls_key","payload",1);
      if (lVar9 == 0) goto LAB_001115c0;
      uVar6 = (**(code **)(*param_1 + 0x5c0))(param_1,lVar9,0);
      iVar2 = (**(code **)(*param_1 + 0x558))(param_1,lVar9);
      pvVar10 = (void *)FUN_0010dbb8(uVar6,PTR_s_2025_03_33_001581b8,iVar2,0xffffffff);
      sVar15 = (size_t)iVar2;
      uVar13 = (ulong)(iVar2 + 1);
      sVar14 = uVar13 - sVar15;
      if (iVar2 < -1) {
        uVar13 = 0xffffffffffffffff;
      }
      sVar1 = 0;
      if (iVar2 != -1) {
        sVar1 = sVar14;
      }
      __dest = operator_new__(uVar13);
      memset(__dest + sVar15,0,sVar1);
      memcpy(__dest,pvVar10,sVar15);
      PTR_s_payload_001581c0 = __dest;
      (**(code **)(*param_1 + 0x600))(param_1,lVar9,pvVar10,2);
      uVar6 = (**(code **)(*param_1 + 0x538))(param_1,__dest);
      uVar7 = (**(code **)(*param_1 + 0x108))(param_1,uVar5,&DAT_0013ebb6,"(Ljava/lang/String;)[B");
      lVar9 = FUN_0010c24c(param_1,lVar4,uVar7,uVar6);
      if (lVar9 == 0) {
        return 0;
      }
      pvVar11 = (void *)(**(code **)(*param_1 + 0x5c0))(param_1,lVar9,0);
      iVar2 = (**(code **)(*param_1 + 0x558))(param_1,lVar9);
      lVar12 = FUN_001105c8(param_1,uVar5,lVar4,"cls_key","dexKey",1);
      pvVar10 = pvVar11;
      if (lVar12 != 0) {
        iVar3 = (**(code **)(*param_1 + 0x558))(param_1,lVar12);
        uVar6 = (**(code **)(*param_1 + 0x5c0))(param_1,lVar12,0);
        pvVar10 = (void *)FUN_0010dbb8(uVar6,PTR_s_2025_03_33_001581b8,iVar3,0xffffffff);
        sVar15 = (size_t)iVar3;
        uVar13 = (ulong)(iVar3 + 1);
        sVar14 = uVar13 - sVar15;
        if (iVar3 < -1) {
          uVar13 = 0xffffffffffffffff;
        }
        sVar1 = 0;
        if (iVar3 != -1) {
          sVar1 = sVar14;
        }
        __dest_00 = operator_new__(uVar13);
        memset((void *)((long)__dest_00 + sVar15),0,sVar1);
        memcpy(__dest_00,pvVar10,sVar15);
        pvVar10 = (void *)FUN_0010dbb8(pvVar11,__dest_00,iVar2,0xffffffff);
      }
      pFVar8 = fopen(__s,"w");
      if (pFVar8 != (FILE *)0x0) {
        sVar14 = fwrite(pvVar10,(long)iVar2,1,pFVar8);
        fclose(pFVar8);
        if ((int)sVar14 < 1) goto LAB_001115c0;
      }
      DAT_00158410 = __s;
      (**(code **)(*param_1 + 0x600))(param_1,lVar9,pvVar11,2);
    }
    else {
      DAT_00158410 = __s;
      if (DAT_001583e8 != '\0') goto LAB_001112d4;
    }
    uVar5 = (**(code **)(*param_1 + 0x108))(param_1,uVar5,&DAT_0013ebb6,&DAT_0013e63a);
    FUN_0010cd04(param_1,lVar4,uVar5);
    uVar5 = 1;
  }
  return uVar5;
}


// ===== Java_com_hello_utils_FileUtils_n007__Ljava_lang_Object_2Ljava_lang_String_2Ljava_lang_String_2 @ 001115e4 =====

undefined8
Java_com_hello_utils_FileUtils_n007__Ljava_lang_Object_2Ljava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4,undefined8 param_5
          )

{
  int iVar1;
  undefined8 uVar2;
  undefined8 uVar3;
  undefined8 uVar4;
  undefined8 uVar5;
  long lVar6;
  char *__s;
  char *__s_00;
  void *__ptr;
  FILE *__s_01;
  size_t sVar7;
  
  if (DAT_001583d0 == '\0') {
    uVar2 = (**(code **)(*param_1 + 0x538))(param_1,DAT_00158458);
    uVar3 = (**(code **)(*param_1 + 0x30))(param_1,"com/hello/base64/BASE64Decoder");
    uVar4 = (**(code **)(*param_1 + 0x108))(param_1,uVar3,"<init>",&DAT_0013e63a);
    uVar5 = (**(code **)(*param_1 + 0x108))(param_1,uVar3,"decodeBuffer","(Ljava/lang/String;)[B");
    uVar3 = FUN_0010ba34(param_1,uVar3,uVar4);
    lVar6 = FUN_0010c24c(param_1,uVar3,uVar5,uVar2);
    if (lVar6 != 0) {
      (**(code **)(*param_1 + 0x5c0))(param_1,lVar6,0);
      (**(code **)(*param_1 + 0x558))(param_1,lVar6);
      uVar2 = (**(code **)(*param_1 + 0x538))(param_1,DAT_00158458);
      uVar3 = (**(code **)(*param_1 + 0x388))
                        (param_1,param_2,"readAssetsFile",
                         "(Landroid/content/Context;Ljava/lang/String;)[B");
      lVar6 = FUN_0010cda4(param_1,param_2,uVar3,param_3,uVar2);
      if (lVar6 != 0) {
        DAT_001583f8 = (**(code **)(*param_1 + 0x548))(param_1,param_4,0);
        DAT_001583f0 = (**(code **)(*param_1 + 0x548))(param_1,param_5,0);
        DAT_00158418 = 0;
        __s = operator_new__(100);
        sprintf(__s,"%s%s%s","/data/user/0/",DAT_001583f8,"/cache/.zip");
        __s_00 = operator_new__(100);
        sprintf(__s_00,"%s%s",DAT_001583f8,DAT_00158460);
        iVar1 = (**(code **)(*param_1 + 0x558))(param_1,lVar6);
        uVar2 = (**(code **)(*param_1 + 0x5c0))(param_1,lVar6,0);
        __ptr = (void *)FUN_0010dbb8(uVar2,__s_00,iVar1,0xffffffff);
        if ((__ptr != (void *)0x0) && (__s_01 = fopen(__s,"w"), __s_01 != (FILE *)0x0)) {
          sVar7 = fwrite(__ptr,(long)iVar1,1,__s_01);
          fclose(__s_01);
          if (0 < (int)sVar7) {
            (**(code **)(*param_1 + 0x600))(param_1,0,__ptr,2);
            DAT_00158408 = __s;
            goto LAB_001118a0;
          }
        }
      }
    }
    uVar2 = 0;
  }
  else {
LAB_001118a0:
    uVar2 = 1;
  }
  return uVar2;
}


// ===== Java_com_hello_utils_FileUtils_n007___3BLjava_lang_String_2Ljava_lang_String_2 @ 001118c8 =====

undefined8
Java_com_hello_utils_FileUtils_n007___3BLjava_lang_String_2Ljava_lang_String_2
          (long *param_1,undefined8 param_2,long param_3,undefined8 param_4,undefined8 param_5)

{
  long lVar1;
  int iVar2;
  undefined8 uVar3;
  char *__s;
  void *pvVar4;
  FILE *pFVar5;
  size_t sVar6;
  
  __s = DAT_00158408;
  if (DAT_001583d0 == '\0') {
    if (param_3 == 0) {
      return 0;
    }
    DAT_001583f0 = (**(code **)(*param_1 + 0x548))(param_1,param_5,0);
    uVar3 = (**(code **)(*param_1 + 0x548))(param_1,param_4,0);
    DAT_00158418 = 0;
    DAT_001583f8 = uVar3;
    __s = operator_new__(100);
    sprintf(__s,"%s%s%s","/data/user/0/",uVar3,"/cache/.zip");
    lVar1 = DAT_001583e0;
    iVar2 = (**(code **)(*param_1 + 0x558))(param_1,param_3);
    if (lVar1 == 0) {
      iVar2 = (**(code **)(*param_1 + 0x558))(param_1,param_3);
      pvVar4 = (void *)(**(code **)(*param_1 + 0x5c0))(param_1,param_3,0);
      pFVar5 = fopen(__s,"w");
      if (pFVar5 != (FILE *)0x0) {
        sVar6 = fwrite(pvVar4,(long)iVar2,1,pFVar5);
        fclose(pFVar5);
        if (0 < (int)sVar6) goto LAB_00111aa0;
      }
    }
    else {
      uVar3 = (**(code **)(*param_1 + 0x5c0))(param_1,param_3,0);
      pvVar4 = (void *)FUN_0010dbb8(uVar3,DAT_001583e0,iVar2,0xffffffff);
      pFVar5 = fopen(__s,"w");
      if (pFVar5 != (FILE *)0x0) {
        sVar6 = fwrite(pvVar4,(long)iVar2,1,pFVar5);
        fclose(pFVar5);
        if (0 < (int)sVar6) {
          (**(code **)(*param_1 + 0x600))(param_1,param_3,pvVar4,2);
          goto LAB_00111aa0;
        }
      }
    }
    uVar3 = 0;
  }
  else {
LAB_00111aa0:
    DAT_00158408 = __s;
    uVar3 = 1;
  }
  return uVar3;
}


// ===== Java_com_hello_utils_FileUtils_decrypt @ 00111ac0 =====

/* Java_com_hello_utils_FileUtils_decrypt(_JNIEnv*, _jclass*, _jbyteArray*, _jstring*) */

undefined8
Java_com_hello_utils_FileUtils_decrypt
          (_JNIEnv *param_1,_jclass *param_2,_jbyteArray *param_3,_jstring *param_4)

{
  return 0;
}


// ===== Java_com_hello_utils_FileUtils_encrypt @ 00111ac8 =====

undefined8 Java_com_hello_utils_FileUtils_encrypt(void)

{
  return 0;
}

