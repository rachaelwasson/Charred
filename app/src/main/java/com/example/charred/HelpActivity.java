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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    String[] universityText = {"UW - Madison", "UW – Eau Claire", "UW – Green Bay", "UW – La Crosse",
        "UW - Milwaukee", "UW - Oshkosh", "UW - Parkside", "UW - Platteville", "UW - River Falls",
        "UW - Stevens Point", "UW - Stout", "UW - Superior", "UW - Whitewater"};

    LatLng mCurrentLatLng = new LatLng(43.0757339,-89.4061951);

    //UW–Eau Claire Resources
    LatLng mWellnessShack = new LatLng(44.8103921, -91.4976599);
    LatLng m22ADayCounseling = new LatLng(44.7938376, -91.4712879);
    LatLng mUWEauClaireCounseling = new LatLng(44.7986258, -91.503503);

    //UW-Green Bay Resources
    LatLng mOneidaBehavioralHealth = new LatLng(44.5192649, -88.1055468);
    LatLng mSpectrumBehavioralHealth = new LatLng(44.47428, -88.0030317);
    LatLng mBrownCountyMentalHealth = new LatLng(44.5228786, -87.9271781);

    //UW–La Crosse Resources
    LatLng mDriftlessRecoveryServices = new LatLng(43.8119153, -91.2524134);
    LatLng mTellurian = new LatLng(43.7664849, -91.2128696);
    LatLng mMayoClinic = new LatLng(43.8043673, -91.2448971);

    //UW-Madison Resources
    LatLng mMadisonCounselingServices = new LatLng(43.0678969, -89.4059981);
    LatLng mUWHealth = new LatLng(43.0550446, -89.3999812);
    LatLng mHarmonia = new LatLng(43.0783529, -89.3892746);
    LatLng mWestside = new LatLng(43.0784608, -89.4571266);
    LatLng mPeaceOfMindTherapy = new LatLng(43.0595989,-89.4946797);

    //UW-Milwaukee Resources
    LatLng mFlourishCounseling = new LatLng(43.0890354, -87.8827545);
    LatLng mMilwaukeeTherapy = new LatLng(43.0890546, -87.8893206);
    LatLng mHillaryCounseling = new LatLng(43.052314, -87.9080861);
    LatLng mMilwaukeeArtTherapy = new LatLng(43.0752638, -88.0306001);

    //UW-Oshkosh Resources
    LatLng mOshkoshCounselingWellness = new LatLng(44.0192483, -88.5334035);
    LatLng mShermanCounseling = new LatLng(44.0170357, -88.5548874);
    LatLng mKetelhutCounseling = new LatLng(44.0412036, -88.5640986);
    LatLng mSamaritanCounseling = new LatLng(44.1248707, -88.6247629);

    //UW-Parkside Resources
    LatLng mAuroraBehavioral = new LatLng(42.580682, -87.9227726);
    LatLng mInterConnections = new LatLng(42.5810841, -87.8246255);
    LatLng mMendingMinds = new LatLng(42.5854209, -87.8271067);

    //UW-Platteville Resources
    LatLng mSouthwestBehavioral = new LatLng(42.7192792, -90.4604962);
    LatLng mFindYourWay = new LatLng(42.7337624, -90.4807013);
    LatLng mWKMPsychology = new LatLng(42.7858381, -90.6667183);

    //UW-River Falls Resources
    LatLng mStCroixPsychological = new LatLng(44.8766893, -92.6279555);
    LatLng mHensonLoretta = new LatLng(44.8560804, -92.6126105);
    LatLng mMHealthFairview = new LatLng(44.8557414, -92.6296237);
    LatLng mRiverFallsCounseling = new LatLng(44.8570658, -92.6282127);

    //UW-Stevens Point Resources
    LatLng mPointCounseling = new LatLng(44.5201038, -89.5824886);
    LatLng mTrueNorthCounseling = new LatLng(44.5240968, -89.5824354);
    LatLng mStacyLuther = new LatLng(44.4900647, -89.5543841);

    //UW-Stout Resources
    LatLng mBeaconMentalHealth = new LatLng(44.8990332, -91.9352459);
    LatLng mArborPlace = new LatLng(44.8815431, -91.8943161);
    LatLng mNorthwestJourneyMenom = new LatLng(44.8832961, -91.8961403);

    //UW-Superior Resources
    LatLng mNorthwestJourneySuperior = new LatLng(46.7076774, -92.0751679);
    LatLng mNorthShoreMentalHealth = new LatLng(46.7832624, -92.1046406);
    LatLng mLakeSuperiorHealth = new LatLng(46.7833622, -92.1724924);

    //UW-Whitewater Resources
    LatLng mAmbroseHealth = new LatLng(42.8400781, -88.7433847);
    LatLng mMorningStar = new LatLng(42.8345141, -88.7543995);
    LatLng mPsychologicalEval = new LatLng(42.8337119, -88.7330177);

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

        if (universityInputString.equals("UW - Madison")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.0757339, -89.4061951);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Madison").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mMadisonCounselingServices).title("Madison Counseling Services").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mUWHealth).title("UW Health Behavioral Health and Recovery Clinic").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mHarmonia).title("Harmónia - Madison Center for Psychotherapy").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mWestside).title("Westside Psychotherapy").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mPeaceOfMindTherapy).title("Peace of Mind Therapy").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW – Eau Claire")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.7977867, -91.5169103);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Eau Claire").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mWellnessShack).title("Wellness Shack Inc.").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(m22ADayCounseling).title("22 A Day Counseling LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mUWEauClaireCounseling).title("UW Eau Claire Counseling").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW – Green Bay")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.5312802, -87.9259502);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Green Bay").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mOneidaBehavioralHealth).title("Oneida Behavioral Health Services").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mSpectrumBehavioralHealth).title("Spectrum Behavioral Health LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mBrownCountyMentalHealth).title("Brown County Mental Health Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW – La Crosse")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.8173997, -91.2333511);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - La Crosse").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mDriftlessRecoveryServices).title("Driftless Recovery Services").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mTellurian).title("Tellurian, Inc. La Crosse CARE Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMayoClinic).title("Mayo Clinic Health System - Outpatient Behavioral Health").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Milwaukee")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(43.0782669, -87.8841573);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Milwaukee").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mFlourishCounseling).title("Flourish Counseling Milwaukee").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMilwaukeeTherapy).title("Milwaukee Therapy").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mHillaryCounseling).title("Hillary Counseling").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMilwaukeeArtTherapy).title("Milwaukee Art Therapy Collective").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Oshkosh")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.0262133, -88.5530043);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Oshkosh").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mOshkoshCounselingWellness).title("Oshkosh Counseling Wellness Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mShermanCounseling).title("Sherman Counseling - Oshkosh").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mKetelhutCounseling).title("Ketelhut Counseling LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mSamaritanCounseling).title("Samaritan Counseling Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Parkside")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(42.6450098, -87.8539297);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Parkside").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mAuroraBehavioral).title("Aurora Behavioral Health Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mInterConnections).title("InterConnections S.C.").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMendingMinds).title("Mending Minds Behavioral Health Services").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Platteville")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(42.7322205, -90.4964561);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Platteville").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mSouthwestBehavioral).title("Southwest Behavioral Services - Outpatient Psychiatry").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mFindYourWay).title("Find Your Way Counseling, LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mWKMPsychology).title("WKM Psychology Clinics").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - River Falls")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.8530041, -92.6244211);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - River Falls").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mStCroixPsychological).title("St Croix Psychological Clinic").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mHensonLoretta).title("Henson Loretta P PhD LP").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMHealthFairview).title("M Health Fairview Clinic - River Falls ").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mRiverFallsCounseling).title("River Falls Counseling, LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        }
        else if (universityInputString.equals("UW - Stevens Point")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.5294529, -89.5735663);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Stevens Point").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mPointCounseling).title("Point Counseling Center LLC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mTrueNorthCounseling).title("True North Counseling & Wellness").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mStacyLuther).title("Stacy M. Stefaniak Luther, PsyD, LPC").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Stout")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(44.8716224, -91.9288928);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Stout").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mBeaconMentalHealth).title("Beacon Mental Health Resources").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mArborPlace).title("Arbor Place").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mNorthwestJourneyMenom).title("Northwest Journey Menomonie").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        } else if (universityInputString.equals("UW - Superior")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(46.7177513, -92.0903409);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW - Superior").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mNorthwestJourneySuperior).title("Northwest Journey Superior").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mNorthShoreMentalHealth).title("North Shore Mental Health Services").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mLakeSuperiorHealth).title("Lake Superior Community Health Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        }
        else if (universityInputString.equals("UW - Whitewater")) {
            mMap.clear();
            mCurrentLatLng = new LatLng(42.8416882, -88.7449515);
            mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("UW-Whitewater").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(mAmbroseHealth).title("Ambrose Health Center").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mMorningStar).title("Morning Star Psychotherapy Associates").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(mPsychologicalEval).title("Psychological Evaluation").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
        }
        else {
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