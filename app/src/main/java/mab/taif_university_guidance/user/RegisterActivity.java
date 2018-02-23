package mab.taif_university_guidance.user;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
   private Button mSignUpBtn;
   private EditText mUserNameEdit,mEmailEdit,mPasswordEdit, mConfirmPassEdit,mJoinDateEdit;
   private ProgressBar mSignUpProgress;
   String joinDateSelected = null;
    int yr, month, day;
    Calendar today;

    private WebSignUpModel mWebSignUpModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSignUpBtn=(Button)findViewById(R.id.button_sign_up);
        mUserNameEdit=(EditText) findViewById(R.id.edit_username);
        mEmailEdit=(EditText)findViewById(R.id.edit_email);
        mPasswordEdit=(EditText)findViewById(R.id.edit_password);
        mConfirmPassEdit=(EditText)findViewById(R.id.edit_confirm_password);
        mJoinDateEdit=(EditText)findViewById(R.id.edit_join_date);
        mSignUpProgress=(ProgressBar)findViewById(R.id.progress_sign_up);

        mSignUpBtn.setOnClickListener(this);

        today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        mWebSignUpModel=new WebSignUpModel();

        mJoinDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJoinDate();
            }
        });




    }

    private void getJoinDate()
    {

        new DatePickerDialog(RegisterActivity.this, mDateSetListener, yr, month, day).show();

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(
                DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            yr = year;
            month = monthOfYear;
            day = dayOfMonth;


            mJoinDateEdit.setText(""+ (month+1)+ "/" + day + "/" + yr);
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_sign_up:

                callAddUserApi();
                break;


        }
    }

    private void callAddUserApi()
    {

     String username=mUserNameEdit.getText().toString();
     String email=mEmailEdit.getText().toString();
     String password=mPasswordEdit.getText().toString();
     String confirmPassword=mConfirmPassEdit.getText().toString();
     joinDateSelected=mJoinDateEdit.getText().toString();

     mSignUpProgress.setVisibility(View.VISIBLE);

      if(username.equals(null))
      {
       mUserNameEdit.setError("enter username");
          return;
      }
      else if(email.equals(null)){

          mEmailEdit.setError("enter email");
          return;
      }
      else if(password.equals(null))
      {
          mPasswordEdit.setError("enter password");
          return;
      }
      else if(!password.equals(confirmPassword)){

          mConfirmPassEdit.setError("not match with password");
          return;
      }
      else if(joinDateSelected.equals(null))
      {
          mJoinDateEdit.setError("click to add Join Date");
          return;
      }
      else {


          mWebSignUpModel.addUser(RegisterActivity.this, username, email, password, joinDateSelected, "user", "1" /*user college*/, new RequestInterface() {
              @Override
              public void onResponse(String response) {
                  Log.d("ADD_USER", response);
                  mSignUpProgress.setVisibility(View.INVISIBLE);
                  if(response.equals("done")){
                      Toast.makeText(RegisterActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {

                      Toast.makeText(RegisterActivity.this, "try again with valid data", Toast.LENGTH_SHORT).show();
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
