package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import fi.hiit.whatisstoredinamobiledevice.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference genderSettings;
    private ListPreference ageSettings;
    private ListPreference frequencySettings;
    private ListPreference countrySettings;

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
        genderSettings = (ListPreference) findPreference("settings_user_gender");
        ageSettings = (ListPreference) findPreference("settings_user_age");
        frequencySettings = (ListPreference) findPreference("settings_data_sending_frequency");
        countrySettings = (ListPreference) findPreference("settings_user_country");
    }

    private void setCurrentValuesForSettingsSummaries() {
        setListPreferenceSummary(frequencySettings);
        setListPreferenceSummary(ageSettings);
        setListPreferenceSummary(genderSettings);
        setListPreferenceSummary(countrySettings);
    }

    private void setListPreferenceSummary(ListPreference lp) {
        lp.setSummary(lp.getEntry());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        setCurrentValuesForSettingsSummaries();
    }
}
