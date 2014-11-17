package fi.hiit.whatisstoredinamobiledevice.data_handling.JSON;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.UniqueIdentifier;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsFragment;

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
            jsonData.put("personal_info", createPersonalDataJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject createPersonalDataJSON() {
        JSONObject personalJSON = new JSONObject();
        try {
            SharedPreferences s = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
            personalJSON.put("gender", s.getString(SettingsFragment.KEY_SETTINGS_USER_GENDER, "No gender selected"));
            personalJSON.put("age", s.getString(SettingsFragment.KEY_SETTINGS_USER_AGE, "No age selected"));
            personalJSON.put("country", s.getString(SettingsFragment.KEY_SETTINGS_USER_COUNTRY, "No country selected"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personalJSON;
    }
}
