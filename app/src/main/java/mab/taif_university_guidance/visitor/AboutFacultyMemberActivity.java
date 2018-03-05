package mab.taif_university_guidance.visitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import mab.taif_university_guidance.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class AboutFacultyMemberActivity extends AppCompatActivity {

    TextView memberNameTxt,memberEmailTxt;
    MaterialRatingBar rateTotal,rateGdDr ,rateVrPatient,rateExWell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_faculty_member);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
         memberNameTxt =(TextView)findViewById( R.id.text_display_name_member);
         memberEmailTxt =(TextView)findViewById( R.id.text_display_email_member);
        rateTotal=(MaterialRatingBar)findViewById(R.id.rate_total);
        rateGdDr=(MaterialRatingBar)findViewById(R.id.rate_gd_dr);
        rateVrPatient=(MaterialRatingBar)findViewById(R.id.rate_vr_patient);
        rateExWell=(MaterialRatingBar)findViewById(R.id.rate_explain_well);


        if(getIntent() != null)
        {

            memberNameTxt.setText( getIntent().getStringExtra("member_name"));
            memberEmailTxt.setText( getIntent().getStringExtra("member_email"));

            int rateTotalNum=Integer.parseInt(getIntent().getStringExtra("rate_total"));
            int goodDrNum=Integer.parseInt(getIntent().getStringExtra("good_dr"));
            int vrPatientNum=Integer.parseInt(getIntent().getStringExtra("very_patient"));
            int explainWellNum=Integer.parseInt(getIntent().getStringExtra("explain_well"));


            rateTotal.setRating( rateTotalNum);
            rateGdDr.setRating( goodDrNum);
            rateVrPatient.setRating( vrPatientNum);
            rateExWell.setRating( explainWellNum);



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
