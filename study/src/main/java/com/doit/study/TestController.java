package com.doit.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test/index")
    public String home() {
        return "/sungjin/index";
    }

    @GetMapping("/test/login")
    public String login() {return "/sungjin/members/loginForm"; }

    @GetMapping("/test/one")
    public String one() {
        return "/sungjin/test/one";
    }

    @GetMapping("/test/new")
    public String makeStudy() {
        return "/sungjin/studyedit/new";
    }

    @PostMapping("/groups/new")
    @ResponseBody
    public String test1(@RequestParam("start") String start, @RequestParam("end") String end) {
        System.out.println(start);
        System.out.println(end);

        return "ok";
    }

//    @GetMapping("profile")
//    public String profile() {
//        return "/members/profileone";
//    }

    @GetMapping("edit")
    public String edit() {
        return "sungjin/studyedit/edit";
    }

    @GetMapping("mywrite")
    public String mywrite() {
        return "/board/mywrite";
    }


}
