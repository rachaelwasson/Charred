package com.example.charred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationBarView;

public class HelpActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    //Bascom Hall
    private final LatLng mBascomHallLatLng = new LatLng(43.0757339,-89.4061951);

    //list of universities to select from
    String[] universityText = {"University of Wisconsin - Madison", "Edgewood College", "Madison Area Technical College"};

    LatLng mCurrentLatLng = new LatLng(43.0757339,-89.4061951);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
        });

        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, universityText);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.universityText);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

    }

    public void onButtonClick(View view) {
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.universityText);
        String universityInputString = acTextView.getText().toString();
        Log.i("selected location", universityInputString);

        if (universityInputString.equals("University of Wisconsin - Madison")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.0757339, -89.4061951);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("Your University"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
        } else if (universityInputString.equals("Edgewood College")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.0666181, -89.4269276);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("Your University"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
        } else if (universityInputString.equals("Madison Area Technical College")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.0937742, -89.4487173);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("Your University"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
        } else {
            //Log.i("code place", "here");
            displayMyLocation();
        }
    }


    //could be used to set current location, if user doesn't input a selected city??? just leaving
    //here for initial set up of get current location in case we need it
    private void displayMyLocation() {
        //check if permission is granted
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        //if not, ask for it
        if(permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        //if permission granted, display marker at center of university
        else {
            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(this,
                    task -> { Location mLastKnownLocation = task.getResult();
                        if(task.isSuccessful() && mLastKnownLocation != null) {
                            mCurrentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("Current Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
                        }
            });
        }
    }


    //handles the result of the request for location permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            //if request is cancelled, the result arrays are empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMyLocation();
            }
        }
    }


    public void goToReminders() {
        Intent remindersIntent = new Intent(this, RemindersActivity.class);
        startActivity(remindersIntent);
    }

    public void goToHome() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void goToHelp() {
        Intent homeIntent = new Intent(this, HelpActivity.class);
        startActivity(homeIntent);
    }

    public void goToJournal() {
        Intent homeIntent = new Intent(this, JournalActivity.class);
        startActivity(homeIntent);
    }


    private NavigationBarView.OnItemSelectedListener bottomnavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.remindersMenuItem:
                    goToReminders();
                    break;
                case R.id.homeMenuItem:
                    goToHome();
                    break;
                case R.id.findHelpMenuItem:MenuItem:
                    goToHelp();
                    break;
                case R.id.journalMenuItem:
                    goToJournal();
                    break;
            }
            return true;
        }
    };
}