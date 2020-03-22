package com.adi.fsmemory.gateway.service;

import com.adi.fsmemory.expose.UserCheckFeign;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="fsmemory-uim")
public interface UIMService extends UserCheckFeign {
}
