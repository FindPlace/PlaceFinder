package com.example.anupam.places.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.anupam.places.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_LOCATION_PERMISSION = 123;
    Toolbar toolbar;
    RelativeLayout relHotels,relATM,relHospitals,relCafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Locate");

        relHotels = (RelativeLayout) findViewById(R.id.rel_hotels);
        relATM = (RelativeLayout) findViewById(R.id.rel_atm);
        relHospitals = (RelativeLayout) findViewById(R.id.rel_hospitals);
        relCafe = (RelativeLayout) findViewById(R.id.rel_cafe);

        relHotels.setOnClickListener(this);
        relATM.setOnClickListener(this);
        relHospitals.setOnClickListener(this);
        relCafe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,MapActivity.class);
        switch (v.getId()){
            case R.id.rel_hotels:
                intent.putExtra("type","lodging");
                intent.putExtra("title","Hotels");
                break;
            case R.id.rel_atm:
                intent.putExtra("type","atm");
                intent.putExtra("title","ATM");
                break;
            case R.id.rel_hospitals:
                intent.putExtra("type","hospital");
                intent.putExtra("title","Hospitals");
                break;
            case R.id.rel_cafe:
                intent.putExtra("type","cafe");
                intent.putExtra("title","Cafe");
                break;

        }
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
       finish();
    }

}
