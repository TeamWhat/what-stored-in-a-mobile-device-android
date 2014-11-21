package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.UniqueIdentifier;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;

public class MainScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        TextView t = (TextView)findViewById(R.id.uid);
        t.setText("Unique identifier: " + new UniqueIdentifier(this).identifier());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        Intent selectedMenuOptionIntent = null;

        switch (id) {
            case R.id.action_settings: {
                selectedMenuOptionIntent = new Intent(this, SettingsActivity.class);
                break;
            } case R.id.action_about: {
                selectedMenuOptionIntent = new Intent(this, About.class);
            }
        }
        startActivity(selectedMenuOptionIntent);
        return super.onOptionsItemSelected(item);
    }

    public void openQuestions(View view) {
        Intent intent = new Intent(this, Questions.class);
        startActivity(intent);
    }

    public void openDeviceData(View view) {
        Intent intent = new Intent(this, DeviceData.class);
        startActivity(intent);
    }

    public void openNetworkConnectionDemo(View view) {
        Intent intent = new Intent(this, NetworkConnection.class);
        startActivity(intent);
    }
}
