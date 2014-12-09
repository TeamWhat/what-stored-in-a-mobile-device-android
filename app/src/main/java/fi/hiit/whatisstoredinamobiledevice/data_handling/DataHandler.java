package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ApplicationDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.AudioDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.TextDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.VideoDataCollector;
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

        // Initialize data collectors and add them to the list
        // todo: later check preferences for each collector and only add the data the app user wants to send
        mCollectorList.add(new DeviceInfoCollector(mIntentServiceContext));
        mCollectorList.add(new ImageDataCollector(mIntentServiceContext));
        mCollectorList.add(new ApplicationDataCollector(mIntentServiceContext));
        mCollectorList.add(new TextDataCollector(mIntentServiceContext));
        mCollectorList.add(new AudioDataCollector(mIntentServiceContext));
        mCollectorList.add(new VideoDataCollector(mIntentServiceContext));
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
