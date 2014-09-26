package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.Map;

public class SQLiteDatabaseAccessor implements DatabaseAccessor {
    private SQLiteQueryBuilder mSQLiteQueryBuilder;
    private DeviceDataOpenHelper mDeviceDataOpenHelper;

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
    public boolean saveAllData(Map map) {
        SQLiteDatabase db = mDeviceDataOpenHelper.getWritableDatabase();

        // todo: save data to db

        db.close();

        // todo: return true if successful, false otherwise
        return false;
    }
}
