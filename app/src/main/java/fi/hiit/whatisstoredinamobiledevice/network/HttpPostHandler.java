package fi.hiit.whatisstoredinamobiledevice.network;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DatabaseAccessor;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;


public class HttpPostHandler {

    private static final int POST_TIMEOUT_WAIT = 60000;
    private final String mJSONUrl;
    private final HttpStack mHttpStack;
    private Context mContext;
    private DatabaseAccessor databaseAccessor;
    private static final String TAG = "HttpPostHandler";
    public static final String SERVER_URL = "http://pdp.cs.helsinki.fi/";


    public HttpPostHandler(Context context, HttpStack httpStack) {
        this.mContext = context;
        this.mHttpStack = httpStack;
        // the ip of the server that should receive the json
        this.mJSONUrl = SERVER_URL + "submit";
    }

    public boolean postJSON(final JSONObject jsonToSend) {
        final RequestQueue queue = Volley.newRequestQueue(mContext, mHttpStack);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, mJSONUrl, jsonToSend,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "POST successful: " + response.toString());
                        queue.stop();
                        // Change sent flags to be 1 (sent)
                        databaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(mContext));
                        databaseAccessor.setAllSent();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "POST errored: " + volleyError);
                        queue.stop();
                    }
                });

        setRequestTimeout(jsonRequest, POST_TIMEOUT_WAIT);
        queue.add(jsonRequest);
        return true;
    }

    private void setRequestTimeout(Request request, int timeout) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
