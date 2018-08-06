package @packageName@.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserVo implements Serializable {

    private Long id;

    private String username;

    private Integer age;

}

