package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;

import java.util.HashMap;
import java.util.Map;


import static fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup.*;


public class SQLiteDatabaseAccessorTest extends InstrumentationTestCase {
    DatabaseAccessor databaseAccessor;
    Map<String, Map<String, String>> hm;

    protected void setUp() {
        databaseAccessor = new SQLiteDatabaseAccessor(new TestDeviceDataOpenHelper(getInstrumentation().getTargetContext()));
        hm = new HashMap<String, Map<String, String>>();
        Map<String, String> testData = new HashMap<String, String>();
        testData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND, "testBrand");
        hm.put(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, testData);
    }

    public Cursor readDeviceDataFromDatabase() {
        TestDeviceDataOpenHelper mDbHelper = new TestDeviceDataOpenHelper(getInstrumentation().getTargetContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME
        };

        Cursor c = db.query(
                DeviceDataContract.DeviceInfoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return c;
    }

    public void testDeviceDataBrandIsSaved() {
        databaseAccessor.saveAllData(hm);
        Cursor c = readDeviceDataFromDatabase();
        c.moveToFirst();
        assertTrue(c.getString(c.getColumnIndex(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND)).equals("testBrand"));
    }

    public void testDeviceDataDateTime() {
        databaseAccessor.saveAllData(hm);
        Cursor c = readDeviceDataFromDatabase();
        c.moveToFirst();
        assertTrue(c.getString(c.getColumnIndex(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME)).matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    public void testSaveAllDataReturnsFalseIfDataSavingFails() {
        HashMap<String, Map<String, String>> badMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> m = new HashMap<String, String>();
        badMap.put("kappa", m);
        assertFalse(databaseAccessor.saveAllData(badMap));
    }


}