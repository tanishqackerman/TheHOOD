package com.meow.thehood;

import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;

public interface SelectListener {
    void onPFPClicked(PostData postData);
    void onStoryClicked(PostData postData);
    void onPostClicked(PostData postData);
    void onLiked(PostData postData);
    void onUnLiked(PostData postData);
    void viewWhoLiked(PostData postData);
    void onChatClicked(ChatList chatList, ProfileData profileData);
}
