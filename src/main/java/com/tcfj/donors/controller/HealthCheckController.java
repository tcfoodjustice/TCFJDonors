package com.tcfj.donors.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andrew.larsen on 1/11/2017.
 */
@RestController
public class HealthCheckController {

    @RequestMapping("/healthCheck")
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck(){
        return "OK";
    }
}
