package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class ApplicationDataCollector implements DataCollector {
    Context context;
    PackageManager packageManager;

    public ApplicationDataCollector(Context appContext) {
        context = appContext;
        packageManager = context.getPackageManager();
    }

    @Override
    public Map<String, Map<String, String>> getData() {
        // :todo Change to getinstalledpackages instead of apps
        return putDataIntoHashMap(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
    }

    @Override
    public String getTableNameForData() {
        return null;
    }

    private Map putDataIntoHashMap(List<ApplicationInfo> appData) {
        HashMap<String, HashMap<String, String>> outerMap = new HashMap<String, HashMap<String, String>>();
        for (ApplicationInfo app : appData) {
            HashMap<String, String> appDataMap = new HashMap<String, String>();
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_TARGET_SDK_VERSION, Integer.toString(app.targetSdkVersion));
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_PERMISSION, app.permission);
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_PROCESS_NAME, app.processName);
            outerMap.put(packageManager.getApplicationLabel(app).toString(), appDataMap);
        }
        return outerMap;
    }
}
