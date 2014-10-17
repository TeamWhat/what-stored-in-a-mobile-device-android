package fi.hiit.whatisstoredinamobiledevice.data_handling.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONPackager {

    public JSONObject createJsonObjectFromData(HashMap<String, HashMap<String, String>> map) {
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
}
