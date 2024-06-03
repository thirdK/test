package com.example.finalapp.controller.user;

import com.example.finalapp.dto.user.UserDto;
import com.example.finalapp.dto.user.UserSessionDto;
import com.example.finalapp.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/join")
    public String join(UserDto userDto) {
        log.info("userDto = {}", userDto);

        userService.registerUser(userDto);

        return "redirect:/user/login";
    }

    @PostMapping("/login")
    public RedirectView login(String loginId, String password, HttpSession session) {
        UserSessionDto loginInfo = userService.findLoginInfo(loginId, password);

        System.out.println("loginId = " + loginId + ", password = " + password);
        session.setAttribute("userId", loginInfo.getUserId());
        session.setAttribute("loginId", loginInfo.getLoginId());

        return new RedirectView("/board/list");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();

        return new RedirectView("/user/login");
    }
}










