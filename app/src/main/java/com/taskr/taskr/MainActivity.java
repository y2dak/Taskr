package com.taskr.taskr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.taskr.taskr.fragments.ScheduleFragment;
import com.taskr.taskr.fragments.TaskFragment;
import com.taskr.taskr.models.Database;
import com.taskr.taskr.models.Task;

import java.util.ArrayList;

import static com.taskr.taskr.Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL;
import static com.taskr.taskr.Globals.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL;
import static com.taskr.taskr.Globals.RESULT_TASK_CREATED;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    MyPageAdapter myPageAdapter;
    private TaskFragment autoFragment;
    private TaskFragment manualFragment;
    private Database database;
    private boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPageAdapter);
        mViewPager.setOffscreenPageLimit(3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit tasks");

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
            }
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
            }
        }


        database = new Database();
        autoFragment = new TaskFragment(this, false);
        manualFragment = new TaskFragment(this, true);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddTaskActivity.class), 1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!firstTime) {
            manualFragment.updateTasks();
            autoFragment.updateTasks();
        }
        firstTime = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {}
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_TASK_CREATED) {
                autoFragment.updateTasks();
                manualFragment.updateTasks();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    public ArrayList<Task> getAutomaticTasks() {
        return database.getAutomaticTasks();
    }

    public ArrayList<Task> getManualTasks() {
        return database.getManualTasks();
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return autoFragment;
                case 1:
                    return manualFragment;
                default:
                    return new ScheduleFragment();
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
                    return "Automatic Tasks";
                case 1:
                    return "Manual Tasks";
                default:
                    return "My Schedules";
            }
        }
    }

}
