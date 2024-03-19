package com.proisii.conj.controller;
import com.proisii.conj.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestParam Integer documentoInput, @RequestParam String passInput) {
        boolean isAuthenticated = authenticationService.authenticate(documentoInput, passInput);

        if (isAuthenticated) return "redirect:/index.html";
        else {
            return "redirect:/login.html";
        }
    }
}
