package com.douyu.wsd.cradle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String home() {
        return "/index";
    }

}
