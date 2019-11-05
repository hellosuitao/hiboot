package com.ruixun.hiboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/page/{pageName}")
    public String pagePath(@PathVariable("pageName") String pageName){
        return pageName;
    }


}
