# 📖 DoIT 스터디 모집 사이트

- ### 사이트 주소 : [DoIT - 스터디 모집 사이트🔗](https://www.doit-study.com)
<br>

## ⛏ 사용기술
- **Spring Boot, Gradle, Java**
- **HTML, CSS, JS(ES6), jQuery AJAX**
- **MySQL, Mybatis**
- **Nginx**
- **AWS EC2, AWS CodeDeploy, AWS S3, AWS RDS**
- **Git, Github, SourceTree**

<br>
<br>

## 📆 프로젝트 정보
- **2022.03~22.07**
- 참여인원(6명)
- **기여도**
  * **BackEnd : 100%**
  <br> 아키텍처 설계 및 CI/CD 까지 BackEnd 파트는 100% 담당하여 제작하였습니다.
  * **FrontEnd : 50%**
  <br> 화면 설계에 참여하였고, 완성된 화면의 코드 정리 및 Thymeleaf, JS 및 Jquery AJAX 등의 적용을 담당했습니다.

<br>
<br>

## 🗂️ 프로젝트 개요
해당 프로젝트의 목표는 개발자를 위한 **스터디 모집 사이트** 제작입니다. 해당 프로젝트를 기획하는 당시 코로나로 인한 비대면 수업 및 모임이 유행했습니다. 따라서 온라인/오프라인 여부를 선택할 수 있는 개발자를 위한 스터디 모집 사이트를 서비스할 수 있으면 좋겠다는 생각이 들어 프로젝트를 진행했습니다.

<br>

사이트 로고인 <strong>'DoIT'</strong>은 스터디를 하자는 뜻의 <strong>'Do it'</strong>과 <strong>개발자의 Identity</strong>를 담은 <strong>'Do IT'</strong>를 중의적으로 표현한 로고입니다.

<br>

회원들은 **네이버/카카오/일반회원** 3가지 타입으로 **회원가입 및 로그인**을 하여 서비스를 이용할 수 있습니다. 회원들은 원하는 스터디 모집 글을 작성하여 올릴 수 있고, 다른 회원들은 글을 보고 **댓글을 달거나 쪽지**를 통해 스터디에 대한 문의를 진행할 수 있습니다. 작성된 쪽지와 댓글은 **실시간 알림**으로 회원에게 전송됩니다.

<br>

해당 서비스는 **AWS EC2에 서버를 두어 제공**되고 있습니다. 서비스의 배포는 **Github actions**와 **AWS CodeDeploy, AWS S3**와 **Nginx**를 이용해 **무중단 배포**를 하고 있습니다. 회원 정보와 사이트의 정보, 세션 정보는 **Amazon RDS for MySQL**을 이용해 관리하고 있습니다. 회원이 올린 이미지 파일과 사이트의 이미지는 AWS S3를 이용해 관리하고 있습니다.

<br>
<br>


## ✏️ 프로젝트 환경

- 자바 버전 : JAVA 11
- Gradle : gradle-7.4.2
- AWS EC2 : Amazon Linux 2

<br>
<br>

## 📔 DB - ERD

<br>

