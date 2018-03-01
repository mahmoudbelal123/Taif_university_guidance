package mab.taif_university_guidance.admin_view.admin_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllColleges;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;

public class UpdateCollegeActivity extends AppCompatActivity {

    private ListView mListViewGetAllColleges;
    private WebGetAllCollegesModel mWebGetAllCollegesModel;
    private AdapterGetAllColleges mGetAllCollegesAdapter;

    String[] idCollegeArray,nameCollegeArray,descriptionCollegeArray ,idUserArray = null;
    StringBuffer idCollegeBuffer,nameCollegeBuffer,descriptionCollegeBuffer ,idUserBuffer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_college);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mListViewGetAllColleges=(ListView)findViewById(R.id.listView_get_All_colleges);
        mWebGetAllCollegesModel=new WebGetAllCollegesModel();

        idCollegeBuffer=new StringBuffer();
        idUserBuffer=new StringBuffer();
        nameCollegeBuffer=new StringBuffer();
        descriptionCollegeBuffer=new StringBuffer();

        getAllColleges();
        onItemCLickList();
    }

    private  void onItemCLickList()
    {
        mListViewGetAllColleges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UpdateCollegeActivity.this , UpdateActivityByAdminActivity.class);
                intent.putExtra("old_college_name" ,nameCollegeArray[i]);
                intent.putExtra("old_college_description" ,descriptionCollegeArray[i]);
                intent.putExtra("old_id_college" ,idCollegeArray[i]);
                intent.putExtra("old_id_user" ,idUserArray[i]);

                startActivity(intent);
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
    private  void getAllColleges()
    {

        mWebGetAllCollegesModel.getAllColleges(UpdateCollegeActivity.this, new RequestInterface() {
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

                   mGetAllCollegesAdapter =new AdapterGetAllColleges(UpdateCollegeActivity.this , idCollegeArray
                           ,idUserArray , nameCollegeArray,descriptionCollegeArray);

                    mListViewGetAllColleges.setAdapter(mGetAllCollegesAdapter);



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
