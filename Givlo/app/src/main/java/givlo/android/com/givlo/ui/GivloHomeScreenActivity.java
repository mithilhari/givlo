package givlo.android.com.givlo.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import givlo.android.com.givlo.R;
import givlo.android.com.givlo.data.SharedPreferencesData;
import givlo.android.com.givlo.utils.InternetConnectivity;

public class GivloHomeScreenActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mPickALocationButton,mCancelButton,mConfirmButton;
    private SharedPreferencesData sharedPreferencesData;
    private Handler fetchAddressHandler;
    private Runnable notification;
    private String mLocationText;
    private Double latitude, longitude;
    private TextView  searchAddressTextView;
    private CheckBox mClothes,mBooks,mShoes,mDonations,mBlankets;
    private LinearLayout mRG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givlo_home_screen);
        mPickALocationButton = (Button) findViewById(R.id.pick_location);
        mCancelButton= (Button) findViewById(R.id.cancel);
        mConfirmButton= (Button) findViewById(R.id.confirm);
        mRG = (LinearLayout) findViewById(R.id.select_category_layout);
        mClothes = (CheckBox) findViewById(R.id.clothes);
        mBooks = (CheckBox) findViewById(R.id.books);
        mShoes = (CheckBox) findViewById(R.id.shoes);
        mDonations = (CheckBox) findViewById(R.id.donation);
        mBlankets = (CheckBox) findViewById(R.id.blankets);
        searchAddressTextView = (TextView) findViewById(R.id.search_address_textview);
        sharedPreferencesData = new SharedPreferencesData();
        fetchAddressHandler = new Handler();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mClothes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && buttonView.isPressed()) {
                }

            }
        });
        mBooks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked&&buttonView.isPressed()){
                }

            }
        });
        mShoes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked&&buttonView.isPressed()){
                }

            }
        });
        mDonations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked&&buttonView.isPressed()){
                }

            }
        });
        mBlankets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked&&buttonView.isPressed()){
                }

            }
        });
        mPickALocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocationText != null && mLocationText.length() > 0) {
                    mRG.setVisibility(View.VISIBLE);
                    mPickALocationButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(GivloHomeScreenActivity.this, "Unable to fetch location!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mRG.setVisibility(View.GONE);
                    mPickALocationButton.setVisibility(View.VISIBLE);
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRG.setVisibility(View.GONE);
                mPickALocationButton.setVisibility(View.VISIBLE);
                goToNgosListScreen();
            }
        });
    }

    public void goToNgosListScreen(){
        Intent goToNgosListScreen=new Intent(GivloHomeScreenActivity.this,NgosListActivity.class);
        startActivity(goToNgosListScreen);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);
        moveToCurrentLocation();
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(final CameraPosition position) {
                mMap.clear();
                fetchAddressHandler.removeCallbacks(notification);
                if (isInternetAvailable()) {
                    notification = new Runnable() {
                        public void run() {

                            mLocationText = new GetAddressFromLocation()
                                    .getLocationAddressFromLatLng(GivloHomeScreenActivity.this,
                                            position.target);
                            latitude = position.target.latitude;
                            longitude = position.target.longitude;
                            searchAddressTextView.setText(mLocationText);
                        }
                    };

                    fetchAddressHandler.postDelayed(notification, 500);
                }
            }
        });
    }

    private void moveToCurrentLocation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (mMap != null) {
                    try {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    } catch (Exception e) {
                        Log.e("Location error", ": " + e.getLocalizedMessage());
                    }
                }
            }
        }, 5000);
    }

    public boolean isInternetAvailable() {
        if (new InternetConnectivity(GivloHomeScreenActivity.this)
                .isNetworkAvailable()) {
            return true;
        } else {
            Toast.makeText(
                    this.getApplicationContext(),
                    getResources().getString(
                            R.string.please_check_your_internet_connection_),
                    Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
