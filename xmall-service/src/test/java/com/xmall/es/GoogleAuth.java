package com.xmall.es;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 11:57:47.239 [main] INFO com.xmall.es.GoogleAuth - google auth key:LU2D7U74KG52GKDN
 * 11:57:47.289 [main] INFO com.xmall.es.GoogleAuth - google auth url :otpauth://totp/admin?secret=LU2D7U74KG52GKDN&algorithm=SHA1&digits=6&period=30
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class GoogleAuth {



    @Test
    public void test() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        log.info("google auth key:{}", key.getKey());

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(null, "admin", key);
        log.info("google auth url :{}", url);
    }
}
