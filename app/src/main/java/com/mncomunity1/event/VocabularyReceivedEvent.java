package com.mncomunity1.event;


import com.mncomunity1.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class VocabularyReceivedEvent {

    private static final String TAG = VocabularyReceivedEvent.class.getSimpleName();
    private Post post;

    public VocabularyReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}