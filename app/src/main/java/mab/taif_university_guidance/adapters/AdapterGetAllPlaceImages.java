package mab.taif_university_guidance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import mab.taif_university_guidance.R;

/**
 * Created by user on 2/20/2018.
 */

public class AdapterGetAllPlaceImages extends BaseAdapter {
    private Context context;
    private String[] imageLevel;
    private String[] levelNumber;


    private TextView detailsTxt ,levelNumberTxt;
    private ImageView placeImage;

    public AdapterGetAllPlaceImages(Context context, String[] imageLevel, String[] levelNumber ) {
        this.context=context;
        this.imageLevel=imageLevel;
        this.levelNumber=levelNumber;

    }
    @Override
    public int getCount() {
        /*return number of elements inside this array*/
        return imageLevel.length;
    }
    @Override
    public Object getItem(int position) {
        /*return the item at posion -position-*/
        return null;
    }

    @Override
    public long getItemId(int position) {
        /*return the id of the row which in this case the index of the array*/
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_get_all_images_places,parent,false);
        View v;

        if(convertView == null) {
            v = new View(context);
            v = inflater.inflate(R.layout.item_get_all_images_places, null);


            detailsTxt= (TextView) v.findViewById(R.id.text_image_place);
            levelNumberTxt= (TextView) v.findViewById(R.id.text_level_number);
            levelNumberTxt= (TextView) v.findViewById(R.id.text_level_number);
            placeImage=(ImageView)v.findViewById(R.id.place_image);


            levelNumberTxt.setText(""+levelNumber[position]);
            Glide.with(context)
                    .load(imageLevel[position])
                    .centerCrop()
                    .into(placeImage);





        }else {
            v = (View) convertView;
        }


        return v;
    }
}
