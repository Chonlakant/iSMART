package com.mncomunity1.event;


import com.mncomunity1.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class SafetyReceivedEvent {

    private static final String TAG = SafetyReceivedEvent.class.getSimpleName();
    private Post post;

    public SafetyReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}