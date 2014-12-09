package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class DeviceInfoCollector implements DataCollector {
    private Context mContext;

    public DeviceInfoCollector(Context context) {
        mContext = context;
    }

    /**
     * Column names used by the application's database
     */
    public final static String[] deviceInfoColumnNames = {
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_VERSION,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SCREEN_SIZE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_INTERNAL_FREE_SPACE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_INTERNAL_TOTAL_SPACE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_EXTERNAL_FREE_SPACE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_EXTERNAL_TOTAL_SPACE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SENT,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME
    };

    /**
     * Android column names that define what data is taken from MediaStore
     */
    @Override
    public Map<String, Map<String, String>> getData() {
        HashMap<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        HashMap<String, String> deviceInfoData = new HashMap<String, String>();
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND, Build.BRAND);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE, Build.DEVICE);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL, Build.MODEL);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT, Build.PRODUCT);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL, Build.SERIAL);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_VERSION, Build.VERSION.RELEASE);
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SCREEN_SIZE, screenSize());
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_INTERNAL_FREE_SPACE, freeInternalStorageSpace());
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_INTERNAL_TOTAL_SPACE, totalInternalStorageSpace());
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_EXTERNAL_FREE_SPACE, freeExternalStorageSpace());
        deviceInfoData.put(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_EXTERNAL_TOTAL_SPACE, totalExternalStorageSpace());

        // outer hashmap for consistency with other collectors
        data.put("0", deviceInfoData);
        return data;
    }

    /* Methods to calculate storage sizes (internal & external, free & total) */
    private String freeInternalStorageSpace() {
        long freeBytesInternal = internalStorage().getUsableSpace();
        return "" + freeBytesInternal;
    }

    private String totalInternalStorageSpace() {
        long totalBytesInternal = internalStorage().getTotalSpace();
        return "" + totalBytesInternal;
    }

    private String freeExternalStorageSpace() {
        long freeBytesExternal = externalStorage().getUsableSpace();
        return "" + freeBytesExternal;
    }

    private String totalExternalStorageSpace() {
        long totalBytesExternal = externalStorage().getTotalSpace();
        return "" + totalBytesExternal;
    }

    private File externalStorage() {
        return new File(mContext.getExternalFilesDir(null).toString());
    }

    private File internalStorage() {
        return new File(mContext.getFilesDir().getAbsoluteFile().toString());
    }

    /**
     * Calculates screen size in inches
     * @return
     */
    private String screenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        return "" + screenInches;
    }

    @Override
    public String getTableNameForData() {
        return DeviceDataContract.DeviceInfoEntry.TABLE_NAME;
    }
}
