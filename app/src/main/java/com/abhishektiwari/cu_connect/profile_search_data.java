package com.abhishektiwari.cu_connect;

public class profile_search_data {
    String uid;
    int followers,following;
    long phone_no;

    public profile_search_data(String uid, int followers, int following, long phone_no) {
        this.uid = uid;
        this.followers = followers;
        this.following = following;
        this.phone_no = phone_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
    }
}
