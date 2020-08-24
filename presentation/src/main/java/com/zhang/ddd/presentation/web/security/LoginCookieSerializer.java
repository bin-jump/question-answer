package com.zhang.ddd.presentation.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoginCookieSerializer extends DefaultCookieSerializer {


    @Override
    public void writeCookieValue(CookieValue cookieValue){
        //log.info("writeCookieValue: " + cookieValue.getRequest().getParameter("rememberme"));
        if (cookieValue.getCookieMaxAge() < 0 &&
                Boolean.parseBoolean(cookieValue.getRequest().getParameter("rememberme"))) {
            int duration =  60*60*24*7;
            cookieValue.setCookieMaxAge(duration);
        }

        super.writeCookieValue(cookieValue);
    }
}
