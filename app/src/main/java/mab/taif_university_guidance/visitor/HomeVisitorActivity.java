package mab.taif_university_guidance.visitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mab.taif_university_guidance.R;

public class HomeVisitorActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mVisitorSearchPlacesBtn , mVisitorCollegeBtn , mVisitorFacultyMemberBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visitor);

        mVisitorSearchPlacesBtn=(Button)findViewById(R.id.button_visitor_search_places);
        mVisitorSearchPlacesBtn.setOnClickListener(this);


        mVisitorCollegeBtn=(Button)findViewById(R.id.button_visitor_colleges);
        mVisitorCollegeBtn.setOnClickListener(this);



        mVisitorFacultyMemberBtn=(Button)findViewById(R.id.button_visitor_faculty_members);
        mVisitorFacultyMemberBtn.setOnClickListener(this);


    }




    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_visitor_search_places :
                openSearchPlaces();
                break;


            case R.id.button_visitor_colleges :
                openCollegeSelection();
                break;



            case R.id.button_visitor_faculty_members :
                openFacultyMemberSelection();
                break;




        }

    }

    private  void openSearchPlaces(){
         startActivity(new Intent(HomeVisitorActivity.this , MapSearchPlacesActivity.class));

    }

    private  void openCollegeSelection(){
        startActivity(new Intent(HomeVisitorActivity.this , SearchCollegeAndDepartmentActivity.class));

    }

    private  void openFacultyMemberSelection(){
        startActivity(new Intent(HomeVisitorActivity.this , SearchFacultyMemberActivity.class));

    }


}
