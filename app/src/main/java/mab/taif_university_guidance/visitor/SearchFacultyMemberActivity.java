package mab.taif_university_guidance.visitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllMembers;
import mab.taif_university_guidance.admin_view.members.GetAllMembersActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;
import mab.taif_university_guidance.model.admin.member.WebGetAllMembersModel;

public class SearchFacultyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDisplayMemberInfoBtn;
     private NiceSpinner spinnerCollege;
     private NiceSpinner spinnerMembers;



     //for college
     String[] idCollegeArray,nameCollegeArray,descriptionCollegeArray ,idUserArray = null;
     StringBuffer idCollegeBuffer,nameCollegeBuffer,descriptionCollegeBuffer ,idUserBuffer = null;
     String idCollege,nameCollege=null;


     // for members

    String userNamesArray[] = null;
    String emailArray[]=null;
    String idMember[]=null;
    String id_College_Member[]=null;
    String passwordMember[]=null;

    String joinDateMember[]=null;

    String rateTotalArray[]=null;
    String goodDrArray[]=null;
    String verPatientArray[]=null;
    String explainWellArray[]=null;



    StringBuffer usernameBuffer , emailBuffer ,idMemberBuffer ,idCollegeMemberBuffer
            ,passwordMemberBuffer,JoinDateMemberBuffer,
            rateTotalBuffer,goodDrBuffer,
            verPatientBuffer
            ,explainWellBuffer=null;

    String currentMemberName ,currentMemberEmail,currentMemberToatalRate,currentMemberGoodDrRate
            ,currentMemberVeryPatientRate,currentMemberExplainWellRate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty_member);
          mDisplayMemberInfoBtn=(Button)findViewById(R.id.button_visitor_display_info);
          mDisplayMemberInfoBtn.setOnClickListener(this);
          spinnerCollege=(NiceSpinner)findViewById(R.id.nice_spinner_select_faculty_member_college_);
          spinnerMembers=(NiceSpinner)findViewById(R.id.nice_spinner_select_member);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        idCollegeBuffer=new StringBuffer();
        idUserBuffer=new StringBuffer();
        nameCollegeBuffer=new StringBuffer();
        descriptionCollegeBuffer=new StringBuffer();


        emailBuffer= new StringBuffer();
        usernameBuffer=new StringBuffer();
        idMemberBuffer=new StringBuffer();
        idCollegeMemberBuffer=new StringBuffer();
        JoinDateMemberBuffer = new StringBuffer();
        passwordMemberBuffer = new StringBuffer();


        rateTotalBuffer = new StringBuffer();
        goodDrBuffer = new StringBuffer();
        explainWellBuffer = new StringBuffer();
        verPatientBuffer = new StringBuffer();


        getAllColleges();

        spinnerCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                idCollege=idCollegeArray[i];
                nameCollege=nameCollegeArray[i];
                getAllMembers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentMemberName=userNamesArray[i];
                currentMemberEmail=emailArray[i];
                currentMemberToatalRate=rateTotalArray[i];
                currentMemberGoodDrRate=goodDrArray[i];
                currentMemberVeryPatientRate=verPatientArray[i];
                currentMemberExplainWellRate=explainWellArray[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


       Intent intent=new Intent(SearchFacultyMemberActivity.this,AboutFacultyMemberActivity.class);


        intent.putExtra("member_name" ,currentMemberName);
        intent.putExtra("member_email" ,currentMemberEmail);
        intent.putExtra("rate_total" ,currentMemberToatalRate);
        intent.putExtra("good_dr" ,currentMemberGoodDrRate);
        intent.putExtra("very_patient" ,currentMemberVeryPatientRate);
        intent.putExtra("explain_well" ,currentMemberExplainWellRate);

       startActivity(intent);



    }


    private void getAllColleges()
    {
        WebGetAllCollegesModel mWebGetAllCollegesModel = new WebGetAllCollegesModel();

        mWebGetAllCollegesModel.getAllColleges(SearchFacultyMemberActivity.this, new RequestInterface() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("colleges");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search_object = jsonArray.getJSONObject(i);
                        idCollegeBuffer.append(search_object.getString("id_college") + "#");
                        idUserBuffer.append(search_object.getString("id_user") + "#");
                        nameCollegeBuffer.append(search_object.getString("name") + "#");
                        descriptionCollegeBuffer.append(search_object.getString("description") + "#");

                    }

                    idCollegeArray = idCollegeBuffer.toString().split("#");
                    idUserArray = idUserBuffer.toString().split("#");
                    nameCollegeArray = nameCollegeBuffer.toString().split("#");
                    descriptionCollegeArray = descriptionCollegeBuffer.toString().split("#");

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchFacultyMemberActivity.this ,android.R.layout.simple_list_item_1,nameCollegeArray);

                    spinnerCollege.setAdapter(adapter);




                } catch (Exception e) {
                    e.getMessage();
                }




            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(SearchFacultyMemberActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private  void getAllMembers()
    {

        WebGetAllMembersModel mWebGetAllMembersModel = new WebGetAllMembersModel();

        mWebGetAllMembersModel.getAllMembers(SearchFacultyMemberActivity.this, idCollege ,new RequestInterface() {
            @Override
            public void onResponse(String response) {
                try {

                    if(response.equals("not found"))
                    {
                        spinnerMembers.setVisibility(View.INVISIBLE);
                        Toast.makeText(SearchFacultyMemberActivity.this, "No Result.!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    spinnerMembers.setVisibility(View.VISIBLE);

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("members");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search_object = jsonArray.getJSONObject(i);
                        usernameBuffer.append(search_object.getString("username") + "#");
                        emailBuffer.append(search_object.getString("email") + "#");
                        idMemberBuffer.append(search_object.getString("id_user") + "#");
                        idCollegeMemberBuffer.append(search_object.getString("user_college") + "#");
                        passwordMemberBuffer.append(search_object.getString("password") + "#");
                        JoinDateMemberBuffer.append(search_object.getString("join_date") + "#");

                        rateTotalBuffer.append(search_object.getString("rate_total") + "#");
                        goodDrBuffer.append(search_object.getString("good_dr") + "#");
                        verPatientBuffer.append(search_object.getString("very_patient") + "#");
                        explainWellBuffer.append(search_object.getString("explain_well") + "#");

                    }

                    userNamesArray = usernameBuffer.toString().split("#");
                    emailArray = emailBuffer.toString().split("#");
                    idMember=idMemberBuffer.toString().split("#");
                    id_College_Member=idCollegeMemberBuffer.toString().split("#");
                    passwordMember=passwordMemberBuffer.toString().split("#");
                    joinDateMember=JoinDateMemberBuffer.toString().split("#");

                    rateTotalArray=rateTotalBuffer.toString().split("#");
                    goodDrArray=goodDrBuffer.toString().split("#");
                    verPatientArray=verPatientBuffer.toString().split("#");
                    explainWellArray=explainWellBuffer.toString().split("#");

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchFacultyMemberActivity.this ,android.R.layout.simple_list_item_1,userNamesArray);

                    spinnerMembers.setAdapter(adapter);

                    emailBuffer= new StringBuffer();
                    usernameBuffer=new StringBuffer();
                    idMemberBuffer=new StringBuffer();
                    idCollegeMemberBuffer=new StringBuffer();
                    rateTotalBuffer = new StringBuffer();
                    goodDrBuffer = new StringBuffer();
                    explainWellBuffer = new StringBuffer();
                    verPatientBuffer = new StringBuffer();


                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onError(VolleyError error) {
                error.getMessage();
            }
        });




    }

}
