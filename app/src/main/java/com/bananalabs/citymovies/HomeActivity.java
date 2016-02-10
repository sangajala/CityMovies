package com.bananalabs.citymovies;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.citymovies.adapter.GridViewAdapter;
import com.com.citymovies.POJO.ImageItem;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {

    private AppConstants objappcontants;
    NavigationView navigationView;
    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;
    Toolbar mToolbar;
    private ImageView iv_profile;
    private TextView username;

    private GridView gridView;
    private GridViewAdapter gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        objappcontants = new AppConstants(HomeActivity.this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorwhite));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //setupEvenlyDistributedToolbar();
    }

    public void initViews() {


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_sample, navigationView, false);
        navigationView.addHeaderView(headerView);
        headerView.getBackground().setAlpha(90);


        username = (TextView) headerView.findViewById(R.id.txtname);

        iv_profile = (ImageView) headerView.findViewById(R.id.iv_profile);

        gridView = (GridView) findViewById(R.id.CM_gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            gridView.setNestedScrollingEnabled(true);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
//                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
//                intent.putExtra("title", item.getTitle());
//                intent.putExtra("image", item.getImage());
//
//                //Start details activity
//                startActivity(intent);
            }
        });

        if (!AppConstants.getFBLoginstatusFromSharedPreference() && !AppConstants.getGplLoginstatusFromSharedPreference() && !AppConstants.getSLoginstatusFromSharedPreference()) {


            username.setText("");
            iv_profile.setVisibility(View.GONE);


        } else {

            if (AppConstants.getFBLoginstatusFromSharedPreference()) {


                username.setText(AppConstants.getFBusernameFromSharedPreference());

                navigationView.getMenu().setGroupVisible(R.id.svn_logout, true);
                navigationView.getMenu().setGroupVisible(R.id.svn_signin, false);
                iv_profile.setVisibility(View.VISIBLE);

            }
            //if (!AppConstants.getFBLoginstatusFromSharedPreference()) {

//            TextView Gplusername;
//            Gplusername = (TextView) navigationView.inflateHeaderView(R.layout.drawer_header).findViewById(R.id.txtname);
//            Gplusername.setText("");

//            navigationView.getMenu().setGroupVisible(R.id.svn_logout, false);
//            navigationView.getMenu().setGroupVisible(R.id.svn_signin, true);
            //         }
            if (AppConstants.getGplLoginstatusFromSharedPreference()) {


                username.setText(AppConstants.getGplusernameFromSharedPreference());

                navigationView.getMenu().setGroupVisible(R.id.svn_logout, true);
                navigationView.getMenu().setGroupVisible(R.id.svn_signin, false);
                iv_profile.setVisibility(View.VISIBLE);
            }

            if (AppConstants.getSLoginstatusFromSharedPreference()) {


                username.setText(AppConstants.getLOGdpnameFromSharedPreference());

                navigationView.getMenu().setGroupVisible(R.id.svn_logout, true);
                navigationView.getMenu().setGroupVisible(R.id.svn_signin, false);
                iv_profile.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        } else if (id == R.id.signin) {

            Intent objintent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(objintent);
            finish();

        } else if (id == R.id.logout) {


            if (!objappcontants.isConnectingToInternet()) {


                objappcontants.alertMessageDialog(
                        "No Internet Connection",
                        "Please check your internet connectivity and try again!",
                        "");

            } else {

                if (AppConstants.getFBLoginstatusFromSharedPreference()) {


                    iv_profile.setVisibility(View.GONE);

                    username.setText("");

                    AppConstants.svnFBLogin(false);
                    FacebookSdk.sdkInitialize(HomeActivity.this);
                    LoginManager.getInstance().logOut();

                    Toast.makeText(getApplicationContext(),
                            "You are Logged out ", Toast.LENGTH_LONG).show();


                } else if (AppConstants.getGplLoginstatusFromSharedPreference()) {


                    if (mGoogleApiClient.isConnected()) {

                        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();
                        AppConstants.svnGplLogin(false);
                        username.setText("");
                        iv_profile.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),
                                "You are Logged out ", Toast.LENGTH_LONG).show();

                    }

                } else if (AppConstants.getSLoginstatusFromSharedPreference()) {

                    AppConstants.svnserviceLogin(false);
                    username.setText("");
                    iv_profile.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(),
                            "You are Logged out ", Toast.LENGTH_LONG).show();


                }


//
                navigationView.getMenu().setGroupVisible(R.id.svn_logout, false);
                navigationView.getMenu().setGroupVisible(R.id.svn_signin, true);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(
                this);
    }

    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
}
