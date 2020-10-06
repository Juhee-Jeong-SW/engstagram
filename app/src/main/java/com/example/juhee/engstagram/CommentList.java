package com.example.juhee.engstagram;

public class CommentList {

    String commentUser;
    String commentDate;
    String commentContext;

    public CommentList(String commentUser, String commentDate, String commentContext) {
        this.commentUser = commentUser;
        this.commentDate = commentDate;
        this.commentContext = commentContext;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContext() {
        return commentContext;
    }

    public void setCommentContext(String commentContext) {
        this.commentContext = commentContext;
    }
}
