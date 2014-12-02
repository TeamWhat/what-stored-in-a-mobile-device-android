package fi.hiit.whatisstoredinamobiledevice.background_collecting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.network.ConnectivityChangeReceiver;


public class DataCollectionAlarmReceiver extends WakefulBroadcastReceiver implements DataResultReceiver.Receiver {
    private static final String TAG = "DataCollectionAlarmReceiver";
    private Context mContext;
    private Intent mDataCollectionIntent;

    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent dataCollectionPendingIntent;

    public void setAlarm(Context context) {
        System.out.println("Alarm method entered");
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mContext = context;
        Intent intent = new Intent(context, DataCollectionAlarmReceiver.class);
        dataCollectionPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+60000, getDataSendingInterval(), dataCollectionPendingIntent);
        System.out.println("Alarm set");
    }

    // This method is called when the scheduled alarm is triggered
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ALARM TRIGGERED");
        mContext = context;
        System.out.println("ConnectivityChangeReciever state: " + isConnectivityChangeReceiverEnabled(context));
        mDataCollectionIntent = getDataCollectionIntent(context);
        startWakefulService(context, mDataCollectionIntent);
    }

    @Override
    public void onReceiveDataCollectionResult() {

        System.out.println("DATA COLLECTED");

//        System.out.println("ConnectivityChangeReciever state: " + isConnectivityChangeReceiverEnabled(mContext));
//        setConnectivityChangeReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED, mContext);
//        System.out.println("ConnectivityChangeReciever state: " + isConnectivityChangeReceiverEnabled(mContext));

        Intent sendDataIntent = new Intent(mContext, SendDataIntentService.class);
        startWakefulService(mContext, sendDataIntent);
        completeWakefulIntent(mDataCollectionIntent);
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
        return Long.parseLong("100000");
    }


    public static void setConnectivityChangeReceiverEnabled(int componentState, Context context) {
        ComponentName componentName = new ComponentName(context, ConnectivityChangeReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(componentName,
                componentState,
                PackageManager.DONT_KILL_APP);
    }

    // return 1 for enabled, 2 for disabled - FOR DEBUG
    public static int isConnectivityChangeReceiverEnabled(Context context) {
        ComponentName componentName = new ComponentName(context, ConnectivityChangeReceiver.class);
        PackageManager pm = context.getPackageManager();
        return pm.getComponentEnabledSetting(componentName);
    }

}
