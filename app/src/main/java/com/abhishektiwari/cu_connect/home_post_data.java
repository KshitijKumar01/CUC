package com.abhishektiwari.cu_connect;

public class home_post_data {
    String imageurl,post_text,post_link;
    Integer like,saves;




    public home_post_data() {

    }
    public home_post_data(String imageurl, String post_text, String post_link,Integer like, Integer saves) {
        this.imageurl = imageurl;
        this.post_text = post_text;
        this.post_link = post_link;
        this.like = like;
        this.saves = saves;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_link() {
        return post_link;
    }

    public void setPost_link(String post_link) {
        this.post_link = post_link;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }
}
