package com.example.sbarai.openkart.Models;

import com.google.firebase.database.Exclude;



/**
 * Created by sbarai on 3/7/18.
 */

public class POChatMessage {

    public String userId;
    public String messageContent;
//    public String timeInMillis;

    public POChatMessage(){}

    public POChatMessage(String id, String msg){
        setUserId(id);
        setMessageContent(msg);
//        setTimeInMillis(ServerValue.TIMESTAMP.toString());
    }

    @Exclude
    public String getUserId() {
        return userId;
    }

    @Exclude
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public String getMessageContent() {
        return messageContent;
    }

    @Exclude
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

//    @Exclude
//    public Long getTimeInMillis() {
//        return Long.valueOf(timeInMillis);
//    }
//
//    @Exclude
//    public void setTimeInMillis(String time) {
//        this.timeInMillis = String.valueOf(time);
//    }

}
