package com.mncomunity1.event;


import com.mncomunity1.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class ProductionReceivedEvent {

    private static final String TAG = ProductionReceivedEvent.class.getSimpleName();
    private Post post;

    public ProductionReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}