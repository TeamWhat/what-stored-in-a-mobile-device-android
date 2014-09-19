package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.viewpagerindicator.CirclePageIndicator;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsActivity;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.utilities.QuestionsPagerAdapter;

public class Questions extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CirclePageIndicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mPager = (ViewPager) findViewById(R.id.questions);
        mPagerAdapter = new QuestionsPagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mIndicator = (CirclePageIndicator)findViewById(R.id.circles_indicator);
        mIndicator.setViewPager(mPager);
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

    public ViewPager getPager(){
        return mPager;
    }

}
