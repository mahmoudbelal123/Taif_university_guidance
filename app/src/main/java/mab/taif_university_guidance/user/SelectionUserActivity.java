package mab.taif_university_guidance.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.visitor.HomeVisitorActivity;

public class SelectionUserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mVisitorBtn , mSignUpBtn , mLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mVisitorBtn=(Button)findViewById(R.id.button_login_visitor);
        mVisitorBtn.setOnClickListener(this);

        mSignUpBtn=(Button)findViewById(R.id.button_register);
        mSignUpBtn.setOnClickListener(this);

        mLoginBtn=(Button)findViewById(R.id.button_login);
        mLoginBtn.setOnClickListener(this);


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
           case R.id.button_login_visitor :
               loginAsVisitor();
               break;


           case R.id.button_register :
               openSignUp();
               break;

           case R.id.button_login :
               openLogin();
               break;



       }

    }

    private  void loginAsVisitor(){
        startActivity(new Intent(SelectionUserActivity.this , HomeVisitorActivity.class));
    }

    private  void openSignUp(){
        startActivity(new Intent(SelectionUserActivity.this , RegisterActivity.class));
    }


    private  void openLogin(){
        startActivity(new Intent(SelectionUserActivity.this , LoginActivity.class));
    }


}
