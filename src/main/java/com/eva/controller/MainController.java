package com.eva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Admin on 30/11/2021.
 */
@Controller
public class MainController {
    @GetMapping("index")
    public String abc() {
        return "index";
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @PostMapping("takeAction")
    public ModelAndView takeAction() {
        ModelAndView rs = new ModelAndView("index");
        return rs;
    }


}
