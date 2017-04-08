package com.simran.ixicode;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simran.ixicode.models.Place;
import com.simran.ixicode.utility.AppConstant;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Place posts = (Place) getIntent().getSerializableExtra(AppConstant.PLACE_ID);
        if (posts != null) {
            Log.i("Simran map", posts.getLatitude() + " list loaded.");
            LatLng place = new LatLng(Double.parseDouble(posts.getLatitude()), Double.parseDouble(posts.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(place).title("Marker in "+posts.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        } else {
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}