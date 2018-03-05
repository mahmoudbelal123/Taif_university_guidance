package mab.taif_university_guidance.admin_view.study_plans;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllDepartments;
import mab.taif_university_guidance.adapters.AdapterGetAllPlans;
import mab.taif_university_guidance.admin_view.department.GetAllDepartmentActivity;
import mab.taif_university_guidance.admin_view.members.GetAllMembersActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;
import mab.taif_university_guidance.model.admin.department.WebGetAllDepartmentsForCollegeModel;
import mab.taif_university_guidance.model.admin.study_plans.WebDeletePlansModel;
import mab.taif_university_guidance.model.admin.study_plans.WebGetAllPlansModel;

public class GetStudyPlanActivity extends AppCompatActivity implements View.OnClickListener{


    NiceSpinner mSelectCollegeSpinner , mSelectDepartmentSpinner;
    ListView mListViewDisplayPlans;
    Button mGetAllPlansBtn , mAddPlanBtn;
    /* for colleges*/
    StringBuffer idCollegeBuffer ,idUserBuffer, nameCollegeBuffer,descriptionCollegeBuffer;
    String[] idCollegeArray,idUserArray,nameCollegeArray,descriptionCollegeArray = null;
    int collegePosition=0;
    /* -- for Departments----*/
    String[] idDepartmentArray,nameDepartmentArray,descriptionDepartmentArray ,idUserDepartmentArray = null;
    StringBuffer idDepartmentBuffer,nameDepartmentBuffer,descriptionDepartmentBuffer ,idUserDepartmentBuffer = null;
     int departmentPosition=0;


    /* for plans*/
    StringBuffer idPlansBuffer ,idUserPlansBuffer,idDepartmentPlansBuffer,idCollegePlansBuffer, namePlansBuffer,descriptionPlansBuffer;
    String[] idCollegePlansArray,idDepartmentPlansArray,idPlansArray,idUserPlansArray,namePlansArray,descriptionPlansArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_study_plan);

        mSelectCollegeSpinner =(NiceSpinner)findViewById(R.id.nice_spinner_select_college_for_plans);
        mSelectDepartmentSpinner =(NiceSpinner)findViewById(R.id.nice_spinner_select_department_for_plans);
        mAddPlanBtn=(Button)findViewById(R.id.button_add_study_plan_getAll);
        mGetAllPlansBtn=(Button)findViewById(R.id.button_get_study_plan_getAll);

        mAddPlanBtn.setOnClickListener(this);
        mGetAllPlansBtn.setOnClickListener(this);






        mListViewDisplayPlans=(ListView)findViewById(R.id.listView_display_all_plans);

        idCollegeBuffer = new StringBuffer();
        idUserBuffer=new StringBuffer();
        nameCollegeBuffer= new StringBuffer();
        descriptionCollegeBuffer= new StringBuffer();


        idDepartmentBuffer=new StringBuffer();
        idUserDepartmentBuffer=new StringBuffer();
        nameDepartmentBuffer=new StringBuffer();
        descriptionDepartmentBuffer=new StringBuffer();
        //---------------//
        idPlansBuffer = new StringBuffer();
        idCollegePlansBuffer = new StringBuffer();
        idDepartmentPlansBuffer = new StringBuffer();
        namePlansBuffer = new StringBuffer();
        descriptionPlansBuffer = new StringBuffer();
        idUserPlansBuffer=new StringBuffer();
        //------------//
        mSelectDepartmentSpinner.setVisibility(View.INVISIBLE);
        mListViewDisplayPlans.setVisibility(View.INVISIBLE);

        getAllColleges();


        mSelectCollegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAllDepartments();

                collegePosition=mSelectCollegeSpinner.getSelectedIndex();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSelectDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departmentPosition=mSelectDepartmentSpinner.getSelectedIndex();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        if(getIntent().getStringExtra("userType")!=null) {
            if (getIntent().getStringExtra("userType").equals("visitor") || getIntent().getStringExtra("userType").equals("user")) {
                mAddPlanBtn.setVisibility(View.INVISIBLE);
            }
            else
            {
                onItemClickList();


            }
        }



    }


    private void onItemClickList()
    {
        mListViewDisplayPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int position=i;
                AlertDialog.Builder builder = new AlertDialog.Builder(GetStudyPlanActivity.this);
                builder.setMessage("select choice");
                builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WebDeletePlansModel mWebDeletePlansModel = new WebDeletePlansModel();
                        mWebDeletePlansModel.deletePlan(GetStudyPlanActivity.this, idPlansArray[position], new RequestInterface() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("done"))
                                {
                                    Toast.makeText(GetStudyPlanActivity.this, "Plan Deleted.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(GetStudyPlanActivity.this, "Plan Not Deleted.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });

                    }
                });

                builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //TODO start update activity for plan

                        Intent intent = new Intent(GetStudyPlanActivity.this ,UpdatePlanActivity.class);

                        intent.putExtra("college_name",nameCollegeArray[collegePosition]);
                        intent.putExtra("department_name",nameDepartmentArray[departmentPosition]);
                        intent.putExtra("idCollege",idCollegeArray[collegePosition]);
                        intent.putExtra("idDepartment",idDepartmentArray[departmentPosition]);
                        intent.putExtra("idUser",idUserArray[collegePosition]);

                        intent.putExtra("plan_name",namePlansArray[position]);
                        intent.putExtra("desc_plan",descriptionPlansArray[position]);
                        intent.putExtra("id_plan",idPlansArray[position]);


                        startActivity(intent);

                    }
                });

