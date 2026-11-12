.class public Lcom/youyou/AppLoad;
.super Ljava/lang/Object;
.source "AppLoad.java"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 13
    const-string v0, "jiaguo"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    .line 14
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static native create(Ljava/lang/ClassLoader;Ljava/lang/Object;I)Z
.end method

.method public static native init(Ljava/lang/ClassLoader;Ljava/lang/Object;)Z
.end method

.method public static native load(Ljava/lang/ClassLoader;Ljava/lang/Object;I)Z
.end method

.method public static native test(Ljava/lang/ClassLoader;Ljava/lang/Object;I)[B
.end method

.method public static native time(Ljava/lang/Object;J)I
.end method
