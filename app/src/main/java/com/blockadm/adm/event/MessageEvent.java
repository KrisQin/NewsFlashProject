package com.blockadm.adm.event;

/**
 * Created by hp on 2019/2/13.
 */

public class MessageEvent extends Event {
    private String message;
    public  MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
