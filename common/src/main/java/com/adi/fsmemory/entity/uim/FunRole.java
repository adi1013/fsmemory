package com.adi.fsmemory.entity.uim;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class FunRole implements Serializable {

    private String fRoleId;
    private String fRoleName;
    private String fRoleDesc;
    private Date createTime;
}
