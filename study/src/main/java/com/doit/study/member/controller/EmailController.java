package com.doit.study.member.controller;

import com.doit.study.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class EmailController {

    private final EmailService emailService;

    /**
     * 메일 체크 메서드
     * @param email
     * @return String
     */
    @PostMapping("/join/mailCheck")
    @ResponseBody
    public String mailCheck(@RequestParam("email") String email) {
        //메일에 보낸 key번호 반환
        String result = emailService.mailSend(email);

        if(result == null) { //key번호가 전송되지 않은 경우
            return "error";
        }

        return result;
    }

    /**
     * 비밀번호 찾기
     * @param data
     * @return ResponseEntity
     */
    @PostMapping("/login/findPassword")
    public ResponseEntity findPwd(@RequestBody Map<String, String> data) {

        //이메일로 비밀번호가 존재하는 회원인지 확인
        String email = data.get("email");
        String result = emailService.findMemberByEmail(email);

        Map<String, String> map = new HashMap<>();

        if(result != null) {
            //임시비밀 번호 전송
            emailService.sendTempPwd(email);
            map.put("result", "임시비밀번호를 전송했습니다.");
        } else {
            map.put("result", "이메일을 한 번 더 확인해주세요.");
        }

        return ResponseEntity.ok().body(map);
    }
}
