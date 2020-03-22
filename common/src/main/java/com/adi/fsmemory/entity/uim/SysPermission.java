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
public class SysPermission implements Serializable {

    private String perId;
    private String perName;
    private String perDesc;
}
