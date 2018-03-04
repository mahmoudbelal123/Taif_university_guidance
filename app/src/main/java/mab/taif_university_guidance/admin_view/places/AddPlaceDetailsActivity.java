package mab.taif_university_guidance.admin_view.places;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

import mab.taif_university_guidance.R;


public class AddPlaceDetailsActivity extends AppCompatActivity {

    EditText namePlaceEdit,detailsPlaceEdit,locationPlaceEdit;
    ImageView placeImageView,levelImageView;
    Button addPlaceBtn;



    private int PICK_IMAGE_REQUEST=1;
    private int PICK_IMAGE_REQUEST_LEVEL=2;
    private Bitmap bitmapPlace ,bitmapLevel;
    private String latitude ,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_details);

        namePlaceEdit=(EditText)findViewById(R.id.edit_name_place);
        detailsPlaceEdit=(EditText)findViewById(R.id.edit_place_details);
        locationPlaceEdit=(EditText)findViewById(R.id.edit_location);
        addPlaceBtn=(Button)findViewById(R.id.button_add_place);
        placeImageView=(ImageView)findViewById(R.id.imageView_upload_place_image);
        levelImageView=(ImageView)findViewById(R.id.imageView_upload_level_image);




        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddPlaceDetailsActivity.this, AddPlacesActivity.class), 200);

            }
        });


        placeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        });

        levelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooserLevel();

            }
        });




    }


    public void showFileChooserLevel(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i.createChooser(i,"choose image"),PICK_IMAGE_REQUEST_LEVEL);
    }

    //this method is to open file chooser to choose image
    public void showFileChooser(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i.createChooser(i,"choose image"),PICK_IMAGE_REQUEST);
    }
    //for upload image
//convert bitmap to string as following
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imagearray=baos.toByteArray();
        String encodeImg= Base64.encodeToString(imagearray,Base64.DEFAULT);
        return encodeImg;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                ContentResolver resolver = getContentResolver();
                bitmapPlace = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(getApplicationContext()).load(filePath).fit().into(placeImageView);
                //  imgbtn.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
       else  if (requestCode == PICK_IMAGE_REQUEST_LEVEL && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                ContentResolver resolver = getContentResolver();
                bitmapLevel = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(getApplicationContext()).load(filePath).fit().into(levelImageView);
                //  imgbtn.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

       else  if (requestCode == 200 && resultCode == RESULT_OK) {

            latitude = data.getStringExtra("lat");
            longitude = data.getStringExtra("lon");
//            mapverif.setVisibility(View.VISIBLE);

//            mapverif.setImageDrawable(getResources().getDrawable(R.drawable.success_verif));



            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(AddPlaceDetailsActivity.this, Locale.getDefault());

                double lat=Double.parseDouble(latitude);
                double lon=Double.parseDouble(longitude);

                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                if(!country.equals(null) || !city.equals(null) || !knownName.equals(null))
                    locationPlaceEdit.setText(address);
                else
                    locationPlaceEdit.setText(address);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();}

    }



}
