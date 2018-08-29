package cn.exrick.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class JWTTests {

    @Test
    public void test() {
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        log.debug("alg:{}", algorithmHS.getName());
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", algorithmHS.getName());
        header.put("typ", "jwt");
        String signStr = JWT.create().withHeader(header)
            .withSubject("dfdfdfdfdfdfdfdf")
            .withIssuer("http://www.baidu.com")
            .withKeyId("dfdfdf")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(new Date().getTime() + 18983989))
            .withJWTId("dfdfdfdfdfdf")
            .withNotBefore(new Date())
            .sign(algorithmHS);
        log.debug("sign:{}", signStr);

        List<String> jwtList = Splitter.on(".").splitToList(signStr);
        log.debug("Header:{}", new String(Base64.getDecoder().decode(jwtList.get(0))));
        log.debug("Playload:{}", new String(Base64.getDecoder().decode(jwtList.get(1))));

        JWTVerifier verifier = JWT.require(algorithmHS)
            .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(signStr);
        log.debug("jwt:{}", jwt);
        log.debug("jwt:{}", jwt.getHeader());
        log.debug("jwt:{}", jwt.getPayload());
        log.debug("jwt:{}", jwt.getSignature());
    }

    @Test
    public void testRsaJwt() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);//生成大小 1024
        KeyPair keyPair = keyPairGen.generateKeyPair();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();//Get the key instance
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKeyStr = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        String publicKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        log.debug("RSAPrivateKey:{},keyLength:{}", privateKeyStr, privateKeyStr.length());
        log.debug("RSAPublicKey:{},keyLength:{}", publicKeyStr, publicKeyStr.length());

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        log.debug("alg:{}", algorithm.getName());
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", algorithm.getName());
        header.put("typ", "jwt");
        String signStr = JWT.create().withHeader(header)
            .withSubject("dfdfdfdfdfdfdfdf")
            .withIssuer("http://www.baidu.com")
            .withKeyId("dfdfdf")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(new Date().getTime() + 18983989))
            .withJWTId("dfdfdfdfdfdf")
            .withNotBefore(new Date())
            .sign(algorithm);
        log.debug("sign:{}", signStr);

        List<String> jwtList = Splitter.on(".").splitToList(signStr);
        log.debug("Header:{}", new String(Base64.getDecoder().decode(jwtList.get(0))));
        log.debug("Playload:{}", new String(Base64.getDecoder().decode(jwtList.get(1))));

        JWTVerifier verifier = JWT.require(algorithm)
            .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(signStr);
        log.debug("jwt:{}", jwt);
        log.debug("jwt header:{}", jwt.getHeader());
        log.debug("jwt playload:{}", jwt.getPayload());
        log.debug("jwt sign:{}", jwt.getSignature());

    }

    @Test
    public void testEC256Jwt() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {

        final String KEY_ALGORITHM = "EC";
        final int KEY_SIZE = 256;
        final String SIGNATURE_ALGORITHM = "SHA512withECDSA";

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        log.info(keyPairGen.getProvider().getName());
        keyPairGen.initialize(KEY_SIZE);//生成大小 1024
        KeyPair keyPair = keyPairGen.generateKeyPair();
        final ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();//Get the key instance
        final ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        String privateKeyStr = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        String publicKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        log.debug("RSAPrivateKey:{},keyLength:{}", privateKeyStr, privateKeyStr.length());
        log.debug("RSAPublicKey:{},keyLength:{}", publicKeyStr, publicKeyStr.length());

        Algorithm algorithm = Algorithm.ECDSA256(publicKey, privateKey);

        log.debug("alg:{}", algorithm.getName());
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", algorithm.getName());
        header.put("typ", "jwt");
        String signStr = JWT.create().withHeader(header)
            .withSubject("dfdfdfdfdfdfdfdf")
            .withIssuer("http://www.baidu.com")
            .withKeyId("dfdfdf")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(new Date().getTime() + 18983989))
            .withJWTId("dfdfdfdfdfdf")
            .withNotBefore(new Date())
            .sign(algorithm);
        log.debug("sign:{}", signStr);

        List<String> jwtList = Splitter.on(".").splitToList(signStr);
        log.debug("Header:{}", new String(Base64.getDecoder().decode(jwtList.get(0))));
        log.debug("Playload:{}", new String(Base64.getDecoder().decode(jwtList.get(1))));

        JWTVerifier verifier = JWT.require(algorithm)
            .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(signStr);
        log.debug("jwt:{}", jwt);
        log.debug("jwt header:{}", jwt.getHeader());
        log.debug("jwt playload:{}", jwt.getPayload());
        log.debug("jwt sign:{}", jwt.getSignature());

        Algorithm algorithm1 = Algorithm.ECDSA256(publicKey, null);

        verifier = JWT.require(algorithm1)
            .build();
        verifier.verify(signStr);
        log.debug("jwt:{}", jwt);
        log.debug("jwt header:{}", jwt.getHeader());
        log.debug("jwt playload:{}", jwt.getPayload());
        log.debug("jwt sign:{}", jwt.getSignature());

        KeyFactory factory = KeyFactory.getInstance("EC", "SunEC");
        ECPublicKey ecPublicKey = (ECPublicKey) factory
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr))); // Helper.toByte(ecRemotePubKey)) is java.security.PublicKey#getEncoded()

        ECPrivateKey ecPrivateKey = (ECPrivateKey) factory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)));

        algorithm1 = Algorithm.ECDSA256(publicKey, null);

        verifier = JWT.require(algorithm1)
            .build();
        verifier.verify(signStr);
        log.debug("jwt:{}", jwt);
        log.debug("jwt header:{}", jwt.getHeader());
        log.debug("jwt playload:{}", jwt.getPayload());
        log.debug("jwt sign:{}", jwt.getSignature());
    }

    @Test
    public void testxx() {
        Security.getAlgorithms("Signature").forEach(log::debug);
        Lists.newArrayList(Security.getProviders()).forEach(x -> {
            log.debug(x.toString());
        });
    }

}
