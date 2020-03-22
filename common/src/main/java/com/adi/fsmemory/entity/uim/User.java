package com.adi.fsmemory.entity.uim;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain=true)
public class User implements Serializable {
    private String userId;
    private String tel;
    private String password;
    private String salt;
    private String userName;
    private String email;
    private String sex;
    private String photoUrl;
    private String constellation;
    private String nickName;
    private Date createTime;
    private Date updateTime;
}
