package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class MusicDataCollector extends MediaDataCollector {
    public MusicDataCollector(Context appContext) {
        super(appContext);
    }

    public static final String[] musicColumnNames =
            {
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_DATETIME,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_ALBUM,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_ARTIST,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_COMPOSER,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_DURATION,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_YEAR,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_DATE_ADDED,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_DATE_MODIFIED,
                    DeviceDataContract.MusicDataEntry.COLUMN_NAME_SIZE
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
                    MediaStore.Audio.AudioColumns.SIZE
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
        return musicColumnNames;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.MusicDataEntry.TABLE_NAME;
    }
}

