package mab.taif_university_guidance.admin_view.study_plans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.study_plans.WebAddPlansModel;

public class AddStudyPlansActivity extends AppCompatActivity {


    TextView collegeNameTxt , departmentNameTxt;
    EditText namePlanEdit,descriptionPlanEdit;
    Button addPlanBtn;
    String idCollege , idDepartment,idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study_plans);
        collegeNameTxt=(TextView)findViewById(R.id.text_name_of_college_plan);
        departmentNameTxt=(TextView)findViewById(R.id.text_name_of_department_plan);
        namePlanEdit=(EditText)findViewById(R.id.edit_name_study_plan);
        descriptionPlanEdit=(EditText)findViewById(R.id.edit_description_plan);
        addPlanBtn=(Button)findViewById(R.id.button_add_plan_add);

        if(getIntent() !=null)
        {
            collegeNameTxt.setText(getIntent().getStringExtra("college_name"));
            departmentNameTxt.setText(getIntent().getStringExtra("department_name"));
            idCollege= getIntent().getStringExtra("idCollege");
            idDepartment= getIntent().getStringExtra("idDepartment");
            idUser=getIntent().getStringExtra("idUser");


        }



        addPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPlan();
            }
        });


    }



    private void addNewPlan()
    {
        String namePlan=namePlanEdit.getText().toString();
        String descriptionPlan=descriptionPlanEdit.getText().toString();

        WebAddPlansModel mWebAddPlansModel = new WebAddPlansModel();
        mWebAddPlansModel.addStudyPlans(AddStudyPlansActivity.this, idCollege, idDepartment, idUser, namePlan, descriptionPlan, new RequestInterface() {
            @Override
            public void onResponse(String response) {
                if(response.equals("done"))
                {
                    Toast.makeText(AddStudyPlansActivity.this, "Plan Added .", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AddStudyPlansActivity.this, "Plan Not Added .", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(AddStudyPlansActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
