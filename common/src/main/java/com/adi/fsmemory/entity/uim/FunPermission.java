package com.adi.fsmemory.entity.uim;


import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class FunPermission {

    private String fPermId;
    private String fPermName;
    private String fPermDesc;
    private Date createTime;

}
