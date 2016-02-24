package com.citymovies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.bananalabs.citymovies.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.common.primitives.Ints;

import java.util.ArrayList;

public class YoutubeOverlayFragment extends Fragment implements OnBackPressedListener {

    private static boolean fullscreen;
    public static YouTubePlayer ytplayer;
    View videoContainer;
    private ArrayList<Integer> hideableViews;
    private static final String VIDEO_ID = "VIDEO_ID";
    private static final String YT_DEVELOPER_KEY = "YT_DEVELOPER_KEY";
    private String videoId; // yt video id
    private String ytKey;
    private static final String HIDEABLE_VIEWS = "HIDEABLE_VIEWS";
    private Display display;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogHelper.logMessage("onCreate: " + savedInstanceState);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            this.videoId = getArguments().getString(VIDEO_ID, null);
            this.hideableViews = getArguments().getIntegerArrayList(HIDEABLE_VIEWS);
            this.ytKey = getArguments().getString(YT_DEVELOPER_KEY);

        }

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.videoId = getString(R.string.youtube_video_code);
        this.ytKey = getString(R.string.youtubedeveloperkey);



        videoContainer = inflater.inflate(R.layout.you_tube_api, container, false);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        this.hideableViews = new ArrayList<>();
        hideableViews.add(R.id.youtube_layout);

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(YoutubeOverlayFragment.HIDEABLE_VIEWS, hideableViews);
        bundle.putString(YoutubeOverlayFragment.YT_DEVELOPER_KEY, ytKey);
        youTubePlayerFragment.setArguments(bundle);

        youTubePlayerFragment.initialize(getString(R.string.youtubedeveloperkey), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                ytplayer = player;

                if (!wasRestored) {
                    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    ytplayer.cueVideo(getString(R.string.youtube_video_code));

                    ytplayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

                        @Override
                        public void onFullscreen(boolean _isFullScreen) {
                            fullscreen = _isFullScreen;
                        }
                    });
                    //player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        return videoContainer;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogHelper.logMessage("onConfigurationChanged: is landscape" + (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE));
        super.onConfigurationChanged(newConfig);

        if (videoId != null && ytplayer != null) {
            ViewGroup.LayoutParams params = videoContainer.getLayoutParams();
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (getActivity() != null) {
                    for (int id : hideableViews) {
                        getActivity().findViewById(id).setVisibility(View.GONE);
                    }
                }
                Point size = new Point();
                display.getRealSize(size);
                params.height = size.y;
                params.width = size.x;
                LogHelper.logMessage("x:" + size.x + " y:" + size.y);
                videoContainer.setX(0);
                videoContainer.setY(0);
                ytplayer.setFullscreen(true);

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (getActivity() != null) {
                    for (int id : hideableViews) {
                        getActivity().findViewById(id).setVisibility(View.VISIBLE);
                    }
                }
                //setVideoPostion(this.attachedImgContainer);
                ytplayer.setFullscreen(false);
            }
            videoContainer.setLayoutParams(params);
        }

    }


    /**
     * Exit fullscreen mode of yt player
     */
    public void exitFullScreen() {
        ytplayer.setFullscreen(false);
    }

    /**
     * @return returns if fragment is in fullscreen mode
     */
    public static boolean isFullScreen() {
        return fullscreen;
    }


    @Override
    public boolean onBackPressed() {
        if (isFullScreen()) {
            exitFullScreen();
            return true;
        }
        return false;
    }


}