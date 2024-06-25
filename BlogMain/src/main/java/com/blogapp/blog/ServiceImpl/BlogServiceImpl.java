package com.blogapp.blog.ServiceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.blogapp.blog.Entity.Blogger;
import com.blogapp.blog.Entity.Comments;
import com.blogapp.blog.Payloads.CommentsDto;
import com.blogapp.blog.Service.BlogService;
import com.blogapp.blog.UserRepository.BloggerRepository;
import com.blogapp.blog.UserRepository.CommentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BloggerRepository bloggerRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public void LikeBlog(int bId){
        Optional<Blogger> bOptional  = this.bloggerRepo.findById(bId);
        if(bOptional.isPresent()){
            Blogger blogger = bOptional.get();
            if(blogger.getLikeCount() == null){
                            blogger.setLikeCount(0);
                        }
            blogger.setLikeCount(blogger.getLikeCount()+1);
            this.bloggerRepo.save(blogger);
        }
        else{
            throw new EntityNotFoundException("No Such Blog" +bId);
        }
    }
    

    @Override
    public CommentsDto createComments(CommentsDto commentDto, Integer bId){
        Blogger blog = bloggerRepo.findById(bId)
        .orElseThrow(() -> new EntityNotFoundException("Blogger not found for ID: " + bId));        
        System.out.println(" BID :>" +blog);
        Comments comm = this.modelMapper().map(commentDto, Comments.class);
        comm.setBlogger(blog);
        Comments savedComm = this.commentRepo.save(comm);
        return this.modelMapper().map(savedComm, CommentsDto.class);
    }

}
