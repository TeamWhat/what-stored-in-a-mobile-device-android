package fi.hiit.whatisstoredinamobiledevice.data_handling.JSON;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import fi.hiit.whatisstoredinamobiledevice.data_handling.UniqueIdentifier;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;

public class JSONPackager {
    private SQLiteDatabaseAccessor databaseAccessor;
    private Context mContext;

    public JSONPackager(Context context) {
        mContext = context;
        databaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(mContext));
    }

    public JSONObject createJsonObjectFromHashMap(HashMap<String, HashMap<String, String>> map) {
        JSONObject jsonData = new JSONObject();
        for (String key : map.keySet()) {
            JSONObject innerData = new JSONObject(map.get(key));
            try {
                jsonData.put(key, innerData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonData;
    }

    public JSONObject createJsonObjectFromStoredData() {
        JSONObject jsonData = new JSONObject();

        JSONObject deviceInfoJson = createJsonObjectFromHashMap(databaseAccessor.getData(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, DeviceInfoCollector.deviceInfoColumnNames, null));
        JSONObject imageDataJson = createJsonObjectFromHashMap(databaseAccessor.getData(DeviceDataContract.ImageDataEntry.TABLE_NAME, ImageDataCollector.imageColumnNames, null));

        try {
            jsonData.put(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, deviceInfoJson);
            jsonData.put(DeviceDataContract.ImageDataEntry.TABLE_NAME, imageDataJson);
            jsonData.put("uid", new UniqueIdentifier(mContext).identifier());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
