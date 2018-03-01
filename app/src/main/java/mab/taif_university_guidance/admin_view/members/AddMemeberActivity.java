package mab.taif_university_guidance.admin_view.members;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.Calendar;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.signup.WebSignUpModel;
import mab.taif_university_guidance.user.RegisterActivity;

public class AddMemeberActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mAddMember;
    private EditText mUserNameMemberEdit,mEmailMemberEdit,mPasswordMemberEdit, mConfirmPassMemberEdit,mJoinDateMemberEdit;
    private ProgressBar mSignUpProgress;
    String joinDateSelected = null;
    int yr, month, day;
    Calendar today;
    WebSignUpModel webSignUpModel ;

    String nameCollege , idCollege= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memeber);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mUserNameMemberEdit=(EditText) findViewById(R.id.edit_username_member);
        mEmailMemberEdit=(EditText)findViewById(R.id.edit_email_member);
        mPasswordMemberEdit=(EditText)findViewById(R.id.edit_password_member);
        mConfirmPassMemberEdit=(EditText)findViewById(R.id.edit_confirm_password_member);
        mJoinDateMemberEdit=(EditText)findViewById(R.id.edit_join_date_member);
        mSignUpProgress=(ProgressBar)findViewById(R.id.progress_sign_up);
        mAddMember=(Button)findViewById(R.id.button_add_member_add);

        mAddMember.setOnClickListener(this);

        if(getIntent() !=null)
        {
           idCollege = getIntent().getStringExtra("idCollege");
           nameCollege= getIntent().getStringExtra("nameCollege");
        }


        today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        webSignUpModel = new WebSignUpModel();

        mJoinDateMemberEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJoinDate();
            }
        });
    }


    private void getJoinDate()
    {

        new DatePickerDialog(AddMemeberActivity.this, mDateSetListener, yr, month, day).show();

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(
                DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            yr = year;
            month = monthOfYear;
            day = dayOfMonth;


            mJoinDateMemberEdit.setText(""+ (month+1)+ "/" + day + "/" + yr);
        }
    };

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
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_member_add:

                callAddUserApi();
                break;


        }
    }

    private void callAddUserApi()
    {

        String username=mUserNameMemberEdit.getText().toString();
        String email=mEmailMemberEdit.getText().toString();
        String password=mPasswordMemberEdit.getText().toString();
        String confirmPassword=mConfirmPassMemberEdit.getText().toString();
        joinDateSelected=mJoinDateMemberEdit.getText().toString();

        mSignUpProgress.setVisibility(View.VISIBLE);

        if(username.equals(null))
        {
            mUserNameMemberEdit.setError("enter username");
            return;
        }
        else if(email.equals(null)){

            mEmailMemberEdit.setError("enter email");
            return;
        }
        else if(password.equals(null))
        {
            mPasswordMemberEdit.setError("enter password");
            return;
        }
        else if(!password.equals(confirmPassword)){

            mConfirmPassMemberEdit.setError("not match with password");
            return;
        }
        else if(joinDateSelected.equals(null))
        {
            mJoinDateMemberEdit.setError("click to add Join Date");
            return;
        }
        else {


            webSignUpModel.addUser(AddMemeberActivity.this, username, email, password, joinDateSelected, "member", getIntent().getStringExtra("idCollege") /*user college*/, new RequestInterface() {
                @Override
                public void onResponse(String response) {
                    Log.d("ADD_USER", response);
                    mSignUpProgress.setVisibility(View.INVISIBLE);
                    if(response.equals("done")){
                        Toast.makeText(AddMemeberActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(AddMemeberActivity.this, "try again with valid data", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("ADD_USER", error.getMessage());
                    mSignUpProgress.setVisibility(View.INVISIBLE);

                }
            });

        }
    }

}
