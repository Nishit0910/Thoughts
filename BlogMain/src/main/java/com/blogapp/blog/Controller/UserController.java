package com.blogapp.blog.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.blogapp.blog.Entity.Blogger;
import com.blogapp.blog.Entity.User;
import com.blogapp.blog.Helper.Message;
import com.blogapp.blog.UserRepository.BloggerRepository;
import com.blogapp.blog.UserRepository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")

    public class UserController {

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private BloggerRepository bloggerRepo;

        @ModelAttribute
        public void addCommonData(Model model, Principal principal){
            String userName = principal.getName();
            System.out.println("USERNAME" +userName);

            User user = userRepo.getUserByUsername(userName);
            System.out.println("USER" +user);
            model.addAttribute("user", user);
        }

        @GetMapping("/dash")
        public String dashboard(Model model, Principal principal){
            model.addAttribute("title","Your Blogs");

            String name = principal.getName();
            User user = this.userRepo.getUserByUsername(name);

            List<Blogger> blogger = this.bloggerRepo.findBloggerByUser(user.getId());

            model.addAttribute("blogger", blogger);

            return "normal/blogs";
        }

        // Open Add Blog Handler
        @GetMapping("/add-blog")
        public String openAddBlog(Model model){
            model.addAttribute("title","Adding Blog");
            model.addAttribute("blogger", new Blogger());
            return "normal/add_blog";
        }

        // processing blog
        @PostMapping("/process-blog")
        public String processBlog(@ModelAttribute Blogger blog, @RequestParam("displayImage") MultipartFile file, Principal principal, HttpSession session)
        {
            try{
                String name = principal.getName();
                User user = this.userRepo.getUserByUsername(name);
                
                if(file.isEmpty()){
                    System.out.println("File is empty!");
                    blog.setImage("blog.jpg");

                } else{
                    // Image addition to the database
                    blog.setImage(file.getOriginalFilename());
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image is uploaded !");
                }
                blog.setUser(user);
                user.getBlogs().add(blog);
                this.userRepo.save(user);
                System.out.println("Blog Posted !");
                System.out.println("BLOG" +blog);
                session.setAttribute("message", new Message("Blog Uploaded" , "success") );
            }
            catch(Exception e){  
                System.out.println("Error" +e.getMessage());
                session.setAttribute("message", new Message("Error Uploading Blog !", "danger") );
            }
            return "normal/add_blog";
        }
        
        @GetMapping("/blogger/{bId}")
        public String showBlogDetail(@PathVariable("bId") Integer bId, Model model){
            System.out.println("BID" +bId);

            Optional<Blogger> bOptional  = this.bloggerRepo.findById(bId);
            Blogger blogger = bOptional.get();
            model.addAttribute("blogger", blogger);

            return "normal/blog-details";
    }

    @GetMapping("/index")
    public String Home(Model model, Principal principal){
        model.addAttribute("title","Blogs Home");

        // String name = principal.getName();
        // User user = this.userRepo.getUserByUsername(name);

        // List<Blogger> blogger = this.bloggerRepo.findBloggerByUser(user.getId());

        List<Blogger> blogger = this.bloggerRepo.findAll();
        model.addAttribute("blogger", blogger);

        return "normal/all-blogs";
    }

    @GetMapping("/delete/{bId}")
    public String deleteBlog(@PathVariable ("bId") Integer bId, Model model, HttpSession session){
        Blogger blogger  = this.bloggerRepo.findById(bId).get();
        blogger.setUser(null);
    try{
        if(blogger.getImage()!= null){
            File file1 = new File ("C:\\Users\\hp\\Desktop\\All Codes\\Springboot\\BlogMain\\target\\classes\\static\\img");
        Path path = Paths.get(file1.getAbsolutePath() + File.separator + blogger.getImage());
        Files.delete(path);
        System.out.println("Deleted");
        } 
    }
    catch(Exception e){
        System.out.println("Not Deleted !" +e);
    }
        this.bloggerRepo.delete(blogger);
        session.setAttribute("message", new Message("Deleted ! Add new ...", "success"));
        return "redirect:/user/add-blog";
    }
    
    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("title", "Profile");
        return "normal/profile";
    }
    }
