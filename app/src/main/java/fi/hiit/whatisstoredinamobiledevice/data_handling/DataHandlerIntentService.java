package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.app.IntentService;
import android.content.Intent;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;

public class DataHandlerIntentService extends IntentService {

    public DataHandlerIntentService() {
        super("DataHandlerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataHandler dataHandler = new DataHandler(this, new SQLiteDatabaseAccessor());
        dataHandler.collectAllData();
    }
}
