package mab.taif_university_guidance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mab.taif_university_guidance.R;

/**
 * Created by user on 2/20/2018.
 */

public class AdapterGetAllMembers extends BaseAdapter {
    private Context context;
    private String[] usernameArray;
    private String[] emailArray;



    private TextView username ,email;

    public AdapterGetAllMembers(Context context, String[] usernameArray , String[] emailArray ) {
        this.context=context;
        this.usernameArray=usernameArray;
        this.emailArray=emailArray;


    }
    @Override
    public int getCount() {
        /*return number of elements inside this array*/
        return usernameArray.length;
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
        inflater.inflate(R.layout.item_get_all_users,parent,false);
        View v;

        if(convertView == null) {
            v = new View(context);
            v = inflater.inflate(R.layout.item_get_all_users, null);


            username= (TextView) v.findViewById(R.id.text_display_name_user_adapter);
            email= (TextView) v.findViewById(R.id.text_display_email_user_adapter);


            username.setText(""+usernameArray[position]);
            email.setText(""+emailArray[position]);






        }else {
            v = (View) convertView;
        }


        return v;
    }
}