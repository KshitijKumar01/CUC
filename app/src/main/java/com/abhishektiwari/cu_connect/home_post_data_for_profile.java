package com.abhishektiwari.cu_connect;

public class home_post_data_for_profile {
    String imageurl,post_text,post_link;




    public home_post_data_for_profile() {

    }
    public home_post_data_for_profile(String imageurl, String post_text, String post_link) {
        this.imageurl = imageurl;
        this.post_text = post_text;
        this.post_link = post_link;

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
}
