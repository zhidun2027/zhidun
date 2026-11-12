.class public Lcom/youyou/shell/StubApp;
.super Landroid/app/Application;
.source "StubApp.java"

.method public constructor <init>()V
    .registers 1
    .prologue
    .line 17
    invoke-direct { p0 }, Landroid/app/Application;-><init>()V
    return-void
.end method

.method protected attachBaseContext(Landroid/content/Context;)V
    .registers 4
    .prologue
    .line 20
    invoke-super { p0, p1 }, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V
    .line 21
    invoke-static { }, Ljava/lang/System;->currentTimeMillis()J
    move-result-wide v0
    invoke-static { p0, v0, v1 }, Lcom/youyou/AppLoad;->time(Ljava/lang/Object;J)I
    .line 22
    invoke-virtual { p0 }, Lcom/youyou/shell/StubApp;->getBaseContext()Landroid/content/Context;
    move-result-object v0
    invoke-virtual { v0 }, Landroid/content/Context;->getClassLoader()Ljava/lang/ClassLoader;
    move-result-object v0
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I
    invoke-static { v0, p0, v1 }, Lcom/youyou/AppLoad;->load(Ljava/lang/ClassLoader;Ljava/lang/Object;I)Z
    .line 60
    return-void
.end method

.method public onCreate()V
    .registers 3
    .prologue
    .line 63
    invoke-virtual { p0 }, Lcom/youyou/shell/StubApp;->getBaseContext()Landroid/content/Context;
    move-result-object v0
    invoke-virtual { v0 }, Landroid/content/Context;->getClassLoader()Ljava/lang/ClassLoader;
    move-result-object v0
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I
    invoke-static { v0, p0, v1 }, Lcom/youyou/AppLoad;->create(Ljava/lang/ClassLoader;Ljava/lang/Object;I)Z
    .line 64
    invoke-super { p0 }, Landroid/app/Application;->onCreate()V
    .line 65
    return-void
.end method
