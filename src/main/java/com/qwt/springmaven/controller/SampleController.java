package com.qwt.springmaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wentao Qian on 12/22/2017.
 */
@Controller
public class SampleController {

    @RequestMapping(value = "/content", method = RequestMethod.GET)
    @ResponseBody
    public String showContent() {
        return "This is sample content.";
    }

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("greeting", "Hello World, from Spring 4 MVC");
        return "index";//this finally map to index.jsp
    }

}
