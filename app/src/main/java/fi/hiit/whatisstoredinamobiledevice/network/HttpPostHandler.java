package fi.hiit.whatisstoredinamobiledevice.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.Intent;
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
import fi.hiit.whatisstoredinamobiledevice.ui.activities.MainScreen;


import fi.hiit.whatisstoredinamobiledevice.background_collecting.DataCollectionAlarmReceiver;


public class HttpPostHandler {

    private static final int POST_TIMEOUT_WAIT = 60000;
    private final String mJSONUrl;
    private final HttpStack mHttpStack;
    private Context mContext;
    private MainScreen mMainScreen;
    private DatabaseAccessor mDatabaseAccessor;
    private static final String TAG = "HttpPostHandler";
    public static final String SERVER_URL = "http://pdp.cs.helsinki.fi/";


    public HttpPostHandler(Context context, HttpStack httpStack) {
        this.mContext = context;
        this.mHttpStack = httpStack;
        // the ip of the server that should receive the json
        this.mJSONUrl = SERVER_URL + "submit";
    }


    public void setMainScreen(MainScreen m) {
        mMainScreen = m;
    }


    // todo: check if you can stop the wakeful intent service as in this method
    /**
     * Post a JSON to the backend server
     * @param jsonToSend
     * @return
     */

    public boolean postJSON(final JSONObject jsonToSend, final Intent intentToStop) {
        final RequestQueue queue = Volley.newRequestQueue(mContext, mHttpStack);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, mJSONUrl, jsonToSend,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DataCollectionAlarmReceiver.completeWakefulIntent(intentToStop);
                        Log.d(TAG, "POST successful, dataCollectionIntent: " + intentToStop);
                        queue.stop();
                        incrementDataSendCounter();
                        // Change sent flags to be sent
                        mDatabaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(mContext));
                        mDatabaseAccessor.setAllSent();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        DataCollectionAlarmReceiver.completeWakefulIntent(intentToStop);
                        Log.d(TAG, "POST errored: " + volleyError);
                        queue.stop();
                    }
                });

        setRequestTimeout(jsonRequest, POST_TIMEOUT_WAIT);
        queue.add(jsonRequest);
        Log.d(TAG, "DATA POST SENT");
        return true;
    }

    private void setRequestTimeout(Request request, int timeout) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void incrementDataSendCounter() {
        SharedPreferences sharedPreferences = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        int dataSendCount = sharedPreferences.getInt("data_send_count", 0);
        dataSendCount++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("data_send_count", dataSendCount);
        editor.commit();
        if (mMainScreen != null) {
            mMainScreen.setDataSendCounter();
        }
    }
}
