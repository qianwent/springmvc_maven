package com.qwt.springmaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wentao Qian on 12/22/2017.
 */
@Controller
public class SampleController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showContent() {
        return "This is sample content.";
    }

}
