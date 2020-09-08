package com.zhang.ddd.presentation.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * for single page application
 */
@Slf4j
@Controller
public class SinglePageFallbackController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
