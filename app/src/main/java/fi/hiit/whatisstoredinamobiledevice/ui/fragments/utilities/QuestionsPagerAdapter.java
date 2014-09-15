package fi.hiit.whatisstoredinamobiledevice.ui.fragments.utilities;

;

import android.app.Fragment;
import android.app.FragmentManager;

import fi.hiit.whatisstoredinamobiledevice.ui.fragments.Question1;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.Question2;

public class QuestionsPagerAdapter extends FragmentPagerAdapter {

    public QuestionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new Question1();
            case 1:
                return new Question2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
