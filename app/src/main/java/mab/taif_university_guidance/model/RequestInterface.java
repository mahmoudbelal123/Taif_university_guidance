package mab.taif_university_guidance.model;

import com.android.volley.VolleyError;

/**
 * Created by user on 10/28/2017.
 */

public interface RequestInterface {
     void onResponse(String response);
     void onError(VolleyError error);
}
