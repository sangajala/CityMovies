package com.bananalabs.citymovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.citymovies.utils.YoutubeOverlayFragment;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView img_thumnail;
    private YoutubeOverlayFragment ytPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        try {

            ytPlayer = YoutubeOverlayFragment.newBuilder
                    (MovieDetailsActivity.this.getResources().getString(R.string.youtubedeveloperkey), this)
                    .setScrollableViewId(R.id.scrollView).buildAndAdd();

            //ytPlayer = YoutubeOverlayFragment(null);

            initViews();


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void initViews() {

        try {


            img_thumnail = (ImageView) findViewById(R.id.img_thumnail);

            final String img_url = "http://img.youtube.com/vi/" + "lYMYbaRalAQ" + "/0.jpg"; // this is link which will give u thumnail image of that video

            MovieDetailsActivity.this.runOnUiThread(new Runnable() {

                public void run() {


                    Picasso.with(MovieDetailsActivity.this)
                            .load(img_url)
                            .placeholder(R.drawable.ic_launcher)
                            .into(img_thumnail);

                }
            });

            img_thumnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ytPlayer.onClick(img_thumnail, MovieDetailsActivity.this.getResources().getString(R.string.youtube_video_code));
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (ytPlayer.onBackPressed()) {
            // handled by fragment
        } else {
            super.onBackPressed();
        }
    }


}
