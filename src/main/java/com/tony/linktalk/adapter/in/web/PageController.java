package com.tony.linktalk.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 페이지 이동을 담당하는 컨트롤러
 */
@Controller
public class PageController {

    /**
     * 메인 페이지로 이동
     *
     * @param model Model 객체
     * @return main.mustache 템플릿
     */
    @GetMapping("/main")
    public String showMainPage(Model model) {
        model.addAttribute("title", "Main Page"); // title 속성을 추가
        return "main"; // main.mustache 템플릿 이름
    }

    /**
     * 로그인 페이지로 이동
     *
     * @param model Model 객체
     * @return login.mustache 템플릿
     */
    @GetMapping("/member/login")
    public String showLoginPage(Model model) {
        model.addAttribute("title", "Login"); // title 속성을 추가
        return "login"; // login.mustache 템플릿 이름
    }

    /**
     * 회원가입 페이지로 이동
     *
     * @param model Model 객체
     * @return signup.mustache 템플릿
     */
    @GetMapping("/member/signUp")
    public String showSignupPage(Model model) {
        model.addAttribute("title", "Sign Up"); // title 속성을 추가
        return "signup"; // signup.mustache 템플릿 이름
    }

    /**
     * 채팅 페이지로 이동
     *
     * @return chat.mustache 템플릿
     */
    @GetMapping("/chat/{chatRoomId}")
    public String showChatPage(@PathVariable String chatRoomId) {
        // Path Variable을 사용하여 필요한 데이터를 전달할 수 있음
        return "chat";  // Mustache 템플릿 이름
    }

}