package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;

public class DataHandler {
    private Context intentServiceContext;
    private List<DataCollector> collectorList;

    public DataHandler(Context context) {
        intentServiceContext = context;
        initCollectorList();
    }

    private void initCollectorList() {
        collectorList = new ArrayList<DataCollector>();
        // todo: Initialize data collectors and add them to the list
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
        for(DataCollector dataCollector : collectorList) {
            // todo: do something with the Map from dataCollector.getData()
        }
    }
}
