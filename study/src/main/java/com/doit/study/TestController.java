package com.doit.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test/index")
    public String home() {
        return "/sungjin/index";
    }

    @GetMapping("/test/login")
    public String login() {return "/sungjin/members/loginForm"; }

    @GetMapping("/test/one")
    public String one() {return "/sungjin/test/one"; }
}
