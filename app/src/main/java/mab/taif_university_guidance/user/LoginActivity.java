package mab.taif_university_guidance.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.admin_view.admin_home.AdminHomeActivity;
import mab.taif_university_guidance.admin_view.study_plans.GetStudyPlanActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.login.WebLoginModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginBtn;
    private EditText mEmailEdit , mPasswordEdit;
    private WebLoginModel mWebLoginModel;
    private ProgressBar mProgressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mLoginBtn=(Button)findViewById(R.id.button_login_user);
        mLoginBtn.setOnClickListener(this);

        mEmailEdit=(EditText)findViewById(R.id.edit_login_email);
        mPasswordEdit=(EditText)findViewById(R.id.edit_login_password);
        mProgressLogin=(ProgressBar)findViewById(R.id.progress_login);

        mWebLoginModel=new WebLoginModel();



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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_login_user:

                openUserHome();
                break;


        }
    }


    private void openUserHome(){
       String email=mEmailEdit.getText().toString();
       String password=mPasswordEdit.getText().toString();
       if(email.equals(null))
       {
           mEmailEdit.setError("enter email");
            return;
       }
       else if(password.equals(null)){
           mPasswordEdit.setError("enter password");
       return;
       }
       else
       {
           mProgressLogin.setVisibility(View.VISIBLE);
           mWebLoginModel.loginUser(LoginActivity.this, email, password, new RequestInterface() {
               @Override
               public void onResponse(String response) {

                   Log.d("LOGIN_USER" , response);
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       String login_response = jsonObject.getString("login_response");
                       mProgressLogin.setVisibility(View.INVISIBLE);

                       if (login_response.equals("done")) {

                           String type=jsonObject.getString("type");

                       if(type.equals("user"))
                       {

                           Intent intent = new Intent(LoginActivity.this , UserHomeActivity.class);
                           intent.putExtra("userType","user");
                           startActivity(intent);
                       }
                       else if(type.equals("admin"))
                       {


                          Intent intent = new Intent(LoginActivity.this , AdminHomeActivity.class);

                          intent.putExtra( "userType","admin");
                          startActivity(intent);
                       }


                   }


                   else if (login_response.equals("incorrect password")) {
                       Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                   }
                   else
                       {
                       Toast.makeText(LoginActivity.this, "Not valid user.!", Toast.LENGTH_LONG).show();
                   }



                   }catch (Exception e){
                       Log.d("EXCEPTION_LOGIN_USER" , e.getMessage());
                       mProgressLogin.setVisibility(View.INVISIBLE);

                   }

                   }

               @Override
               public void onError(VolleyError error) {
                   //Log.d("LOGIN_USER" , error.getMessage());
                   error.getNetworkTimeMs();
                   mProgressLogin.setVisibility(View.INVISIBLE);


               }
           });

       }
    }





}
