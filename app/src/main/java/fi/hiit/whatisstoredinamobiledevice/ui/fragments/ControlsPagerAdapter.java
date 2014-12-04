package fi.hiit.whatisstoredinamobiledevice.ui.fragments;
import android.app.Fragment;
import android.app.FragmentManager;

import fi.hiit.whatisstoredinamobiledevice.ui.fragments.utilities.FragmentPagerAdapter;


public class ControlsPagerAdapter extends FragmentPagerAdapter {
    public ControlsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new AboutFragment();
            case 1:
                return new FirstTimeSettingsFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}
