package givlo.android.com.givlo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import givlo.android.com.givlo.R;
import givlo.android.com.givlo.data.SharedPreferencesData;
import givlo.android.com.givlo.utils.GpsConnectivity;
import givlo.android.com.givlo.utils.InternetConnectivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleApiClient mGoogleApiClient;
    private SharedPreferencesData sharedPreferencesData;
    public final int NO_GPS = 2, NO_INTERNET = 1;
    private Dialog mLocationAlertDialog, mInternetAlertDialog;
    private Button mOpenLocationSettingsButton, mSetLocationManuallyButton,
            mOpenInternetSettingsButton;
    private int noOfTimesRequested = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        sharedPreferencesData=new SharedPreferencesData();
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(),
                sharedPreferencesData.APP_LATITUDE, "");
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(),
                sharedPreferencesData.APP_LONGITUDE, "");
        setContentView(R.layout.activity_splash);
        setLocationAlertDialog();
        setInternetAlertDialog();
        handleLocation();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    Location mLastLocation;
    LocationRequest mLocationRequest = new LocationRequest();
    boolean mRequestingLocationUpdates;

    protected void createLocationRequest() {

        mLocationRequest.setInterval(100);
        mLocationRequest.setFastestInterval(50);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null&&(mLastLocation.getAccuracy()<=60||noOfTimesRequested>3)) {
            storeLocation(mLastLocation);
        }else{
            startLocationUpdates();
        }
        noOfTimesRequested++;
    }

    private void storeLocation(Location location) {
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(),
                sharedPreferencesData.CURRENT_LATITUDE,
                "" + location.getLatitude());
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(),
                sharedPreferencesData.CURRENT_LONGITUDE,
                "" + location.getLongitude());
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(), sharedPreferencesData.APP_LATITUDE, ""
                        + location.getLatitude());
        sharedPreferencesData.storeValueIntoSharedPreference(
                getApplicationContext(), sharedPreferencesData.APP_LONGITUDE,
                "" + location.getLongitude());
        GpsConnectivity gpsConnectivity = new GpsConnectivity(this);
        InternetConnectivity intConnectivity = new InternetConnectivity(this);
        if (gpsConnectivity.isGPSEnabled()
                && intConnectivity.isNetworkAvailable()) {
            goToHomeScreen();
        }
    }
    public void goToHomeScreen() {
        Intent callingHomeIntent = new Intent(this, GivloHomeScreenActivity.class);
        startActivity(callingHomeIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()
                && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void startLocationUpdates() {
        if(mGoogleApiClient!=null&&mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }else{
            mGoogleApiClient = new GoogleApiClient.Builder(this)

                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
            mGoogleApiClient.connect();
            startLocationUpdates();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Check GPS status, And show an alert to enable GPS if necessary.
     */
    void handleLocation() {
        GpsConnectivity gpsConnectivity = new GpsConnectivity(this);
        if (!gpsConnectivity.isGPSEnabled()) {
            /*
             * GPS disabled. Show an alert that can navigate to GPS setting
			 * screen.
			 */
            showLocationDialog();
        } else {
            hideLocationAlertDialog();
            getLocationDetails();
//            startLocationUpdates();
        }
    }
    @SuppressWarnings("deprecation")
    private void setLocationAlertDialog() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        mLocationAlertDialog = new Dialog(SplashActivity.this);
        mLocationAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLocationAlertDialog.setCanceledOnTouchOutside(false);
        mLocationAlertDialog
                .setContentView(R.layout.dialog_location_unavailable);
        mSetLocationManuallyButton = (Button) mLocationAlertDialog
                .findViewById(R.id.set_location_button);
        mOpenLocationSettingsButton = (Button) mLocationAlertDialog
                .findViewById(R.id.open_settings_button);
        lp.copyFrom(mLocationAlertDialog.getWindow().getAttributes());
        lp.width = getWindowManager().getDefaultDisplay().getWidth();
        mLocationAlertDialog.getWindow().setAttributes(lp);
        Typeface copperplateGothicLight = Typeface.createFromAsset(
                getApplicationContext().getAssets(), "fonts/open-sans.regular.ttf");
        mSetLocationManuallyButton.setTypeface(copperplateGothicLight);
        mOpenLocationSettingsButton.setTypeface(copperplateGothicLight);
        setOnClickListenersToDialogButtons();
    }

    private void setInternetAlertDialog() {
        mInternetAlertDialog = new Dialog(SplashActivity.this);
        mInternetAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mInternetAlertDialog.setCanceledOnTouchOutside(false);
        mInternetAlertDialog
                .setContentView(R.layout.dialog_internet_unavailable);
        mOpenInternetSettingsButton = (Button) mInternetAlertDialog
                .findViewById(R.id.open_settings_button);
        Typeface copperplateGothicLight = Typeface.createFromAsset(
                getApplicationContext().getAssets(), "fonts/open-sans.regular.ttf");
        mOpenInternetSettingsButton.setTypeface(copperplateGothicLight);
        mOpenInternetSettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS),
                        NO_INTERNET);
            }
        });
    }
    private void setOnClickListenersToDialogButtons() {
        mSetLocationManuallyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        mOpenLocationSettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToLocationSettingsScreen();
            }

        });
    }

    public void goToLocationSettingsScreen() {
        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(myIntent, NO_GPS);
    }

    void handleInternet() {
        InternetConnectivity intConnectivity = new InternetConnectivity(this);
        if (!intConnectivity.isNetworkAvailable()) {
            /*
             * GPS disabled. Show an alert that can navigate to GPS setting
			 * screen.
			 */
            showInternetDialog();
        } else {
            hideInternetAlertDialog();
            handleLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NO_GPS:
                handleLocation();
                break;
            case NO_INTERNET:
                handleInternet();
                break;
            default:
                break;
        }
    }

    public void getLocationDetails() {
        createLocationRequest();
    }

    public void showLocationDialog() {
        if (mLocationAlertDialog != null) {
            if (!mLocationAlertDialog.isShowing()) {
                mLocationAlertDialog.show();
            } else {
                setLocationAlertDialog();
                mLocationAlertDialog.show();
            }
        } else {
            setLocationAlertDialog();
            mLocationAlertDialog.show();
        }
    }

    private void hideLocationAlertDialog() {
        if (mLocationAlertDialog != null && mLocationAlertDialog.isShowing()) {
            mLocationAlertDialog.dismiss();
        }
    }

    public void showInternetDialog() {
        if (mInternetAlertDialog != null) {
            if (!mInternetAlertDialog.isShowing()) {
                mInternetAlertDialog.show();
            } else {
                setInternetAlertDialog();
                mInternetAlertDialog.show();
            }
        } else {
            setInternetAlertDialog();
            mInternetAlertDialog.show();
        }

    }

    private void hideInternetAlertDialog() {
        if (mInternetAlertDialog != null && mInternetAlertDialog.isShowing()) {
            mInternetAlertDialog.dismiss();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null&&(location.getAccuracy()<=60||noOfTimesRequested>3)) {
            storeLocation(location);
        }else{
            startLocationUpdates();
        }
        noOfTimesRequested++;

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }


}
