package mab.taif_university_guidance.model.admin.member;

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
import mab.taif_university_guidance.model.tables.User;

/**
 * Created by user on 2/18/2018.
 */

public class WebDeleteMembersModel {

    private RequestQueue queue;
    private StringRequest requestString;

    public void deleteMember(final Context context, final String idUser , final RequestInterface requestInterface)
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


                params.put(User.ID_USER,idUser );
                params.put(TAGS.TAG,TAGS.DELTE_MEMBER );

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
