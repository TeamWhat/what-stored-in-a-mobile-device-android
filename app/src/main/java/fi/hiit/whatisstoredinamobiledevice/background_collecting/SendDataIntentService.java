package fi.hiit.whatisstoredinamobiledevice.background_collecting;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

import fi.hiit.whatisstoredinamobiledevice.data_handling.JSON.JSONPackager;
import fi.hiit.whatisstoredinamobiledevice.network.Connectivity;
import fi.hiit.whatisstoredinamobiledevice.network.ConnectivityChangeReceiver;
import fi.hiit.whatisstoredinamobiledevice.network.HttpPostHandler;


public class SendDataIntentService extends IntentService {


    public SendDataIntentService() { super("SendDataIntentService"); }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("SEND DATA INTENT STARTED");
        Connectivity connectivity = new Connectivity(this);

        if (!connectivity.isConnectedAndIsWifiIfOnlyWifiSet()) {
            DataCollectionAlarmReceiver.completeWakefulIntent(intent);
            ConnectivityChangeReceiver.completeWakefulIntent(intent);

            System.out.println("ConnectivityChangeReciever state: " + DataCollectionAlarmReceiver.isConnectivityChangeReceiverEnabled(this));
            DataCollectionAlarmReceiver.setConnectivityChangeReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED, this);
            System.out.println("ConnectivityChangeReciever state: " + DataCollectionAlarmReceiver.isConnectivityChangeReceiverEnabled(this));

            System.out.println("STOPPED SEND DATA INTENT AND ENABLED CCR - NO NETWORK");
        } else {
            JSONPackager jsonPkgr = new JSONPackager(this);
            HttpPostHandler httpPostHdlr = new HttpPostHandler(this, new HurlStack());

            JSONObject collectedDataJSON = jsonPkgr.createJsonObjectFromStoredData();
            httpPostHdlr.postJSON(collectedDataJSON, intent);
            System.out.println("SENT POST");
        }
    }
}
