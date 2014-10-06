package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public abstract class MediaDataCollector implements DataCollector {
    private Context mAppContext;
    private Cursor mCursor;

    public MediaDataCollector(Context appContext) {
        this.mAppContext = appContext;
    }

    public Map<String, Map<String, String>> getData() {
        mCursor = mAppContext.getContentResolver().query(getUri(), getProjection(), getSelection(), getSelectionArgs(), getSortOrder());
        Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        putImageDataIntoHashMap(data, getOwnColumnNames());
        mCursor.close();
        return data;
    }

    protected abstract Uri getUri();
    protected abstract String[] getProjection();
    protected abstract String getSelection();
    protected abstract String[] getSelectionArgs();
    protected abstract String getSortOrder();
    protected abstract String[] getOwnColumnNames();

    private void putImageDataIntoHashMap(Map<String, Map<String, String>> data, String[] ownColumnNames) {
        String[] androidColumnNames = mCursor.getColumnNames();
        int imageCounter = 0;
        while (mCursor.moveToNext()) {
            data.put("" + imageCounter, getSingleImageHashMap(androidColumnNames, ownColumnNames));
            imageCounter++;
        }
    }

    private HashMap<String, String> getSingleImageHashMap(String[] androidColumnNames, String[] ownColumnNames) {
        HashMap<String, String> imageData = new HashMap<String, String>();
        for (int i=0; i<androidColumnNames.length; i++) {
            imageData.put(ownColumnNames[i], mCursor.getString(mCursor.getColumnIndex(androidColumnNames[i])));
        }
        return imageData;
    }
}
