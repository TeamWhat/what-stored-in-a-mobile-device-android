package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class AudioDataCollector extends MediaDataCollector {
    public AudioDataCollector(Context appContext) {
        super(appContext);
    }

    public static final String[] audioColumnNames =
            {
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_ALBUM,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_ARTIST,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_COMPOSER,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_DURATION,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_YEAR,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATE_ADDED,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATE_MODIFIED,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_SIZE,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_IS_ALARM,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_IS_MUSIC,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_IS_NOTIFICATION,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_IS_PODCAST,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_IS_RINGTONE,
                    DeviceDataContract.AudioDataEntry.COLUMN_NAME_DATETIME
            };

    public static String[] projection =
            {
                    MediaStore.Audio.AudioColumns.ALBUM,
                    MediaStore.Audio.AudioColumns.ARTIST,
                    MediaStore.Audio.AudioColumns.COMPOSER,
                    MediaStore.Audio.AudioColumns.DURATION,
                    MediaStore.Audio.AudioColumns.YEAR,
                    MediaStore.Audio.AudioColumns.DATE_ADDED,
                    MediaStore.Audio.AudioColumns.DATE_MODIFIED,
                    MediaStore.Audio.AudioColumns.SIZE,
                    MediaStore.Audio.AudioColumns.IS_ALARM,
                    MediaStore.Audio.AudioColumns.IS_MUSIC,
                    MediaStore.Audio.AudioColumns.IS_NOTIFICATION,
                    MediaStore.Audio.AudioColumns.IS_PODCAST,
                    MediaStore.Audio.AudioColumns.IS_RINGTONE
            };

    @Override
    protected Uri getUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
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
        return audioColumnNames;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.AudioDataEntry.TABLE_NAME;
    }
}

