package mab.taif_university_guidance.visitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mab.taif_university_guidance.R;

public class SearchFacultyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDisplayMemberInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty_member);
          mDisplayMemberInfoBtn=(Button)findViewById(R.id.button_visitor_display_info);
        mDisplayMemberInfoBtn.setOnClickListener(this);

    }





    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.button_visitor_display_info:
            displayMemberInfo();
            break;

    }

    }

    private void displayMemberInfo(){
        startActivity(new Intent(SearchFacultyMemberActivity.this , AboutFacultyMemberActivity.class));
    }


}
