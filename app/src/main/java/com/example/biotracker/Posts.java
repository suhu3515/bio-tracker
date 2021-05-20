package com.example.biotracker;

public class Posts {

    int post_id;
    String post_date,post_caption,post_image,post_likes,post_comments,user_name;

    public Posts(int post_id, String post_date, String post_caption, String post_image, String post_likes, String post_comments, String user_name) {
        this.post_id = post_id;
        this.post_date = post_date;
        this.post_caption = post_caption;
        this.post_image = post_image;
        this.post_likes = post_likes;
        this.post_comments = post_comments;
        this.user_name = user_name;
    }

    public int getPost_id() {
        return post_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getPost_caption() {
        return post_caption;
    }

    public String getPost_image() {
        return post_image;
    }

    public String getPost_likes() {
        return post_likes;
    }

    public String getPost_comments() {
        return post_comments;
    }

    public String getUser_name() {
        return user_name;
    }
}
