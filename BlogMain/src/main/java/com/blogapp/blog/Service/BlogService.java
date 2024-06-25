package com.blogapp.blog.Service;

import com.blogapp.blog.Payloads.CommentsDto;


public interface BlogService {
    
    void LikeBlog(int bId);

    CommentsDto createComments(CommentsDto commentDto, Integer bId);
}
