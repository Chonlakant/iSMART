package ismart.is.com.ismart.event;


import ismart.is.com.ismart.model.Post;

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