![doit_db](https://user-images.githubusercontent.com/92250627/178093924-53d53ba2-5a0b-48fc-a275-6acd01c2a38c.png)

### 테이블 구성



- SR_MOIT_TB : 스터디 게시글 테이블
- USERS_TB : 회원정보 테이블
- SOCIAL_USERS_TB : 소셜 회원 정보 테이블
- WISH_TB : 위시리스트 관리 테이블
- FI_INFO_TB : 파일 관리 테이블
- SR_COMMENT_TB : 댓글 관리 테이블
- NOTE_TB : 쪽지 테이블
- ALARM_TB : 댓글/쪽지 알람 테이블
- USERS_PROFILE_TB : 회원 프로필 관리 테이블

<br>

### 기타 테이블
- SPRING_SESSION, SPRING_SESSION_ATTRIBUTES
- 스프링 세션 관리 테이블

<br>
<br>

## 🌐 브라우저 호환성
<br>

<img src="https://img.shields.io/badge/Firefox-FF7139?style=for-the-badge&logo=Firefox&logoColor=white">
<img src="https://img.shields.io/badge/Chrome-4285F4?style=for-the-badge&logo=Firefox&logoColor=white">
<img src="https://img.shields.io/badge/Microsoft Edge-0078D7?style=for-the-badge&logo=Firefox&logoColor=white">

<br>
해당 사이트는 IE를 지원하지 않습니다.

<br>
<br>


## ⬇️ 실행 방법
해당 프로젝트는 jar 파일로 배포되고 있습니다.

다음 방법으로 로컬 환경에서 프로젝트를 실행할 수 있습니다.

윈도우 환경에서 실행하는 경우 'gradle/wrapper'에서 gradle-wrapper.jar 파일을 통해 실행합니다.

<br>

리눅스 환경에서는 다음 명령어로 jar 파일을 실행합니다.

```linux
java -jar gradle-wrapper.jar
```

<br>
<br>


## ⭐ 핵심 코드 및 시연

<br>
<br>

### 1. 메인화면

<br>

<img width="823" alt="1-메인화면" src="https://user-images.githubusercontent.com/92250627/178096462-f09f1271-3209-4459-bdb8-9a20aacf11b8.PNG">

<br>

- **메인화면**입니다.
- 메인화면에서는 원하는 스터디를 **검색**하거나 **생성**할 수 있습니다.
- 메인화면에는 모집 중인 스터디 글이 카드 형식으로 노출됩니다.


### 2. 로그인

<br>

<img width="822" alt="2-로그인" src="https://user-images.githubusercontent.com/92250627/178096587-9a08b461-3328-4d5b-8d89-4d1b69796613.PNG">

<br>

- **로그인**화면입니다.
- 로그인 화면에서는 **로그인/소셜로그인/회원가입/비밀번호 찾기**가 가능합니다.
- 회원의 **유효성 검사**는 **java에서 제공하는 Bean Validation**을 이용해 진행합니다.
- 다음은 실제 로그인 코드 일부입니다.

<br>

```java
    /**
     * 일반 로그인 오류 검증 컨트롤러
     * @param loginDto
     * @param bindingResult
     * @param redirectURL
     * @param request
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        //유효성 검사
        if(bindingResult.hasErrors()) {
            return "/members/loginForm";
        }
```

<br>

- **bindingResult 객체**를 사용해 유효성 검사에 실패하는 경우 로그인 페이지에 그 결과를 반환하도록 했습니다.
- 유효성 검사와 오류 메시지는 dto 클래스에서 어노테이션을 사용해 지정해줬습니다.
- 다음은 실제 회원 dto 코드 일부입니다.

<br>

```java
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
      private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,16}", message = "비밀번호는 8~16자 영문 소문자와 숫자, 특수문자를 사용하세요.")
      private String password;
```

<br>

- 로그인 화면에서는 소셜 로그인이 가능합니다.
- 사용 가능한 소셜 로그인은 **네이버**와 **카카오**입니다.
- 네이버와 카카오 로그인은 **OAuth 2.0 오픈 프로토콜**을 이용해 구현했습니다.
- 다음은 소셜 로그인을 위한 회원가입 및 로그인 절차를 담은 시연 영상입니다.

<br>

![소셜로그인](https://user-images.githubusercontent.com/92250627/178097011-f70338a9-2d16-4544-be99-c018dfcd32f8.gif)

<br>

### 3. 스터디 모집 글 작성

- 로그인 회원은 스터디 모집 글을 작성할 수 있습니다.
- 다음 화면은 스터디 모집 글 작성 화면입니다.

<br>

<img width="819" alt="3-글쓰기" src="https://user-images.githubusercontent.com/92250627/178097078-20fd5316-e978-4805-a047-b7f47758e50c.PNG">

<br>

- 사진 및 동영상 첨부를 위한 **WYSIWYG 에디터**로는 <strong>'Summernote'</strong>를 사용했습니다.
- 첨부한 사진은 **FI_INFO_TB**을 통해 관리되며, 사진 객체는 **Amazon S3 bucket**에서 관리하고 있습니다.
- 다음은 스터디 모집 글 작성을 위해 이미지를 첨부하는 시연 영상입니다.

<br>

![사진](https://user-images.githubusercontent.com/92250627/178097335-1640fd94-4645-469a-8801-518033ae8627.gif)

<br>

### 4. 스터디 모집 글 댓글 작성

<br>

- 작성한 글에는 회원이 댓글을 작성할 수 있습니다.
- 댓글은 **수정/삭제**가 가능하며, 작성한 댓글에 대한 **대댓글**을 작성할 수 있습니다.
- 댓글의 **depth는 level 2**로, 작성한 **대댓글은 모두 level 2**로 취급되어 관리됩니다.
- 다음은 댓글의 수정, 삭제, 대댓글 작성을 시연한 영상입니다.

<br>

![댓글](https://user-images.githubusercontent.com/92250627/178097430-6f29c75c-e35f-40bf-ae7d-6fa70769c0bc.gif)

<br>

### 5. 쪽지

<br>

- 회원 간에는 쪽지 전송이 가능합니다.
- 다음은 쪽지 전송화면입니다.

<br>

<img width="822" alt="4-쪽지" src="https://user-images.githubusercontent.com/92250627/178097470-d01b4466-185e-46e1-8617-02acf71de33b.PNG">

<br>

- 쪽지 기능은 **Websocket** 위에서 동작하는 **Stomp** 프로토콜을 이용해 구현했습니다.
- 또한 해당 프로토콜을 이용해 쪽지와 댓글 작성시 상대 회원에게 실시간 알림이 전송되는 기능을 구현했습니다.
- 다음은 쪽지 및 댓글 전송/알림 전송을 위한 설정 코드입니다.

<br>

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/alarm");
        config.setApplicationDestinationPrefixes("/server");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```

<br>

- Websocket을 사용할 수 없는 경우 <strong>대체(Fallback) 옵션</strong>을 사용하기 위해 SockJS 프로토콜을 <strong>'withSockjs()'</strong>를 사용해 활성화합니다.
- 다음은 쪽지를 받았을 경우 알림창이 뜨고 쪽지함으로 이동하는 시연 영상입니다.

<br>

![알림](https://user-images.githubusercontent.com/92250627/178098383-50e09868-465d-4e2b-9e9e-559b7c6b6c5a.gif)

<br>

### 6. 회원정보 수정(프로필 이미지 변경하기)

<br>

- 회원정보 페이지에서는 회원에 대한 간략한 정보와 회원 이미지를 확인할 수 있습니다.
- 해당 페이지에서는 회원이 작성한 스터디 게시글 목록을 확인할 수 있습니다.
- 회원정보 수정화면에서는 **닉네임 및 비밀번호 변경, 회원 탈퇴**가 가능합니다.
- 회원정보 페이지에서는 **프로필 이미지 변경**이 가능합니다.
- 업로드 된 회원 프로필 이미지는 **Amazon S3 bucket**에서 관리됩니다.
- 다음은 회원 이미지 변경 시연 영상입니다.

<br>

![프로필사진](https://user-images.githubusercontent.com/92250627/178098532-456fb9d2-e66e-4e9f-a775-24fd35ec4e06.gif)

<br>

### 7. 위시리스트

<br>

- 회원은 마음에 드는 스터디 모집 글을 위시리스트에 담을 수 있습니다.
- 홈 화면이나 스터디 게시글 화면에서 ❤️ 버튼을 눌러 위시리스트에 담거나 취소할 수 있습니다.
- 다음은 위시리스트에 스터디 게시글을 담고 취소하는 시연 영상입니다.

<br>

![위시리스트](https://user-images.githubusercontent.com/92250627/178098650-58e6f3d3-f153-4ff5-8a30-23ff490fc2ac.gif)

### 8. 인터셉터

<br>

- 해당 프로젝트에서 <strong>인증(Authentication)</strong>를 위해 **Session**을 활용하고 있습니다.
- 인증되지 않은 회원의 접근을 방지하기 위해 **Interceptor**를 활용하고 있습니다.
- 다음은 인터셉터 설정 코드입니다.

<br>

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AlarmService alarmService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(alarmService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/join", "/login", "/logout", "/kakao/**", "/naver/**", "/favicon.ico",
                        "/health", "/board/result/**", "/css/**", "/img/**", "/js/**", "/join/**", "/login/**",
                        "/error", "/board/list/**", "/lib/sockjs-client/**", "/websocket/**", "/board/search/**");

        registry.addInterceptor(new JoinCheckInterceptor())
                .order(2)
                .addPathPatterns("/join");
    }
}
```

<br>

- 다음은 로그인 여부를 체크(회원이 인증(authentication)되었는지 체크하고 인증하는 LoginCheckInterceptor 일부입니다.

<br>

```java
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);
        log.info("session 값 = " +session);

        if (session == null ||
                (session.getAttribute(SessionConst.LOGIN_MEMBER) == null &&
                 session.getAttribute(SessionConst.NAVER_MEMBER) == null &&
                 session.getAttribute(SessionConst.KAKAO_MEMBER) == null)) {
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
```

<br>

- <strong>preHandle</strong> 메서드는 컨트롤러가 실행되기 전에 호출되는 메서드로 회원이 인증된 사용자인지 체크하는 메서드입니다.
- 인증되지 않은 회원의 경우 로그인 화면으로 <strong>redirect</strong>됩니다.
- 다음은 redirect될 경우 url 모습입니다.

<br>

<p align="center"><img width="261" height="20" alt="redirect" src="https://user-images.githubusercontent.com/92250627/178099302-c810b49d-e157-49af-b4fa-85114e116906.png" ></p>

<br>

### 9. SSL 인증서

<br>

- 해당 사이트는 **SSL 인증**이 완료된 사이트입니다.

<br>

<p align="center"><img width="350" alt="ssl인증" src="https://user-images.githubusercontent.com/92250627/178099412-4d583183-85af-455f-b86c-e7df319205ec.png" ></>

<br>
<br>


## 🎸ETC

<br>

### 프로젝트 관련 블로그 링크

<br>

[스프링 MVC와 계층형 아키텍처🔗](https://determination.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-MVC%EC%99%80-%EA%B3%84%EC%B8%B5%ED%98%95-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98Layered-Architecture)

<br>

[나는 ServiceImpl을 잘 활용했는가?🔗](https://determination.tistory.com/entry/%EB%82%98%EB%8A%94-ServiceImpl%EC%9D%84-%EC%9E%98-%ED%99%9C%EC%9A%A9%ED%96%88%EB%8A%94%EA%B0%80)

<br>

[프로젝트를 더욱 S.O.L.I.D 하게 수정해보기🔗](https://determination.tistory.com/entry/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EB%A5%BC-%EB%8D%94%EC%9A%B1-SOLID-%ED%95%98%EA%B2%8C-%EC%88%98%EC%A0%95%ED%95%B4%EB%B3%B4%EA%B8%B0)

<br>

[세션 저장소로 데이터베이스 사용하기(MySQL)🔗](https://determination.tistory.com/entry/%EC%84%B8%EC%85%98-%EC%A0%80%EC%9E%A5%EC%86%8C%EB%A1%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0MySQL)

<br>

[스프링 인터셉터🔗](https://determination.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9D%B8%ED%84%B0%EC%85%89%ED%84%B0Interceptor)

<br>

[OAuth2.0으로 네이버/카카오 로그인 구현하기🔗](https://determination.tistory.com/entry/OAuth20%EC%9C%BC%EB%A1%9C-%EB%84%A4%EC%9D%B4%EB%B2%84%EC%B9%B4%EC%B9%B4%EC%98%A4-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)

<br>

[스프링 시큐리티(Spring Security) CSRF 설정(AJAX, POST FORM)🔗](https://determination.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0Spring-Security-CSRF-%EC%84%A4%EC%A0%95AJAX-POST-FORM)

<br>

[Github Actions로 properties 파일 만들어서 배포하기🔗](https://determination.tistory.com/entry/Github-Actions%EB%A1%9C-properties-%ED%8C%8C%EC%9D%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EC%84%9C-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0)

<br>

[Nginx client_max_body_size 수정하기🔗](https://determination.tistory.com/entry/Nginx-clientmaxbodysize-%EC%88%98%EC%A0%95%ED%95%98%EA%B8%B0)

<br>

[프로젝트를 RESTful하게 API 리팩토링하기🔗](https://determination.tistory.com/entry/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-RESTful%ED%95%98%EA%B2%8C-API-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81%ED%95%98%EA%B8%B0)

<br>


노션 링크


