/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksigner;

import com.android.apksig.SigningCertificateLineage;
import com.android.apksig.internal.util.X509CertificateUtils;
import com.android.apksigner.ParameterException;
import com.android.apksigner.PasswordRetriever;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SignerParams {
    private String name;
    private String keystoreFile;
    private String keystoreKeyAlias;
    private String keystorePasswordSpec;
    private String keyPasswordSpec;
    private Charset passwordCharset;
    private String keystoreType;
    private String keystoreProviderName;
    private String keystoreProviderClass;
    private String keystoreProviderArg;
    private String keyFile;
    private String certFile;
    private String v1SigFileBasename;
    private PrivateKey privateKey;
    private List<X509Certificate> certs;
    private final SigningCertificateLineage.SignerCapabilities.Builder signerCapabilitiesBuilder = new SigningCertificateLineage.SignerCapabilities.Builder();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystoreKeyAlias() {
        return this.keystoreKeyAlias;
    }

    public void setKeystoreKeyAlias(String keystoreKeyAlias) {
        this.keystoreKeyAlias = keystoreKeyAlias;
    }

    public void setKeystorePasswordSpec(String keystorePasswordSpec) {
        this.keystorePasswordSpec = keystorePasswordSpec;
    }

    public void setKeyPasswordSpec(String keyPasswordSpec) {
        this.keyPasswordSpec = keyPasswordSpec;
    }

    public void setPasswordCharset(Charset passwordCharset) {
        this.passwordCharset = passwordCharset;
    }

    public void setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
    }

    public void setKeystoreProviderName(String keystoreProviderName) {
        this.keystoreProviderName = keystoreProviderName;
    }

    public void setKeystoreProviderClass(String keystoreProviderClass) {
        this.keystoreProviderClass = keystoreProviderClass;
    }

    public void setKeystoreProviderArg(String keystoreProviderArg) {
        this.keystoreProviderArg = keystoreProviderArg;
    }

    public String getKeyFile() {
        return this.keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public String getV1SigFileBasename() {
        return this.v1SigFileBasename;
    }

    public void setV1SigFileBasename(String v1SigFileBasename) {
        this.v1SigFileBasename = v1SigFileBasename;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public List<X509Certificate> getCerts() {
        return this.certs;
    }

    public SigningCertificateLineage.SignerCapabilities.Builder getSignerCapabilitiesBuilder() {
        return this.signerCapabilitiesBuilder;
    }

    boolean isEmpty() {
        return this.name == null && this.keystoreFile == null && this.keystoreKeyAlias == null && this.keystorePasswordSpec == null && this.keyPasswordSpec == null && this.passwordCharset == null && this.keystoreType == null && this.keystoreProviderName == null && this.keystoreProviderClass == null && this.keystoreProviderArg == null && this.keyFile == null && this.certFile == null && this.v1SigFileBasename == null && this.privateKey == null && this.certs == null;
    }

    public void loadPrivateKeyAndCerts(PasswordRetriever passwordRetriever) throws Exception {
        if (this.keystoreFile != null) {
            if (this.keyFile != null) {
                throw new ParameterException("--ks and --key may not be specified at the same time");
            }
            if (this.certFile != null) {
                throw new ParameterException("--ks and --cert may not be specified at the same time");
            }
            this.loadPrivateKeyAndCertsFromKeyStore(passwordRetriever);
        } else if (this.keyFile != null) {
            this.loadPrivateKeyAndCertsFromFiles(passwordRetriever);
        } else {
            throw new ParameterException("KeyStore (--ks) or private key file (--key) must be specified");
        }
    }

    private void loadPrivateKeyAndCertsFromKeyStore(PasswordRetriever passwordRetriever) throws Exception {
        Charset[] charsetArray;
        String keystorePasswordSpec;
        KeyStore ks;
        String ksType;
        if (this.keystoreFile == null) {
            throw new ParameterException("KeyStore (--ks) must be specified");
        }
        String string = ksType = this.keystoreType != null ? this.keystoreType : KeyStore.getDefaultType();
        if (this.keystoreProviderName != null) {
            ks = KeyStore.getInstance(ksType, this.keystoreProviderName);
        } else if (this.keystoreProviderClass != null) {
            Provider ksProvider;
            Class<?> ksProviderClass = Class.forName(this.keystoreProviderClass);
            if (!Provider.class.isAssignableFrom(ksProviderClass)) {
                throw new ParameterException("Keystore Provider class " + this.keystoreProviderClass + " not subclass of " + Provider.class.getName());
            }
            if (this.keystoreProviderArg != null) {
                try {
                    ksProvider = (Provider)ksProviderClass.getConstructor(String.class).newInstance(this.keystoreProviderArg);
                }
                catch (NoSuchMethodException e2) {
                    ksProvider = (Provider)ksProviderClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    ksProvider = (Provider)ksProviderClass.getMethod("configure", String.class).invoke((Object)ksProvider, this.keystoreProviderArg);
                }
            } else {
                ksProvider = (Provider)ksProviderClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            ks = KeyStore.getInstance(ksType, ksProvider);
        } else {
            ks = KeyStore.getInstance(ksType);
        }
        String string2 = keystorePasswordSpec = this.keystorePasswordSpec != null ? this.keystorePasswordSpec : "stdin";
        if (this.passwordCharset != null) {
            Charset[] charsetArray2 = new Charset[1];
            charsetArray = charsetArray2;
            charsetArray2[0] = this.passwordCharset;
        } else {
            charsetArray = new Charset[]{};
        }
        Charset[] additionalPasswordEncodings = charsetArray;
        List<char[]> keystorePasswords = passwordRetriever.getPasswords(keystorePasswordSpec, "Keystore password for " + this.name, additionalPasswordEncodings);
        SignerParams.loadKeyStoreFromFile(ks, "NONE".equals(this.keystoreFile) ? null : this.keystoreFile, keystorePasswords);
        String keyAlias = null;
        PrivateKey key = null;
        try {
            Key entryKey;
            if (this.keystoreKeyAlias == null) {
                Enumeration<String> aliases = ks.aliases();
                if (aliases != null) {
                    while (aliases.hasMoreElements()) {
                        String entryAlias = aliases.nextElement();
                        if (!ks.isKeyEntry(entryAlias)) continue;
                        keyAlias = entryAlias;
                        if (this.keystoreKeyAlias != null) {
                            throw new ParameterException(this.keystoreFile + " contains multiple key entries. --ks-key-alias option must be used to specify which entry to use.");
                        }
                        this.keystoreKeyAlias = keyAlias;
                    }
                }
                if (this.keystoreKeyAlias == null) {
                    throw new ParameterException(this.keystoreFile + " does not contain key entries");
                }
            }
            if (!ks.isKeyEntry(keyAlias = this.keystoreKeyAlias)) {
                throw new ParameterException(this.keystoreFile + " entry \"" + keyAlias + "\" does not contain a key");
            }
            if (this.keyPasswordSpec != null) {
                List<char[]> keyPasswords = passwordRetriever.getPasswords(this.keyPasswordSpec, "Key \"" + keyAlias + "\" password for " + this.name, additionalPasswordEncodings);
                entryKey = SignerParams.getKeyStoreKey(ks, keyAlias, keyPasswords);
            } else {
                try {
                    entryKey = SignerParams.getKeyStoreKey(ks, keyAlias, keystorePasswords);
                }
                catch (UnrecoverableKeyException expected) {
                    List<char[]> keyPasswords = passwordRetriever.getPasswords("stdin", "Key \"" + keyAlias + "\" password for " + this.name, additionalPasswordEncodings);
                    entryKey = SignerParams.getKeyStoreKey(ks, keyAlias, keyPasswords);
                }
            }
            if (entryKey == null) {
                throw new ParameterException(this.keystoreFile + " entry \"" + keyAlias + "\" does not contain a key");
            }
            if (!(entryKey instanceof PrivateKey)) {
                throw new ParameterException(this.keystoreFile + " entry \"" + keyAlias + "\" does not contain a private key. It contains a key of algorithm: " + entryKey.getAlgorithm());
            }
            key = (PrivateKey)entryKey;
        }
        catch (UnrecoverableKeyException e3) {
            throw new IOException("Failed to obtain key with alias \"" + keyAlias + "\" from " + this.keystoreFile + ". Wrong password?", e3);
        }
        this.privateKey = key;
        Certificate[] certChain = ks.getCertificateChain(keyAlias);
        if (certChain == null || certChain.length == 0) {
            throw new ParameterException(this.keystoreFile + " entry \"" + keyAlias + "\" does not contain certificates");
        }
        this.certs = new ArrayList<X509Certificate>(certChain.length);
        for (Certificate cert : certChain) {
            this.certs.add((X509Certificate)cert);
        }
    }

    private static void loadKeyStoreFromFile(KeyStore ks, String file, List<char[]> passwords) throws Exception {
        Exception lastFailure = null;
        for (char[] password : passwords) {
            try {
                if (file != null) {
                    try (FileInputStream in = new FileInputStream(file);){
                        ks.load(in, password);
                    }
                } else {
                    ks.load(null, password);
                }
                return;
            }
            catch (Exception e2) {
                lastFailure = e2;
            }
        }
        if (lastFailure == null) {
            throw new RuntimeException("No keystore passwords");
        }
        throw lastFailure;
    }

    private static Key getKeyStoreKey(KeyStore ks, String keyAlias, List<char[]> passwords) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        UnrecoverableKeyException lastFailure = null;
        for (char[] password : passwords) {
            try {
                return ks.getKey(keyAlias, password);
            }
            catch (UnrecoverableKeyException e2) {
                lastFailure = e2;
            }
        }
        if (lastFailure == null) {
            throw new RuntimeException("No key passwords");
        }
        throw lastFailure;
    }

    private void loadPrivateKeyAndCertsFromFiles(PasswordRetriever passwordRetriever) throws Exception {
        Collection<? extends Certificate> certs;
        PKCS8EncodedKeySpec keySpec;
        if (this.keyFile == null) {
            throw new ParameterException("Private key file (--key) must be specified");
        }
        if (this.certFile == null) {
            throw new ParameterException("Certificate file (--cert) must be specified");
        }
        byte[] privateKeyBlob = SignerParams.readFully(new File(this.keyFile));
        try {
            Charset[] charsetArray;
            String passwordSpec;
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(privateKeyBlob);
            String string = passwordSpec = this.keyPasswordSpec != null ? this.keyPasswordSpec : "stdin";
            if (this.passwordCharset != null) {
                Charset[] charsetArray2 = new Charset[1];
                charsetArray = charsetArray2;
                charsetArray2[0] = this.passwordCharset;
            } else {
                charsetArray = new Charset[]{};
            }
            Charset[] additionalPasswordEncodings = charsetArray;
            List<char[]> list = passwordRetriever.getPasswords(passwordSpec, "Private key password for " + this.name, additionalPasswordEncodings);
            keySpec = SignerParams.decryptPkcs8EncodedKey(encryptedPrivateKeyInfo, list);
        }
        catch (IOException e2) {
            if (this.keyPasswordSpec == null) {
                keySpec = new PKCS8EncodedKeySpec(privateKeyBlob);
            }
            throw new InvalidKeySpecException("Failed to parse encrypted private key blob " + this.keyFile, e2);
        }
        try {
            this.privateKey = SignerParams.loadPkcs8EncodedPrivateKey(keySpec);
        }
        catch (InvalidKeySpecException e3) {
            throw new InvalidKeySpecException("Failed to load PKCS #8 encoded private key from " + this.keyFile, e3);
        }
        try (FileInputStream in = new FileInputStream(this.certFile);){
            certs = X509CertificateUtils.generateCertificates(in);
        }
        ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>(certs.size());
        for (Certificate certificate : certs) {
            certList.add((X509Certificate)certificate);
        }
        this.certs = certList;
    }

    private static PKCS8EncodedKeySpec decryptPkcs8EncodedKey(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo, List<char[]> passwords) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
        InvalidKeySpecException lastKeySpecException = null;
        InvalidKeyException lastKeyException = null;
        for (char[] password : passwords) {
            PBEKeySpec decryptionKeySpec = new PBEKeySpec(password);
            try {
                SecretKey decryptionKey = keyFactory.generateSecret(decryptionKeySpec);
                return encryptedPrivateKeyInfo.getKeySpec(decryptionKey);
            }
            catch (InvalidKeySpecException e2) {
                lastKeySpecException = e2;
            }
            catch (InvalidKeyException e3) {
                lastKeyException = e3;
            }
        }
        if (lastKeyException == null && lastKeySpecException == null) {
            throw new RuntimeException("No passwords");
        }
        if (lastKeyException != null) {
            throw lastKeyException;
        }
        throw lastKeySpecException;
    }

    private static PrivateKey loadPkcs8EncodedPrivateKey(PKCS8EncodedKeySpec spec) throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            try {
                return KeyFactory.getInstance("EC").generatePrivate(spec);
            }
            catch (InvalidKeySpecException invalidKeySpecException2) {
                try {
                    return KeyFactory.getInstance("DSA").generatePrivate(spec);
                }
                catch (InvalidKeySpecException invalidKeySpecException3) {
                    throw new InvalidKeySpecException("Not an RSA, EC, or DSA private key");
                }
            }
        }
    }

    private static byte[] readFully(File file) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try (FileInputStream in = new FileInputStream(file);){
            SignerParams.drain(in, result);
        }
        return result.toByteArray();
    }

    private static void drain(InputStream in, OutputStream out) throws IOException {
        int chunkSize;
        byte[] buf = new byte[65536];
        while ((chunkSize = in.read(buf)) != -1) {
            out.write(buf, 0, chunkSize);
        }
    }
}

