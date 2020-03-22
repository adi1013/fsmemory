package com.adi.fsmemory.expose;

import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.restful.RestResultObj;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UserCheckFeign {

    @RequestMapping("/user/test")
    RestResultObj test();


    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    RestResultObj login(User user);


    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    RestResultObj userRegister(User user);

    @RequestMapping(value = "/user/logout")
    RestResultObj logout();

    @RequestMapping(value = "/user/secret/{userName}")
    User getUserSecretInfo(@PathVariable(value = "userName") String userName);
}
