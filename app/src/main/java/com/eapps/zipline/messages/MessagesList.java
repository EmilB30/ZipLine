package com.eapps.zipline.messages;

public class MessagesList {

    private String username,lastMessage;

    public MessagesList(String username,String lastMessage)
    {
        this.username=username;
        this.lastMessage=lastMessage;

    }
    public String getUsername(){
        return username;

    }
    public String getLastMessage(){
        return lastMessage;
    }
}
