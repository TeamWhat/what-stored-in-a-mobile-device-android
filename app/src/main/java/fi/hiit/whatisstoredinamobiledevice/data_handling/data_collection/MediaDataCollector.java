package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/*
 * Collectors use Template method design pattern
 * It defers some methods to subclasses
 */
public abstract class MediaDataCollector implements DataCollector {
    private Context mAppContext;
    private Cursor mCursor;

    public MediaDataCollector(Context appContext) {
        this.mAppContext = appContext;
    }

    public Map<String, Map<String, String>> getData() {
        mCursor = getCursor();
        Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        putDataIntoHashMap(data, getOwnColumnNames());
        mCursor.close();
        return data;
    }

    public Cursor getCursor() {
        return mAppContext.getContentResolver().query(
                getUri(),
                getProjection(),
                getSelection(),
                getSelectionArgs(),
                getSortOrder());
    }

    private void putDataIntoHashMap(Map<String, Map<String, String>> data, String[] ownColumnNames) {
        String[] androidColumnNames = mCursor.getColumnNames();
        int dataCounter = 0;
        while (mCursor.moveToNext()) {
            data.put("" + dataCounter, getSingleObjectHashMap(androidColumnNames, ownColumnNames));
            dataCounter++;
        }
    }

    private HashMap<String, String> getSingleObjectHashMap(String[] androidColumnNames, String[] ownColumnNames) {
        HashMap<String, String> data = new HashMap<String, String>();
        for (int i=0; i<androidColumnNames.length; i++) {
            data.put(ownColumnNames[i], mCursor.getString(mCursor.getColumnIndex(androidColumnNames[i])));
        }
        return data;
    }

    /*
     * These methods define what kind of data is collected
     * Subclasses must implement them
     */
    protected abstract Uri getUri();
    protected abstract String[] getProjection();
    protected abstract String getSelection();
    protected abstract String[] getSelectionArgs();
    protected abstract String getSortOrder();
    protected abstract String[] getOwnColumnNames();
}
