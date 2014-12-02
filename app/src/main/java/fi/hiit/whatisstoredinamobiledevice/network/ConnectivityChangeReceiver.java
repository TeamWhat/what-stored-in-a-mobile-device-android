package fi.hiit.whatisstoredinamobiledevice.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import fi.hiit.whatisstoredinamobiledevice.background_collecting.SendDataIntentService;


// todo: does this class belong to network or background_collecting package
public class ConnectivityChangeReceiver extends WakefulBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("CONNECTION CHANGED RECEIVED");
            Intent sendDataIntent = new Intent(context, SendDataIntentService.class);
            startWakefulService(context, sendDataIntent);
        }
}
