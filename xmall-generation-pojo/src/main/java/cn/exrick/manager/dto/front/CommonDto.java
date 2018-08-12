package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class CommonDto implements Serializable {

    private Long userId;

    private String imgData;

    private String token;
}
