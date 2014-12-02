package fi.hiit.whatisstoredinamobiledevice.background_collecting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


// todo: enable/disable this boot receiver through package manager when send data setting enabled/disabled
public class DataCollectionBootReceiver extends BroadcastReceiver{
    DataCollectionAlarmReceiver alarmReceiver = new DataCollectionAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarmReceiver.setAlarm(context);
        }
    }

}
