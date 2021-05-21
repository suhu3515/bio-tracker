package com.example.biotracker;

public class Comments {

    int commentId;
    String commentedUser, commentText;

    public Comments(int commentId, String commentedUser, String commentText) {
        this.commentId = commentId;
        this.commentedUser = commentedUser;
        this.commentText = commentText;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getCommentedUser() {
        return commentedUser;
    }

    public String getCommentText() {
        return commentText;
    }
}
