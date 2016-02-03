package ismart.is.com.ismart.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import ismart.is.com.ismart.R;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;

public class VideoFragment extends Fragment {

    VideoView mVideoView;
    String path = "http://www.fieldandrurallife.tv/videos/Benltey%20Mulsanne.mp4";
    MediaController mediaController;
    public static VideoFragment getInstance(String message) {
        VideoFragment mainFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MSG", message);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        mVideoView = (VideoView) rootView.findViewById(R.id.surface_view);
         mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(mVideoView);
        //URI either from net
        Uri video = Uri.parse("http://www.fieldandrurallife.tv/videos/Benltey%20Mulsanne.mp4");
       // mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(video);
        mVideoView.start();
        return rootView;
    }

}
