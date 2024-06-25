package com.blogapp.blog.UserRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.blogapp.blog.Entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
    @Query("from Comments as c where c.blogger.id =:userId")
    public Comments findBloggerById(@Param("userId")int userId);

    @Query("from Comments as d where d.blogger.id =:bId")
    public List<Comments> findCommentsByBlog(@Param("bId")int bId);
    }

