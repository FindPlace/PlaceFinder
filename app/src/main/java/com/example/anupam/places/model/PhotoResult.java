package com.example.anupam.places.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by anupam on 06-11-2016.
 */
public class PhotoResult implements Parcelable{
    LatLng latLng;
    private double rating;
    private String vicinity;
    private String name;
    private String photo_reference;
    private Integer width;
    private double lat;
    private double lng;

    protected PhotoResult(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        rating = in.readDouble();
        vicinity = in.readString();
        name = in.readString();
        photo_reference = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<PhotoResult> CREATOR = new Creator<PhotoResult>() {
        @Override
        public PhotoResult createFromParcel(Parcel in) {
            return new PhotoResult(in);
        }

        @Override
        public PhotoResult[] newArray(int size) {
            return new PhotoResult[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public PhotoResult(LatLng latLng, double rating, String vicinity, String name, String photo_reference, Integer width,double lat,double lng) {
        this.latLng = latLng;
        this.rating = rating;
        this.vicinity = vicinity;
        this.name = name;
        this.photo_reference = photo_reference;
        this.width = width;
        this.lat = lat;
        this.lng = lng;
    }

    public void setLatLng(LatLng latLng) {

        this.latLng = latLng;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PhotoResult(LatLng latLng, double rating, String vicinity, String name, double lat, double lng) {
        this.latLng = latLng;
        this.rating = rating;
        this.vicinity = vicinity;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeDouble(rating);
        dest.writeString(vicinity);
        dest.writeString(name);
        dest.writeString(photo_reference);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
