package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.test.InstrumentationTestCase;

import java.util.HashMap;

public class ApplicationDataCollectorTest extends InstrumentationTestCase {
    ApplicationDataCollector adc;
    protected void setUp() {
        adc = new ApplicationDataCollector(getInstrumentation().getContext());
    }

    public void testApplicationDataCollectorFindsThisApp() {
        HashMap<String, HashMap<String, String>> dataMap = (HashMap)adc.getData();
        boolean found = false;
        for (String key : dataMap.keySet()) {
            if (dataMap.get(key).get("package_name").equals("fi.hiit.whatisstoredinamobiledevice")) {
                found = true;
                break;
            }
        }
        if (found == false) {
            fail();
        }
    }
}