builder.show();
            }
        });

    }

    private void getAllColleges()
    {
        WebGetAllCollegesModel mWebGetAllCollegesModel= new WebGetAllCollegesModel();

        mWebGetAllCollegesModel.getAllColleges(GetStudyPlanActivity.this, new RequestInterface() {
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GetStudyPlanActivity.this ,android.R.layout.simple_list_item_1,nameCollegeArray);

                    mSelectCollegeSpinner.setAdapter(adapter);





                } catch (Exception e) {
                    e.getMessage();
                }




            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(GetStudyPlanActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAllDepartments()
    {

        WebGetAllDepartmentsForCollegeModel mWebGetAllDepartmentsForCollegeModel = new WebGetAllDepartmentsForCollegeModel();

        String idCollege=idCollegeArray[mSelectCollegeSpinner.getSelectedIndex()];
        mWebGetAllDepartmentsForCollegeModel.getAllDepartmentsForCollege(GetStudyPlanActivity.this, idCollege , new RequestInterface() {
            @Override
            public void onResponse(String response) {
                try {

                    if(response.equals("notDone"))
                    {
                        mSelectDepartmentSpinner.setVisibility(View.INVISIBLE);
                        mListViewDisplayPlans.setVisibility(View.INVISIBLE);

                        return;
                    }

                    mSelectDepartmentSpinner.setVisibility(View.VISIBLE);

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("departments");

                    mListViewDisplayPlans.setVisibility(View.VISIBLE);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search_object = jsonArray.getJSONObject(i);
                        idDepartmentBuffer.append(search_object.getString("id_department") + "#");
                        idUserDepartmentBuffer.append(search_object.getString("id_user") + "#");
                        nameDepartmentBuffer.append(search_object.getString("name") + "#");
                        descriptionDepartmentBuffer.append(search_object.getString("description") + "#");

                    }

                    idDepartmentArray = idDepartmentBuffer.toString().split("#");
                    idUserDepartmentArray = idUserDepartmentBuffer.toString().split("#");
                    nameDepartmentArray = nameDepartmentBuffer.toString().split("#");
                    descriptionDepartmentArray = descriptionDepartmentBuffer.toString().split("#");


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GetStudyPlanActivity.this ,android.R.layout.simple_list_item_1,nameDepartmentArray);

                    mSelectDepartmentSpinner.setAdapter(adapter);

                    idDepartmentBuffer= new StringBuffer();
                    idUserDepartmentBuffer=new StringBuffer();
                    nameDepartmentBuffer = new StringBuffer();
                    descriptionDepartmentBuffer=new StringBuffer();

//                        idDepartmentArray=null;
//                        idUserDepartmentArray=null;
//                        nameDepartmentArray=null;
//                        descriptionDepartmentArray=null;


                }
                catch (Exception e) {
                    e.getMessage();
                }



            }

            @Override
            public void onError(VolleyError error) {

                Toast.makeText(GetStudyPlanActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_study_plan_getAll:

                //Todo Start Activity
                openStudyPlans();


                break;


            case R.id.button_get_study_plan_getAll:
                  getStudyPlans();
                break;


        }
    }

      private void openStudyPlans()
      {
          Intent intent = new Intent(GetStudyPlanActivity.this ,AddStudyPlansActivity.class);
          intent.putExtra("college_name",nameCollegeArray[collegePosition]);
          intent.putExtra("department_name",nameDepartmentArray[departmentPosition]);
          intent.putExtra("idCollege",idCollegeArray[collegePosition]);
          intent.putExtra("idDepartment",idDepartmentArray[departmentPosition]);
          intent.putExtra("idUser",idUserArray[collegePosition]);
          startActivity(intent);
      }

    private void getStudyPlans()
    {

        WebGetAllPlansModel mWebGetAllPlansModel = new WebGetAllPlansModel();
        mWebGetAllPlansModel.getAllStudyPlans(GetStudyPlanActivity.this, idCollegeArray[collegePosition], idDepartmentArray[departmentPosition], new RequestInterface() {
            @Override
            public void onResponse(String response) {

           try {
               if(response.equals("not found"))
               {
                   mListViewDisplayPlans.setVisibility(View.INVISIBLE);

               }
               JSONObject jsonResponse = new JSONObject(response);
               JSONArray jsonArray = jsonResponse.getJSONArray("plans");

               mListViewDisplayPlans.setVisibility(View.VISIBLE);

               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject search_object = jsonArray.getJSONObject(i);
                   idPlansBuffer.append(search_object.getString("id_study_plan") + "#");
                   idDepartmentPlansBuffer.append(search_object.getString("id_department") + "#");
                   idCollegePlansBuffer.append(search_object.getString("id_college") + "#");
                   idUserPlansBuffer.append(search_object.getString("id_user") + "#");
                   namePlansBuffer.append(search_object.getString("plan_name") + "#");
                   descriptionPlansBuffer.append(search_object.getString("plan_description") + "#");

               }

               idPlansArray = idPlansBuffer.toString().split("#");
               idCollegePlansArray = idCollegePlansBuffer.toString().split("#");
               idDepartmentPlansArray = idDepartmentPlansBuffer.toString().split("#");
               idUserPlansArray = idUserPlansBuffer.toString().split("#");
               namePlansArray = namePlansBuffer.toString().split("#");
               descriptionPlansArray = descriptionPlansBuffer.toString().split("#");


               AdapterGetAllPlans mAdapterGetAllPlans=new AdapterGetAllPlans(GetStudyPlanActivity.this,
                       idCollegePlansArray,idUserPlansArray,namePlansArray,descriptionPlansArray,idPlansArray,idDepartmentPlansArray);

               mListViewDisplayPlans.setAdapter(mAdapterGetAllPlans);

               idPlansBuffer = new StringBuffer();
               idCollegePlansBuffer = new StringBuffer();
               idDepartmentPlansBuffer = new StringBuffer();
               namePlansBuffer = new StringBuffer();
               descriptionPlansBuffer = new StringBuffer();
               idUserPlansBuffer=new StringBuffer();

           }catch (Exception e)
           {

           }

            }

            @Override
            public void onError(VolleyError error) {

            }
        });



    }
}
