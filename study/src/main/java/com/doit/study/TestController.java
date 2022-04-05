package com.doit.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test/index")
    public String home() {
        return "/sungjin/index";
    }

    @GetMapping("/board/notice_admin")
    public String notice() {
        return "/sungjin/board/notice_admin";
    }

    @GetMapping("/board/wishlist")
    public String widhList() {
        return "/sungjin/board/wishlist";
    }

    @GetMapping("/test/login")
    public String login() {return "/sungjin/members/loginForm"; }

    @GetMapping("/test/one")
    public String one() {return "/sungjin/test/one"; }
}
