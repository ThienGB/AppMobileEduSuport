package com.example.edusuport.model;

public class MessageList {
    private String name, phone, lastMsg, profilePic, chatKey;
    private int unseenMsg;

    public MessageList(String name, String phone, String lastMsg, String profilePic, int unseenMsg, String chatKey) {
        this.name = name;
        this.phone = phone;
        this.lastMsg = lastMsg;
        this.profilePic = profilePic;
        this.unseenMsg = unseenMsg;
        this.chatKey = chatKey;
    }

    public MessageList() {
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public int getUnseenMsg() {
        return unseenMsg;
    }

    public String getChatKey() {
        return chatKey;
    }
}
