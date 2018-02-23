package mab.taif_university_guidance.admin_view.members;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import mab.taif_university_guidance.admin_view.department.AddDepartmentActivity;
import mab.taif_university_guidance.admin_view.department.UpdateDepartmentActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;
import mab.taif_university_guidance.model.admin.department.WebDeleteDepartmentModel;
import mab.taif_university_guidance.model.admin.department.WebGetAllDepartmentsForCollegeModel;

public class GetAllMembersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_department);



    }




}
