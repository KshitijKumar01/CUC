package com.abhishektiwari.cu_connect;

public class companion_request_post_data {
    String from,to,date,uid,text_message,posted_on;

    public companion_request_post_data()
    {}
    public companion_request_post_data(String from, String to, String date, String uid, String text_message, String posted_on) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.uid = uid;
        this.text_message = text_message;
        this.posted_on = posted_on;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }
}
