package mab.taif_university_guidance.admin_view.department;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.department.WebUpdateDepartmentModel;



public class UpdateDepartmentActivity extends AppCompatActivity {

    private EditText mNewDepartmentNameEdit , mUpdateDepartmentDescriptionEdit;
    private Button mUpdateDepartmentDataBtn;
    private WebUpdateDepartmentModel mWebUpdateDepartmentModel;

    String name,description,idCollege,idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_department);


        mWebUpdateDepartmentModel=new WebUpdateDepartmentModel();
        mUpdateDepartmentDataBtn=(Button)findViewById(R.id.button_update_department_update);

        mNewDepartmentNameEdit=(EditText)findViewById(R.id.edit_update_name_department);
        mUpdateDepartmentDescriptionEdit=(EditText)findViewById(R.id.edit_update_description_department);

        if(getIntent() !=null)
        {
            mNewDepartmentNameEdit.setText(getIntent().getStringExtra("department_name"));
            mUpdateDepartmentDescriptionEdit.setText(getIntent().getStringExtra("department_description"));

        }



        mUpdateDepartmentDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDepartment();
            }
        });


    }


    private  void updateDepartment()
    {
        String nameDepartment=mNewDepartmentNameEdit.getText().toString();
        String description=mUpdateDepartmentDescriptionEdit.getText().toString();
        String idDepartment=getIntent().getStringExtra("id_department");

        mWebUpdateDepartmentModel.updateDepartment(UpdateDepartmentActivity.this, nameDepartment, description, "id_college", "id_user", idDepartment, "id_study_plan", new RequestInterface() {
            @Override
            public void onResponse(String response) {
                if(response.equals("done"))
                {
                    Toast.makeText(UpdateDepartmentActivity.this, "done", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdateDepartmentActivity.this, "Not Done ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }


}
