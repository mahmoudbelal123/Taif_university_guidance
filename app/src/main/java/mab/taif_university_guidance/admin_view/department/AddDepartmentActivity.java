package mab.taif_university_guidance.admin_view.department;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.department.WebAddDepartmentModel;

public class AddDepartmentActivity extends AppCompatActivity {

    TextView mNameOfCollegeTxt;
    EditText mNameDepartment;
    EditText mDescriptionDepartment;
    Button mAddDepartmentBtn;

    WebAddDepartmentModel mWebAddDepartmentModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mWebAddDepartmentModel=new WebAddDepartmentModel();

        mNameOfCollegeTxt=(TextView)findViewById(R.id.text_name_of_college);
        mNameDepartment =(EditText)findViewById(R.id.edit_name_department_college);
        mDescriptionDepartment=(EditText)findViewById(R.id.edit_description_department_college);
        mAddDepartmentBtn=(Button)findViewById( R.id.button_save_department_data);

        if(getIntent() !=null)
        {
            mNameOfCollegeTxt.setText(getIntent().getStringExtra("name_college"));
        }


            mAddDepartmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addDepartment();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addDepartment()
    {
        String idUser= null;
        String idCollege = null;

        if(getIntent() !=null)
        {
            idCollege=getIntent().getStringExtra("id_college");
            idUser=getIntent().getStringExtra("id_user");

        }
        String nameDepartment=mNameDepartment.getText().toString();
        String descriptionDepartment=mDescriptionDepartment.getText().toString();
        mWebAddDepartmentModel.addDepartment(AddDepartmentActivity.this, nameDepartment, descriptionDepartment, idCollege, idUser, "id_study_plan", new RequestInterface() {
            @Override
            public void onResponse(String response) {
                     if(response.equals("done"))
                     {
                         Toast.makeText(AddDepartmentActivity.this, "Department Added", Toast.LENGTH_SHORT).show();

                      }

                       else
                     {
                         Toast.makeText(AddDepartmentActivity.this, "Department Not Added", Toast.LENGTH_SHORT).show();

                     }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(AddDepartmentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}
