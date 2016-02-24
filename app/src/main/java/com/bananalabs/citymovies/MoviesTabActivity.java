package com.bananalabs.citymovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.citymovies.adapter.Adapter;
import com.citymovies.adapter.TheatersAdapter;


public class MoviesTabActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView CM_txtmovies, CM_txttheat;
    ViewPager m_viewPager, t_viewPager;
    TabLayout CM_movtabs, CM_thtabs;
    String movieslist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviestab);

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

        setTitle("");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorwhite));


    }

    public void initViews() {


        movieslist = getIntent().getExtras().getString("movies");


        CM_txtmovies = (TextView) findViewById(R.id.CM_txtmovies);
        CM_txttheat = (TextView) findViewById(R.id.CM_txttheat);

        CM_txtmovies.setOnClickListener(this);
        CM_txttheat.setOnClickListener(this);


        m_viewPager = (ViewPager) findViewById(R.id.m_viewpager);
        t_viewPager = (ViewPager) findViewById(R.id.t_viewpager);

        CM_movtabs = (TabLayout) findViewById(R.id.CM_movtabs);
        CM_thtabs = (TabLayout) findViewById(R.id.CM_thtabs);

        if (movieslist.equalsIgnoreCase("movies")) {

            CM_txtmovies.setTextColor(Color.parseColor("#669E5D"));
            CM_txttheat.setTextColor(Color.parseColor("#FFFFFF"));

            CM_thtabs.setVisibility(View.GONE);
            t_viewPager.setVisibility(View.GONE);

            CM_movtabs.setVisibility(View.VISIBLE);
            m_viewPager.setVisibility(View.VISIBLE);

            if (m_viewPager != null) {

                setupmoviesViewPager(m_viewPager);
            }


            CM_movtabs.setupWithViewPager(m_viewPager);


        } else if (movieslist.equalsIgnoreCase("theaters")) {

            CM_txtmovies.setTextColor(Color.parseColor("#FFFFFF"));
            CM_txttheat.setTextColor(Color.parseColor("#669E5D"));

            CM_movtabs.setVisibility(View.GONE);
            m_viewPager.setVisibility(View.GONE);


            CM_thtabs.setVisibility(View.VISIBLE);
            t_viewPager.setVisibility(View.VISIBLE);


            if (t_viewPager != null) {

                setuptheatersViewPager(t_viewPager);
            }


            CM_thtabs.setupWithViewPager(t_viewPager);

        }


    }

    private void setupmoviesViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MovieListFragment(), "Latest");
        adapter.addFragment(new MovieListFragment(), "Near by");
        adapter.addFragment(new MovieListFragment(), "Rating");
        adapter.addFragment(new MovieListFragment(), "Upcoming");
        viewPager.setAdapter(adapter);
    }

    private void setuptheatersViewPager(ViewPager viewPager) {

        TheatersAdapter adapter = new TheatersAdapter(getSupportFragmentManager());
        adapter.addFragment(new TheatersListFragment(), "Latest");
        adapter.addFragment(new TheatersListFragment(), "Near by");
        adapter.addFragment(new TheatersListFragment(), "Upcoming");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.CM_txtmovies:

                CM_txtmovies.setTextColor(Color.parseColor("#669E5D"));
                CM_txttheat.setTextColor(Color.parseColor("#FFFFFF"));

                CM_thtabs.setVisibility(View.GONE);
                t_viewPager.setVisibility(View.GONE);

                CM_movtabs.setVisibility(View.VISIBLE);
                m_viewPager.setVisibility(View.VISIBLE);

                if (m_viewPager != null) {

                    setupmoviesViewPager(m_viewPager);
                }


                CM_movtabs.setupWithViewPager(m_viewPager);

                break;

            case R.id.CM_txttheat:

                CM_txtmovies.setTextColor(Color.parseColor("#FFFFFF"));
                CM_txttheat.setTextColor(Color.parseColor("#669E5D"));

                CM_movtabs.setVisibility(View.GONE);
                m_viewPager.setVisibility(View.GONE);


                CM_thtabs.setVisibility(View.VISIBLE);
                t_viewPager.setVisibility(View.VISIBLE);


                if (t_viewPager != null) {

                    setuptheatersViewPager(t_viewPager);
                }


                CM_thtabs.setupWithViewPager(t_viewPager);

                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Intent objintent = new Intent(MoviesTabActivity.this, HomeActivity.class);
            startActivity(objintent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        Intent objintent = new Intent(MoviesTabActivity.this, HomeActivity.class);
        startActivity(objintent);
        finish();

    }


}
