package fi.hiit.whatisstoredinamobiledevice.data_handling.JSON;

import android.test.InstrumentationTestCase;

import org.json.JSONObject;
import java.util.HashMap;

import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandler;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup;

public class JSONPackagerTest extends InstrumentationTestCase {
    private HashMap<String, HashMap<String, String>> testMap;
    private JSONPackager jsonPackager;
    private JSONObject joe;

    protected void setUp() {
        jsonPackager = new JSONPackager(getInstrumentation().getTargetContext());
        testMap = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> innerTestMap = new HashMap<String, String>();
        innerTestMap.put("1", "2");
        innerTestMap.put("3", "4");
        testMap.put("0", innerTestMap);
        joe = jsonPackager.createJsonObjectFromHashMap(testMap);
        new DataHandler(getInstrumentation().getTargetContext(), new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(getInstrumentation().getTargetContext()))).collectAllData();
    }

    public void testJSONObjectFromHashMapNotNull() {
        assertTrue(joe != null);
    }

    public void testJSONObjectFromStoredData() {
        // todo:Change this when we add tables
        JSONObject jobj = jsonPackager.createJsonObjectFromStoredData();

        assertTrue(jobj.length() == 6);
        assertTrue(jobj.has("uid"));
        assertTrue(jobj.has("image_info"));
        assertTrue(jobj.has("device_info"));
        assertTrue(jobj.has("personal_info"));
        assertTrue(jobj.has("application_data"));
        assertTrue(jobj.has("text_data"));
    }
}
