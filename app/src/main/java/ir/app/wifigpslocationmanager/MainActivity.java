package ir.app.wifigpslocationmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Button button_getLocation;
    public Button getButton_getLocation;
    View v;
    //-1 = not set yet
    double lat = -1;
    double lng = -1;

    private LocationManager locationManager;
    private Button Button;
    private Bundle saveInstanceState;
    private Exception Exception;
    private RuntimeException RuntimeException;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button = findViewById(R.id.button_setting);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        button_getLocation = findViewById(R.id.button_getLocation);

        //Init location manager
        if (checkLocationPermission()) {

            try {

                initLocationManager();
            }
            catch (Exception e) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "provider doesn't exist: network", Toast.LENGTH_LONG);
                toast.show();

            }


//            initLocationManager();


        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1905);

        }


        button_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "lat: " + lat + "lng: " + lng, Toast.LENGTH_SHORT).show();


            }

            private boolean onClick(Object button) {
                return false;
            }
        });

    }

    protected void onClick(Object button) {
        Button button1 = button_getLocation;
    }


    @SuppressLint("MissingPermission")
    private void initLocationManager() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            //Network
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 1, this);

            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            //GPS
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, this);

            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {

            lng = location.getLongitude();
            lat = location.getLatitude();

        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private boolean checkLocationPermission() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1905) {

            if (checkLocationPermission()) {

                try {

                    initLocationManager();
                }
                catch (Exception e){

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "provider doesn't exist: network", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1905);

            }

        }

    }

    public Context startActivity(MainActivity mainActivity) {
        return null;
    }

}