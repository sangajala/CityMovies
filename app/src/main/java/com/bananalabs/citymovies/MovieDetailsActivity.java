package com.bananalabs.citymovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.citymovies.utils.YoutubeOverlayFragment;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailsActivity extends AppCompatActivity {

    ImageView img_thumnail;
    private YoutubeOverlayFragment ytPlayer;
    NestedScrollView nestview;
    Toolbar mToolbar;
    private ViewPager _pager;
    private PagerAdapter pm;
    private TypedArray img;
    private List<Integer> elements;
    int[] moviepics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        try {


            initToolbar();
            initViews();

            YoutubeOverlayFragment fragment = new YoutubeOverlayFragment();

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.webvideo_layout2, fragment)
                    .commit();


        } catch (Exception e) {

            e.printStackTrace();
        }
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

        try {


            moviepics = getResources().getIntArray(R.array.movie_imgs);

            _pager = (ViewPager) findViewById(R.id.moviepicspager);

            _pager.setClipToPadding(false);

            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 * 2, getResources().getDisplayMetrics());
            _pager.setPageMargin(-margin);


            pm = new PagersAdapter(this);


            elements = new ArrayList<Integer>();
            for (int i = 0; i < moviepics.length; i++) {
                this.elements.add(i);
            }

            _pager.setAdapter(pm);
            _pager.setOffscreenPageLimit(moviepics.length);


//            img_thumnail = (ImageView) findViewById(R.id.img_thumbnail);
//
//            final String img_url = "http://img.youtube.com/vi/" + "kAxTqRBOrKw" + "/0.jpg"; // this is link which will give u thumnail image of that video
//
//            MovieDetailsActivity.this.runOnUiThread(new Runnable() {
//
//                public void run() {
//
//
//                    Picasso.with(MovieDetailsActivity.this)
//                            .load(img_url)
//                            .placeholder(R.drawable.ic_launcher)
//                            .into(img_thumnail);
//
//                }
//            });
//
//            img_thumnail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    class PagersAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        public PagersAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = _inflater.inflate(R.layout.pager_item, container, false);
            container.addView(itemView);


            final LinearLayout linear_thumbnails = (LinearLayout) itemView.findViewById(R.id.linear_thumbnails);

            // Get the border size to show around each image
            int borderSize = linear_thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize * 2);

            // Set the thumbnail layout parameters. Adjust as required
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
//            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            //final ImageView thumbView = (ImageView) itemView.findViewById(R.id.imageview);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 //_pager.setCurrentItem(position);
                                                 Intent objintent = new Intent(MovieDetailsActivity.this, GalleryActivity.class);
                                                 startActivity(objintent);
                                                 finish();
                                             }
                                         }

            );
            linear_thumbnails.addView(thumbView);


            img = getResources().obtainTypedArray(R.array.movie_imgs);


            int sad = img.getResourceId(elements.get(position), -1);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .

                            load(img.getResourceId(elements.get(position),

                                    -1))
                    .

                            asBitmap()

                    .

                            into(new SimpleTarget<Bitmap>() {
                                     @Override
                                     public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                         //imageView.setImage(ImageSource.bitmap(bitmap));
                                         thumbView.setImageBitmap(bitmap);
                                     }
                                 }

                            );

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Intent objintent = new Intent(MovieDetailsActivity.this, HomeActivity.class);
            startActivity(objintent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        if (YoutubeOverlayFragment.isFullScreen()) {
            YoutubeOverlayFragment. ytplayer.setFullscreen(false);
        }else{

            finish();
        }

//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.webvideo_layout2);
//        Log.e("fragment.isVisible()", "  " + fragment.isVisible());
//        if (fragment != null && fragment.isVisible()) {
//            //finish();
//            super.onBackPressed();
//        }
        //super.onBackPressed();

//        Intent objintent = new Intent(MovieDetailsActivity.this, HomeActivity.class);
//        startActivity(objintent);
//        finish();

    }


}
