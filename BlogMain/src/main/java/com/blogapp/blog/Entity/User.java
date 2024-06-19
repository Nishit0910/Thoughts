package com.blogapp.blog.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    
    @NotBlank(message = "Bhai kuch to daal de !")
    @Size(min = 3 ,max = 10, message = "Bhai 3 se 10 ke beech kuch daal")
    private String userName;
    
    @NotBlank(message = "Bhai sochle !")
    private String password;
    
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
            , message = "Bhai Email to daalna padega")
    @Column(unique = true)
    private String email;

    private String role;

    @AssertTrue(message = "Bhai kuch ni hai usme allow karde")
    private boolean agree;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Blogger> bloggers = new ArrayList<>();

    public User() {
        super();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Blogger> getBlogs() {
        return bloggers;
    }

    public void setBlogs(List<Blogger> blogs) {
        this.bloggers = blogs;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [Id=" + Id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", role="
                + role + ", agree=" + agree + "]";
    }

    
    
}
