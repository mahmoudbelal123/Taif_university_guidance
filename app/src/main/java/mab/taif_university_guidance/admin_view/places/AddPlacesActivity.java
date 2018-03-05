package mab.taif_university_guidance.admin_view.places;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.util.AppUtils;

public class AddPlacesActivity extends FragmentActivity implements
         OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        double latitude, longitude;
        boolean verif = true;
        //FcPermissionsB mFcPermissionsB;
private GoogleMap mMap;
private GoogleApiClient mGoogleApiClient;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// Set the desired icon
        // occurred.
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("lat", latitude + "");
        resultIntent.putExtra("lon", longitude + "");

        setResult(RESULT_OK, resultIntent);
        finish();
        }
        });
        }

@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!AppUtils.isLocationEnabled(AddPlacesActivity.this)) {
        // notify user
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(AddPlacesActivity.this);
        dialog.setMessage("no Location");
        dialog.setPositiveButton("open setting", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface paramDialogInterface, int paramInt) {
        Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(myIntent);
        }
        });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

@Override
public void onClick(DialogInterface paramDialogInterface, int paramInt) {
        // TODO Auto-generated method stub

        }
        });
        //  dialog.show();
        } else {
        if (mMap != null) {
        try {


                LatLng sydney = new LatLng(21.4327167, 40.4935724);
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

       // LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 17);
        mMap.animateCamera(cameraUpdate);

                // Add a marker in Sydney and move the camera


        } catch (Exception e) {

        }
        }

        }

// ============ TODO Get the Address =============================//

        //================================================================//


        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
        }
        mMap.setMyLocationEnabled(true);
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
@Override
public void onMyLocationChange(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if (latitude != 0.0) {
        if (verif) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude,
        longitude)).zoom(16).build();
        Marker marker = mMap.addMarker(new MarkerOptions()
        .position(new LatLng(latitude,
        longitude))
        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker)))
        );
        verif = false;
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        }
        }
        };
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
@Override
public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        Marker marker = mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker)))
        );
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        latitude = latLng.latitude;
        longitude = latLng.longitude;


        }
        });


        // very Important for not get the current Location
       //>> mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
        }
        mMap.setMyLocationEnabled(true);
        }



@Override
public void onConnected(@Nullable Bundle bundle) {

        }

@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(AddPlacesActivity.this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
        }


private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) AddPlacesActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
        drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
        }
        }
