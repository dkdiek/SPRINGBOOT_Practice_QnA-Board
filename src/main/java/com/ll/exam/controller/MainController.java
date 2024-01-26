package com.ll.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        System.out.println("성공");
        return "안녕하세요";
    }
}
