package com.meow.thehood.Models;

import java.io.Serializable;

public class ChatList implements Serializable {

    String username, pfpchat, myusername;

    public ChatList(String username, String pfpchat) {
        this.username = username;
        this.pfpchat = pfpchat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPfpchat() {
        return pfpchat;
    }

    public void setPfpchat(String pfpchat) {
        this.pfpchat = pfpchat;
    }

    public String getMyusername() {
        return myusername;
    }

    public void setMyusername(String myusername) {
        this.myusername = myusername;
    }
}
