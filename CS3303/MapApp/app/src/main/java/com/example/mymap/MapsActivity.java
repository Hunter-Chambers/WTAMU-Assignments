package com.example.mymap;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
 * Name:        Hunter Chambers, hc998658
 *
 * Version:     04/14/2020
 *
 * Description: This app utilizes the Google Maps API to display a world map in a Fragment.
 *              There are 3 buttons that are displayed at the top of the screen that allow
 *              the user to switch between normal, satellite, and hybrid views. Zoom controls
 *              are enabled to allow the user to zoom in and out on all 3 views.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng empireStateBuilding = new LatLng(40.748623, -73.985707);
        mMap.addMarker(new MarkerOptions().position(empireStateBuilding).title("Empire State Building"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(empireStateBuilding));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(empireStateBuilding));
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    public void mapButton(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void satelliteButton(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void hybridButton(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

}
