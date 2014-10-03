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

    public HashMap<String, HashMap<String, String>> collectMediaData(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mCursor = mAppContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        putImageDataIntoHashMap(data);
        mCursor.close();
        return data;
    }

    private void putImageDataIntoHashMap(HashMap<String, HashMap<String, String>> data) {
        String[] cNames = mCursor.getColumnNames();
        int imageCounter = 0;
        while (mCursor.moveToNext()) {
            data.put("" + imageCounter, getSingleImageHashMap(cNames));
            imageCounter++;
        }
    }

    private HashMap<String, String> getSingleImageHashMap(String[] cNames) {
        HashMap<String, String> imageData = new HashMap<String, String>();
        for (int i=0; i<cNames.length; i++) {
            imageData.put(cNames[i], mCursor.getString(mCursor.getColumnIndex(cNames[i])));
        }
        return imageData;
    }
}
