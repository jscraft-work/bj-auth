package com.bj.auth.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String originalPath = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        int status = statusCode != null ? statusCode : 500;
        String safeMessage = message != null && !message.isBlank()
                ? message
                : "요청을 처리하는 중 알 수 없는 오류가 발생했습니다.";
        String safePath = originalPath != null && !originalPath.isBlank()
                ? originalPath
                : request.getRequestURI();

        model.addAttribute("status", status);
        model.addAttribute("message", safeMessage);
        model.addAttribute("path", safePath);
        model.addAttribute("query", request.getQueryString());

        return "error";
    }
}
