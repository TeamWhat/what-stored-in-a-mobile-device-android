package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DatabaseAccessor;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;

public class DataHandler {
    private Context mIntentServiceContext;
    private List<DataCollector> mCollectorList;
    private DatabaseAccessor mDatabaseAccessor;

    public DataHandler(Context context) {
        mIntentServiceContext = context;
        mDatabaseAccessor = new SQLiteDatabaseAccessor();
        initCollectorList();
    }

    private void initCollectorList() {
        mCollectorList = new ArrayList<DataCollector>();

        // Initialize data collectors and add them to the list, todo: later check preferences for each collector
        DeviceDataCollector deviceDataCollector = new DeviceDataCollector();
        mCollectorList.add(deviceDataCollector);
    }

    public void collectAllData() {
        if (mCollectorList == null) {
            throw new IllegalStateException("You must initialize the collector list first, it was null");
        }

        if (mCollectorList.isEmpty()) {
            throw new IllegalStateException("You must add collectors to the collector list first, it was empty");
        }

        try {
            goThroughCollectors();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goThroughCollectors() throws Exception {
        Map<String, Map<String, String>> allData = new HashMap<String, Map<String, String>>();
        for(DataCollector dataCollector : mCollectorList) {
            // put the data into the allData map
            allData.put(dataCollector.getTableNameForData(), dataCollector.getData());
        }

        if (!mDatabaseAccessor.saveAllData(allData)) {
            throw new Exception("Saving to database failed.");
        }
    }
}
