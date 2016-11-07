package com.example.anupam.places.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anupam.places.R;
import com.example.anupam.places.constant.Constant;
import com.example.anupam.places.model.PhotoResult;
import com.example.anupam.places.util.GPSTracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anupam on 06-11-2016.
 */
public class PlaceDetailsAdapter extends RecyclerView.Adapter<PlaceDetailsAdapter.ViewHolder>{

    Context context;
    GPSTracker gps;

    public PlaceDetailsAdapter(Context context, ArrayList<PhotoResult> photoResultArrayList) {
        this.context = context;
        this.photoResultArrayList = photoResultArrayList;
    }

    ArrayList<PhotoResult> photoResultArrayList;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_details,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(photoResultArrayList.get(position).getPhoto_reference()!=null) {
            Picasso.with(context).load(Constant.BASE_URL + Constant.PHOTO_URL + "maxwidth=" + photoResultArrayList.get(position).getWidth() + "&photoreference=" + photoResultArrayList.get(position).getPhoto_reference() + "&key=" + Constant.GOOGLE_API_KEY).error(R.drawable.img_no_img).placeholder(R.drawable.img_loading).into(holder.imgHotel);
        }
        else
        {
            holder.imgHotel.setImageDrawable(context.getResources().getDrawable(R.drawable.img_no_img));
        }
        holder.txtName.setText(photoResultArrayList.get(position).getName());
        holder.txtAddress.setText(photoResultArrayList.get(position).getVicinity());
        if(photoResultArrayList.get(position).getRating()!=0) {
            holder.txtRating.setText(String.valueOf(photoResultArrayList.get(position).getRating()));
            holder.ratingBar.setRating((float) photoResultArrayList.get(position).getRating());
        }
        else
        {
            holder.txtRating.setText("No reviews yet");
            holder.ratingBar.setVisibility(View.GONE);
        }
        double distance = distance(photoResultArrayList.get(position).getLat(),photoResultArrayList.get(position).getLng());
        holder.txtDistance.setText("Distance from your location :"+String.format("%.2f",distance));
    }

    @Override
    public int getItemCount() {
        return photoResultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHotel;
        TextView txtName,txtAddress,txtRating,txtDistance;
        RatingBar ratingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgHotel = (ImageView) itemView.findViewById(R.id.img_hotel);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
            txtRating = (TextView) itemView.findViewById(R.id.txt_rating);
            txtDistance = (TextView) itemView.findViewById(R.id.txt_distance);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
        }
    }
    private double distance( double latitude, double longitude) {
        gps = new GPSTracker(context);
        double currentLatitude= gps.getLatitude();
        double currentLongitude = gps.getLongitude();
        double theta = currentLongitude - longitude;
        double dist = Math.sin(deg2rad(currentLatitude)) * Math.sin(deg2rad(latitude)) + Math.cos(deg2rad(currentLatitude)) * Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
