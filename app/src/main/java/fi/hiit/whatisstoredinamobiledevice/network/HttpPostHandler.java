package fi.hiit.whatisstoredinamobiledevice.network;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpPostHandler {

    private final String mStringDemoUrl;
    private final String mJSONDemoUrl;
    private final HttpStack mHttpStack;
    private Context mContext;


    public HttpPostHandler(Context context, HttpStack httpStack) {
        this.mStringDemoUrl = "http://pickingdigitalpockets.herokuapp.com/demos";
        this.mContext = context;
        this.mHttpStack = httpStack;
        // the ip of the server that should receive the json
        this.mJSONDemoUrl = "http://0.0.0.0:3000/submit";

    }


    // http://www.itworld.com/article/2702452/development/how-to-send-a-post-request-with-google-volley-on-android.html
    public boolean postTestMessage(final String postMessage) {
        final RequestQueue queue = Volley.newRequestQueue(mContext, mHttpStack);

        StringRequest sr = new StringRequest(Request.Method.POST, mStringDemoUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.print(response);
                queue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print(error);
                queue.stop();
            }
        })

        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("demo[message]", postMessage);
                return params;
            }
        };


        queue.add(sr);
        return true;
    }

    public boolean postJSON(final JSONObject jsonToSend) {
        System.out.println(jsonToSend.toString());
        RequestQueue queue = Volley.newRequestQueue(mContext, mHttpStack);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, mJSONDemoUrl, jsonToSend,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
         System.out.print(jsonObject.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.print(volleyError);
                    }
                });

        queue.add(jsonRequest);
        return true;
    }



}
