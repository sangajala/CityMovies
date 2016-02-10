package com.bananalabs.citymovies;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    AppConstants objconstants;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        objconstants = new AppConstants(RegistrationActivity.this);

//        initViews();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        //getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        setTitle(getString(R.string.registration));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorwhite));




        //setupEvenlyDistributedToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Intent objintent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(objintent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        Intent objintent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(objintent);
        finish();

    }

    @Override
    public void onClick(View v) {


    }
}
