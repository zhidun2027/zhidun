.class public Lcom/youyou/utils/base64/BASE64Decoder;
.super Lcom/youyou/utils/base64/CharacterDecoder;
.source "BASE64Decoder.java"


# static fields
.field private static final pem_array:[C

.field private static final pem_convert_array:[B


# instance fields
.field decode_buffer:[B


# direct methods
.method static constructor <clinit>()V
    .locals 5

    .prologue
    .line 78
    const/16 v2, 0x40

    new-array v2, v2, [C

    fill-array-data v2, :array_0

    sput-object v2, Lcom/youyou/utils/base64/BASE64Decoder;->pem_array:[C

    .line 90
    const/16 v2, 0x100

    new-array v2, v2, [B

    sput-object v2, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    .line 93
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    const/16 v2, 0xff

    if-lt v0, v2, :cond_0

    .line 96
    const/4 v1, 0x0

    .local v1, "j":I
    :goto_1
    sget-object v2, Lcom/youyou/utils/base64/BASE64Decoder;->pem_array:[C

    array-length v2, v2

    if-lt v1, v2, :cond_1

    .line 99
    return-void

    .line 94
    .end local v1    # "j":I
    :cond_0
    sget-object v2, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    const/4 v3, -0x1

    aput-byte v3, v2, v0

    .line 93
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 97
    .restart local v1    # "j":I
    :cond_1
    sget-object v2, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    sget-object v3, Lcom/youyou/utils/base64/BASE64Decoder;->pem_array:[C

    aget-char v3, v3, v1

    int-to-byte v4, v1

    aput-byte v4, v2, v3

    .line 96
    add-int/lit8 v1, v1, 0x1

    goto :goto_1

    .line 78
    nop

    :array_0
    .array-data 2
        0x41s
        0x42s
        0x43s
        0x44s
        0x45s
        0x46s
        0x47s
        0x48s
        0x49s
        0x4as
        0x4bs
        0x4cs
        0x4ds
        0x4es
        0x4fs
        0x50s
        0x51s
        0x52s
        0x53s
        0x54s
        0x55s
        0x56s
        0x57s
        0x58s
        0x59s
        0x5as
        0x61s
        0x62s
        0x63s
        0x64s
        0x65s
        0x66s
        0x67s
        0x68s
        0x69s
        0x6as
        0x6bs
        0x6cs
        0x6ds
        0x6es
        0x6fs
        0x70s
        0x71s
        0x72s
        0x73s
        0x74s
        0x75s
        0x76s
        0x77s
        0x78s
        0x79s
        0x7as
        0x30s
        0x31s
        0x32s
        0x33s
        0x34s
        0x35s
        0x36s
        0x37s
        0x38s
        0x39s
        0x2bs
        0x2fs
    .end array-data
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    .line 62
    invoke-direct {p0}, Lcom/youyou/utils/base64/CharacterDecoder;-><init>()V

    .line 100
    const/4 v0, 0x4

    new-array v0, v0, [B

    iput-object v0, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    .line 62
    return-void
.end method


# virtual methods
.method protected bytesPerAtom()I
    .locals 1

    .prologue
    .line 66
    const/4 v0, 0x4

    return v0
.end method

.method protected bytesPerLine()I
    .locals 1

    .prologue
    .line 71
    const/16 v0, 0x48

    return v0
.end method

.method protected decodeAtom(Ljava/io/PushbackInputStream;Ljava/io/OutputStream;I)V
    .locals 12
    .param p1, "inStream"    # Ljava/io/PushbackInputStream;
    .param p2, "outStream"    # Ljava/io/OutputStream;
    .param p3, "rem"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    const/4 v11, 0x1

    const/4 v10, 0x0

    const/4 v9, -0x1

    const/4 v8, 0x3

    const/4 v7, 0x2

    .line 107
    iget-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    if-nez v5, :cond_0

    .line 108
    const/4 v5, 0x4

    new-array v5, v5, [B

    iput-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    .line 111
    :cond_0
    const/4 v0, -0x1

    .local v0, "a":B
    const/4 v1, -0x1

    .local v1, "b":B
    const/4 v2, -0x1

    .local v2, "c":B
    const/4 v3, -0x1

    .line 113
    .local v3, "d":B
    if-ge p3, v7, :cond_1

    .line 114
    new-instance v5, Ljava/io/IOException;

    const-string v6, "BASE64Decoder: Not enough bytes for an atom."

    invoke-direct {v5, v6}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 117
    :cond_1
    invoke-virtual {p1}, Ljava/io/PushbackInputStream;->read()I

    move-result v4

    .line 118
    .local v4, "i":I
    if-ne v4, v9, :cond_2

    .line 119
    new-instance v5, Ljava/io/IOException;

    invoke-direct {v5}, Ljava/io/IOException;-><init>()V

    throw v5

    .line 121
    :cond_2
    const/16 v5, 0xa

    if-eq v4, v5, :cond_1

    const/16 v5, 0xd

    if-eq v4, v5, :cond_1

    .line 122
    iget-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    int-to-byte v6, v4

    aput-byte v6, v5, v10

    .line 124
    iget-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    add-int/lit8 v6, p3, -0x1

    invoke-virtual {p0, p1, v5, v11, v6}, Lcom/youyou/utils/base64/BASE64Decoder;->readFully(Ljava/io/InputStream;[BII)I

    move-result v4

    .line 125
    if-ne v4, v9, :cond_3

    .line 126
    new-instance v5, Ljava/io/IOException;

    invoke-direct {v5}, Ljava/io/IOException;-><init>()V

    throw v5

    .line 129
    :cond_3
    if-le p3, v8, :cond_4

    iget-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v5, v5, v8

    const/16 v6, 0x3d

    if-ne v5, v6, :cond_4

    .line 130
    const/4 p3, 0x3

    .line 132
    :cond_4
    if-le p3, v7, :cond_5

    iget-object v5, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v5, v5, v7

    const/16 v6, 0x3d

    if-ne v5, v6, :cond_5

    .line 133
    const/4 p3, 0x2

    .line 135
    :cond_5
    packed-switch p3, :pswitch_data_0

    .line 148
    :goto_0
    packed-switch p3, :pswitch_data_1

    .line 162
    :goto_1
    return-void

    .line 137
    :pswitch_0
    sget-object v5, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    iget-object v6, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v6, v6, v8

    and-int/lit16 v6, v6, 0xff

    aget-byte v3, v5, v6

    .line 140
    :pswitch_1
    sget-object v5, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    iget-object v6, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v6, v6, v7

    and-int/lit16 v6, v6, 0xff

    aget-byte v2, v5, v6

    .line 143
    :pswitch_2
    sget-object v5, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    iget-object v6, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v6, v6, v11

    and-int/lit16 v6, v6, 0xff

    aget-byte v1, v5, v6

    .line 144
    sget-object v5, Lcom/youyou/utils/base64/BASE64Decoder;->pem_convert_array:[B

    iget-object v6, p0, Lcom/youyou/utils/base64/BASE64Decoder;->decode_buffer:[B

    aget-byte v6, v6, v10

    and-int/lit16 v6, v6, 0xff

    aget-byte v0, v5, v6

    goto :goto_0

    .line 150
    :pswitch_3
    shl-int/lit8 v5, v0, 0x2

    and-int/lit16 v5, v5, 0xfc

    ushr-int/lit8 v6, v1, 0x4

    and-int/lit8 v6, v6, 0x3

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    goto :goto_1

    .line 153
    :pswitch_4
    shl-int/lit8 v5, v0, 0x2

    and-int/lit16 v5, v5, 0xfc

    ushr-int/lit8 v6, v1, 0x4

    and-int/lit8 v6, v6, 0x3

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    .line 154
    shl-int/lit8 v5, v1, 0x4

    and-int/lit16 v5, v5, 0xf0

    ushr-int/lit8 v6, v2, 0x2

    and-int/lit8 v6, v6, 0xf

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    goto :goto_1

    .line 157
    :pswitch_5
    shl-int/lit8 v5, v0, 0x2

    and-int/lit16 v5, v5, 0xfc

    ushr-int/lit8 v6, v1, 0x4

    and-int/lit8 v6, v6, 0x3

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    .line 158
    shl-int/lit8 v5, v1, 0x4

    and-int/lit16 v5, v5, 0xf0

    ushr-int/lit8 v6, v2, 0x2

    and-int/lit8 v6, v6, 0xf

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    .line 159
    shl-int/lit8 v5, v2, 0x6

    and-int/lit16 v5, v5, 0xc0

    and-int/lit8 v6, v3, 0x3f

    or-int/2addr v5, v6

    int-to-byte v5, v5

    invoke-virtual {p2, v5}, Ljava/io/OutputStream;->write(I)V

    goto :goto_1

    .line 135
    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    .line 148
    :pswitch_data_1
    .packed-switch 0x2
        :pswitch_3
        :pswitch_4
        :pswitch_5
    .end packed-switch
.end method
