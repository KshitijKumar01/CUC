package com.abhishektiwari.cu_connect;

public class Roommate_request_post_data {

String in , persons,date,text_message,uid;
int element;

public  Roommate_request_post_data()
{

}
    public Roommate_request_post_data(String in, String persons, String date, String text_message, String uid,int element) {
        this.in = in;
        this.persons = persons;
        this.date = date;
        this.text_message = text_message;
        this.uid = uid;
        this.element=element;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getElement() {return element;}

    public void setElement(int element) {this.element = element;}
}
