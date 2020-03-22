package com.adi.fsmemory.entity.uim;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Accessors(chain = true)
public class FunGroup implements Serializable {

    private String groupId;
    private String groupName;
    private String userId;
    private String groupDesc;
    private String searchId;   //需要保持唯一，可以给用户自定义
    private Date createTime;
    private Date updTime;
}
