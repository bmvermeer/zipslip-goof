package io.snyk.demo.controller;

import io.snyk.demo.domain.User;
import io.snyk.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    @Autowired
    UserRepo userrepo;

    private static final String SUFFIX = "SNYK";

    public boolean validateToken(String token) {
        if (!token.endsWith(SUFFIX)) {
            return false;
        }
        if (userrepo.findByToken(token) == null) {
            return false;
        }
        return true;
    }

    public static String createNewToken() {
        return UUID.randomUUID().toString() + SUFFIX;
    }

    public boolean isLoggedIn(String token) {
        User user = userrepo.findByToken(token);
        return (token != null) && (user != null);
    }




}
