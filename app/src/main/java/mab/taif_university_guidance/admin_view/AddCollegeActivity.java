package mab.taif_university_guidance.admin_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebAddCollegeModel;

public class AddCollegeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mCollegeNameEdit , mCollegeDescription;
    private Button mSaveCollegeData;
     private WebAddCollegeModel mWebAddCollegeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_college);

        mSaveCollegeData=(Button)findViewById(R.id.button_save_college_data);
        mSaveCollegeData.setOnClickListener(this);

        mCollegeNameEdit=(EditText)findViewById(R.id.edit_name_college);
        mCollegeDescription=(EditText)findViewById(R.id.edit_description_college);

        mWebAddCollegeModel = new WebAddCollegeModel();


    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_save_college_data:

                callAddCollegeData();
                break;


        }


    }

    private  void callAddCollegeData()
    {
        String collegeName=mCollegeNameEdit.getText().toString();
        String collegeDescription=mCollegeDescription.getText().toString();

        mWebAddCollegeModel.addCollege(AddCollegeActivity.this, collegeName, collegeDescription, "2", new RequestInterface() {
            @Override
            public void onResponse(String response) {
                if(response.equals("done"))
                {
                    Toast.makeText(AddCollegeActivity.this, "college Added!.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error) {
                     Toast.makeText(AddCollegeActivity.this, "Error!.\n"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
