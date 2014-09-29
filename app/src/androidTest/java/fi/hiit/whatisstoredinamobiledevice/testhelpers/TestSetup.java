package fi.hiit.whatisstoredinamobiledevice.testhelpers;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DataHandlerTest;

public class TestSetup {
    public static void setupMockito(DataHandlerTest dataHandlerTest) {
        System.setProperty(
                "dexmaker.dexcache",
                dataHandlerTest.getInstrumentation().getTargetContext().getCacheDir().getPath());
    }
}
