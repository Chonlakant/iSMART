package com.mncomunity1.event;


import com.mncomunity1.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class ManagemantReceivedEvent {

    private static final String TAG = ManagemantReceivedEvent.class.getSimpleName();
    private Post post;

    public ManagemantReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}