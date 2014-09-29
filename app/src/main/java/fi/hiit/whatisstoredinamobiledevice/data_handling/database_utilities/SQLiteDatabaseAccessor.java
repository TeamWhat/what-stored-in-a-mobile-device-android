package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SQLiteDatabaseAccessor implements DatabaseAccessor {
    private SQLiteQueryBuilder mSQLiteQueryBuilder;
    private DeviceDataOpenHelper mDeviceDataOpenHelper;
    private SQLiteDatabase db;

    public SQLiteDatabaseAccessor(Context context) {
        mSQLiteQueryBuilder = new SQLiteQueryBuilder();
        mDeviceDataOpenHelper = new DeviceDataOpenHelper(context);
    }

    /**
     * Saves the data in the map to the database
     * @param map map of data to be saved
     * @return true if save successful, false otherwise
     */
    @Override
    public boolean saveAllData(Map<String, Map<String, String>> map) {
        db = mDeviceDataOpenHelper.getWritableDatabase();

        boolean saveSuccessful = saveAll(map);

        db.close();

        return saveSuccessful;
    }

    private boolean saveAll(Map<String, Map<String, String>> map) {
        for(String tableName : map.keySet()) {
            if (!saveRow(tableName, map)) return false;
        }
        return true;
    }

    private boolean saveRow(String tableName, Map<String, Map<String, String>> map) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        insertValues(tableName, map, values);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                tableName,
                null,
                values);
        return newRowId != -1;
    }

    private void insertValues(String tableName, Map<String, Map<String, String>> map, ContentValues values) {
        values.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME, datetime());
        for(String columnName : map.get(tableName).keySet()) {
            values.put(columnName, map.get(tableName).get(columnName));
        }
    }

    private String datetime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
