package mab.taif_university_guidance.admin_view.department;

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
import mab.taif_university_guidance.adapters.AdapterGetAllColleges;
import mab.taif_university_guidance.adapters.AdapterGetAllDepartments;
import mab.taif_university_guidance.admin_view.UpdateCollegeActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;
import mab.taif_university_guidance.model.admin.department.WebDeleteDepartmentModel;
import mab.taif_university_guidance.model.admin.department.WebGetAllDepartmentsForCollegeModel;

public class GetAllDepartmentActivity extends AppCompatActivity {

    NiceSpinner mSelectCollegeSpinner;
    Button mAddDepartment;
    List<String> colleges=new ArrayList<>();
    WebGetAllCollegesModel mWebGetAllCollegesModel;
    WebDeleteDepartmentModel mWebDeleteDepartmentModel;

    /* -- for Colleges----*/
    String[] idCollegeArray,nameCollegeArray,descriptionCollegeArray ,idUserArray = null;
    StringBuffer idCollegeBuffer,nameCollegeBuffer,descriptionCollegeBuffer ,idUserBuffer = null;

    /* -- for Departments----*/
    String[] idDepartmentArray,nameDepartmentArray,descriptionDepartmentArray ,idUserDepartmentArray = null;
    StringBuffer idDepartmentBuffer,nameDepartmentBuffer,descriptionDepartmentBuffer ,idUserDepartmentBuffer = null;


    WebGetAllDepartmentsForCollegeModel mWebGetAllDepartmentsForCollegeModel;

    private ListView mListViewDisplayDepartments;
    AdapterGetAllDepartments mAdapterGetAllDepartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_department);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

         mWebDeleteDepartmentModel = new WebDeleteDepartmentModel();

        mSelectCollegeSpinner=(NiceSpinner)findViewById(R.id.nice_spinner_select_college_for_department);

        mListViewDisplayDepartments=(ListView)findViewById(R.id.listView_display_all_departments);
        mAddDepartment = (Button) findViewById(R.id.button_add_department);

        mWebGetAllCollegesModel=new WebGetAllCollegesModel();
        mWebGetAllDepartmentsForCollegeModel=new WebGetAllDepartmentsForCollegeModel();

        mListViewDisplayDepartments=(ListView)findViewById(R.id.listView_display_all_departments);

        idCollegeBuffer=new StringBuffer();
        idUserBuffer=new StringBuffer();
        nameCollegeBuffer=new StringBuffer();
        descriptionCollegeBuffer=new StringBuffer();


        idDepartmentBuffer=new StringBuffer();
        idUserDepartmentBuffer=new StringBuffer();
        nameDepartmentBuffer=new StringBuffer();
        descriptionDepartmentBuffer=new StringBuffer();
        mListViewDisplayDepartments.setVisibility(View.INVISIBLE);


        listOnItemClick();

        mAddDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDepartment();



            }
        });

        getAllColleges();

        mSelectCollegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAllDepartments();
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
    private void listOnItemClick()
    {
        mListViewDisplayDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                final int idDepartment=i;
                AlertDialog.Builder builder = new AlertDialog.Builder(GetAllDepartmentActivity.this);
                builder.setMessage("Select your choice");
                builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
mWebDeleteDepartmentModel.deleteCollege(GetAllDepartmentActivity.this, idDepartmentArray[idDepartment], new RequestInterface() {
    @Override
    public void onResponse(String response) {
        if(response.equals("done"))
        {
            Toast.makeText(GetAllDepartmentActivity.this, "Deleted Done", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(GetAllDepartmentActivity.this, "Deleted Not Done", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onError(VolleyError error) {

        Toast.makeText(GetAllDepartmentActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        });

                    }
                });
                builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(GetAllDepartmentActivity.this , UpdateDepartmentActivity.class);
                        intent.putExtra("id_department" , idDepartmentArray[idDepartment]);
                        intent.putExtra("id_user" , idUserDepartmentArray[idDepartment]);
                        intent.putExtra("department_name" , nameDepartmentArray[idDepartment]);
                        intent.putExtra("department_description" , descriptionDepartmentArray[idDepartment]);
                        startActivity(intent);


                    }
                });
                builder.show();







            }

        });


            }

    private void openAddDepartment()
    {


        Intent intent = new Intent(GetAllDepartmentActivity.this , AddDepartmentActivity.class);

         intent.putExtra("name_college" , nameCollegeArray[mSelectCollegeSpinner.getSelectedIndex()]);
         intent.putExtra("id_user" , idUserArray[mSelectCollegeSpinner.getSelectedIndex()]);
         intent.putExtra("id_college" , idCollegeArray[mSelectCollegeSpinner.getSelectedIndex()]);
        intent.putExtra("id_department" , idDepartmentArray[mSelectCollegeSpinner.getSelectedIndex()]);
        startActivity(intent);

    }




    private void getAllColleges()
    {
     mWebGetAllCollegesModel.getAllColleges(GetAllDepartmentActivity.this, new RequestInterface() {
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

                     ArrayAdapter<String> adapter = new ArrayAdapter<String>(GetAllDepartmentActivity.this ,android.R.layout.simple_list_item_1,nameCollegeArray);

                     mSelectCollegeSpinner.setAdapter(adapter);




                 } catch (Exception e) {
                     e.getMessage();
                 }




         }

         @Override
         public void onError(VolleyError error) {
             Toast.makeText(GetAllDepartmentActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
    }


    private void getAllDepartments()
    {
        String idCollege=idCollegeArray[mSelectCollegeSpinner.getSelectedIndex()];
        mWebGetAllDepartmentsForCollegeModel.getAllDepartmentsForCollege(GetAllDepartmentActivity.this, idCollege , new RequestInterface() {
            @Override
            public void onResponse(String response) {
                try {

                    if(response.equals("notDone"))
                    {
                        mListViewDisplayDepartments.setVisibility(View.INVISIBLE);
                        return;
                    }

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("departments");

                        mListViewDisplayDepartments.setVisibility(View.VISIBLE);

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


                        mAdapterGetAllDepartments = new AdapterGetAllDepartments(GetAllDepartmentActivity.this, idDepartmentArray
                                , idUserDepartmentArray, nameDepartmentArray, descriptionDepartmentArray);

                        mListViewDisplayDepartments.setAdapter(mAdapterGetAllDepartments);

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

                Toast.makeText(GetAllDepartmentActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
