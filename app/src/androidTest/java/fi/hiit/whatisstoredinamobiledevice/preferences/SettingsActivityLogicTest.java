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

        String valueInSharedPrefs = sharedPrefs.getString(SettingsFragment.KEY_SETTINGS_USER_GENDER, "");
        String valueDefinedInXML = getActivity().getString(R.string.gender_male_value);

        assertTrue("Selecting gender male was not saved to shared preferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedAndValueNotFemale() {
        selectMale();

        String valueInSharedPrefs = sharedPrefs.getString(SettingsFragment.KEY_SETTINGS_USER_GENDER, "");
        String valueDefinedInXML = getActivity().getString(R.string.gender_female_value);

        assertFalse("Gender male was selected, but value was female in SharedPrefs", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testGenderSelectionFirstMaleThanChangeFemale() {
        // TODO
    }


    public void mockSelections(int categoryId, int optionId) {
        mua.selectOption(solo, getActivity(), categoryId, optionId);
    }

    public void selectMonthly() {
        mockSelections(R.string.settings_data_sending_frequency_title, R.string.frequency_monthly);
    }

    public void selectWeekly() {
        mockSelections(R.string.settings_data_sending_frequency_title, R.string.frequency_weekly);
    }

    public void selectFemale() {
        mockSelections(R.string.settings_gender_title, R.string.gender_female);
    }

    public void selectMale() {
        mockSelections(R.string.settings_gender_title, R.string.gender_male);
    }
}
