package com.bj.auth.controller;

import com.bj.auth.entity.User;
import com.bj.auth.service.UserService;
import com.bj.auth.dto.SignupRequest;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.util.Collections;

@Controller
public class SignupController {

    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;
    private final RequestCache requestCache;

    public SignupController(
            UserService userService,
            SecurityContextRepository securityContextRepository,
            RequestCache requestCache
    ) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.requestCache = requestCache;
    }

    @GetMapping("/signup")
    public String signupForm(
            @ModelAttribute("signupRequest") SignupRequest signupRequest
    ) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute("signupRequest") SignupRequest signupRequest,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (!signupRequest.getPassword().equals(signupRequest.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호 확인이 일치하지 않습니다.");
            return "signup";
        }
        User user;
        try {
            user = userService.signup(signupRequest);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
            return "signup";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), Collections.emptyList()
                ),
                null,
                Collections.emptyList()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            return "redirect:" + savedRequest.getRedirectUrl();
        }

        return "redirect:/";
    }
}
