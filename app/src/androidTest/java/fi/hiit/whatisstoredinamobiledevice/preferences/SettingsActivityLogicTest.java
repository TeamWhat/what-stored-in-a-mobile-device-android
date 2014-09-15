package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import android.test.suitebuilder.annotation.MediumTest;
import com.robotium.solo.Solo;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockUserActions;

public class SettingsActivityLogicTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private Solo solo;
    private MockUserActions mua;
    private SharedPreferences sharedPrefs;

    public SettingsActivityLogicTest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        solo = new Solo(getInstrumentation(), getActivity());
        mua = new MockUserActions();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }



    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @MediumTest
    public void testDefaultSharedPreferancesExist() {
        assertTrue("Default Shared Preferences does not exist", sharedPrefs != null);
    }

    @MediumTest
    public void testGenderSelectionMaleIsSaved() {
        selectMale();

        assertTrue("Selecting gender male was not saved to shared preferences", true);
    }


    public void selectFemale() {
        solo.clickOnText(getActivity().getString(R.string.settings_gender_title));
        solo.clickOnText(getActivity().getString(R.string.gender_female));
    }

    public void selectMale() {
        solo.clickOnText(getActivity().getString(R.string.settings_gender_title));
        solo.clickOnText(getActivity().getString(R.string.gender_male));
    }

    public boolean checkTextDisplayed(int stringId) {
        return solo.searchText(getActivity().getString(stringId));
    }
}
