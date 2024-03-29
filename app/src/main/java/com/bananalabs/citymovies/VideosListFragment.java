/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bananalabs.citymovies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citymovies.utils.MovieDetails;
import com.citymovies.utils.YoutubeOverlayFragment;
import com.com.citymovies.POJO.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class VideosListFragment extends Fragment {

    ImageItem objimageitem;
    public static  YoutubeOverlayFragment fragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_moives_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                getRandomSublist(MovieDetails.sCheeseStrings, 9, MovieDetails.youtubeUrls)));
    }

    private  List<ImageItem> getRandomSublist(String[] array, int amount, String[] urls) {



        ArrayList<ImageItem> list = new ArrayList<ImageItem>(amount);

        for (int i = 0; i < amount; i++) {

            objimageitem = new ImageItem();

            objimageitem.setMoviename(array[i]);
            objimageitem.setMovietrailerurl(urls[i]);

            list.add(objimageitem);
        }

//        Random random = new Random();
//        while (list.size() < amount) {
//            list.add(array[random.nextInt(array.length)]);
//        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        YoutubeOverlayFragment sfragment = fragment;

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<ImageItem> mValues;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView, VideoPreviewPlayButton;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.CM_iv_movie);
                VideoPreviewPlayButton = (ImageView) view.findViewById(R.id.VideoPreviewPlayButton);
                mTextView = (TextView) view.findViewById(R.id.CM_txtmovieName);
            }

            @Override
            public String toString() {

                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position).getMoviename();
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<ImageItem> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gallery_list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position).getMoviename();
            holder.mTextView.setText(mValues.get(position).getMoviename());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CheeseDetailActivity.class);
//                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
                }
            });

            holder.VideoPreviewPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // fragment = new YoutubeOverlayFragment();

                   //  sfragment.onClick(holder.mImageView, mValues.get(position).getMovietrailerurl(), position);

                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(MovieDetails.getRandomCheeseDrawable(position))
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
