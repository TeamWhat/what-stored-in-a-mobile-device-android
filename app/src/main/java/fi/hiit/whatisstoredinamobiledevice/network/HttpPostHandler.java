package fi.hiit.whatisstoredinamobiledevice.network;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HttpPostHandler {

    private Context mContext;


    public HttpPostHandler(Context mContext) {
        this.mContext = mContext;
    }

    // Source: http://www.itworld.com/article/2702452/development/how-to-send-a-post-request-with-google-volley-on-android.html
    public boolean postTestMessage(final String postMessage) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // todo: Change to server post url
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something to mark that POST was completed
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something to tell that there was an error with the POST
                    }
                }
        )


        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("message",postMessage);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return true;
    }



}
