package com.example.springbootherokurommel.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DemoAppController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public DemoAppController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/api")
    @ResponseBody
    public String apiDemo() {
        return "Hola desde la api desde !!";
    }

    @RequestMapping(value = "feed", method = RequestMethod.GET)
    public String feed(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        PagedList<Post> userFeed = facebook.feedOperations().getFeed();
        model.addAttribute("userFeed", userFeed);
        return "feed";
    }
    @RequestMapping(value = "friends", method = RequestMethod.GET)
    public String friends(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        List<User> friends = facebook.friendOperations().getFriendProfiles();
        model.addAttribute("friends", friends);

        return "friends";
    }

}
