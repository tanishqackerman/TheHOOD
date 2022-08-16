package com.meow.thehood.Models;

public class PostLikedBy {

    String pfplike, usernamelike;

    public PostLikedBy(String usernamelike) {
        this.usernamelike = usernamelike;
    }

    public String getPfplike() {
        return pfplike;
    }

    public void setPfplike(String pfplike) {
        this.pfplike = pfplike;
    }

    public String getUsernamelike() {
        return usernamelike;
    }

    public void setUsernamelike(String usernamelike) {
        this.usernamelike = usernamelike;
    }
}
