package com.example.anupam.places.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anupam.places.model.PhotoResult;
import com.example.anupam.places.util.GPSTracker;
import com.example.anupam.places.R;
import com.example.anupam.places.constant.Constant;
import com.example.anupam.places.response.PlaceDetails;
import com.example.anupam.places.response.Result;
import com.example.anupam.places.rest.ApiClient;
import com.example.anupam.places.rest.ApiInterface;
import com.example.anupam.places.util.ConnectionDetector;
import com.example.anupam.places.util.ShowAlertDialogue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 123;
    GPSTracker gps;

    GoogleMap googleMap;
    double latitude = 0;
    double longitude = 0;
    private Integer PROXIMITY_RADIUS = 5000;
    ArrayList<PlaceDetails> placeDetailses;
    String types;
    ConnectionDetector connectionDetector;
    View bottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    ArrayList<PhotoResult> photoResults;

    ImageView imgHotel;
    TextView txtName, txtAddress, txtRating, txtDistance;
    RatingBar ratingBar;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        connectionDetector = new ConnectionDetector(MapActivity.this);
        if (!connectionDetector.isNetworkAvailable()) {
            ShowAlertDialogue.showDialogue(MapActivity.this, MainActivity.class, Constant.ERROR_NETWORK);
        }
        gps = new GPSTracker(MapActivity.this);
        if (!gps.canGetLocation()) {
            ShowAlertDialogue.showSettingsAlert(MapActivity.this);
        }
        setContentView(R.layout.activity_map);

        bottomSheet = findViewById(R.id.bottom_sheet);
        imgHotel = (ImageView) findViewById(R.id.img_hotel);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtRating = (TextView) findViewById(R.id.txt_rating);
        txtDistance = (TextView) findViewById(R.id.txt_distance);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent().hasExtra("title")) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

          ActivityCompat.requestPermissions(MapActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
      //  if(googleMap!=null)
     //   fetchLocationOnMap();

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        if(googleMap!=null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(this.googleMap ==null)
        {
            this.googleMap = googleMap;
            fetchLocationOnMap();
            googleMap.setOnMarkerClickListener(this);
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        }
    }

    public void fetchLocationOnMap() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

            com.example.anupam.places.response.Location location1 = new com.example.anupam.places.response.Location();
            location1.setLat(latitude);
            location1.setLng(longitude);
            String location = location1.getLat() + "," + location1.getLng();

        if(getIntent().hasExtra("type"))
        {
            types = getIntent().getStringExtra("type");
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Result> call = apiService.getPlaces(location,PROXIMITY_RADIUS,types,true,Constant.GOOGLE_API_KEY);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.code() == 200)
                {
                    progressDialog.dismiss();
                    if(response.body()!=null)
                    {
                        progressDialog.dismiss();
                        placeDetailses = response.body().getResults();

                        photoResults = new ArrayList<PhotoResult>();
                        for(int i=0;i<placeDetailses.size();i++)
                        {
                            MarkerOptions markerOptions = new MarkerOptions();
                            double latitude = placeDetailses.get(i).getGeometry().getLocation().getLat();
                            double longitude = placeDetailses.get(i).getGeometry().getLocation().getLng();
                            LatLng latLng = new LatLng(latitude,longitude);
                            String placeName = placeDetailses.get(i).getName();
                            String vicinity = placeDetailses.get(i).getVicinity();
                            if(placeDetailses.get(i).getPhotos()!=null) {
                                for (int j = 0; j < placeDetailses.get(i).getPhotos().size(); j++) {

                                    photoResults.add(new PhotoResult(latLng, placeDetailses.get(i).getRating(), vicinity, placeName, placeDetailses.get(i).getPhotos().get(j).getPhoto_reference(), placeDetailses.get(i).getPhotos().get(j).getWidth(),placeDetailses.get(i).getGeometry().getLocation().getLat(),placeDetailses.get(i).getGeometry().getLocation().getLng()));
                                }
                            }
                            else
                            {
                                photoResults.add(new PhotoResult(latLng, placeDetailses.get(i).getRating(), vicinity,placeName ,placeDetailses.get(i).getGeometry().getLocation().getLat(),placeDetailses.get(i).getGeometry().getLocation().getLng()));
                            }
                            googleMap.addMarker(markerOptions.position(latLng).title(placeName + vicinity));
                            googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                        }

                    }
                }
                else if(response.code() == 408)
                {
                    progressDialog.dismiss();
                    ShowAlertDialogue.showDialogue(MapActivity.this,MainActivity.class,"Server timeout");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MapActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_list) {
            Intent intent = new Intent(MapActivity.this,ListActivity.class);
            if(photoResults!=null &&!photoResults.isEmpty()) {
                intent.putParcelableArrayListExtra("result", photoResults);
                intent.putExtra("title",getIntent().getStringExtra("title"));
                startActivity(intent);
            }
            else
            {
                ShowAlertDialogue.showDialogue(MapActivity.this,MainActivity.class,Constant.ERROR_MSG);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng markerPos = marker.getPosition();
        for(PhotoResult photoResult:photoResults)
        {
            if(markerPos.equals(photoResult.getLatLng()))
            {
                if(photoResult.getPhoto_reference()!=null) {
                    Picasso.with(MapActivity.this).load(Constant.BASE_URL + Constant.PHOTO_URL + "maxwidth=" + photoResult.getWidth() + "&photoreference=" + photoResult.getPhoto_reference() + "&key=" + Constant.GOOGLE_API_KEY).error(R.drawable.img_no_img).placeholder(R.drawable.img_loading).into(imgHotel);
                }
                else
                {
                    imgHotel.setImageDrawable(getResources().getDrawable(R.drawable.img_no_img));
                }
                    txtName.setText(photoResult.getName());
                    txtAddress.setText(photoResult.getVicinity());
                if(photoResult.getRating()!=0) {
                    ratingBar.setVisibility(View.VISIBLE);
                    txtRating.setText(String.valueOf(photoResult.getRating()));
                    ratingBar.setRating((float) photoResult.getRating());
                }
                else
                {
                    txtRating.setText("No reviews yet");
                    ratingBar.setVisibility(View.GONE);
                }
                    double distance = distance(photoResult.getLat(), photoResult.getLng());
                    txtDistance.setText("Distance from your location :" + String.format("%.2f", distance));

            }
        }
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        return false;
    }
    private double distance( double latitude, double longitude) {
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
