package mab.taif_university_guidance.admin_view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import mab.taif_university_guidance.R;
import mab.taif_university_guidance.adapters.AdapterGetAllUsers;
import mab.taif_university_guidance.admin_view.department.GetAllDepartmentActivity;
import mab.taif_university_guidance.admin_view.members.AddMemeberActivity;
import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.admin.user.WebDeleteUserModel;
import mab.taif_university_guidance.model.admin.user.WebGetAllUsersModel;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdapterGetAllUsers getAllUsersAdapter;

    private ListView mListViewGetAllUsers;
    String userNamesArray[] = null;
    String emailArray[]=null;


    StringBuffer usernameBuffer , emailBuffer=null;

    private WebGetAllUsersModel mWebGetAllUsersModel;
    private WebDeleteUserModel mWebDeleteUserModel;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         mListViewGetAllUsers=(ListView)findViewById(R.id.list_get_all_users);
         mWebGetAllUsersModel=new WebGetAllUsersModel();
         mWebDeleteUserModel = new WebDeleteUserModel();

         builder=new AlertDialog.Builder(AdminHomeActivity.this);

         usernameBuffer=new StringBuffer();
         emailBuffer=new StringBuffer();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getAllUsers();

        clickOnListView();
    }


    private  void clickOnListView()
    {
        mListViewGetAllUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String username = userNamesArray[i];
                String email=emailArray[i];

                builder.setTitle(username);
                builder.setMessage(email);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {

                        mWebDeleteUserModel.DeleteUser(AdminHomeActivity.this, username, new RequestInterface() {
                            @Override
                            public void onResponse(String response) {
                               if(response.equals("done"))
                               {
                                   Toast.makeText(AdminHomeActivity.this, "Deleted Done.!", Toast.LENGTH_LONG).show();
                                   dialogInterface.dismiss();
                                   recreate();

                               }
                               else
                                {

                                    Toast.makeText(AdminHomeActivity.this, "Deleted Not Done.!", Toast.LENGTH_LONG).show();
                                    dialogInterface.dismiss();

                                }
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Toast.makeText(AdminHomeActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();

                            }
                        });
                    }
                });

                builder.show();

            }
        });
    }

    private  void getAllUsers()
    {

        mWebGetAllUsersModel.getAllUsers(AdminHomeActivity.this, new RequestInterface() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("users");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search_object = jsonArray.getJSONObject(i);
                        usernameBuffer.append(search_object.getString("username") + "#");
                        emailBuffer.append(search_object.getString("email") + "#");

                    }

                    userNamesArray = usernameBuffer.toString().split("#");
                    emailArray = emailBuffer.toString().split("#");

                    getAllUsersAdapter=new AdapterGetAllUsers(AdminHomeActivity.this , userNamesArray,emailArray);

                    mListViewGetAllUsers.setAdapter(getAllUsersAdapter);
                    //btn.setOnClickListener(Restaurant_ProfileFragment.this);



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

//    private void deleteUser(String username){
//
//        mWebDeleteUserModel.DeleteUser(AdminHomeActivity.this, username, new RequestInterface() {
//            @Override
//            public void onResponse(String response) {
//                if(response.equals("done"))
//                {
//
//                }
//                else
//                {
//
//                }
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//
//            }
//        });
//
//    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage_users)
        {
         //TODO Call GetAllUsers


        }
        else if (id == R.id.nav_manage_colleges) {
            startActivity(new Intent(AdminHomeActivity.this , ManageCollegesActivity.class));
        }

        else if (id == R.id.nav_manage_departments)
        {
            startActivity(new Intent(AdminHomeActivity.this , GetAllDepartmentActivity.class));

        }
        else if (id == R.id.nav_manage_members) {

            startActivity(new Intent(AdminHomeActivity.this , AddMemeberActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
