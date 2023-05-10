package com.example.money_split_f1.Event.Data;

import com.example.money_split_f1.User.UserData;

import java.util.ArrayList;
import java.util.List;

public class PostUserList {
    private List<Post> userCreated;
    private List<Post> userParticipates;
    private List<Post> allPosts;
    private final String userEmail = UserData.getInstance().getEmail();

    //sort Post List into owned and participating posts
    public PostUserList(List<Post> allPosts) {
        this.allPosts = allPosts;
        this.userParticipates = new ArrayList<>();
        this.userCreated = new ArrayList<>();

        for (Post post:allPosts) {
            if (post != null &&  post.getCreator() != null && post.getCreator().getEmail().equals(userEmail)){
                userCreated.add(post);
            }else if (post != null && post.getParticipantEmails().contains(userEmail) && !post.getCreator().getEmail().equals(userEmail)){
                userParticipates.add(post);
            }

        }
    }

    public List<Post> getAllPosts() {
        return allPosts;
    }

    public List<Post> getUserCreated() {
        return userCreated;
    }

    public List<Post> getUserParticipates() {
        return userParticipates;
    }
}
