package fi.hiit.whatisstoredinamobiledevice.background_collecting;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.data_handling.JSON.JSONPackager;
import fi.hiit.whatisstoredinamobiledevice.network.Connectivity;
import fi.hiit.whatisstoredinamobiledevice.network.HttpPostHandler;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsFragment;

public class DataCollectionAlarmReceiver extends WakefulBroadcastReceiver implements DataResultReceiver.Receiver {
    private Context mContext;
    private Intent mDataCollectionIntent;

    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent dataCollectionPendingIntent;

    public void setAlarm(Context context) {
        System.out.println("Alarm method entered");
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mContext = context;
        Intent intent = new Intent(context, DataCollectionAlarmReceiver.class);
        dataCollectionPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), getDataSendingInterval(), dataCollectionPendingIntent);
        System.out.println("Alarm set");
    }

    // This method is called when the scheduled alarm is triggered
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ALARM TRIGGERED");
        mContext = context;
        mDataCollectionIntent = getDataCollectionIntent(context);
        startWakefulService(context, mDataCollectionIntent);
    }

    @Override
    public void onReceiveDataCollectionResult() {
        System.out.println("DATA COLLECTED");
        if (mDataCollectionIntent == null) {
            new String();
        }
        attemptDataSend(mContext, mDataCollectionIntent);
    }

    public static void attemptDataSend(Context mContext, Intent mDataCollectionIntent) {
        Connectivity connectivity = new Connectivity(mContext);
        if (connectivity.isConnected()) {
            JSONPackager jsonPkgr= new JSONPackager(mContext);
            HttpPostHandler httpPostHdlr = new HttpPostHandler(mContext, new HurlStack());

            JSONObject collectedDataJSON = jsonPkgr.createJsonObjectFromStoredData();
            httpPostHdlr.postJSON(collectedDataJSON, mDataCollectionIntent);

            System.out.println("Data sent");

            setConnectivityChangeReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_DISABLED, mContext);
        } else {
            setConnectivityChangeReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED, mContext);
            ConnectivityChangeReceiver connChangeReceiver = new ConnectivityChangeReceiver();
            connChangeReceiver.setContext(mContext);
            connChangeReceiver.setDataCollectionIntent(mDataCollectionIntent);
            System.out.println("Conn receiver set");
        }
    }

    private static void setConnectivityChangeReceiverEnabled(int componentState, Context mContext) {
        ComponentName componentName = new ComponentName(mContext, ConnectivityChangeReceiver.class);
        PackageManager pm = mContext.getPackageManager();
        pm.setComponentEnabledSetting(componentName,
                componentState,
                PackageManager.DONT_KILL_APP);
    }

    public static class ConnectivityChangeReceiver extends BroadcastReceiver {
        Context context;
        Intent dataCollectionIntent;

        public void setContext(Context context) {
            this.context = context;
        }
        public void setDataCollectionIntent(Intent dataCollectionIntent) {
            this.dataCollectionIntent = dataCollectionIntent;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("Connectivity change receiver");
            attemptDataSend(context, dataCollectionIntent);
        }
    }


    private Intent getDataCollectionIntent(Context context) {
        Intent intent = new Intent(context, DataHandlerIntentService.class);
        DataResultReceiver receiver = new DataResultReceiver(new Handler());
        receiver.setReceiver(this);
        intent.putExtra("receiver", receiver);
        return intent;
    }

    // todo: Get datasending interval from settings
 //   private long getDataSendingInterval() {
 //       SharedPreferences s = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
 //       return Long.parseLong(s.getString(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY, "86400000"));
 //   }
    private long getDataSendingInterval() {
        SharedPreferences s = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        return Long.parseLong("60000");
    }
}
