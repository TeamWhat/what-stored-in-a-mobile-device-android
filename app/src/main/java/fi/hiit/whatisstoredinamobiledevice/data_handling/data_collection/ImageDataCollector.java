package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.provider.MediaStore;

import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class ImageDataCollector extends MediaDataCollector {
    public ImageDataCollector(Context appContext) {
        super(appContext);
    }
    public static final String[] imageColumnNames =
            {
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_TAKEN,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_IS_PRIVATE,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_LATITUDE,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_LONGITUDE,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATA_ADDED,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_SIZE,
                    DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_MODIFIED
            };

    @Override
    public Map getData() {
        String[] mProjection =
                {
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.IS_PRIVATE,
                        MediaStore.Images.ImageColumns.LATITUDE,
                        MediaStore.Images.ImageColumns.LONGITUDE,
                        MediaStore.Images.ImageColumns.DATE_ADDED,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED
                };
        return super.collectMediaData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mProjection, null, null, null, imageColumnNames);
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.ImageDataEntry.TABLE_NAME;
    }
}
