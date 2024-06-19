package com.blogapp.blog.UserRepository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapp.blog.Entity.Blogger;

public interface BloggerRepository extends JpaRepository<Blogger, Integer> {
    
    @Query("from Blogger as b where b.user.id =:userId")
    public List<Blogger> findBloggerByUser(@Param("userId")int userId);
}