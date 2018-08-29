package cn.exrick.common.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.tuple.Pair;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public enum JwtAlgorithmEnum {
    ECDSA256 {
        @Override
        public Algorithm getAlgorithm(JwtKeyInfo jwtKeyInfo) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
            return Algorithm.ECDSA256(buildECPublicKey(jwtKeyInfo.getPublicKey()), buildECPrivateKey(jwtKeyInfo.getPrivateKey()));
        }

        @Override
        public Algorithm getAlgorithm(Pair<ECPublicKey, ECPrivateKey> keyPair) {
            return Algorithm.ECDSA256(keyPair.getLeft(),keyPair.getRight());
        }

    };

    private static final String KEY_ALGORITHM = "EC";

    private static final int KEY_SIZE = 256;

    private static final String PROVIDER = "SunEC";

    public abstract Algorithm getAlgorithm(JwtKeyInfo jwtKeyInfo) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException;

    public abstract Algorithm getAlgorithm(Pair<ECPublicKey, ECPrivateKey> keyPair);

    public Pair<ECPublicKey, ECPrivateKey> buildECKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        final ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();//Get the key instance
        final ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        return Pair.of(publicKey, privateKey);
    }

    public ECPublicKey buildECPublicKey(String publicKeyStr) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
        ECPublicKey ecPublicKey = (ECPublicKey) factory
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));
        return ecPublicKey;
    }

    public ECPrivateKey buildECPrivateKey(String privateKeyStr) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
        ECPrivateKey ecPrivateKey = (ECPrivateKey) factory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)));
        return ecPrivateKey;
    }



    public JwtKeyInfo getFormKeyPair(Pair<ECPublicKey, ECPrivateKey> keyPair) {
        if (Objects.isNull(keyPair)) {
            return null;
        }
        String ecPublicKeyStr = Base64.getEncoder().encodeToString(keyPair.getLeft().getEncoded());
        String ecPrivateKeyStr = Base64.getEncoder().encodeToString(keyPair.getRight().getEncoded());
        return new JwtKeyInfo(ecPublicKeyStr, ecPrivateKeyStr);
    }

    public String getBase64KeyStr(ECPublicKey ecPublicKey) {
        if (Objects.isNull(ecPublicKey)) {
            throw new IllegalArgumentException("PublicKey is null");
        }
        return Base64.getEncoder().encodeToString(ecPublicKey.getEncoded());
    }

    public String getBase64KeyStr(ECPrivateKey ecPrivateKey) {
        if (Objects.isNull(ecPrivateKey)) {
            throw new IllegalArgumentException("PrivateKey is null");
        }
        return Base64.getEncoder().encodeToString(ecPrivateKey.getEncoded());
    }


}
