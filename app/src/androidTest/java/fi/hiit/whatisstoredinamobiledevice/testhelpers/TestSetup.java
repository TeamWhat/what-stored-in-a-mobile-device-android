package fi.hiit.whatisstoredinamobiledevice.testhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.InstrumentationTestCase;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class TestSetup {
    public static void setupMockito(InstrumentationTestCase dataHandlerTest) {
        System.setProperty(
                "dexmaker.dexcache",
                dataHandlerTest.getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    public static class TestDeviceDataOpenHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "test_WhatIsStoredOnAMobileDevice.db";

        // SQL helpers
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";

        // SQL commands
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DeviceDataContract.DeviceInfoEntry.TABLE_NAME + " (" +
                        DeviceDataContract.DeviceInfoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT + TEXT_TYPE + COMMA_SEP +
                        DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL + TEXT_TYPE +
                        " )";

        public TestDeviceDataOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}
    }
}
