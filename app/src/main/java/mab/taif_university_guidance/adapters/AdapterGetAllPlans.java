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

public class AdapterGetAllPlans extends BaseAdapter {
    private Context context;
    private String[] idCollegesArray;
    private String[] idDepartmentsArray;
    private String[] idPlans;
    private String[] idUsersArray;
    private String[] nameArray;
    private String[] descriptionArray;


    private TextView name ,description;

    public AdapterGetAllPlans(Context context, String[] idCollegesArray, String[] idUsersArray, String[] nameArray, String[] descriptionArray,
                              String[] idPlans ,String[] idDepartments ) {
        this.context=context;
        this.idCollegesArray=idCollegesArray;
        this.idUsersArray=idUsersArray;
        this.nameArray=nameArray;
        this.descriptionArray=descriptionArray;
        this.idDepartmentsArray=idDepartments;
        this.idPlans=idPlans;


    }
    @Override
    public int getCount() {
        /*return number of elements inside this array*/
        return idUsersArray.length;
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
        inflater.inflate(R.layout.item_get_all_plans,parent,false);
        View v;

        if(convertView == null) {
            v = new View(context);
            v = inflater.inflate(R.layout.item_get_all_plans, null);


            name= (TextView) v.findViewById(R.id.text_plan_name);
            description= (TextView) v.findViewById(R.id.text_plan_description);


            name.setText(""+nameArray[position]);
            description.setText(""+descriptionArray[position]);






        }else {
            v = (View) convertView;
        }


        return v;
    }
}
