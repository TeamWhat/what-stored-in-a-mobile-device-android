package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import fi.hiit.whatisstoredinamobiledevice.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference genderSetting;

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
        genderSetting = (ListPreference) findPreference("settings_user_gender");
    }

    private void setCurrentValuesForSettingsSummaries() {
        setGenderSettingSummary();
    }

    private void setGenderSettingSummary() {
        genderSetting.setSummary(genderSetting.getEntry());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        setCurrentValuesForSettingsSummaries();
    }
}
