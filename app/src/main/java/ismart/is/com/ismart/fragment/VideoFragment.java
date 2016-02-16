package ismart.is.com.ismart.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.activity.DetailCourseActivity;
import ismart.is.com.ismart.adapter.DetailRecyclerAdapter;
import ismart.is.com.ismart.adapter.MyCourseRecyclerAdapter;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.MaintenanceReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceRequestedEvent;
import ismart.is.com.ismart.model.Post;
import ismart.is.com.ismart.model.PostDetail;

public class VideoFragment extends BaseFragment {
    MyCourseRecyclerAdapter myCourseRecyclerAdapter;
    ArrayList<Post> list = new ArrayList<>();

    VideoView mVideoView;
    RecyclerView recList;
    String path = "http://www.fieldandrurallife.tv/videos/Benltey%20Mulsanne.mp4";


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
        recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        //URI either from net
        Uri video = Uri.parse("http://www.fieldandrurallife.tv/videos/Benltey%20Mulsanne.mp4");
        // mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(video);
        mVideoView.start();
        return rootView;
    }

    @Subscribe
    public void GetMainten(final MaintenanceReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            list.add(event.getPost());
            recList.setAdapter(myCourseRecyclerAdapter);
            myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(getActivity(), list);
        }

    }

}