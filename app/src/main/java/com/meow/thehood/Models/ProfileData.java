package com.meow.thehood.Models;

import java.io.Serializable;

public class ProfileData implements Serializable {

    String pfp, name, username, bio, email;
    int followers, following, posts;
    boolean verified;

    public ProfileData(String pfp, String name, String username, String bio, String email, boolean verified, int followers, int following, int posts) {
        this.pfp = pfp;
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.email = email;
        this.verified = verified;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
    }

    public ProfileData(String pfp, String name, String username, String bio, String email) {
        this.pfp = pfp;
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.email = email;
    }

    public ProfileData() {

    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
