package com.nagarro.persistence.activity;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nagarro.persistence.R;
import com.nagarro.persistence.database.AppDatabase;
import com.nagarro.persistence.databinding.ActivityMainBinding;
import com.nagarro.persistence.entity.User;
import com.nagarro.persistence.utils.DatabaseInitializer;

import java.util.List;

/**
 * Created by Zachary on 4/20/2018.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    /**Method runs when map is ready*/
    public void onMapReady(GoogleMap googleMap) {
        List<User> list_users = DatabaseInitializer.getUserList(AppDatabase.getAppDatabase(this));
        mMap = googleMap;
        drawLocations(list_users);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            /**Waits for click on marker listner adn starts new intents based on click*/
            public boolean onMarkerClick(Marker marker) {
                String user_first = marker.getTitle();
                User blank_user= whichUser(user_first, list_users);
                String first_name = blank_user.getFirstName();
                String last_name = blank_user.getLastName();
                String linkedlink = blank_user.getLink();
                Intent intent = new Intent(MapsActivity.this, UserProfilesActivity.class);
                intent.putExtra("FIRST_NAME", first_name);
                intent.putExtra("LAST_NAME", last_name);
                intent.putExtra("LINKED_LINK", linkedlink);
                startActivity(intent);

                return false;
            }
        });
    }
    /**Displays all users on the map and moves the camera to their location*/
    public void drawLocations(List<User> userList){
        for (int x = 0; x < userList.size(); x++){
            double Column1 = userList.get(x).getLat();
            double Column2 = userList.get(x).getLon();
            String Column3 = userList.get(x).getLink();
            String Column4 = userList.get(x).getFirstName();
            LatLng user_marker = new LatLng(Column1, Column2);
            /**Adds markers on the map when fed in latitude and longitude*/
            mMap.addMarker(new MarkerOptions().position(user_marker).title(Column4));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(user_marker));
        }
    }

    /**Finds users in the the list of users we pulled from the room database*/
    public User whichUser(String name, List<User> userList){
        User blankuser = new User();
        for (int i = 0; i < userList.size(); i++ ){
            String params = userList.get(i).getFirstName();
            if (name.equals(params)){
                return userList.get(i);
            }
        }
        return blankuser;
    }

}
