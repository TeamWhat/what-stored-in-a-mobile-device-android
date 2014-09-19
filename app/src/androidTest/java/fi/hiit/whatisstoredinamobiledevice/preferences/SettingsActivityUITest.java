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
        solo = new Solo(getInstrumentation(), getActivity());
        setActivityInitialTouchMode(true);
        mua = new MockUserActions(getActivity(), solo);
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
    public void testCountryTitleIsShown() {
        assertTrue(checkTextDisplayed(R.string.settings_country_title));
    }

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingFemale() {
        mua.selectMale();
        assertFalse(checkTextDisplayed(R.string.gender_female));
        mua.selectFemale();
        assertTrue(checkTextDisplayed(R.string.gender_female));
    }

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingMale() {
        mua.selectFemale();
        assertFalse(checkTextDisplayed(R.string.gender_male));
        mua.selectMale();
        assertTrue(checkTextDisplayed(R.string.gender_male));
    }

    @MediumTest
    public void testCountrySummaryIsShownAfterSelectingSierraLeone() {
        mua.selectOption(solo, getActivity(), R.string.settings_country_title, R.string.country_sierra_leone);
        assertTrue(checkTextDisplayed(R.string.country_sierra_leone));
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
        ensureCheckBoxNotChecked();
        assertFalse(solo.isCheckBoxChecked(0));
        solo.clickOnCheckBox(0);
        assertTrue(solo.isCheckBoxChecked(0));
    }


    @MediumTest
    public void testFrequencyUnavailableIfCheckBoxNotSelected() {
        ensureCheckBoxNotChecked();
        solo.clickOnText(getActivity().getString(R.string.settings_data_sending_frequency_title));
        assertFalse(checkTextDisplayed(R.string.frequency_daily) && checkTextDisplayed(R.string.frequency_daily) && checkTextDisplayed(R.string.frequency_monthly));
    }

    @MediumTest
    public void testFrequencyAvailableIfCheckBoxIsSelected() {
        ensureCheckBoxChecked();
        solo.clickOnText(getActivity().getString(R.string.settings_data_sending_frequency_title));
        assertTrue(checkTextDisplayed(R.string.frequency_daily));
        assertTrue(checkTextDisplayed(R.string.frequency_weekly));
        assertTrue(checkTextDisplayed(R.string.frequency_monthly));
    }

    @MediumTest
    public void testFrequencySummaryIsShownAfterSelectingWeekly() {
        if (!solo.isCheckBoxChecked(0)) {
            solo.clickOnCheckBox(0);

        }

        mua.selectMonthly();
        assertFalse(checkTextDisplayed(R.string.frequency_weekly));

        mua.selectWeekly();
        assertTrue(checkTextDisplayed(R.string.frequency_weekly));
    }

    public void ensureCheckBoxNotChecked() {
        if (solo.isCheckBoxChecked(0)) {
            solo.clickOnCheckBox(0);
        }
    }

    public void ensureCheckBoxChecked() {
        if (!solo.isCheckBoxChecked(0)) {
            solo.clickOnCheckBox(0);
        }
    }

    public boolean checkTextDisplayed(int stringId) {
        return solo.searchText(getActivity().getString(stringId));
    }
}
