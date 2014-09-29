package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceDataCollector;


public class SQLiteDatabaseAccessorTest extends AndroidTestCase {
    DatabaseAccessor databaseAccessor;

    protected void setUp() {
        databaseAccessor = new SQLiteDatabaseAccessor(getContext());
    }

    public Cursor readDatabase() {
        DeviceDataOpenHelper mDbHelper = new DeviceDataOpenHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL
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

    public void testDeviceDataIsSaved() {
        DataCollector ddc = new DeviceDataCollector();
        Map<String, Map<String, String>> hm = new HashMap<String, Map<String, String>>();
        hm.put(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, ddc.getData());
        databaseAccessor.saveAllData(hm);
        Cursor c = readDatabase();
        assertTrue(c.getColumnCount() > 0);
    }
}
