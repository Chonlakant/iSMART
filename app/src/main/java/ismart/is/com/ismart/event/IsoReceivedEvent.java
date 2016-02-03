package ismart.is.com.ismart.event;


import ismart.is.com.ismart.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class IsoReceivedEvent {

    private static final String TAG = IsoReceivedEvent.class.getSimpleName();
    private Post post;

    public IsoReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}