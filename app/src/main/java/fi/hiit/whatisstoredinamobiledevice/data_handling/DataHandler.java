package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceDataCollector;

public class DataHandler {
    private Context intentServiceContext;
    private List<DataCollector> collectorList;

    public DataHandler(Context context) {
        intentServiceContext = context;
        initCollectorList();
    }

    private void initCollectorList() {
        collectorList = new ArrayList<DataCollector>();

        // Initialize data collectors and add them to the list, todo: later check preferences for each collector
        DeviceDataCollector deviceDataCollector = new DeviceDataCollector();
        collectorList.add(deviceDataCollector);
    }

    public void collectAllData() {
        if (collectorList == null) {
            throw new IllegalStateException("You must initialize the collector list first, it was null");
        }

        if (collectorList.isEmpty()) {
            throw new IllegalStateException("You must add collectors to the collector list first, it was empty");
        }

        goThroughCollectors();

    }

    private void goThroughCollectors() {
        Map<String, Map<String, String>> allData = new HashMap<String, Map<String, String>>();
        for(DataCollector dataCollector : collectorList) {
            // put the data into the allData map
            allData.put(dataCollector.getTableNameForData(), dataCollector.getData());
        }
        // todo: call DB access interface to save allData map to DB
    }
}
