package com.blogapp.blog.Payloads;

import java.io.Serializable;

public class CommentsDto implements Serializable{
    private int cId;

    private String comment;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
