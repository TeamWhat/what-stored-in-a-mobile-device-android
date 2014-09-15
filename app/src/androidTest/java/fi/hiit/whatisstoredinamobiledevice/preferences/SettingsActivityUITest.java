package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import com.robotium.solo.Solo;

import fi.hiit.whatisstoredinamobiledevice.R;

public class SettingsActivityUITest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private Solo solo;

    public SettingsActivityUITest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @MediumTest
    public void testMainTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.title_activity_settings));
    }

    @MediumTest
    public void testPersonalInfoCategoryTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_user_info_title));
    }

    @MediumTest
    public void testDataSendingCategoryTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_data_sending_title));
    }

    @MediumTest
    public void testGenderTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_gender_title));
    }

    @MediumTest
    public void testAgeTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_age_title));
    }

    public boolean checkTextDisplayed(int stringId) {
        return solo.searchText(getActivity().getString(stringId));
    }
}
