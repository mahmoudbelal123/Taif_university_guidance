package mab.taif_university_guidance.admin_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebUpdateCollegeModel;

public class UpdateActivityByAdminActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mNewCollegeNameEdit , mUpdateCollegeDescriptionEdit;
    private Button mUpdateCollegeDataBtn;
    private WebUpdateCollegeModel mWebUpdateCollegeModel;

    String name,description,idCollege,idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_by_admin);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mUpdateCollegeDataBtn=(Button)findViewById(R.id.button_update_college_update);
        mUpdateCollegeDataBtn.setOnClickListener(this);

        mNewCollegeNameEdit=(EditText)findViewById(R.id.edit_update_name_college);
        mUpdateCollegeDescriptionEdit=(EditText)findViewById(R.id.edit_update_description_college);

        mWebUpdateCollegeModel = new WebUpdateCollegeModel();

        if(getIntent() != null) {
             name = getIntent().getStringExtra("old_college_name");
             description = getIntent().getStringExtra("old_college_description");
             idCollege = getIntent().getStringExtra("old_id_college");
             idUser = getIntent().getStringExtra("old_id_user");


            mNewCollegeNameEdit.setText(name);
            mUpdateCollegeDescriptionEdit.setText(description);
        }

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
    private void updateCollegeData()
    {

           String newName=mNewCollegeNameEdit.getText().toString();
           String newDescription=mUpdateCollegeDescriptionEdit.getText().toString();

           if(newName.equals(null)){
               mNewCollegeNameEdit.setError("enter name");
          return;
           }
           else if(newDescription.equals(null))
           {
            mUpdateCollegeDescriptionEdit.setError("enter description");
           }
           else {
               mWebUpdateCollegeModel.updateCollege(UpdateActivityByAdminActivity.this, newName, newDescription,idCollege,idUser , new RequestInterface() {
                   @Override
                   public void onResponse(String response) {
                       if (response.equals("done")) {
                           Toast.makeText(UpdateActivityByAdminActivity.this, "Updated Done .!", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(UpdateActivityByAdminActivity.this, "Updated Not Done.!", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onError(VolleyError error) {
                       Toast.makeText(UpdateActivityByAdminActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }




    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_update_college_update:
                updateCollegeData();
                break;
        }
    }





}
