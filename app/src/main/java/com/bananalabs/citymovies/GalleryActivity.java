package com.bananalabs.citymovies;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.citymovies.adapter.Adapter;

public class GalleryActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ViewPager g_viewpager;
    TabLayout CM_gtabs;
    String movieslist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initToolbar();
        initViews();
    }

    private void initToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        setTitle("Gallery");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorwhite));


    }

    public void initViews() {


        g_viewpager = (ViewPager) findViewById(R.id.g_viewpager);

        CM_gtabs = (TabLayout) findViewById(R.id.CM_gtabs);


        if (g_viewpager != null) {

            setupgalleryViewPager(g_viewpager);
        }


        CM_gtabs.setupWithViewPager(g_viewpager);


    }

    private void setupgalleryViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new VideosListFragment(), "Videos");
        adapter.addFragment(new PhotosListFragment(), "Photos");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Intent objintent = new Intent(GalleryActivity.this, MovieDetailsActivity.class);
            startActivity(objintent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        Intent objintent = new Intent(GalleryActivity.this, MovieDetailsActivity.class);
        startActivity(objintent);
        finish();

    }
}
