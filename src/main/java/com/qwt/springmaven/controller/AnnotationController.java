package com.qwt.springmaven.controller;

import com.qwt.springmaven.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wentao Qian on 4/1/2018.
 */
@Controller
public class AnnotationController {

    private MyService myService;

    @Autowired
    public AnnotationController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(value = "/annotation", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String callMyService(String caller) {
        //if testing exception, then do not pass caller.
        return myService.applyService(caller);
        //TODO: need common web response, and global exception handler
    }

}
