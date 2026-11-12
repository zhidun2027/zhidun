/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksigner;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordRetriever
implements AutoCloseable {
    public static final String SPEC_STDIN = "stdin";
    private final Charset mConsoleEncoding;
    private final Map<File, InputStream> mFileInputStreams = new HashMap<File, InputStream>();
    private boolean mClosed;

    public PasswordRetriever() {
        this.mConsoleEncoding = PasswordRetriever.getConsoleEncoding();
    }

    public List<char[]> getPasswords(String spec, String description, Charset ... additionalPwdEncodings) throws IOException {
        this.assertNotClosed();
        if (spec.startsWith("pass:")) {
            char[] pwd = spec.substring("pass:".length()).toCharArray();
            return this.getPasswords(pwd, additionalPwdEncodings);
        }
        if (SPEC_STDIN.equals(spec)) {
            Console console = System.console();
            if (console != null) {
                char[] pwd = console.readPassword(description + ": ", new Object[0]);
                if (pwd == null) {
                    throw new IOException("Failed to read " + description + ": console closed");
                }
                return this.getPasswords(pwd, additionalPwdEncodings);
            }
            System.out.println(description + ": ");
            byte[] encodedPwd = PasswordRetriever.readEncodedPassword(System.in);
            if (encodedPwd.length == 0) {
                throw new IOException("Failed to read " + description + ": standard input closed");
            }
            return this.getPasswords(encodedPwd, Charset.defaultCharset(), additionalPwdEncodings);
        }
        if (spec.startsWith("file:")) {
            byte[] encodedPwd;
            String name = spec.substring("file:".length());
            File file = new File(name).getCanonicalFile();
            InputStream in = this.mFileInputStreams.get(file);
            if (in == null) {
                in = new FileInputStream(file);
                this.mFileInputStreams.put(file, in);
            }
            if ((encodedPwd = PasswordRetriever.readEncodedPassword(in)).length == 0) {
                throw new IOException("Failed to read " + description + " : end of file reached in " + file);
            }
            return this.getPasswords(encodedPwd, Charset.defaultCharset(), additionalPwdEncodings);
        }
        if (spec.startsWith("env:")) {
            String name = spec.substring("env:".length());
            String value = System.getenv(name);
            if (value == null) {
                throw new IOException("Failed to read " + description + ": environment variable " + value + " not specified");
            }
            return this.getPasswords(value.toCharArray(), additionalPwdEncodings);
        }
        throw new IOException("Unsupported password spec for " + description + ": " + spec);
    }

    private List<char[]> getPasswords(char[] pwd, Charset ... additionalEncodings) {
        ArrayList<char[]> passwords = new ArrayList<char[]>(3);
        this.addPasswords(passwords, pwd, additionalEncodings);
        return passwords;
    }

    private List<char[]> getPasswords(byte[] encodedPwd, Charset encodingForDecoding, Charset ... additionalEncodings) {
        ArrayList<char[]> passwords = new ArrayList<char[]>(4);
        try {
            char[] pwd = PasswordRetriever.decodePassword(encodedPwd, encodingForDecoding);
            this.addPasswords(passwords, pwd, additionalEncodings);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        PasswordRetriever.addPassword(passwords, PasswordRetriever.castBytesToChars(encodedPwd));
        return passwords;
    }

    private void addPasswords(List<char[]> passwords, char[] pwd, Charset ... additionalEncodings) {
        char[] encodedPwd;
        if (additionalEncodings != null && additionalEncodings.length > 0) {
            for (Charset encoding : additionalEncodings) {
                try {
                    char[] encodedPwd2 = PasswordRetriever.castBytesToChars(PasswordRetriever.encodePassword(pwd, encoding));
                    PasswordRetriever.addPassword(passwords, encodedPwd2);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        PasswordRetriever.addPassword(passwords, pwd);
        if (this.mConsoleEncoding != null) {
            try {
                encodedPwd = PasswordRetriever.castBytesToChars(PasswordRetriever.encodePassword(pwd, this.mConsoleEncoding));
                PasswordRetriever.addPassword(passwords, encodedPwd);
            }
            catch (IOException encodedPwd3) {
                // empty catch block
            }
        }
        try {
            encodedPwd = PasswordRetriever.castBytesToChars(PasswordRetriever.encodePassword(pwd, Charset.defaultCharset()));
            PasswordRetriever.addPassword(passwords, encodedPwd);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static void addPassword(List<char[]> passwords, char[] password) {
        for (char[] existingPassword : passwords) {
            if (!Arrays.equals(password, existingPassword)) continue;
            return;
        }
        passwords.add(password);
    }

    private static byte[] encodePassword(char[] pwd, Charset cs) throws IOException {
        ByteBuffer pwdBytes = cs.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode(CharBuffer.wrap(pwd));
        byte[] encoded = new byte[pwdBytes.remaining()];
        pwdBytes.get(encoded);
        return encoded;
    }

    private static char[] decodePassword(byte[] pwdBytes, Charset encoding) throws IOException {
        CharBuffer pwdChars = encoding.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode(ByteBuffer.wrap(pwdBytes));
        char[] result = new char[pwdChars.remaining()];
        pwdChars.get(result);
        return result;
    }

    private static char[] castBytesToChars(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        char[] chars = new char[bytes.length];
        for (int i2 = 0; i2 < bytes.length; ++i2) {
            chars[i2] = (char)(bytes[i2] & 0xFF);
        }
        return chars;
    }

    private static boolean isJava9OrHigherErrOnTheSideOfCaution() {
        String versionString = System.getProperty("java.specification.version");
        if (versionString == null) {
            return true;
        }
        return !versionString.startsWith("1.");
    }

    private static Charset getConsoleEncoding() {
        if (PasswordRetriever.isJava9OrHigherErrOnTheSideOfCaution()) {
            return null;
        }
        String consoleCharsetName = null;
        try {
            Method encodingMethod = Console.class.getDeclaredMethod("encoding", new Class[0]);
            encodingMethod.setAccessible(true);
            consoleCharsetName = (String)encodingMethod.invoke(null, new Object[0]);
        }
        catch (ReflectiveOperationException ignored) {
            return null;
        }
        if (consoleCharsetName == null) {
            return Charset.defaultCharset();
        }
        try {
            return PasswordRetriever.getCharsetByName(consoleCharsetName);
        }
        catch (IllegalArgumentException e2) {
            return null;
        }
    }

    public static Charset getCharsetByName(String charsetName) throws IllegalArgumentException {
        if ("cp65001".equalsIgnoreCase(charsetName)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(charsetName);
    }

    private static byte[] readEncodedPassword(InputStream in) throws IOException {
        int b2;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        while ((b2 = in.read()) != -1 && b2 != 10) {
            if (b2 == 13) {
                int next = in.read();
                if (next == -1 || next == 10) break;
                if (!(in instanceof PushbackInputStream)) {
                    in = new PushbackInputStream(in);
                }
                ((PushbackInputStream)in).unread(next);
            }
            result.write(b2);
        }
        return result.toByteArray();
    }

    private void assertNotClosed() {
        if (this.mClosed) {
            throw new IllegalStateException("Closed");
        }
    }

    @Override
    public void close() {
        for (InputStream in : this.mFileInputStreams.values()) {
            try {
                in.close();
            }
            catch (IOException iOException) {}
        }
        this.mFileInputStreams.clear();
        this.mClosed = true;
    }
}

