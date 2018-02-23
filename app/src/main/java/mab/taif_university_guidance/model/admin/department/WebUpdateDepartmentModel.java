package mab.taif_university_guidance.model.admin.department;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import mab.taif_university_guidance.model.RequestInterface;
import mab.taif_university_guidance.model.TAGS;
import mab.taif_university_guidance.model.URL;
import mab.taif_university_guidance.model.tables.College;
import mab.taif_university_guidance.model.tables.Department;

/**
 * Created by user on 2/20/2018.
 */

public class WebUpdateDepartmentModel {

    private RequestQueue queue;
    private StringRequest requestString;

    public void updateDepartment(final Context context, final String name, final String description,final String id_College  , final String id_user,final String id_department, final String id_study_plan, final RequestInterface requestInterface)
    {
        queue = Volley.newRequestQueue(context);
        requestString = new StringRequest(com.android.volley.Request.Method.POST, URL.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestInterface.onResponse(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestInterface.onError(error);
            }
        })

        {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put(Department.ID_DEPARTMENT, id_department);
                params.put(Department.NAME, name);
                params.put(Department.DESCRIPTION, description);
                params.put(Department.ID_COLLEGE, id_College);
                params.put(Department.ID_USER, id_user);
                params.put(Department.ID_STUDY_PLAN, id_study_plan);

                params.put(TAGS.TAG,TAGS.ADMIN_UPDATE_DEPARTMENT );

                return params;
            }
        };

        queue.add(requestString);
        requestString.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
