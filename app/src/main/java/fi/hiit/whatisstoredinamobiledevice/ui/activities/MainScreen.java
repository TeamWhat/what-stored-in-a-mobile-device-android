package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

    public static final String KEY_DATA_SEND_COUNT = "data_send_count";
    public static final String KEY_FIRST_START = "first_start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstTimeSettings();
        setContentView(R.layout.activity_main_screen);
        getActionBar().setIcon(android.R.color.transparent);

        mSendDataProgressBar = (ProgressBar) findViewById(R.id.main_screen_send_data_progress_bar);
        mSendDataProgressBar.setVisibility(View.INVISIBLE);

        mJSONPackager = new JSONPackager(getApplicationContext());
        mHttpPOSTHandler = new HttpPostHandler(getApplicationContext(), new HurlStack());
        mHttpPOSTHandler.setMainScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataSendCounter();
    }

    public void setDataSendCounter() {
        TextView dataSendCounterTextView = (TextView) findViewById(R.id.data_send_counter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int dataSendCounter = sharedPreferences.getInt(KEY_DATA_SEND_COUNT, 0);

        if (dataSendCounter == 0) {
            dataSendCounterTextView.setText(getString(R.string.main_screen_data_not_sent));
        }else if (dataSendCounter == 1) {
            dataSendCounterTextView.setText(getString(R.string.main_screen_data_sent_once));
        }else {
            dataSendCounterTextView.setText("Data has been sent " + String.valueOf(dataSendCounter) + " times");
        }

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

    private void firstTimeSettings() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(pref.getBoolean(KEY_FIRST_START, true)) {
            SharedPreferences.Editor editor = pref.edit();

            // Set that the first time settings are not prompted anymore
            editor.putBoolean(KEY_FIRST_START, false);
            editor.commit();

            Intent intent = new Intent(this, FirstLaunchActivity.class);
            startActivity(intent);
        }
    }

    public void collectAndSendDataToServer(View view) {
        findViewById(R.id.main_screen_send_data_button).setEnabled(false);
        mSendDataProgressBar.setVisibility(View.VISIBLE);
        startDataCollectionIntent();
    }

    public void showGraphs(View view) {
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    @Override
    public void onReceiveDataCollectionResult() {
        JSONObject collectedDataJSON = mJSONPackager.createJsonObjectFromStoredData();
        mHttpPOSTHandler.postJSON(collectedDataJSON, null);

        // Buttons are enabled when http request is started, not when response is received
        mSendDataProgressBar.setVisibility(View.INVISIBLE);
        findViewById(R.id.main_screen_send_data_button).setEnabled(true);
        setDataSendCounter();
    }
}
