package ismart.is.com.ismart.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ismart.is.com.ismart.BaseActivity;
import ismart.is.com.ismart.OnFragmentInteractionListener;
import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.CustomViewPager;
import ismart.is.com.ismart.adapter.FragmentPageAdapter;
import ismart.is.com.ismart.fragment.VideoFragment;


public class MainActivityTap extends BaseActivity implements OnFragmentInteractionListener {

    @InjectView(R.id.viewpager)
    CustomViewPager mViewpager;
    @InjectView(R.id.tabs)
    TabLayout mTabs;

    private FragmentPageAdapter pageAdapter;
    String event, friend, camera, chat, more;

    public Integer userId;

    public Context mContext;
    private Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tap);
        ButterKnife.inject(this);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("All Course");
            toolbar.setTitleTextColor(Color.BLACK);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


        mViewpager.setPagingEnabled(false);
        setupViewPager(mViewpager);
        mTabs.setTabTextColors(Color.WHITE,Color.WHITE);
        setupTabLayout(mTabs);


    }

    ArrayList<Fragment> mFragments = new ArrayList<>();

    public void setupViewPager(ViewPager viewPager) {
        pageAdapter = new FragmentPageAdapter(getApplicationContext(),getSupportFragmentManager());

        mFragments.add(VideoFragment.getInstance(friend));
        mFragments.add(VideoFragment.getInstance(chat));
        mFragments.add(VideoFragment.getInstance(more));

        //pageAdapter.addFragment(FragmentFeedView.getInstance(library), "event", R.drawable.ic_tabbar_time_line,"");
        pageAdapter.addFragment(mFragments.get(0), "VDO", R.mipmap.ic_launcher, "");
        //pageAdapter.addFragment(FragmentCamera.getInstance(camera), "camera", R.drawable.ic_tabbar_photo,"");
        pageAdapter.addFragment(mFragments.get(1), "Sterming", R.mipmap.ic_launcher, "");
        pageAdapter.addFragment(mFragments.get(2), "Animation", R.mipmap.ic_launcher, "");
        viewPager.setAdapter(pageAdapter);

    }

    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewpager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(pageAdapter != null) {
                assert tab != null;
                tab.setCustomView(pageAdapter.getTabView(i));
            }
        }
        tabLayout.requestFocus();
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;



    public class MPagerAdapter extends FragmentPagerAdapter {
        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = ((Fragment) mFragments.get(position));
            return Fragment.instantiate(mContext, fragment.getClass().getName(), null);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                // record the fragment tag here.
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }
    }



}
