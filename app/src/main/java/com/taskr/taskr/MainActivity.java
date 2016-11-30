package com.taskr.taskr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;
import com.taskr.taskr.fragments.BlankFragment;
import com.taskr.taskr.fragments.TaskFragment;
import com.taskr.taskr.models.Database;
import com.taskr.taskr.models.DummyContent;
import com.taskr.taskr.models.Task;

import pub.devrel.easypermissions.EasyPermissions;

import static com.taskr.taskr.Globals.RC_SIGN_IN;
import static com.taskr.taskr.Globals.RESULT_TASK_CREATED;
import static com.taskr.taskr.Globals.TASK;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, TaskFragment.OnListFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener {
    ViewPager mViewPager;
    MyPageAdapter myPageAdapter;
    private final static String TAG = "MainActivity";
    private Database database = new Database();
    private TaskFragment autoFragment;
    private TaskFragment manualFragment;
    private GoogleApiClient mGoogleApiClient;
    private GoogleAccountCredential mCredential;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPageAdapter);
        autoFragment = new TaskFragment();
        manualFragment = new TaskFragment();
        autoFragment.initialize(this, false);
        manualFragment.initialize(this, true);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signIn();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddTaskActivity.class), 1);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        switch(requestCode) {
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                System.out.println("status code: " + result.getStatus().getStatusCode());
                handleSignInResult(result);
                break;
            case 1:
                if (resultCode == RESULT_TASK_CREATED) {
                    Task task = data.getParcelableExtra(TASK);
                    database.addTask(task);
                    autoFragment.updateTasks();
                    manualFragment.updateTasks();
                }
                break;
        }
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            System.out.println("status code: " + result.getStatus().getStatusCode());
//            handleSignInResult(result);
//        }
//        if (requestCode == 1) {
//            if (resultCode == RESULT_TASK_CREATED) {
//                Task task = data.getParcelableExtra(TASK);
//                database.addTask(task);
//                autoFragment.updateTasks();
//                manualFragment.updateTasks();
//            }
//        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Error: Could not login", Toast.LENGTH_SHORT).show();
        }
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.signOut:
                signOut();
                break;
            case R.id.map:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
        }
        return false;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        signIn();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
