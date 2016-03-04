package com.mncomunity1.fragment;

import android.support.v4.app.Fragment;

import com.mncomunity1.event.ApiBus;


public abstract class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        ApiBus.getInstance().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        ApiBus.getInstance().unregister(this);
        super.onPause();
    }




}
