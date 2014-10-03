package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;

public abstract class MediaDataCollector implements DataCollector {
    private Context mAppContext;
    private Cursor mCursor;

    public MediaDataCollector(Context appContext) {
        this.mAppContext = appContext;
    }

    public HashMap<String, HashMap<String, String>> collectMediaData(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, String[] ownColumnNames) {
        mCursor = mAppContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        putImageDataIntoHashMap(data, ownColumnNames);
        mCursor.close();
        return data;
    }

    private void putImageDataIntoHashMap(HashMap<String, HashMap<String, String>> data, String[] ownColumnNames) {
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
