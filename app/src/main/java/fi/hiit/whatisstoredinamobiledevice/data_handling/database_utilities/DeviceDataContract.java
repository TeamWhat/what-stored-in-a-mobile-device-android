package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.provider.BaseColumns;

public final class DeviceDataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DeviceDataContract() {}

    /* Inner class that defines the table contents */
    public static abstract class DeviceInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "device_info";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_DEVICE = "device";
        public static final String COLUMN_NAME_MODEL = "model";
        public static final String COLUMN_NAME_PRODUCT = "product";
        public static final String COLUMN_NAME_SERIAL = "serial";
    }

    public static abstract class ImageDataEntry implements BaseColumns {
        // todo: image constants here
        public static final String TABLE_NAME = "image_info";

    }

    // todo: more tables here
}