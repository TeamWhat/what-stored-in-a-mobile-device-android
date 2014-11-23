package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class ApplicationDataCollector implements DataCollector {
    Context context;
    PackageManager packageManager;

    public final static String[] applicationDataColumnNames = {
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_APPLICATION_LABEL,
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_PACKAGE_NAME,
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_FIRST_INSTALLED,
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_VERSION_NAME,
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_TARGET_SDK_VERSION,
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_DATETIME
    };

    public ApplicationDataCollector(Context appContext) {
        context = appContext;
        packageManager = context.getPackageManager();
    }

    @Override
    public Map<String, Map<String, String>> getData() {
        return putDataIntoHashMap(packageManager.getInstalledPackages(packageManager.GET_META_DATA));
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.ApplicationDataEntry.TABLE_NAME;
    }

    private Map putDataIntoHashMap(List<PackageInfo> appData) {
        HashMap<String, HashMap<String, String>> outerMap = new HashMap<String, HashMap<String, String>>();
        int dataCounter = 0;
        for (PackageInfo app : appData) {
            HashMap<String, String> appDataMap = new HashMap<String, String>();
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_APPLICATION_LABEL, packageManager.getApplicationLabel(app.applicationInfo).toString());
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_PACKAGE_NAME, app.packageName);
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_FIRST_INSTALLED, Long.toString(app.firstInstallTime));
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_VERSION_NAME, app.versionName);
            appDataMap.put(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_TARGET_SDK_VERSION, Integer.toString(app.applicationInfo.targetSdkVersion));
            outerMap.put(Integer.toString(dataCounter), appDataMap);
            dataCounter++;
        }
        return outerMap;
    }
}
