package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class TextDataCollector extends MediaDataCollector {

    public TextDataCollector(Context context) { super(context); }

    public static final String[] textColumnNames =
            {
                DeviceDataContract.TextDataEntry.COLUMN_NAME_DATE_ADDED,
                DeviceDataContract.TextDataEntry.COLUMN_NAME_DATE_MODIFIED,
                DeviceDataContract.TextDataEntry.COLUMN_NAME_SIZE,
                DeviceDataContract.TextDataEntry.COLUMN_NAME_DATETIME
            };

    public static String[] projection =
            {
                    MediaStore.Files.FileColumns.DATE_ADDED,
                    MediaStore.Files.FileColumns.DATE_MODIFIED,
                    MediaStore.Files.FileColumns.SIZE
            };

    @Override
    protected Uri getUri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] getProjection() {
        return projection;
    }

    @Override
    protected String getSelection() {
        return MediaStore.Files.FileColumns.MIME_TYPE + "=?";
    }

    @Override
    protected String[] getSelectionArgs() {
        String textMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("text");
        return new String[]{ textMimeType };
    }

    @Override
    protected String getSortOrder() {
        return null;
    }

    @Override
    protected String[] getOwnColumnNames() {
        return textColumnNames;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.TextDataEntry.TABLE_NAME;
    }
}
