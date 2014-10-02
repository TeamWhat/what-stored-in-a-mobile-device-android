package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

import fi.hiit.whatisstoredinamobiledevice.R;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
