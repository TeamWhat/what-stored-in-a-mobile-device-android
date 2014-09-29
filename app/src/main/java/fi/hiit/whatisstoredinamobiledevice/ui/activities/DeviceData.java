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


import fi.hiit.whatisstoredinamobiledevice.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
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

    public void queryDatabase() {
        // todo: refactor query
        TextView deviceDataText = new TextView(this);
        deviceDataText = (TextView)findViewById(R.id.deviceDataText);

        DeviceDataOpenHelper deviceDataOpenHelper = new DeviceDataOpenHelper(this);
        SQLiteDatabase db = deviceDataOpenHelper.getReadableDatabase();

        String[] projection = {
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT,
                DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL
        };

        String sortOrder = DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME + " DESC";

        Cursor c = db.query(
                DeviceDataContract.DeviceInfoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        c.moveToFirst();
        String datetime = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DATETIME));
        String brand = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND));
        String device = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE));
        String model = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL));
        String product = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT));
        String serial = c.getString(c.getColumnIndexOrThrow(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL));

        deviceDataText.setText(
                "Date collected: " + datetime + "\n" +
                "Brand: " + brand + "\n" +
                "Device: " + device + "\n" +
                "Model: " + model + "\n" +
                "Product: " + product + "\n" +
                "Serial: " + serial + "\n"
        );
    }

    @Override
    public void onReceiveResult() {
        queryDatabase();
    }
}
