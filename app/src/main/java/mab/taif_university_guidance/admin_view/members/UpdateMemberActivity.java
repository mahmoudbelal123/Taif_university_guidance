package mab.taif_university_guidance.admin_view.members;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.member.WebUpdateMembersModel;

public class UpdateMemberActivity extends AppCompatActivity {

    private Button mUpdateMember;
    private EditText mUserNameMemberEdit,mEmailMemberEdit,mPasswordMemberEdit, mConfirmPassMemberEdit,mJoinDateMemberEdit;
    private ProgressBar mSignUpProgress;

    String idMember , idCollege=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mUpdateMember=(Button)findViewById(R.id.button_update_member);
        mUserNameMemberEdit=(EditText) findViewById(R.id.edit_username_member_update);
        mEmailMemberEdit=(EditText)findViewById(R.id.edit_email_member_update);
        mPasswordMemberEdit=(EditText)findViewById(R.id.edit_password_member_update);
        mConfirmPassMemberEdit=(EditText)findViewById(R.id.edit_confirm_password_member_update);
        mJoinDateMemberEdit=(EditText)findViewById(R.id.edit_join_date_member_update);

        if(getIntent() != null)
        {
            idMember =getIntent().getStringExtra("id_member");
            idCollege =getIntent().getStringExtra("id_college");

            mUserNameMemberEdit.setText(getIntent().getStringExtra("username"));
            mEmailMemberEdit.setText(getIntent().getStringExtra("email"));
            mPasswordMemberEdit.setText(getIntent().getStringExtra("password"));
            mJoinDateMemberEdit.setText(getIntent().getStringExtra("join_date"));

        }



        mUpdateMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               updateMember();

            }
        });
    }

    private void updateMember()
    {
        String username = mUserNameMemberEdit.getText().toString();
        String email=mEmailMemberEdit.getText().toString();
        String password=mPasswordMemberEdit.getText().toString();
        String confirmPassword=mConfirmPassMemberEdit.getText().toString();
        String join_date=mJoinDateMemberEdit.getText().toString();




        WebUpdateMembersModel mWebUpdateMembersModel = new WebUpdateMembersModel();

        //TODO call the update member
        //mWebUpdateMembersModel.updateMember(UpdateMemberActivity.this ,idMember,);

       if(password.equals(confirmPassword))
        mWebUpdateMembersModel.updateMember(UpdateMemberActivity.this, idMember, username, email, password, join_date, idCollege, new RequestInterface() {
            @Override
            public void onResponse(String response) {
                if(response.equals("done"))
                    Toast.makeText(UpdateMemberActivity.this, "Updated done.!", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(UpdateMemberActivity.this, "Try Again.!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(UpdateMemberActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       else
       {
           mConfirmPassMemberEdit.setError("password not match.!");
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


}
