package com.example.anupam.places.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.anupam.places.R;
import com.example.anupam.places.adapter.PlaceDetailsAdapter;
import com.example.anupam.places.constant.Constant;
import com.example.anupam.places.model.PhotoResult;
import com.example.anupam.places.util.ShowAlertDialogue;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlaceDetailsAdapter mAdapter;
    ArrayList<PhotoResult> photoResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("title"))
        {
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(getIntent().hasExtra("result")) {
            photoResults = getIntent().getParcelableArrayListExtra("result");
        }
        mAdapter = new PlaceDetailsAdapter(ListActivity.this,photoResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ListActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ListActivity.this,MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }



}
