package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DatabaseAccessor;

public class DataHandler {
    private Context mIntentServiceContext;
    private List<DataCollector> mCollectorList;
    private DatabaseAccessor mDatabaseAccessor;

    public DataHandler(Context context, DatabaseAccessor databaseAccessor) {
        mIntentServiceContext = context;
        mDatabaseAccessor = databaseAccessor;
        initCollectorList();
    }

    public boolean collectAllData() {
        Map<String, Map<String, Map<String, String>>> allData = goThroughCollectors();
        return mDatabaseAccessor.saveAllData(allData);
    }

    private void initCollectorList() {
        mCollectorList = new ArrayList<DataCollector>();

        // Initialize data collectors and add them to the list, todo: later check preferences for each collector
        mCollectorList.add(new DeviceInfoCollector());
        mCollectorList.add(new ImageDataCollector(mIntentServiceContext));
    }

    private Map<String, Map<String, Map<String, String>>> goThroughCollectors() {
        Map<String, Map<String, Map<String, String>>> allData = new HashMap<String, Map<String, Map<String, String>>>();
        for(DataCollector dataCollector : mCollectorList) {
            // put the data into the allData map
            allData.put(dataCollector.getTableNameForData(), dataCollector.getData());
        }
        return allData;
    }
}
