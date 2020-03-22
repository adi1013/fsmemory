package com.adi.fsmemory.gateway.test;

import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/testMethod")
    public RestResultObj testGateway(String params){
        logger.info("----------------testGateway--------------");
        return RestResultType.SUCCESS.toRestfulResult("test");
    }
}
