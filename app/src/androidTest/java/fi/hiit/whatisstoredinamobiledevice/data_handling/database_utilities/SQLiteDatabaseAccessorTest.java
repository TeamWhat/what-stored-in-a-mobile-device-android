package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;

import java.util.HashMap;
import java.util.Map;


import static fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup.*;


public class SQLiteDatabaseAccessorTest extends InstrumentationTestCase {
    DatabaseAccessor databaseAccessor;
    Map<String, Map<String, Map<String, String>>> hm;

    protected void setUp() {
        getInstrumentation().waitForIdleSync();
        databaseAccessor = new SQLiteDatabaseAccessor(new TestDeviceDataOpenHelper(getInstrumentation().getTargetContext()));
        getInstrumentation().waitForIdleSync();
        hm = new HashMap<String, Map<String, Map<String, String>>>();
        getInstrumentation().waitForIdleSync();
        HashMap<String, Map<String, String>> testData = new HashMap<String, Map<String, String>>();
        getInstrumentation().waitForIdleSync();
        HashMap<String, String> innerMap = new HashMap<String, String>();
        getInstrumentation().waitForIdleSync();

        innerMap.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND, "testBrand");

        getInstrumentation().waitForIdleSync();
        testData.put("0", innerMap);
        getInstrumentation().waitForIdleSync();
        hm.put(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, testData);
        getInstrumentation().waitForIdleSync();
    }

    public Cursor readDeviceDataFromDatabase() {
        getInstrumentation().waitForIdleSync();
        TestDeviceDataOpenHelper mDbHelper = new TestDeviceDataOpenHelper(getInstrumentation().getTargetContext());
        getInstrumentation().waitForIdleSync();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        getInstrumentation().waitForIdleSync();

        String[] projection = {
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME
        };

        getInstrumentation().waitForIdleSync();
        Cursor c = db.query(
                DeviceDataContract.DeviceInfoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        getInstrumentation().waitForIdleSync();
        return c;
    }

    public void testDeviceDataBrandIsSaved() {
        getInstrumentation().waitForIdleSync();
        boolean saveSuccessful = databaseAccessor.saveAllData(hm);
        getInstrumentation().waitForIdleSync();
        Cursor c = readDeviceDataFromDatabase();
        getInstrumentation().waitForIdleSync();
        c.moveToFirst();
        getInstrumentation().waitForIdleSync();
        if (saveSuccessful) {
            int colIndex = c.getColumnIndex(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND);
            String testResult = c.getString(colIndex);
            assertTrue(testResult.equals("testBrand"));
        };
    }

    public void testSaveAllDataReturnsFalseIfDataSavingFails() {
        HashMap<String, Map<String, Map<String, String>>> reallyBadMap = new HashMap<String, Map<String, Map<String, String>>>();
        HashMap<String, Map<String, String>> badMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> m = new HashMap<String, String>();
        badMap.put("kappa", m);
        reallyBadMap.put("0", badMap);
        assertFalse(databaseAccessor.saveAllData(reallyBadMap));
    }


}
