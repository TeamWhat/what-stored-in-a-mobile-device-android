package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import android.provider.BaseColumns;

public final class DeviceDataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DeviceDataContract() {}

    public final static String[] TABLE_NAMES = {
        DeviceInfoEntry.TABLE_NAME,
        ImageDataEntry.TABLE_NAME,
        AudioDataEntry.TABLE_NAME,
        VideoDataEntry.TABLE_NAME,
        ApplicationDataEntry.TABLE_NAME,
        TextDataEntry.TABLE_NAME
    };

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
        public static final String COLUMN_NAME_VERSION = "version";
        public static final String COLUMN_NAME_SCREEN_SIZE = "screen_size";
        public static final String COLUMN_NAME_INTERNAL_FREE_SPACE = "free_internal_space";
        public static final String COLUMN_NAME_INTERNAL_TOTAL_SPACE = "total_internal_space";
        public static final String COLUMN_NAME_EXTERNAL_FREE_SPACE = "free_external_space";
        public static final String COLUMN_NAME_EXTERNAL_TOTAL_SPACE = "total_external_space";
        public static final String COLUMN_NAME_SENT = "sent";
    }

    public static abstract class ImageDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "image_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_DATE_TAKEN = "date_taken";
        public static final String COLUMN_NAME_IS_PRIVATE = "is_private";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_SENT = "sent";
    }

    public static abstract class AudioDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "audio_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_ALBUM = "album";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_COMPOSER = "composer";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_IS_ALARM = "is_alarm";
        public static final String COLUMN_NAME_IS_MUSIC = "is_music";
        public static final String COLUMN_NAME_IS_NOTIFICATION = "is_notification";
        public static final String COLUMN_NAME_IS_PODCAST = "is_podcast";
        public static final String COLUMN_NAME_IS_RINGTONE = "is_ringtone";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_SENT = "sent";
    }

    public static abstract class VideoDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "video_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_ALBUM = "album";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_IS_PRIVATE = "is_private";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_RESOLUTION = "resolution";
        public static final String COLUMN_NAME_TAGS = "tags";
        public static final String COLUMN_NAME_DATE_TAKEN = "date_taken";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_SENT = "sent";
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
        public static final String COLUMN_NAME_SENT = "sent";
    }

    public static abstract class TextDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "text_data";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_MIME_TYPE = "mime_type";
        public static final String COLUMN_NAME_SENT = "sent";
    }

    // todo: more tables here
}