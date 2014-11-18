package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.provider.BaseColumns;

public final class DeviceDataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DeviceDataContract() {}

    /* Inner class that defines the table contents */
    public static abstract class DeviceInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "device_info";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_DEVICE = "device";
        public static final String COLUMN_NAME_MODEL = "model";
        public static final String COLUMN_NAME_PRODUCT = "product";
        public static final String COLUMN_NAME_SERIAL = "serial";
    }

    public static abstract class ImageDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "image_info";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_DATE_TAKEN = "date_taken";
        public static final String COLUMN_NAME_IS_PRIVATE = "is_private";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
    }

    public static abstract class MusicDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "music_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_ALBUM = "album";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_COMPOSER = "composer";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
    }

    public static abstract class ApplicationDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "application_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_APPLICATION_LABEL = "application_label";
        public static final String COLUMN_NAME_FIRST_INSTALLED = "first_installed";
        public static final String COLUMN_NAME_VERSION_NAME = "version_name";
        public static final String COLUMN_NAME_PACKAGE_NAME = "package_name";
        public static final String COLUMN_NAME_TARGET_SDK_VERSION = "target_sdk_version";
    }

    public static abstract class TextDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "text_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_MIME_TYPE = "mime_type";
    }

    // todo: more tables here
}