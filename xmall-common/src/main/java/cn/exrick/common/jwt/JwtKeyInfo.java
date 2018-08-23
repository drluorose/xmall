package cn.exrick.common.jwt;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class JwtKeyInfo {
    public JwtKeyInfo() {
    }

    public JwtKeyInfo(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    private String privateKey;

    private String publicKey;
}
