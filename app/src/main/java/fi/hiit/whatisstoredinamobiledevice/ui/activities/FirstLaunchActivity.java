package fi.hiit.whatisstoredinamobiledevice.ui.activities;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsFragment;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.Controls;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.ControlsPagerAdapter;


public class FirstLaunchActivity extends FragmentActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        mPager = (ViewPager) findViewById(R.id.first_time_screens);
        mPagerAdapter = new ControlsPagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Controls firstTimeSettingsControls = new Controls();
        SettingsFragment settingsFragment = new SettingsFragment();
        firstTimeSettingsControls.setPager(mPager);
        transaction.add(R.id.first_time_settings_controls, firstTimeSettingsControls);
        transaction.commit();
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
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void nextButtonPressed(View view) {
        mPager.setCurrentItem(1);
    }

    public void finishButtonPressed(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        Resources res = getResources();

        setGenderPreference(editor, res);
        setAgePreference(editor);
        setCountryPreference(editor, res);
        setEmailPreference(editor);
        setSendDataPreference(editor);
        setWifiPreference(editor);
        setFrequencyPreference(editor, res);

        //initialize counter on how many times data has been sent
        editor.putInt("data_send_count", 0);

        editor.commit();

        finish();
    }

    private void setGenderPreference(SharedPreferences.Editor editor, Resources res) {
        RadioGroup genderRadioGroup = (RadioGroup)findViewById(R.id.genderRadioGroup);
        int radioButtonID = genderRadioGroup.getCheckedRadioButtonId();
        View radioButton = genderRadioGroup.findViewById(radioButtonID);
        int idx = genderRadioGroup.indexOfChild(radioButton);

        if (radioButtonID == -1) {
            editor.putString(SettingsFragment.KEY_SETTINGS_USER_GENDER, "Not selected");
        }else {
            String[] genderValues = res.getStringArray(R.array.gender_array_values);
            editor.putString(SettingsFragment.KEY_SETTINGS_USER_GENDER, genderValues[idx]);
        }
    }

    private void setAgePreference(SharedPreferences.Editor editor) {
        Spinner ageSpinner = (Spinner)findViewById(R.id.ageSelectionSpinner);
        editor.putString(SettingsFragment.KEY_SETTINGS_USER_AGE, (String) ageSpinner.getSelectedItem());
    }

    private void setCountryPreference(SharedPreferences.Editor editor, Resources res) {
        Spinner countrySpinner = (Spinner)findViewById(R.id.countrySelectionSpinner);
        String[] countryValues = res.getStringArray(R.array.country_array_values);
        editor.putString(SettingsFragment.KEY_SETTINGS_USER_COUNTRY, countryValues[countrySpinner.getSelectedItemPosition()]);
    }

    private void setEmailPreference(SharedPreferences.Editor editor) {
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        editor.putString(SettingsFragment.KEY_SETTINGS_USER_EMAIL, emailEditText.getText().toString());
    }

    private void setSendDataPreference(SharedPreferences.Editor editor) {
        CheckBox sendDataCheckBox = (CheckBox) findViewById(R.id.sendDataCheckbox);
        editor.putBoolean(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING, sendDataCheckBox.isChecked());
    }

    private void setWifiPreference(SharedPreferences.Editor editor) {
        CheckBox sendDataOnWifiCheckBox = (CheckBox) findViewById(R.id.sendDataOnWifiCheckbox);
        editor.putBoolean(SettingsFragment.KEY_SETTINGS_ONLY_WIFI, sendDataOnWifiCheckBox.isChecked());
    }

    private void setFrequencyPreference(SharedPreferences.Editor editor, Resources res) {
        Spinner dataSendingFrequencySpinner = (Spinner) findViewById(R.id.dataSendingPeriodSelectionSpinner);
        String[] frequencyValues = res.getStringArray(R.array.frequency_array_values);
        editor.putString(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY, frequencyValues[dataSendingFrequencySpinner.getSelectedItemPosition()]);
    }

    public ViewPager getPager(){
        return mPager;
    }
}