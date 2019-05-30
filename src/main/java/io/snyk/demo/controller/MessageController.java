package io.snyk.demo.controller;

import io.snyk.demo.domain.Message;
import io.snyk.demo.domain.User;
import io.snyk.demo.repo.MessageRepo;
import io.snyk.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class MessageController {

    @Autowired
    MessageRepo repo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping("/message")
    public String getMessage (@CookieValue(value = "token", defaultValue = "") String token, Model model) {
        if (!tokenUtil.isLoggedIn(token)) {
            return "redirect:/login";
        }
        return pagecontent(model);
    }

    @PostMapping("/message")
    public String processMessage(@CookieValue("token") String token, @ModelAttribute Message message, Model model) {
        User user = userRepo.findByToken(token);
        message.setPoster(user.getUsername());
        repo.save(message);
        return pagecontent(model);
    }


// checkTokens

    private String pagecontent(Model model) {
        model.addAttribute("messages", repo.findAll());
        model.addAttribute("message", new Message());
        return "message";
    }



}
