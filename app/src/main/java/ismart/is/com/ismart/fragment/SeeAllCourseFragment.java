package ismart.is.com.ismart.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.MyCourseRecyclerAdapter;
import ismart.is.com.ismart.event.ActivityResultBus;
import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.QualityRequestedEvent;
import ismart.is.com.ismart.model.Post;

public class SeeAllCourseFragment extends Fragment {


    public static SeeAllCourseFragment getInstance(String message) {
        SeeAllCourseFragment mainFragment = new SeeAllCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MSG", message);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    MyCourseRecyclerAdapter myCourseRecyclerAdapter;
    ArrayList<Post> list = new ArrayList<>();
    RecyclerView recList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_courses_all, container, false);
        ApiBus.getInstance().postQueue(new QualityRequestedEvent());

        recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(getActivity(), list);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }


}
