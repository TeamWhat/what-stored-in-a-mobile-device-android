package fi.hiit.whatisstoredinamobiledevice.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.preference.PreferenceManager;

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

    private String mJSONUrl;
    private static final int POST_TIMEOUT_WAIT = 120000;
    private final HttpStack mHttpStack;
    private Context mContext;
    private MainScreen mMainScreen;
    private DatabaseAccessor mDatabaseAccessor;

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



    /**
     * Post a JSON to the backend server
     * @param jsonToSend
     * @return
     */
    public boolean postJSON(final JSONObject jsonToSend, final Intent intentToStop) {

//        System.out.println("jsonToSend lenght: " + jsonToSend.toString().length());

        final RequestQueue queue = Volley.newRequestQueue(mContext, mHttpStack);
        JsonObjectRequest jsonRequest = getJsonObjectRequest(jsonToSend, intentToStop, queue);
        setRequestTimeout(jsonRequest, POST_TIMEOUT_WAIT);
        queue.add(jsonRequest);
        return true;
    }

    private JsonObjectRequest getJsonObjectRequest(JSONObject jsonToSend, final Intent intentToStop, final RequestQueue queue) {
        return new JsonObjectRequest(Request.Method.POST, mJSONUrl, jsonToSend,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        queue.stop();
                        incrementDataSendCounter();

                        DataCollectionAlarmReceiver.setConnectivityChangeReceiverState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED, mContext);

                        // Stop cpu wake lock
                        if (intentToStop != null) {
                            ConnectivityChangeReceiver.completeWakefulIntent(intentToStop);
                            DataCollectionAlarmReceiver.completeWakefulIntent(intentToStop);
                        }

                        // Change sent flags to be sent
                        mDatabaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(mContext));
                        mDatabaseAccessor.setAllSent();

//                        System.out.println(  "SUCCESSFUL RESPONSE, CCR DISABLED: " + DataCollectionAlarmReceiver.isConnectivityChangeReceiverEnabled(mContext) + ", INTENT STOPPED: " + intentToStop);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        queue.stop();

                        // Stop cpu wake lock
                        if (intentToStop != null) {
                            ConnectivityChangeReceiver.completeWakefulIntent(intentToStop);
                            DataCollectionAlarmReceiver.completeWakefulIntent(intentToStop);
                        }

//                        System.out.println("ERRORED RESPONSE: " + volleyError.networkResponse.statusCode
//                                + "\nINTENT STOPPED: " + intentToStop);
                    }
                });
    }

    private void setRequestTimeout(Request request, int timeout) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void incrementDataSendCounter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int dataSendCount = sharedPreferences.getInt(MainScreen.KEY_DATA_SEND_COUNT, 0);
        dataSendCount++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MainScreen.KEY_DATA_SEND_COUNT, dataSendCount);
        editor.commit();
        if (mMainScreen != null) {
            mMainScreen.setDataSendCounter();
        }
    }
}
