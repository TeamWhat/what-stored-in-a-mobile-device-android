package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;

public class DeviceData extends Activity implements DataResultReceiver.Receiver {
    private final static String[] deviceInfoProjection = {
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT,
            DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL
    };

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

    // todo: after deciding "real" UI layout, use data from this cursor to display device info
    private Cursor getDeviceInfoCursor(SQLiteDatabase db) {
        String sortOrder = DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME + " DESC";

        return initCursor(DeviceDataContract.DeviceInfoEntry.TABLE_NAME, db, deviceInfoProjection, sortOrder);
    }

    // todo: change textview to real UI layout
    public void queryDatabase() {
        TextView deviceDataText = (TextView)findViewById(R.id.deviceDataText);

        DeviceDataOpenHelper deviceDataOpenHelper = new DeviceDataOpenHelper(this);
        SQLiteDatabase db = deviceDataOpenHelper.getReadableDatabase();

        getDeviceInfoAndSetDeviceInfoTextField(deviceDataText, db);
    }

    // Temporary demo UI methods below

    private String getStringFromCursor(Cursor c, String columnName) {
        return c.getString(c.getColumnIndexOrThrow(columnName));
    }

    private void setTextViewText(TextView deviceDataText, Cursor c, Map textViewStrings) {
        String textViewText = buildTextViewText(textViewStrings);
        deviceDataText.setText(textViewText);
    }

    private String buildTextViewText(Map<String, String> textViewStrings) {
        String textViewText = "";
        for(String key : textViewStrings.keySet()) {
            textViewText += key + textViewStrings.get(key) + "\n";
        }
        return textViewText;
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

    private Map<String, String> getDeviceInfoTextViewStrings(Cursor c) {
        HashMap<String, String> textViewStrings = new HashMap<String, String>();

        String datetime = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME);
        String brand = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND);
        String device = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE);
        String model = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL);
        String product = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT);
        String serial = getStringFromCursor(c, DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL);

        textViewStrings.put("Date collected: ", datetime);
        textViewStrings.put("Brand: ", brand);
        textViewStrings.put("Device: ", device);
        textViewStrings.put("Model: ", model);
        textViewStrings.put("Product: ", product);
        textViewStrings.put("Serial: ", serial);

        return textViewStrings;
    }

    private void getDeviceInfoAndSetDeviceInfoTextField(TextView deviceDataText, SQLiteDatabase db) {
        Cursor deviceInfoCursor = getDeviceInfoCursor(db);

        Map<String, String> textViewStrings = getDeviceInfoTextViewStrings(deviceInfoCursor);
        setTextViewText(deviceDataText, deviceInfoCursor, textViewStrings);
    }
}
