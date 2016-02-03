package ismart.is.com.ismart.event;


import ismart.is.com.ismart.model.Post;

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