package com.taskr.taskr;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.taskr.taskr.fragments.BlankFragment;
import com.taskr.taskr.fragments.TaskFragment;
import com.taskr.taskr.models.Database;
import com.taskr.taskr.models.DummyContent;
import com.taskr.taskr.models.OfflineDatabase;
import com.taskr.taskr.models.Task;

import java.util.ArrayList;
import java.util.Collection;

import static com.taskr.taskr.Globals.MY_PERMISSIONS_REQUEST_READ_EXTERNAL;
import static com.taskr.taskr.Globals.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL;
import static com.taskr.taskr.Globals.RC_SIGN_IN;
import static com.taskr.taskr.Globals.RESULT_TASK_CREATED;
import static com.taskr.taskr.Globals.TASK;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, TaskFragment.OnListFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener {
    ViewPager mViewPager;
    MyPageAdapter myPageAdapter;
    private GoogleApiClient mGoogleApiClient;
    private final static String TAG = "MainActivity";
    private Database database = new Database();
    private TaskFragment autoFragment;
    private TaskFragment manualFragment;
    private OfflineDatabase offlineDatabase;
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

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        offlineDatabase = new OfflineDatabase();
        autoFragment = new TaskFragment(this, false);
        manualFragment = new TaskFragment(this, true);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);

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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_TASK_CREATED) {
//                ArrayList<Task> tasks = offlineDatabase.getAllTasks();
//                for (Task task : tasks) {
//                    System.out.println("Task: " + task.getName());
//                }
                Task task = data.getParcelableExtra(TASK);
                database.addTask(task);
                autoFragment.updateTasks();
                manualFragment.updateTasks();
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Error: Could not login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    public Database getDatabase() {
        return database;
    }

//    public OfflineDatabase getOfflineDatabase() {
//        return offlineDatabase;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    public ArrayList<Task> getTasks() {
        return offlineDatabase.getAllTasks();
    }

    public ArrayList<Task> getAutomaticTasks() {
        return offlineDatabase.getAutomaticTasks();
    }

    public ArrayList<Task> getManualTasks() {
        return offlineDatabase.getManualTasks();
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
                    return "Automatic Tasks";
                case 1:
                    return "Manual Tasks";
                default:
                    return "My Schedules";
            }
        }
    }

}
