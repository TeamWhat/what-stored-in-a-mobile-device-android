package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeviceDataOpenHelper extends SQLiteOpenHelper {
    // Update database version every time schema (DeviceDataContract) is updated
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WhatIsStoredOnAMobileDevice.db";

    // SQL helpers
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SEMICOLON_SEP = "; ";
    private static final String PRIMARY_KEY_INIT = " INTEGER PRIMARY KEY AUTOINCREMENT";

    // SQL commands
    private static final String CREATE_DEVICE_INFO_TABLE = "CREATE TABLE " + DeviceDataContract.DeviceInfoEntry.TABLE_NAME + " (" +
            DeviceDataContract.DeviceInfoEntry._ID + PRIMARY_KEY_INIT + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL + TEXT_TYPE +
            " )";

    private static final String CREATE_IMAGE_INFO_TABLE = "CREATE TABLE " + DeviceDataContract.ImageDataEntry.TABLE_NAME + " (" +
            DeviceDataContract.ImageDataEntry._ID + PRIMARY_KEY_INIT + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_TAKEN + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_IS_PRIVATE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_LATITUDE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_ADDED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_SIZE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_MODIFIED + TEXT_TYPE +
            " )";

    private static final String CREATE_AUDIO_DATA_TABLE = "CREATE TABLE " + DeviceDataContract.AudioDataEntry.TABLE_NAME + " (" +
            DeviceDataContract.AudioDataEntry._ID + PRIMARY_KEY_INIT + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_ALBUM + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_ARTIST + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_COMPOSER + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_DURATION + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATE_ADDED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATE_MODIFIED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.AudioDataEntry.COLUMN_NAME_SIZE + TEXT_TYPE +
            " )";

    private static final String CREATE_APPLICATION_DATA_TABLE = "CREATE TABLE " + DeviceDataContract.ApplicationDataEntry.TABLE_NAME + " (" +
            DeviceDataContract.ApplicationDataEntry._ID + PRIMARY_KEY_INIT + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_APPLICATION_LABEL + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_PACKAGE_NAME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_FIRST_INSTALLED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_VERSION_NAME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.ApplicationDataEntry.COLUMN_NAME_TARGET_SDK_VERSION + TEXT_TYPE +
            " )";


    private static final String CREATE_TEXT_DATA_TABLE = "CREATE TABLE " + DeviceDataContract.TextDataEntry.TABLE_NAME + " (" +
            DeviceDataContract.TextDataEntry._ID + PRIMARY_KEY_INIT + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_DATETIME + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_DATE_ADDED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_DATE_MODIFIED + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_SIZE + TEXT_TYPE + COMMA_SEP +
            DeviceDataContract.TextDataEntry.COLUMN_NAME_MIME_TYPE + TEXT_TYPE +
            " )";

    public DeviceDataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen (SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DEVICE_INFO_TABLE);
        sqLiteDatabase.execSQL(CREATE_IMAGE_INFO_TABLE);
        sqLiteDatabase.execSQL(CREATE_APPLICATION_DATA_TABLE);
        sqLiteDatabase.execSQL(CREATE_TEXT_DATA_TABLE);
        sqLiteDatabase.execSQL(CREATE_AUDIO_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // todo: define what we do when database is upgraded
    }
}
