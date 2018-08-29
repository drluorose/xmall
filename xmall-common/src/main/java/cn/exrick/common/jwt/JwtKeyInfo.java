package cn.exrick.common.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class JwtKeyInfo implements Serializable {
    public JwtKeyInfo() {
    }

    public JwtKeyInfo(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    private String privateKey;

    private String publicKey;
}
