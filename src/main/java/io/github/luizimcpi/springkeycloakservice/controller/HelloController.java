package io.github.luizimcpi.springkeycloakservice.controller;

import io.github.luizimcpi.springkeycloakservice.security.CustomJwt;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('fullstack-developer')")
    public Message hello() {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        var message = MessageFormat
                .format("Hello fullstack master {0} {1}, how is it going today?",
                        jwt.getFirstname(), jwt.getLastname());
        return new Message(message);
    }

    record Message(String message) {}
}

