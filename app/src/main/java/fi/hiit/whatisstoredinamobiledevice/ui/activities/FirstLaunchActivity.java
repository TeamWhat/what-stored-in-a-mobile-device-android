package fi.hiit.whatisstoredinamobiledevice.ui.activities;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

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
        mPager.setCurrentItem(0);
    }

    public ViewPager getPager(){
        return mPager;
    }
}