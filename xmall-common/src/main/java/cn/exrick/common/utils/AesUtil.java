package cn.exrick.common.utils;

import java.security.Key;
import java.security.Security;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 * @Description AES算法工具类
 * @author 王鑫
 * @date Dec 13, 2017 12:50:39 PM
 */
public class AESUtil {

    public static final String _ENCODING = "UTF-8";
    public static final String _KEY_AES = "AES";
    public static final String _AES_PKCS5 = "AES/ECB/PKCS5Padding";

    public static boolean isPlaintext(String text) {
        return ((Pattern.matches("^[0-9xX]*$", text)) && (text.length() < 27));
    }

    private static Key toKeyDES(byte[] key, String algorithm) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
        return keySpec;
    }

    /**
     * @Description AES解密
     * @author 王鑫
     * @param data
     *            参数
     * @param key
     *            密钥
     * @return 解密后参数
     */
    public static String decryptToString(String data, String key) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        data = data.trim();
        if (!isPlaintext(data)) {
            try {
                Key k = toKeyDES(key.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
                cipher.init(2, k);
                byte[] source = Hex.decode(data);
                return new String(cipher.doFinal(source), "UTF-8");
            } catch (Exception e) {
                System.err.println("# 解密失败");
            }
        }
        return data;
    }

    /**
     * @Description AES加密
     * @author 王鑫
     * @param data
     *            参数
     * @param key
     *            密钥
     * @return 加密后的参数
     */
    public static String encryptToString(String data, String key) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        try {
            byte[] plaintext = data.getBytes("UTF-8");
            Key k = toKeyDES(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
            cipher.init(1, k);
            byte[] ciphertext = cipher.doFinal(plaintext, 0, plaintext.length);
            return new String(Hex.encode(ciphertext)).toUpperCase();
        } catch (Exception e) {
            System.err.println("# AES加密失败");
        }
        return data;
    }

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            System.err.println("# 初始化运算环境失败");
        }
    }
}