package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.domain.category.FirstInterestCategory;
import com.doit.study.member.domain.category.SecondInterestCategory;
import com.doit.study.member.domain.category.ThirdInterestCategory;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;

    //첫 번째 카테고리 정보
    @ModelAttribute("first_categories")
    public List<FirstInterestCategory> firstCategory() {
        List<FirstInterestCategory> categories = new ArrayList<>();
        categories.add(new FirstInterestCategory("back", "백엔드"));
        categories.add(new FirstInterestCategory("front", "프론트엔드"));
        return categories;
    }

    //두 번째 카테고리 정보
    @ModelAttribute("second_categories")
    public List<SecondInterestCategory> secondCategory() {
        List<SecondInterestCategory> categories = new ArrayList<>();
        categories.add(new SecondInterestCategory("study", "스터디"));
        categories.add(new SecondInterestCategory("project", "프로젝트"));
        categories.add(new SecondInterestCategory("cert", "자격증"));
        return categories;
    }

    //세 번째 카테고리 정보
    @ModelAttribute("third_categories")
    public List<ThirdInterestCategory> thirdCategory() {
        List<ThirdInterestCategory> categories = new ArrayList<>();
        categories.add(new ThirdInterestCategory("java", "자바"));
        categories.add(new ThirdInterestCategory("spring", "스프링"));
        return categories;
    }

    //성별 정보
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("male", "남자"));
        genders.add(new Gender("female", "여자"));
        return genders;
    }

    /**
     * 카카오 로그인 콜백 컨트롤러
     * @param code
     * @param model
     * @param session
     * @param redirectAttributes
     */
    @RequestMapping(value = "/kakao/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(@RequestParam String code,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        log.info("code = " + code);

        //access token 발급 받기
        String access_Token = kakaoService.getAccessKakaoToken(code);
        log.info("생성된 access_Token은 ?" + access_Token);

        //access token으로 얻은 로그인한 유저 정보 얻어오기
        HashMap<String, String> userInfo = kakaoService.getKaKaoUserInfo(access_Token);
        log.info("access_Token : " + userInfo.get("accessToken"));
        log.info("nickname : " + userInfo.get("nickname"));
        log.info("email : " + userInfo.get("email"));
        log.info("gender : " + userInfo.get("gender"));
        log.info("id : " + userInfo.get("id"));

        //카카오 회원 고유 id값 가져오기
        String id = userInfo.get("id");

        //카카오 회원 정보 가져오기
        JSONObject kakaoInfo = new JSONObject(userInfo);

        //kakaoDto 초기화
        KakaoDto kakaoDto = new KakaoDto(id, userInfo.get("nickname"), userInfo.get("accessToken"), userInfo.get("email"), userInfo.get("gender"));
        log.info("새로운 kakaoDto = " + kakaoDto);

        //찾는 회원이 회원가입되어 있지 않을 경우
        if(kakaoService.findSocialMember(id) == null) {
            log.info("회원가입이 필요합니다.");
            model.addAttribute("kakaoDto", kakaoDto);
            model.addAttribute("url", "/join/kakao");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "/alertKakao";
        } else {
            //찾는 회원이 회원가입되어 있을 경우
            log.info("회원 찾기 성공");
            kakaoDto = kakaoService.findSocialMember(id);
            log.info("회원 kakaoDto는 ? = " + kakaoDto);
        }

        //세션에 카카오 회원 정보 전달
        session.setAttribute(SessionConst.KAKAO_MEMBER, kakaoDto);
//        model.addAttribute("kakaoInfo", kakaoDto);
        log.info("kakaoDto = " + kakaoDto);

        return "redirect:/";
    }

//    /**
//     * 카카오 로그아웃 컨트롤러
//     * @param request
//     */
//    @RequestMapping(value = "/kakaoLogout")
//    public String logout(HttpServletRequest request) {
//
//        //세션 값 가져오기
//        HttpSession session = request.getSession(false);
//
//        if(session != null) {
//            session.invalidate();
//        }
//
//        return "redirect:/";
//    }


    /**
     * 카카오 연결 끊기
     * @param kakaoDto
     */
    @RequestMapping(value = "/kakaoUnlink")
    public String unlink(@SessionAttribute(name = SessionConst.KAKAO_MEMBER, required = false) KakaoDto kakaoDto) {
        log.info("kakaoDto = " + kakaoDto);
        String accessToken = kakaoDto.getAccessToken();
        kakaoService.unlinkKakao(accessToken);
        return "/";
    }

    /**
     * 카카오 회원가입 컨트롤러
     * @param kakaoDto
     */
    @GetMapping("/join/kakao")
    public String kakaoJoinForm(@ModelAttribute("kakaoDto") KakaoDto kakaoDto) {
        log.info("kakaoDto = " + kakaoDto);
        return "members/kakaoJoinForm";
    }

    /**
     * 카카오 회원 가입 인증 컨트롤러
     * @param kakaoDto
     * @param bindingResult
     * @param session
     */
    @PostMapping("/join/kakao")
    public String kakaoJoin(@Valid @ModelAttribute("kakaoDto") KakaoDto kakaoDto,
                            BindingResult bindingResult,
                            HttpSession session) {

        //저장값 출력
        log.info("id={}, name={}, email={}, interest1={}, interest2={}, interest3={}",
                kakaoDto.getKakaoId(), kakaoDto.getKakaoName(), kakaoDto.getKakaoEmail(),
                kakaoDto.getKakaoInterest1(), kakaoDto.getKakaoInterest2(), kakaoDto.getKakaoInterest3());

        //오류 발생시 오류 결과 리턴
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/kakaoJoinForm";
        }

        //오류가 없으면 회원가입 실행
        kakaoDto = kakaoService.joinSocial(kakaoDto);

        session.setAttribute(SessionConst.KAKAO_MEMBER, kakaoDto);
        return "redirect:/";
    }
}
