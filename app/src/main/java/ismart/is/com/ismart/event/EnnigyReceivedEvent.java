package ismart.is.com.ismart.event;


import ismart.is.com.ismart.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class EnnigyReceivedEvent {

    private static final String TAG = EnnigyReceivedEvent.class.getSimpleName();
    private Post post;

    public EnnigyReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}