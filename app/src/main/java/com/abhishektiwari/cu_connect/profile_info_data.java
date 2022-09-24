package com.abhishektiwari.cu_connect;

public class profile_info_data {
    int  Likes,Followers,Following;

    public profile_info_data(int likes, int followers, int following) {
        Likes = likes;
        Followers = followers;
        Following = following;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public int getFollowers() {
        return Followers;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }

    public int getFollowing() {
        return Following;
    }

    public void setFollowing(int following) {
        Following = following;
    }
}
