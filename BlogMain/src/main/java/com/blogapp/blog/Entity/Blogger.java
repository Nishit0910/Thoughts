package com.blogapp.blog.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Blogger")
public class Blogger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bId;

    private String title;

    @Column(length = 1000)
    private String blogs;
    

    private String image;

    @ManyToOne
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public String getBlogs() {
        return blogs;
    }

    public void setBlogs(String blogs) {
        this.blogs = blogs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Blogger [bId=" + bId + ", title=" + title + ", blogs=" + blogs + ", image=" + image + ", user=" + user
                + "]";
    }
    
}
