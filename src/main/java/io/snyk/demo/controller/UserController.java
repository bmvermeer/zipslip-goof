package io.snyk.demo.controller;

import io.snyk.demo.domain.User;
import io.snyk.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserController {

    @Autowired
    UserRepo repo;

    @Autowired
    TokenUtil tokenUtil;


    @GetMapping("/register")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model, HttpServletResponse response) {
        if (user.isEmpty() || repo.findByUsername(user.getUsername()) != null) {
            return "bad";
        }

        String token = tokenUtil.createNewToken();
        user.setToken(token);
        repo.save(user);

        setCookie(token, response);

        repo.findAll().forEach(System.out::println);
        return "redirect:/message";
    }

    @GetMapping("/login")
    public String newLogin (@CookieValue(value = "token", defaultValue = "") String token, Model model) {
        if (tokenUtil.isLoggedIn(token)) {
            return "redirect:/message";
        }

        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logOut (HttpServletResponse response) {
        setCookie(null, response);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletResponse response) {
        User logInUser = repo.findByUsernameAndAndPassword(user.getUsername(), user.getPassword());
        if (logInUser == null) {
            return "bad";
        }

        setCookie(logInUser.getToken(), response);
        return "redirect:/message";
    }


    private void setCookie(String token, HttpServletResponse response) {
        Cookie newCookie = new Cookie("token", token);
        newCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(newCookie);
    }







}
