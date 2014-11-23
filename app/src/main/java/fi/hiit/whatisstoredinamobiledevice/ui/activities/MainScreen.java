package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

import fi.hiit.whatisstoredinamobiledevice.data_handling.DataResultReceiver;
import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandlerIntentService;
import fi.hiit.whatisstoredinamobiledevice.data_handling.JSON.JSONPackager;
import fi.hiit.whatisstoredinamobiledevice.network.HttpPostHandler;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;

public class MainScreen extends Activity implements DataResultReceiver.Receiver {

    private JSONPackager mJSONPackager;
    private HttpPostHandler mHttpPOSTHandler;
    private ProgressBar mSendDataProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mSendDataProgressBar = (ProgressBar) findViewById(R.id.main_screen_send_data_progress_bar);
        mSendDataProgressBar.setVisibility(View.INVISIBLE);

        mJSONPackager = new JSONPackager(getApplicationContext());
        mHttpPOSTHandler = new HttpPostHandler(getApplicationContext(), new HurlStack());
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

    private void startDataCollectionIntent() {
        Intent intent = new Intent(this, DataHandlerIntentService.class);
        DataResultReceiver receiver = new DataResultReceiver(new Handler());
        receiver.setReceiver(this);
        intent.putExtra("receiver", receiver);
        startService(intent);
    }



    public void collectAndSendDataToServer(View view) {
        findViewById(R.id.main_screen_send_data_button).setEnabled(false);
        mSendDataProgressBar.setVisibility(View.VISIBLE);
        startDataCollectionIntent();
    }

    @Override
    public void onReceiveResult() {
        JSONObject collectedDataJSON = mJSONPackager.createJsonObjectFromStoredData();
        mHttpPOSTHandler.postJSON(collectedDataJSON);

        // Buttons are enabled when http request is started, not when response is received
        mSendDataProgressBar.setVisibility(View.INVISIBLE);
        findViewById(R.id.main_screen_send_data_button).setEnabled(true);
    }
}
