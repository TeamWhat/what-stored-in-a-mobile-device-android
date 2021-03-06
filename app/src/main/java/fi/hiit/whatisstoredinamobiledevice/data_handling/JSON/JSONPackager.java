package fi.hiit.whatisstoredinamobiledevice.data_handling.JSON;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.UniqueIdentifier;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ApplicationDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.AudioDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.TextDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.VideoDataCollector;
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

    public JSONObject createJsonObjectFromMap(Map<String, Map<String, String>> map) {
        JSONObject jsonData = new JSONObject();
        for (String key : map.keySet()) {

            // Check if the data has been sent
            if (map.get(key).get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SENT) != null
                    && map.get(key).get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SENT).equals("sent")) {
                continue;
            }

            JSONObject innerData = new JSONObject(removeUselessFields(map.get(key)));
            try {
                jsonData.put(key, innerData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonData;
    }

    /**
     * Remove fields that aren't useful to the backend
     * @param map
     * @return
     */
    private Map<String, String> removeUselessFields(Map<String, String> map) {
        Map<String, String> mapWithoutUselessFields = new HashMap<String, String>();
        mapWithoutUselessFields.putAll(map);
        mapWithoutUselessFields.remove(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SENT);
        return mapWithoutUselessFields;
    }

    public JSONObject createJsonObjectFromStoredData() {
        JSONObject jsonData = new JSONObject();

        JSONObject deviceInfoJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, DeviceInfoCollector.deviceInfoColumnNames, null));
        JSONObject imageDataJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.ImageDataEntry.TABLE_NAME, ImageDataCollector.imageColumnNames, null));
        JSONObject applicationDataJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.ApplicationDataEntry.TABLE_NAME, ApplicationDataCollector.applicationDataColumnNames, null));
        JSONObject textDataJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.TextDataEntry.TABLE_NAME, TextDataCollector.textColumnNames, null));
        JSONObject audioDataJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.AudioDataEntry.TABLE_NAME, AudioDataCollector.audioColumnNames, null));
        JSONObject videoDataJson = createJsonObjectFromMap(databaseAccessor.getData(DeviceDataContract.VideoDataEntry.TABLE_NAME, VideoDataCollector.videoColumnNames, null));

        try {
            jsonData.put(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, deviceInfoJson);
            jsonData.put(DeviceDataContract.ImageDataEntry.TABLE_NAME, imageDataJson);
            jsonData.put(DeviceDataContract.ApplicationDataEntry.TABLE_NAME, applicationDataJson);
            jsonData.put(DeviceDataContract.TextDataEntry.TABLE_NAME, textDataJson);
            jsonData.put(DeviceDataContract.AudioDataEntry.TABLE_NAME, audioDataJson);
            jsonData.put(DeviceDataContract.VideoDataEntry.TABLE_NAME, videoDataJson);
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
            SharedPreferences sharedPreferences = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
            personalJSON.put("gender", sharedPreferences.getString(SettingsFragment.KEY_SETTINGS_USER_GENDER, "No gender selected"));
            personalJSON.put("age", sharedPreferences.getString(SettingsFragment.KEY_SETTINGS_USER_AGE, "No age selected"));
            personalJSON.put("country", sharedPreferences.getString(SettingsFragment.KEY_SETTINGS_USER_COUNTRY, "No country selected"));
            personalJSON.put("email", sharedPreferences.getString(SettingsFragment.KEY_SETTINGS_USER_EMAIL, "No email entered"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personalJSON;
    }

    private void incrementDataSendCounter() {
        SharedPreferences sharedPreferences = mContext.getApplicationContext().getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        int dataSendCount = sharedPreferences.getInt("data_send_count", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("data_send_count", dataSendCount++);
        editor.commit();
    }
}
