package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.domain.category.FirstInterestCategory;
import com.doit.study.member.domain.category.SecondInterestCategory;
import com.doit.study.member.domain.category.ThirdInterestCategory;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.NaverService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverController {

    private final NaverService naverService;
    private String apiResult;

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

    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/naver/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model,
                           @RequestParam String code,
                           @RequestParam String state,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) throws IOException, ParseException {
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

        JSONObject kakaoInfo =  new JSONObject(userInfo);
        log.info("userInfo = " + userInfo);

        NaverDto naverDto = new NaverDto(userInfo.get("id"), userInfo.get("name"), userInfo.get("email"), userInfo.get("gender"));
        log.info("새로운 naverDto = "+naverDto);

        //네이버 회원 id 가져오기
        String id = userInfo.get("id");

        //찾는 회원이 회원가입되어 있지 않을 경우
        if(naverService.findSocialMember(id) == null) {
            log.info("회원가입이 필요합니다.");
            model.addAttribute("naverDto", naverDto);
            model.addAttribute("url", "/join/naver");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "/alertNaver";
        } else {
            //찾는 회원이 회원가입되어 있을 경우
            log.info("회원 찾기 성공");
            naverDto = naverService.findSocialMember(id);
            log.info("회원 naverDto는 ? = " + naverDto);
        }

        //세션에 네이버 회원 정보 전달
        session.setAttribute(SessionConst.NAVER_MEMBER, naverDto);
//        model.addAttribute("kakaoInfo", kakaoDto);
        log.info("naverDto = " + naverDto);

        return "redirect:/";
        //

//        String naverEmail = naverDto.getNaverEmail();
//        if (naverService.findSocialMember(naverEmail) == null) {
//            log.info("회원가입이 필요합니다.");
//            redirectAttributes.addFlashAttribute("naverMember", naverDto);
//            return "redirect:/join?social="+"naver";
//        }
    }


      //네이버 회원 삭제
//    @GetMapping("deleteNaver")
//    public String delete() {
//        return "redirect:/https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=RmYsFcVGsS8vYkjw-8VDc7aQWPkrWxGsZhBueNxnEFk&client_secret=GwjoZZh06n&access_token=AAAAPHBLfvRic6W7of6tQlAkdJYFf-AT-jmK94F2DCcPdrDe-GgT8e_cjE34eWtidu-NNeKak2O4U7MDiXPYljUtBgY&service_provider=NAVER\n";
//
//    }

    /**
     * 네이버 회원가입 컨트롤러
     * @param naverDto
     * @return
     */
    @GetMapping("/join/naver")
    public String naverJoinForm(@ModelAttribute("naverDto") NaverDto naverDto) {
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
    public String naverJoin(@Valid @ModelAttribute("naverDto") NaverDto naverDto,
                            BindingResult bindingResult,
                            HttpSession session) {

        //저장값 출력
        log.info("id={}, name={}, email={}, interest1={}, interest2={}, interest3={}",
                naverDto.getNaverId(), naverDto.getNaverName(), naverDto.getNaverEmail(),
                naverDto.getNaverInterest1(), naverDto.getNaverInterest2(), naverDto.getNaverInterest3());

        //오류 발생시 오류 결과 리턴
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/naverJoinForm";
        }

        //오류가 없으면 회원가입 실행
        naverDto = naverService.joinSocial(naverDto);

        session.setAttribute(SessionConst.NAVER_MEMBER, naverDto);
        return "redirect:/";
    }

}
