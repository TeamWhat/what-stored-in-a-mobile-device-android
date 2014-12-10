package fi.hiit.whatisstoredinamobiledevice.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import fi.hiit.whatisstoredinamobiledevice.background_collecting.SendDataIntentService;

public class ConnectivityChangeReceiver extends WakefulBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            System.out.println("CONNECTION CHANGED RECEIVED");
            Intent sendDataIntent = new Intent(context, SendDataIntentService.class);
            startWakefulService(context, sendDataIntent);
        }
}
