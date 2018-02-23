package mab.taif_university_guidance.admin_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mab.taif_university_guidance.R;

public class ManageCollegesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAddCollegeBtn , mUpdateCollegeBtn ,mDeleteCollegeBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_colleges);

        mAddCollegeBtn=(Button)findViewById(R.id.button_add_college);
        mAddCollegeBtn.setOnClickListener(this);

        mUpdateCollegeBtn=(Button)findViewById(R.id.button_update_college);
        mUpdateCollegeBtn.setOnClickListener(this);


        mDeleteCollegeBtn=(Button)findViewById(R.id.button_delete_college);
        mDeleteCollegeBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_college:
                openAddCollegeActivity();
                break;

            case R.id.button_update_college:
                openUpdateCollegeActivity();
                break;

            case  R.id.button_delete_college:
                  openDeleteCollegeActivity();
                  break;
        }
    }

   private void openAddCollegeActivity()
    {
        startActivity(new Intent(ManageCollegesActivity.this , AddCollegeActivity.class));

    }

    private void openUpdateCollegeActivity()
    {
        startActivity(new Intent(ManageCollegesActivity.this , UpdateCollegeActivity.class));

    }

    private void openDeleteCollegeActivity()
    {
        startActivity(new Intent(ManageCollegesActivity.this , DeleteCollegeActivity.class));

    }
}
