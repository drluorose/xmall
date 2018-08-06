package @packageName@.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddReq implements Serializable {

    private Long id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "年龄不正确，不能小于1")
    @Max(value = 150, message = "年龄不正确，不能大于150")
    private Integer age;

}