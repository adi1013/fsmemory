package com.adi.fsmemory.entity.uim;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain=true)
public class SysRole implements Serializable {

    private String roleId;
    private String roleName;
    private String roleDesc;


}
