package com.example.sbarai.openkart.Models;

/**
 * Created by sbarai on 1/18/18.
 */

public class Comment {

    String content;
    User commenter;
    long dateTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }



}
