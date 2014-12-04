package fi.hiit.whatisstoredinamobiledevice.background_collecting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class DataCollectionBootReceiver extends BroadcastReceiver{
    DataCollectionAlarmReceiver alarmReceiver = new DataCollectionAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarmReceiver.setDataCollectionAlarm(context);
        }
    }
}
