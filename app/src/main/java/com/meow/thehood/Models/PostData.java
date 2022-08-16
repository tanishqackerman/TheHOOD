package com.meow.thehood.Models;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class PostData implements Serializable {
    String postpic, pfp, username, caption, time, postid;
    int likes, comments;

    public PostData() {

    }

    public PostData(String postpic, String caption, String time, String username, String pfp, int likes, int comments, String postid) {
        this.postpic = postpic;
        this.caption = caption;
        this.time = time;
        this.username = username;
        this.pfp = pfp;
        this.likes = likes;
        this.comments = comments;
        this.postid = postid;
    }

    public String getPostpic() {
        return postpic;
    }

    public void setPostpic(String postpic) {
        this.postpic = postpic;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}