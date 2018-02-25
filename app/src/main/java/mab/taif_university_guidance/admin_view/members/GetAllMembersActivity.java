package mab.taif_university_guidance.admin_view.members;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllDepartments;
import mab.taif_university_guidance.adapters.AdapterGetAllMembers;
import mab.taif_university_guidance.adapters.AdapterGetAllUsers;
import mab.taif_university_guidance.admin_view.AdminHomeActivity;
import mab.taif_university_guidance.admin_view.department.GetAllDepartmentActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;
import mab.taif_university_guidance.model.admin.department.WebGetAllDepartmentsForCollegeModel;
import mab.taif_university_guidance.model.admin.member.WebDeleteMembersModel;
import mab.taif_university_guidance.model.admin.member.WebGetAllMembersModel;

public class GetAllMembersActivity extends AppCompatActivity {

    NiceSpinner mSelectCollegeSpinner;
    Button mAddMember;
    List<String> colleges=new ArrayList<>();
    WebGetAllCollegesModel mWebGetAllCollegesModel;

    WebGetAllMembersModel mWebGetAllMembersModel;


    private ListView mListViewGetAllMembers;
    String userNamesArray[] = null;
    String emailArray[]=null;
    String idMember[]=null;
    String id_College_Member[]=null;
    String passwordMember[]=null;

    String joinDateMember[]=null;



    StringBuffer usernameBuffer , emailBuffer ,idMemberBuffer ,idCollegeMemberBuffer ,passwordMemberBuffer,JoinDateMemberBuffer =null;

    AdapterGetAllMembers mAdapterGetAllMembers;
    String idCollege,nameCollege=null;


    String[] idCollegeArray,nameCollegeArray,descriptionCollegeArray ,idUserArray = null;
    StringBuffer idCollegeBuffer,nameCollegeBuffer,descriptionCollegeBuffer ,idUserBuffer = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_members);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSelectCollegeSpinner=(NiceSpinner)findViewById(R.id.nice_spinner_select_college_for_member);
        mListViewGetAllMembers = (ListView)findViewById(R.id.listView_display_all_members);


        mAddMember= (Button)findViewById(R.id.button_add_member);

        mWebGetAllCollegesModel=new WebGetAllCollegesModel();
        mWebGetAllMembersModel= new WebGetAllMembersModel();

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


        getAllColleges();



        mSelectCollegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        mAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GetAllMembersActivity.this , AddMemeberActivity.class);
                intent.putExtra("idCollege",idCollege);
                intent.putExtra("nameCollege",nameCollege);
                if(idCollege==null || nameCollege==null)
                {
                    Toast.makeText(GetAllMembersActivity.this, "Select College", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(intent);
                }


            }
        });


             onListClickItem();
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
    private  void onListClickItem()
    {
        mListViewGetAllMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              final int position=i;

                AlertDialog.Builder builder = new AlertDialog.Builder(GetAllMembersActivity.this);
                builder.setMessage("Select Choice");
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WebDeleteMembersModel mWebDeleteMembersModel= new WebDeleteMembersModel();
                        mWebDeleteMembersModel.deleteMember(GetAllMembersActivity.this, idMember[position], new RequestInterface() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("done"))
                                {
                                    Toast.makeText(GetAllMembersActivity.this, "Member Deleted", Toast.LENGTH_SHORT).show();
                                    recreate();
                                }
                                else
                                {
                                    Toast.makeText(GetAllMembersActivity.this, "No Member Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });


                    }
                });
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                      Intent intent = new Intent(GetAllMembersActivity.this , UpdateMemberActivity.class);
                        intent.putExtra("id_member",idMember[position]);
                        intent.putExtra("id_college",id_College_Member[position]);
                        intent.putExtra("username",userNamesArray[position]);
                        intent.putExtra("email",emailArray[position]);
                        intent.putExtra("password",passwordMember[position]);
                        intent.putExtra("join_date",joinDateMember[position]);

                        startActivity(intent);
                    }
                });
                builder.show();


            }
        });

    }

    private void getAllColleges()
    {
        mWebGetAllCollegesModel.getAllColleges(GetAllMembersActivity.this, new RequestInterface() {
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GetAllMembersActivity.this ,android.R.layout.simple_list_item_1,nameCollegeArray);

                    mSelectCollegeSpinner.setAdapter(adapter);




                } catch (Exception e) {
                    e.getMessage();
                }




            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(GetAllMembersActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //---------------------------------------------------------------------------------------------//

    private  void getAllMembers()
    {

        mWebGetAllMembersModel.getAllMembers(GetAllMembersActivity.this, idCollege ,new RequestInterface() {
            @Override
            public void onResponse(String response) {
                try {

                    if(response.equals("not found"))
                    {
                        mListViewGetAllMembers.setVisibility(View.INVISIBLE);
                        Toast.makeText(GetAllMembersActivity.this, "No Result.!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mListViewGetAllMembers.setVisibility(View.VISIBLE);

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

                    }

                     userNamesArray = usernameBuffer.toString().split("#");
                     emailArray = emailBuffer.toString().split("#");
                     idMember=idMemberBuffer.toString().split("#");
                     id_College_Member=idCollegeMemberBuffer.toString().split("#");
                    passwordMember=passwordMemberBuffer.toString().split("#");
                    joinDateMember=JoinDateMemberBuffer.toString().split("#");

                    mAdapterGetAllMembers=new AdapterGetAllMembers(GetAllMembersActivity.this , userNamesArray,emailArray);

                    mListViewGetAllMembers.setAdapter(mAdapterGetAllMembers);

                    emailBuffer= new StringBuffer();
                    usernameBuffer=new StringBuffer();
                    idMemberBuffer=new StringBuffer();
                    idCollegeMemberBuffer=new StringBuffer();



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
