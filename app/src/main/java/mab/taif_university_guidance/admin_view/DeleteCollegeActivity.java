package mab.taif_university_guidance.admin_view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllColleges;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.college.WebDeleteCollegeModel;
import mab.taif_university_guidance.model.admin.college.WebGetAllCollegesModel;

public class DeleteCollegeActivity extends AppCompatActivity {

    private ListView mListViewGetAllColleges;
    private WebGetAllCollegesModel mWebGetAllCollegesModel;
    private WebDeleteCollegeModel mWebDeleteCollegeModel ;
    private AdapterGetAllColleges mGetAllCollegesAdapter;


    String[] idCollegeArray,nameCollegeArray,descriptionCollegeArray ,idUserArray = null;
    StringBuffer idCollegeBuffer,nameCollegeBuffer,descriptionCollegeBuffer ,idUserBuffer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_college);

        mListViewGetAllColleges=(ListView)findViewById(R.id.listView_get_All_colleges_delete);
        mWebGetAllCollegesModel=new WebGetAllCollegesModel();
        mWebDeleteCollegeModel=new WebDeleteCollegeModel();

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


                final String currentId=idCollegeArray[i];
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCollegeActivity.this);
                builder.setTitle(nameCollegeArray[i]);
                builder.setMessage("Are you sure to delete this college ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       mWebDeleteCollegeModel.deleteCollege(DeleteCollegeActivity.this, currentId, new RequestInterface() {
                           @Override
                           public void onResponse(String response) {
                               if(response.equals("done"))
                               {
                                   Toast.makeText(DeleteCollegeActivity.this, "Deleted.!", Toast.LENGTH_SHORT).show();
                               recreate();
                               }
                               else
                               {
                                   Toast.makeText(DeleteCollegeActivity.this, "Not Deleted.!", Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onError(VolleyError error) {
                               Toast.makeText(DeleteCollegeActivity.this, "Not Deleted.!\n"+error.getMessage(), Toast.LENGTH_SHORT).show();

                           }
                       });
                    }
                });

                builder.show();
            }
        });

    }

    private  void getAllColleges()
    {

        mWebGetAllCollegesModel.getAllColleges(DeleteCollegeActivity.this, new RequestInterface() {
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

                    mGetAllCollegesAdapter =new AdapterGetAllColleges(DeleteCollegeActivity.this , idCollegeArray
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
