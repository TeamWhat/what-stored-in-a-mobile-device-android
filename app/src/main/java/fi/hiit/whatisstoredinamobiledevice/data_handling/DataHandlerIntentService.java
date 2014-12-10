package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;

public class DataHandlerIntentService extends IntentService {

    public DataHandlerIntentService() {
        super("DataHandlerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        System.out.println("DATA COLLECTION STARTED");
        DataHandler dataHandler = new DataHandler(this, new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(this)));
        dataHandler.collectAllData();
        ResultReceiver rec = intent.getParcelableExtra("receiver");
        Bundle b = new Bundle();
        rec.send(0, b);
    }


}
