package com.ll.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        System.out.println("성공");
        return "abc";
    }
    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <h1>안녕하세요</h1>
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이 입력" />
                    <input type="submit" value="page2로 POST이동" />
                </form>
                """;
    }
    @GetMapping("/page2")
    @ResponseBody
    public String showPageGet(@RequestParam(defaultValue = "0") int age){
        return """
                <h1>입력된 나이:%d</h1>
                <h1>get방식으로 왔습니다</h1>
                """.formatted(age);
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPagePost(@RequestParam(defaultValue = "0") int age){
        return """
                <h1>입력된 나이:%d</h1>
                <h1>post방식으로 왔습니다</h1>
                """.formatted(age);
    }
}
