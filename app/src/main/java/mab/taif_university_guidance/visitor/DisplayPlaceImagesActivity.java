package mab.taif_university_guidance.visitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllPlaceImages;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.place.WebImagesPlaceModel;

public class DisplayPlaceImagesActivity extends AppCompatActivity {


    ListView listViewDisplayPlaceImages;
    ImageView imageViewPlaceImageOnly;
    TextView detailsTxt;

    String[] detailsArray ,imageLevelArray,levelNumberArray;
    StringBuffer detailsBuffer,imageLevelBuffer,levelNumberBuffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_place_images);

        listViewDisplayPlaceImages=(ListView)findViewById(R.id.listView_display_images_places);
        imageViewPlaceImageOnly=(ImageView)findViewById(R.id. imageView_image_place_only);
        detailsTxt=(TextView)findViewById(R.id.text_image_place);

        detailsBuffer=new StringBuffer();
        imageLevelBuffer=new StringBuffer();
        levelNumberBuffer=new StringBuffer();

        if(getIntent()!=null) {
            Glide.with(DisplayPlaceImagesActivity.this)
                    .load(getIntent().getStringExtra("imagePlace"))
                    .centerCrop()
                    .into(imageViewPlaceImageOnly);
            detailsTxt.setText(getIntent().getStringExtra("details"));
        }




        getImagesPlaces();
    }


    private void getImagesPlaces()
    {


        WebImagesPlaceModel mWebImagesPlaceModel = new WebImagesPlaceModel();
        if(getIntent() != null)
        {
            mWebImagesPlaceModel.getImagesPlace(DisplayPlaceImagesActivity.this, getIntent().getStringExtra("namePlace"), new RequestInterface() {
                @Override
                public void onResponse(String response) {

                    if(response.equals("not done"))
                    {

                        Toast.makeText(DisplayPlaceImagesActivity.this, "No Result", Toast.LENGTH_SHORT).show();                        return;
                    }


                    try
                    {

                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("placeImages");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject search_object = jsonArray.getJSONObject(i);
                            detailsBuffer.append(search_object.getString("details") + "#");
                            imageLevelBuffer.append(search_object.getString("image_level") + "#");
                            levelNumberBuffer.append(search_object.getString("level_number") + "#");

                        }

                        detailsArray = detailsBuffer.toString().split("#");
                        imageLevelArray = imageLevelBuffer.toString().split("#");
                        levelNumberArray = levelNumberBuffer.toString().split("#");

                        AdapterGetAllPlaceImages adapterGetAllPlaceImages = new AdapterGetAllPlaceImages(DisplayPlaceImagesActivity.this,imageLevelArray,levelNumberArray);
                        listViewDisplayPlaceImages.setAdapter(adapterGetAllPlaceImages);



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
    }
}
