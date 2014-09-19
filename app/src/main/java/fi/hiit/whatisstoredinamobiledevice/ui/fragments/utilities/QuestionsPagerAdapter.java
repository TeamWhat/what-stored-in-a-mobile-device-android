package fi.hiit.whatisstoredinamobiledevice.ui.fragments.utilities;

import android.app.Fragment;
import android.app.FragmentManager;

import fi.hiit.whatisstoredinamobiledevice.ui.fragments.PersonalQuestions;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.WelcomeScreen;

public class QuestionsPagerAdapter extends FragmentPagerAdapter {

    public QuestionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new WelcomeScreen();
            case 1:
                return new PersonalQuestions();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
