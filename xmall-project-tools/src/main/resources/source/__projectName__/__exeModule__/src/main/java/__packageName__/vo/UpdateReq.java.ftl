package @packageName@.vo;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateReq implements Serializable {

    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不能小于0")
    private Long id;

    private String username;

    private Integer age;

}