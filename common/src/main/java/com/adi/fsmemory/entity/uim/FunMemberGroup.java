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
public class FunMemberGroup {

    private String ugId;
    private User user;
    private FunGroup group;
    private User inviterUser;
    private Integer joinCheck;
    private Date joinTime;
}
