package com.qwt.springmaven.controller;

import com.qwt.springmaven.model.User;
import com.qwt.springmaven.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wentao Qian on 12/22/2017.
 */
@Controller
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

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

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, User> showMap() {
        Map<String, User> map = new HashMap<>();
        map.put("list", sampleService.getUser());
        return map;
    }

}
