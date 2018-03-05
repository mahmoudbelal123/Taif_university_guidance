package mab.taif_university_guidance.visitor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.admin_view.places.AddPlacesActivity;
import mab.taif_university_guidance.admin_view.study_plans.GetStudyPlanActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.place.WebGetAllPlacesModel;

public class MapSearchPlacesActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private NiceSpinner spinnerSelectPlace;
    private ImageView imageViewPlaces;
     LatLng sydney;

    String[] idPlaceArray,namePlaceArray,imagePlaceArray,imageLevelArray,locationArray,detailsArray,levelNumberArray;
    StringBuffer idPlaceBuffer,namePlaceBuffer,imagePlaceBuffer,imageLevelBuffer,locationBuffer,detailsBuffer,levelNumberBuffer;


    String latitude ="21.4327167" ;
    String longitude="40.4935724";

    SupportMapFragment mapFragment;
    String namePlace , imagePlace,imageDetails=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search_places);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        spinnerSelectPlace=(NiceSpinner)findViewById(R.id.nice_spinner_select_place);
        imageViewPlaces=(ImageView)findViewById(R.id.imageView_place);


        idPlaceBuffer=new StringBuffer();
        namePlaceBuffer=new StringBuffer();
        imagePlaceBuffer=new StringBuffer();
        imageLevelBuffer=new StringBuffer();
        locationBuffer=new StringBuffer();
        detailsBuffer=new StringBuffer();
        levelNumberBuffer=new StringBuffer();


         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        getAllPlaces();

        spinnerSelectPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMap.clear();
                String[] LatAndLong=locationArray[i].toString().split(",");

                namePlace=namePlaceArray[i];
                imagePlace=imagePlaceArray[i];
                imageDetails=detailsArray[i];

                latitude=LatAndLong[0];
                longitude=LatAndLong[1];

                double lat=Double.parseDouble(latitude);
                double lag=Double.parseDouble(longitude);

                sydney = new LatLng(lat, lag);
                mMap.addMarker(new MarkerOptions().position(sydney).title(namePlaceArray[i]));


                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 17);
                mMap.animateCamera(cameraUpdate);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageViewPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapSearchPlacesActivity.this , DisplayPlaceImagesActivity.class);
                intent.putExtra("namePlace",namePlace);
                intent.putExtra("imagePlace",imagePlace);
                intent.putExtra("details",imageDetails);

                startActivity(intent);
            }
        });
    }

private  void getAllPlaces()
{
    WebGetAllPlacesModel mWebGetAllPlacesModel = new WebGetAllPlacesModel();
    mWebGetAllPlacesModel.getAllPlaces(MapSearchPlacesActivity.this, new RequestInterface() {
        @Override
        public void onResponse(String response) {
            if(response.equals("notDone"))
            {
                spinnerSelectPlace.setText("No Result");
                return;
            }


            try
            {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("places");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject search_object = jsonArray.getJSONObject(i);
                idPlaceBuffer.append(search_object.getString("id_place") + "#");
                namePlaceBuffer.append(search_object.getString("name_place") + "#");
                imagePlaceBuffer.append(search_object.getString("image_place") + "#");
                imageLevelBuffer.append(search_object.getString("image_level") + "#");
                locationBuffer.append(search_object.getString("location") + "#");
                detailsBuffer.append(search_object.getString("details") + "#");
                levelNumberBuffer.append(search_object.getString("level_number") + "#");

            }

            idPlaceArray = idPlaceBuffer.toString().split("#");
            namePlaceArray = namePlaceBuffer.toString().split("#");
            imagePlaceArray = imagePlaceBuffer.toString().split("#");
            imageLevelArray = imageLevelBuffer.toString().split("#");
            locationArray = locationBuffer.toString().split("#");
            detailsArray = detailsBuffer.toString().split("#");
            levelNumberArray = levelNumberBuffer.toString().split("#");


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapSearchPlacesActivity.this ,android.R.layout.simple_list_item_1,namePlaceArray);
            spinnerSelectPlace.setAdapter(adapter);



        }
                catch (Exception e) {
                     e.getMessage();
        }

    }

        @Override
        public void onError(VolleyError error) {

        }
    });

}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        double lat=Double.parseDouble(latitude);
        double log=Double.parseDouble(longitude);


        /*sydney = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(sydney).title("location"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 17);
        mMap.animateCamera(cameraUpdate);
*/
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) MapSearchPlacesActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
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
