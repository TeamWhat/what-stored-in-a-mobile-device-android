package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import fi.hiit.whatisstoredinamobiledevice.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.DeviceInfoCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;

public class DeviceData extends Activity implements DataResultReceiver.Receiver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        Intent intent = new Intent(this, DataHandlerIntentService.class);
        DataResultReceiver receiver = new DataResultReceiver(new Handler());
        receiver.setReceiver(this);
        intent.putExtra("receiver", receiver);
        startService(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent selectedMenuOptionIntent = new Intent(this, SettingsActivity.class);
        startActivity(selectedMenuOptionIntent);
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult() {
        queryDatabase();
    }

    private Cursor getDeviceInfoCursor(SQLiteDatabase db) {
        return getDataCursor(db, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME, DeviceInfoCollector.deviceInfoColumnNames, DeviceDataContract.DeviceInfoEntry.TABLE_NAME);
    }

    private Cursor getImageInfoCursor(SQLiteDatabase db) {
        return getDataCursor(db, DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATETIME, ImageDataCollector.imageColumnNames, DeviceDataContract.ImageDataEntry.TABLE_NAME);
    }

    private Cursor getDataCursor(SQLiteDatabase db, String datetime, String[] columnNames, String tablename) {
        String sortOrder = datetime + " DESC";
        String[] finalColumnNames = initColumnNamesArray(columnNames, datetime);
        return initCursor(tablename, db, finalColumnNames, sortOrder);
    }

    private String[] initColumnNamesArray(String[] columnNames, String datetime) {
        // Change this if we add more universal columns (like datetime)
        String[] columnNamesWithUniversalColumns = new String[columnNames.length+1];
        for (int i = 0; i < columnNames.length; i++) {
            columnNamesWithUniversalColumns[i] = columnNames[i];
        }
        columnNamesWithUniversalColumns[columnNamesWithUniversalColumns.length-1] = datetime;
        return columnNamesWithUniversalColumns;
    }

    public Cursor initCursor(String tablename, SQLiteDatabase db, String[] projection, String sortOrder) {
        Cursor c = db.query(
                tablename,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        c.moveToFirst();
        return c;
    }

    // todo: change textview to real UI layout
    public void queryDatabase() {
        TextView deviceDataText = (TextView)findViewById(R.id.deviceDataText);
        deviceDataText.setMovementMethod(new ScrollingMovementMethod());
        DeviceDataOpenHelper deviceDataOpenHelper = new DeviceDataOpenHelper(this);
        SQLiteDatabase db = deviceDataOpenHelper.getReadableDatabase();

        getDeviceDataAndSetDeviceDataTextField(deviceDataText, db);
    }

    // Temporary demo UI methods below

    private String getStringFromCursor(Cursor c, String columnName) {
        return c.getString(c.getColumnIndexOrThrow(columnName));
    }

    private void setTextViewText(TextView deviceDataText, Map textViewStrings) {
        String textViewText = buildTextViewText(textViewStrings);
        deviceDataText.setText(textViewText);
    }

    private String buildTextViewText(Map<String, String> textViewStrings) {
        String textViewText = "";
        for(String key : textViewStrings.keySet()) {
            textViewText += textViewStrings.get(key) + "\n";
        }
        return textViewText;
    }

    private void getDeviceDataAndSetDeviceDataTextField(TextView deviceDataText, SQLiteDatabase db) {
        Cursor deviceInfoCursor = getDeviceInfoCursor(db);
        Map<String, String> deviceInfoTextViewStrings = getDeviceInfoTextViewStrings(deviceInfoCursor);
        deviceInfoCursor.close();

        Cursor imageInfoCursor = getImageInfoCursor(db);
        Map<String, String> imageTextViewStrings = getImageInfoTextViewStrings(imageInfoCursor);
        imageInfoCursor.close();

        Map<String, String> textViewStrings = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] parts1 = s1.split("-");
                String[] parts2 = s2.split("-");
                if (!parts1[0].equals(parts2[0])) {
                    return parts1[0].compareTo(parts2[0]);
                }
                return Integer.parseInt(parts1[1]) - Integer.parseInt(parts2[1]);
            }
        });
        textViewStrings.putAll(deviceInfoTextViewStrings);
        textViewStrings.putAll(imageTextViewStrings);

        setTextViewText(deviceDataText, textViewStrings);
    }

    private Map<String, String> getImageInfoTextViewStrings(Cursor c) {
        HashMap<String, String> textViewStrings = new HashMap<String, String>();

        int hashMapKey = 0;
        while (c.moveToNext()) {
            String datetime = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATETIME);
            String dateModified = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_MODIFIED);
            String size = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_SIZE);
            String dateAdded = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATA_ADDED);
            String dateTaken = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_DATE_TAKEN);
            String isPrivate = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_IS_PRIVATE);
            String latitude = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_LATITUDE);
            String longitude = getStringFromCursor(c, DeviceDataContract.ImageDataEntry.COLUMN_NAME_LONGITUDE);

            textViewStrings.put("img-" + hashMapKey++, "Date collected: " + datetime);
            textViewStrings.put("img-" + hashMapKey++, "Size: " + Long.parseLong(size)/1000 + " kB");
            textViewStrings.put("img-" + hashMapKey++, "Date modified: " + new Date(Long.parseLong(dateModified) * 1000).toString());
            textViewStrings.put("img-" + hashMapKey++, "Date added: " + new Date(Long.parseLong(dateAdded) * 1000).toString());
            textViewStrings.put("img-" + hashMapKey++, "Date taken: " + new Date(Long.parseLong(dateTaken)).toString());
            textViewStrings.put("img-" + hashMapKey++, "Is private: " + isPrivate);
            textViewStrings.put("img-" + hashMapKey++, "Latitude: " + latitude);
            textViewStrings.put("img-" + hashMapKey++, "Longitude: " + longitude);
            textViewStrings.put("img-" + hashMapKey++, "");
            hashMapKey++;
        }

        return textViewStrings;
    }

    private Map<String, String> getDeviceInfoTextViewStrings(Cursor c) {
        HashMap<String, String> textViewStrings = new HashMap<String, String>();

        String datetime = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME);
        String brand = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND);
        String device = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE);
        String model = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL);
        String product = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT);
        String serial = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL);

        int hashMapKey = 0;
        textViewStrings.put("device-" + hashMapKey++, "Date collected: " + datetime);
        textViewStrings.put("device-" + hashMapKey++, "Brand: " + brand);
        textViewStrings.put("device-" + hashMapKey++, "Device: " + device);
        textViewStrings.put("device-" + hashMapKey++, "Model: " + model);
        textViewStrings.put("device-" + hashMapKey++, "Product: " + product);
        textViewStrings.put("device-" + hashMapKey++, "Serial: " + serial);
        textViewStrings.put("device-" + hashMapKey++, "");

        return textViewStrings;
    }
}
