package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.NaverService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.support.RequestContextUtils.getInputFlashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverController {

    private final NaverService naverService;
    private final MemberService memberService;
    private String apiResult;

    //성별 정보
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("M", "남자"));
        genders.add(new Gender("F", "여자"));
        return genders;
    }

    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/naver/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model,
                           @RequestParam String code,
                           @RequestParam String state,
                           HttpServletRequest request,
                           HttpSession session) throws IOException, ParseException {
        OAuth2AccessToken oauthToken;
        oauthToken = naverService.getAccessToken(session, code, state);
        log.info("토큰 = "+oauthToken);

        //1. 로그인 사용자 정보를 읽어온다.
        apiResult = naverService.getUserProfile(oauthToken); //String형식의 json데이터

        /** apiResult json 구조
        {"resultcode":"00",
         "message":"success",
         "response":{"id":"33666449","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
         **/
        //String형식인 apiResult를 json형태로 바꿈
        HashMap<String, String> userInfo = naverService.getNaverUserInfo(apiResult);
        log.info("userInfo = " + userInfo);

        SocialDto socialDto = new SocialDto(userInfo.get("id"), userInfo.get("name"), userInfo.get("email"), userInfo.get("gender"));
        log.info("새로운 naverDto = "+socialDto);

        //네이버 회원 id 가져오기
        String social_id = userInfo.get("id");

        MemberDto memberDto;

        //찾는 회원이 회원가입되어 있지 않을 경우
        if(naverService.findSocialMember(social_id) == null) {
            log.info("회원가입이 필요합니다.");
            model.addAttribute("accessToken", oauthToken);
            model.addAttribute("socialDto", socialDto);
            model.addAttribute("url", "/join/naver");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "/alertSocialJoin";
        } else {
            //찾는 회원이 회원가입되어 있을 경우
            log.info("회원 찾기 성공");
            memberDto = naverService.findSocialMember(social_id);
            log.info("회원 socialDto는 ? = " + socialDto);
        }

        //세션에 네이버 회원 정보 전달
        session.setAttribute(SessionConst.NAVER_MEMBER, memberDto);
        log.info("memberDto = " + memberDto);
        return "redirect:/";
    }


//      네이버 회원 삭제
    @GetMapping("/naverUnlink")
    public String delete() {
        return "redirect:/https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=Rqvi_Vwj3d5f4g6XhkfFjWrm_6DQMWQVCOM28WRZYNY&client_secret=GwjoZZh06n&access_token=AAAAPMRNkTMZILcNCr1l9oY2C1bheoqEgosG7NktTFps4qf2UPjapjsC4JZjAKzyoUm1RIcIUJSei2vIX0OL4PugiOw&service_provider=NAVER\n";
    }

    /**
     * 네이버 회원가입 컨트롤러
     * @param naverDto
     * @return
     */
    @GetMapping("/join/naver")
    public String naverJoinForm(@ModelAttribute("naverDto") SocialDto naverDto) {
        log.info("naverDto = " + naverDto);
        return "members/naverJoinForm";
    }

    /**
     * 네이버 회원 가입 인증 컨트롤러
     * @param naverDto
     * @param bindingResult
     * @param session
     */
    @PostMapping("/join/naver")
    public String naverJoin(@Valid @ModelAttribute("naverDto") SocialDto naverDto,
                            BindingResult bindingResult,
                            HttpSession session) {

        //저장값 출력
        log.info("id={}, name={}, email={}, gender={}",
                naverDto.getSocialId(), naverDto.getSocialName(), naverDto.getSocialEmail(), naverDto.getSocialGender());

        //오류 발생시 오류 결과 리턴
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/naverJoinForm";
        }

        //오류가 없으면 회원가입 실행
        naverDto = memberService.joinSocial(naverDto);

        session.setAttribute(SessionConst.NAVER_MEMBER, naverDto);
        return "redirect:/";
    }

}
