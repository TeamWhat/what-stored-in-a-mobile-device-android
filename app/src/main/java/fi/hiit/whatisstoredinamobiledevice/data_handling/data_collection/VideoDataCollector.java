package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class VideoDataCollector extends MediaDataCollector {

    public VideoDataCollector(Context context) { super(context); }

    /**
     * Column names used by the application's database
     */
    public static final String[] videoColumnNames =
            {
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_ALBUM,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_ARTIST,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_CATEGORY,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DESCRIPTION,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DURATION,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_IS_PRIVATE,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_LANGUAGE,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_LATITUDE,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_LONGITUDE,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_RESOLUTION,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_TAGS,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DATE_TAKEN,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DATE_ADDED,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DATE_MODIFIED,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_SIZE,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_SENT,
                    DeviceDataContract.VideoDataEntry.COLUMN_NAME_DATETIME
            };

    /**
     * Android column names that define what data is taken from MediaStore
     */
    public static String[] projection =
            {
                    MediaStore.Video.VideoColumns.ALBUM,
                    MediaStore.Video.VideoColumns.ARTIST,
                    MediaStore.Video.VideoColumns.CATEGORY,
                    MediaStore.Video.VideoColumns.DESCRIPTION,
                    MediaStore.Video.VideoColumns.DURATION,
                    MediaStore.Video.VideoColumns.IS_PRIVATE,
                    MediaStore.Video.VideoColumns.LANGUAGE,
                    MediaStore.Video.VideoColumns.LATITUDE,
                    MediaStore.Video.VideoColumns.LONGITUDE,
                    MediaStore.Video.VideoColumns.RESOLUTION,
                    MediaStore.Video.VideoColumns.TAGS,
                    MediaStore.Video.VideoColumns.DATE_TAKEN,
                    MediaStore.Video.VideoColumns.DATE_ADDED,
                    MediaStore.Video.VideoColumns.DATE_MODIFIED,
                    MediaStore.Video.VideoColumns.SIZE
            };

    @Override
    protected Uri getUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    protected String[] getProjection() {
        return projection;
    }

    @Override
    protected String getSelection() {
        return null;
    }

    @Override
    protected String[] getSelectionArgs() {
        return null;
    }

    @Override
    protected String getSortOrder() {
        return null;
    }

    @Override
    protected String[] getOwnColumnNames() {
        return videoColumnNames;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.VideoDataEntry.TABLE_NAME;
    }
}
