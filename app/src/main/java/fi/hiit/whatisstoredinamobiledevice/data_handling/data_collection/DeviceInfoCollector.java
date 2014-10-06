package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class DeviceInfoCollector implements DataCollector {
    public final static String[] deviceInfoColumnNames = {
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL
    };

    @Override
    public Map<String, Map<String, String>> getData() {
        HashMap<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        HashMap<String, String> deviceInfoData = new HashMap<String, String>();
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND, Build.BRAND);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE, Build.DEVICE);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL, Build.MODEL);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT, Build.PRODUCT);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL, Build.SERIAL);
        // outer hashmap for consistency with other collectors
        data.put("0", deviceInfoData);
        return data;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.DeviceInfoEntry.TABLE_NAME;
    }
}
