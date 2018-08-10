package cn.exrick.manager.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class RoleDto implements Serializable {

    private int id;

    private String name;

    private String permissions;

    private String description;
}
