package com.blogapp.blog.Controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogapp.blog.Entity.User;
import com.blogapp.blog.Helper.Message;
import com.blogapp.blog.UserRepository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home To Thoughts");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About Thoughts");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Signup");
        model.addAttribute("user",new User()); 
        return "signup";
    }

        @PostMapping("/blogs")
    public String blogs(@Valid @ModelAttribute("user") User user, BindingResult res, Model model, HttpSession session){
       try{
        if(res.hasErrors()){
            System.out.println("Kuch to gadbad hai !" +res.toString());
            model.addAttribute("user", user);
            throw new Exception("Kuch to gadbad hai !");
        }
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println("USER" +user);
        User info = this.userRepo.save(user);
        model.addAttribute("user",new User());
        session.setAttribute("message", new Message("Done Bhai !", "alert-success"));
        return "normal/blogs";
       }
       catch(Exception e){
        e.printStackTrace();
        model.addAttribute("user", user);
        session.setAttribute("message", new Message("Neeche dekh " +e.getMessage(), "alert-danger"));
        return "signup";
       }
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("user",new User()); 
        return "login";
    }

    }