package mab.taif_university_guidance.model.admin.place;

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
import mab.taif_university_guidance.model.tables.Department;
import mab.taif_university_guidance.model.tables.Place;

/**
 * Created by user on 2/18/2018.
 */

public class WebAddPlaceModel {

    private RequestQueue queue;
    private StringRequest requestString;

    public void addPlace(final Context context, final String name_place, final String place_details ,final String place_image,final String place_level,final String location,final String level_number, final RequestInterface requestInterface)
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

                params.put(Place.NAME_PLACE, name_place);
                params.put(Place.DETAILS, place_details);
                params.put(Place.LEVEL_NUMBER, level_number);
                params.put(Place.IMAGE_PLACE, place_image);
                params.put(Place.IMAGE_LEVEL, place_level);
                params.put(Place.LOCATION, location);
                params.put(TAGS.TAG,TAGS.ADD_PLACE );

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
