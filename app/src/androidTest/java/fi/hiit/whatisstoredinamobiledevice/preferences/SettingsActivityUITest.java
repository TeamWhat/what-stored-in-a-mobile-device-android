package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import com.robotium.solo.Solo;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockUserActions;

public class SettingsActivityUITest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private Solo solo;
    private MockUserActions mua;

    public SettingsActivityUITest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        solo = new Solo(getInstrumentation(), getActivity());
        mua = new MockUserActions();
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

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingFemale() {
        selectMale();
        assertFalse(checkTextDisplayed(R.string.gender_female));
        selectFemale();
        assertTrue(checkTextDisplayed(R.string.gender_female));
    }

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingMale() {
        selectFemale();
        assertFalse(checkTextDisplayed(R.string.gender_male));
        selectMale();
        assertTrue(checkTextDisplayed(R.string.gender_male));
    }

    @MediumTest
    public void testGenderOptionsCanOpen() {
        solo.clickOnText(getActivity().getString(R.string.settings_gender_title));
        assertTrue(checkTextDisplayed(R.string.gender_male));
        assertTrue(checkTextDisplayed(R.string.gender_female));
    }

    @MediumTest
    public void testSendCheckBoxIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_enable_data_sending_title));
    }

    @MediumTest
    public void testSendCheckBoxSavesSelectedOption() {
        assertFalse(solo.isCheckBoxChecked(0));
        solo.clickOnCheckBox(0);
        assertTrue(solo.isCheckBoxChecked(0));
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
