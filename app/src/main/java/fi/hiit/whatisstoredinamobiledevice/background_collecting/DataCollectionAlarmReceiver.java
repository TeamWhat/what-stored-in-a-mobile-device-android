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
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.network.ConnectivityChangeReceiver;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsFragment;

/**
 * For class purpose see https://developer.android.com/training/scheduling/alarms.html
 * and https://developer.android.com/training/scheduling/wakelock.html#cpu
 */
public class DataCollectionAlarmReceiver extends WakefulBroadcastReceiver implements DataResultReceiver.Receiver {
    public static final int TRIGGER_WAIT = 300000; // Wait 5 minutes after alarm set before starting first collect/send sequence
    private Context mContext;
    private Intent mDataCollectionIntent;


    public void setDataCollectionAlarm(Context context) {
        getAlarmManager(context).setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, getAlarmTriggerTime(), getDataSendingInterval(context), getDataCollectionPendingIntent(context));
        setDataCollectionBootReceiverState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED, context);
//        System.out.println("ALARM SET");
    }

    public void cancelDataCollectionAlarm(Context context) {
        setDataCollectionBootReceiverState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED, context);
        setConnectivityChangeReceiverState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED, context);
        getAlarmManager(context).cancel(getDataCollectionPendingIntent(context));
//        System.out.println("ALARM CANCELED");
    }

    private AlarmManager getAlarmManager(Context context) {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    private long getAlarmTriggerTime() {
        return SystemClock.elapsedRealtime() + TRIGGER_WAIT;
    }

    private long getDataSendingInterval(Context context) {
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
        return Long.parseLong(s.getString(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY, "86400000"));
    }

    private PendingIntent getDataCollectionPendingIntent(Context context) {
        Intent intent = new Intent(context, DataCollectionAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    // This method is called when the scheduled alarm is triggered
    @Override
    public void onReceive(Context context, Intent intent) {
//        System.out.println("ALARM TRIGGERED");
        mContext = context;
//        System.out.println("ConnectivityChangeReciever state: " + isConnectivityChangeReceiverEnabled(context));
        mDataCollectionIntent = getDataCollectionIntent(context);
        startWakefulService(context, mDataCollectionIntent);
    }

    @Override
    public void onReceiveDataCollectionResult() {
//        System.out.println("DATA COLLECTED");
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

    public static void setConnectivityChangeReceiverState(int componentState, Context context) {
        ComponentName componentName = new ComponentName(context, ConnectivityChangeReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(componentName,
                componentState,
                PackageManager.DONT_KILL_APP);
    }

    public static void setDataCollectionBootReceiverState(int componentState, Context context) {
        ComponentName componentName = new ComponentName(context, DataCollectionBootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(componentName,
                componentState,
                PackageManager.DONT_KILL_APP);
    }


    // DEBUG METHODS

    // return 1 for enabled, 2 for disabled
//    public static int isConnectivityChangeReceiverEnabled(Context context) {
//        ComponentName componentName = new ComponentName(context, ConnectivityChangeReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        return pm.getComponentEnabledSetting(componentName);
//    }

}
