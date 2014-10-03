package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SQLiteDatabaseAccessor implements DatabaseAccessor {
    private SQLiteQueryBuilder mSQLiteQueryBuilder;
    private SQLiteOpenHelper mDeviceDataOpenHelper;
    private SQLiteDatabase db;

    public SQLiteDatabaseAccessor(SQLiteOpenHelper deviceDataOpenHelper) {
        mSQLiteQueryBuilder = new SQLiteQueryBuilder();
        mDeviceDataOpenHelper = deviceDataOpenHelper;
    }

    /**
     * Saves the data in the map to the database
     * @param allDataMap map of data to be saved
     * @return true if save successful, false otherwise
     */
    @Override
    public boolean saveAllData(Map<String, Map<String, Map<String, String>>> allDataMap) {
        db = mDeviceDataOpenHelper.getWritableDatabase();

        boolean saveSuccessful = saveAll(allDataMap);

        db.close();

        return saveSuccessful;
    }

    private boolean saveAll(Map<String, Map<String, Map<String, String>>> allDataMap) {
        for(String tableName : allDataMap.keySet()) {
            for(String tempRowIndex : allDataMap.get(tableName).keySet()) {
                if (!saveRow(tableName, tempRowIndex, allDataMap.get(tableName))) return false;
            }
        }
        return true;
    }

    private boolean saveRow(String tableName, String tempRowIndex, Map<String, Map<String, String>> tableMap) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        insertValues(tempRowIndex, tableMap, values);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                tableName,
                null,
                values);
        return newRowId != -1;
    }

    private void insertValues(String tempRowIndex, Map<String, Map<String, String>> tableMap, ContentValues values) {
        values.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME, datetime());
        for(String columnName : tableMap.get(tempRowIndex).keySet()) {
            values.put(columnName, tableMap.get(tempRowIndex).get(columnName));
        }
    }

    private String datetime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
