package com.com.citymovies.POJO;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;
    private String moviename = "";
    private String movietrailerurl = "";

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public ImageItem() {

    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMovietrailerurl() {
        return movietrailerurl;
    }

    public void setMovietrailerurl(String movietrailerurl) {
        this.movietrailerurl = movietrailerurl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
