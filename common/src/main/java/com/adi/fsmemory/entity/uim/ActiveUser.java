package com.adi.fsmemory.entity.uim;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class ActiveUser {

    private User onlineUser;
    private Set<String> roles;
    private Set<String> permissions;
}
