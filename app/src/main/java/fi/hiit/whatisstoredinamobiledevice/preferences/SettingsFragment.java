package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.background_collecting.DataCollectionAlarmReceiver;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference genderSettings;
    private ListPreference ageSettings;
    private ListPreference frequencySettings;
    private ListPreference countrySettings;
    private EditTextPreference emailSettings;

    public static final String KEY_SETTINGS_USER_GENDER = "settings_user_gender";
    public static final String KEY_SETTINGS_USER_AGE = "settings_user_age";
    public static final String KEY_SETTINGS_USER_COUNTRY = "settings_user_country";
    public static final String KEY_SETTINGS_USER_EMAIL = "settings_user_email";

    public static final String KEY_SETTINGS_ENABLE_DATA_SENDING = "settings_enable_data_sending";
    public static final String KEY_SETTINGS_ONLY_WIFI = "settings_only_wifi";
    public static final String KEY_SETTINGS_DATA_SENDING_FREQUENCY = "settings_data_sending_frequency";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        setPreferenceItemsToFields();

        setCurrentValuesForSettingsSummaries();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setPreferenceItemsToFields() {
        genderSettings = (ListPreference) findPreference(KEY_SETTINGS_USER_GENDER);
        ageSettings = (ListPreference) findPreference(KEY_SETTINGS_USER_AGE);
        countrySettings = (ListPreference) findPreference(KEY_SETTINGS_USER_COUNTRY);
        frequencySettings = (ListPreference) findPreference(KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        emailSettings = (EditTextPreference) findPreference(KEY_SETTINGS_USER_EMAIL);
    }

    private void setCurrentValuesForSettingsSummaries() {
        setListPreferenceSummary(genderSettings);
        setListPreferenceSummary(ageSettings);
        setListPreferenceSummary(countrySettings);
        setListPreferenceSummary(frequencySettings);

        emailSettings.setSummary(emailSettings.getText());
    }

    private void setListPreferenceSummary(ListPreference lp) {
        lp.setSummary(lp.getEntry());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setCurrentValuesForSettingsSummaries();
        setDataSendingAlarmIfEnabled(sharedPreferences, key);
    }

    private void setDataSendingAlarmIfEnabled(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(KEY_SETTINGS_ENABLE_DATA_SENDING)){
           return;
        }
        Context context = getActivity().getApplicationContext();
        DataCollectionAlarmReceiver dcar = new DataCollectionAlarmReceiver();
        if(sharedPreferences.getBoolean(key, false)) {
        } else {
            dcar.cancelDataCollectionAlarm(context);
            dcar.setDataCollectionAlarm(context);
        }
    }

}
