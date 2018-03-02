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
import mab.taif_university_guidance.model.admin.study_plans.WebUpdatePlansModel;

public class UpdatePlanActivity extends AppCompatActivity {

    TextView collegeNameTxt , departmentNameTxt;
    EditText namePlanEdit,descriptionPlanEdit;
    Button updatePlanBtn;
    String idCollege , idDepartment,idUser,idPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plan);

        collegeNameTxt=(TextView)findViewById(R.id.text_name_of_college_plan_update);
        departmentNameTxt=(TextView)findViewById(R.id.text_name_of_department_plan_update);
        namePlanEdit=(EditText)findViewById(R.id.edit_name_study_plan_update);
        descriptionPlanEdit=(EditText)findViewById(R.id.edit_description_plan_update);
        updatePlanBtn=(Button)findViewById(R.id.button_add_plan_update);

        if(getIntent() !=null)
        {
            collegeNameTxt.setText(getIntent().getStringExtra("college_name"));
            departmentNameTxt.setText(getIntent().getStringExtra("department_name"));
            idCollege= getIntent().getStringExtra("idCollege");
            idDepartment= getIntent().getStringExtra("idDepartment");
            idUser=getIntent().getStringExtra("idUser");

            namePlanEdit.setText(getIntent().getStringExtra("plan_name"));
            descriptionPlanEdit.setText(getIntent().getStringExtra("desc_plan"));
            idPlan=getIntent().getStringExtra("id_plan");

        }


        updatePlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudyPlan();
            }
        });

    }


    private void updateStudyPlan()
    {
        String newName=namePlanEdit.getText().toString();
        String newDescription=descriptionPlanEdit.getText().toString();

        WebUpdatePlansModel mWebUpdatePlansModel = new WebUpdatePlansModel();

        mWebUpdatePlansModel.updatePlan(UpdatePlanActivity.this, idPlan, newName, newDescription, new RequestInterface() {
            @Override
            public void onResponse(String response) {
                if(response.equals("done"))
                {
                    Toast.makeText(UpdatePlanActivity.this, "Update Plan Done.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdatePlanActivity.this, "Update Plan Not Done.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

}
