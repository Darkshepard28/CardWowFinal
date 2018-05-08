package com.nagarro.persistence.activity;

import android.Manifest;
import android.arch.persistence.room.Database;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.nagarro.persistence.R;
import com.nagarro.persistence.database.AppDatabase;
import com.nagarro.persistence.databinding.ActivityMainBinding;
import com.nagarro.persistence.entity.User;
import com.nagarro.persistence.utils.DatabaseInitializer;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        /**Populates  database with  test data we can use this button to refresh our database as well*/
        activityMainBinding.clickHereBtn.setOnClickListener(view ->
                DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this))
        );

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {
                /**Asks for permission for the using location*/
                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
                Toast.makeText(getApplicationContext(), "App boot 7 seconds", Toast.LENGTH_LONG).show();
                Thread.sleep(6000);
                locationWatcher(gps);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //List<User> all_users = DatabaseInitializer.getUserList(AppDatabase.getAppDatabase(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent intent = new Intent (this, HatActivity.class);
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                shutDown(findViewById(android.R.id.content));
                return true;
            case R.id.item5:
                Intent homeintent = new Intent(this, MainActivity.class);
                startActivity(homeintent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /** Takes in current view and closes down the application  **/
    public void shutDown(View v){
        finish();
        System.exit(0);
    }
    /**Starts map intent when clicked*/
    public void onClickMap(View view){
        Intent intent = new Intent (this, MapsActivity.class);
        startActivity(intent);
    }

    /**Starts Profile Intents whe clicked*/
    public void onClickProfile(View view){
        Intent intent = new Intent (this, ProfileActivity.class);
        startActivity(intent);
    }
    /**Checks user location and updates it at regular intervals*/
    public void locationWatcher(GPSTracker gps) {
        gps = new GPSTracker(MainActivity.this);
        User cool_user = new User();
        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            /**Adds some components to our main user*/
            cool_user.setLat(latitude);
            cool_user.setLon(longitude);
            cool_user.setFirstName("Zachary");
            cool_user.setLastName("Stovall");
            cool_user.setLink("google.com");
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            DatabaseInitializer.updateChange(AppDatabase.getAppDatabase(this), cool_user);

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }
    }
    @Override
    /**Destoys Instance of app database when are finished with it**/
    protected void onDestroy () {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}
