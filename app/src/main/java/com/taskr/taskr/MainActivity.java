package com.taskr.taskr;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.taskr.taskr.fragments.BlankFragment;
import com.taskr.taskr.fragments.DayFragment;
import com.taskr.taskr.models.DummyContent;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, DayFragment.OnListFragmentInteractionListener {
    ViewPager mViewPager;
    MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPageAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DayFragment();
                default:
                    return new BlankFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day";
                case 1:
                    return "Week";
                default:
                    return "Month";
            }
        }
    }

}
