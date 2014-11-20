package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.webkit.HttpAuthHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
        String datetime = datetime();
        for(String tableName : allDataMap.keySet()) {
            for(String tempRowIndex : allDataMap.get(tableName).keySet()) {
                if (!saveRow(tableName, tempRowIndex, allDataMap.get(tableName), datetime)) return false;
            }
        }
        return true;
    }

    private boolean saveRow(String tableName, String tempRowIndex, Map<String, Map<String, String>> tableMap, String datetime) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        insertValues(tempRowIndex, tableMap, values, datetime);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                tableName,
                null,
                values);
        return newRowId != -1;
    }

    // Saves colmun values of a row
    private void insertValues(String tempRowIndex, Map<String, Map<String, String>> tableMap, ContentValues values, String datetime) {
        values.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME, datetime);
        for(String columnName : tableMap.get(tempRowIndex).keySet()) {
            // put time to all columns first, second row replaces time with correct data for all columns but datetime
            if (dateInWrongFormat(columnName, tableMap, tempRowIndex)) {
                values.put(columnName, ""+(Long.parseLong(tableMap.get(tempRowIndex).get(columnName))/1000));
            } else {
                values.put(columnName, tableMap.get(tempRowIndex).get(columnName));
            }
        }
    }

    private boolean dateInWrongFormat(String columnName, Map<String, Map<String, String>> tableMap, String tempRowIndex) {
        return tableMap.get(tempRowIndex).get(columnName) != null && (
                columnName.equals(DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_TAKEN) ||
                columnName.equals(DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_FIRST_INSTALLED)
        );
    }


    private String datetime() {
        Date date = new Date();
        return date.getTime()/1000+"";
    }

    public HashMap<String, HashMap<String, String>> getData(String tablename, String[] projection, String sortOrder) {
        db = mDeviceDataOpenHelper.getReadableDatabase();
        Cursor c = db.query(
                tablename,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        return putDataIntoHashMap(c);
    }

    private HashMap<String, HashMap<String, String>> putDataIntoHashMap(Cursor cursor) {
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        int dataCounter = 0;
        while (cursor.moveToNext()) {
            data.put("" + dataCounter, getSingleObjectHashMap(cursor));
            dataCounter++;
        }
        return data;
    }

    private HashMap<String, String> getSingleObjectHashMap(Cursor cursor) {
        HashMap<String, String> data = new HashMap<String, String>();
        for (int i=0; i< cursor.getColumnCount(); i++) {
            data.put(cursor.getColumnName(i), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
        }
        return data;
    }

}
