package com.zhang.ddd.presentation.web.security;

import com.zhang.ddd.infrastructure.common.api.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginFailureController {

    @GetMapping("/signin-failed")
    public Response onLoginFailed() {

        return Response.failed("Sign in failed, wrong user name or password");
    }
}
