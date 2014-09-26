package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class DeviceDataCollector implements DataCollector {

    @Override
    public Map getData() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("Brand", Build.BRAND);
        data.put("Device", Build.DEVICE);
        data.put("Model", Build.MODEL);
        data.put("Product", Build.PRODUCT);
        data.put("Serial", Build.SERIAL);
        return data;
    }

    @Override
    public String getTableNameForData() {
        return "device_info_data";
    }
}
