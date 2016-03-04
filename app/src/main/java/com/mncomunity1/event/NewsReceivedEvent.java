package com.mncomunity1.event;


import com.mncomunity1.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class NewsReceivedEvent {

    private static final String TAG = NewsReceivedEvent.class.getSimpleName();
    private Post post;

    public NewsReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}