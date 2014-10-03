package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class DeviceDataCollector implements DataCollector {

    @Override
    public Map getData() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("brand", Build.BRAND);
        data.put("device", Build.DEVICE);
        data.put("model", Build.MODEL);
        data.put("product", Build.PRODUCT);
        data.put("serial", Build.SERIAL);
        return data;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.DeviceInfoEntry.TABLE_NAME;
    }
}
