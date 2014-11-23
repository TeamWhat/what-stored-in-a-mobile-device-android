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
        assertTrue(mua.checkTextDisplayed(R.string.title_activity_settings));
    }

    @MediumTest
    public void testPersonalInfoCategoryTitleIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_user_info_title));
    }

    @MediumTest
    public void testDataSendingCategoryTitleIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_data_sending_title));
    }

    @MediumTest
    public void testGenderTitleIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_gender_title));
    }

    @MediumTest
    public void testAgeTitleIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_age_title));
    }

    @MediumTest
    public void testCountryTitleIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_country_title));
    }

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingFemale() {
        mua.selectMale();
        assertFalse(mua.checkTextDisplayed(R.string.gender_female));
        mua.selectFemale();
        assertTrue(mua.checkTextDisplayed(R.string.gender_female));
    }

    @MediumTest
    public void testGenderSummaryIsShownAfterSelectingMale() {
        mua.selectFemale();
        assertFalse(mua.checkTextDisplayed(R.string.gender_male));
        mua.selectMale();
        assertTrue(mua.checkTextDisplayed(R.string.gender_male));
    }

    @MediumTest
    public void testCountrySummaryIsShownAfterSelectingSierraLeone() {
        mua.selectOption(R.string.settings_country_title, R.string.country_sierra_leone);
        assertTrue(mua.checkTextDisplayed(R.string.country_sierra_leone));
    }

    @MediumTest
    public void testGenderOptionsCanOpen() {
        solo.clickOnText(getActivity().getString(R.string.settings_gender_title));
        assertTrue(mua.checkTextDisplayed(R.string.gender_male));
        assertTrue(mua.checkTextDisplayed(R.string.gender_female));
    }

    @MediumTest
    public void testSendCheckBoxIsShown() {
        assertTrue(mua.checkTextDisplayed(R.string.settings_enable_data_sending_title));
    }

    @MediumTest
    public void testSendCheckBoxSavesSelectedOption() {
        mua.ensureCheckBoxNotChecked();
        assertFalse(solo.isCheckBoxChecked(0));
        solo.clickOnCheckBox(0);
        assertTrue(solo.isCheckBoxChecked(0));
    }


    @MediumTest
    public void testFrequencyUnavailableIfCheckBoxNotSelected() {
        mua.ensureCheckBoxNotChecked();
        solo.clickOnText(getActivity().getString(R.string.settings_data_sending_frequency_title));
        assertFalse(mua.checkTextDisplayed(R.string.frequency_daily) && mua.checkTextDisplayed(R.string.frequency_weekly) && mua.checkTextDisplayed(R.string.frequency_monthly));
    }

    @MediumTest
    public void testFrequencyAvailableIfCheckBoxIsSelected() {
        mua.ensureCheckBoxChecked();
        solo.clickOnText(getActivity().getString(R.string.settings_data_sending_frequency_title));
        assertTrue(mua.checkTextDisplayed(R.string.frequency_daily));
        assertTrue(mua.checkTextDisplayed(R.string.frequency_weekly));
        assertTrue(mua.checkTextDisplayed(R.string.frequency_monthly));
    }

    @MediumTest
    public void testFrequencySummaryIsShownAfterSelectingWeekly() {
        mua.ensureCheckBoxChecked();

        mua.selectMonthly();
        assertFalse(mua.checkTextDisplayed(R.string.frequency_weekly));

        mua.selectWeekly();
        assertTrue(mua.checkTextDisplayed(R.string.frequency_weekly));
    }

    @MediumTest
    public void testFrequencySummaryIsDailyByDefault() {
        assertTrue(mua.checkTextDisplayed(R.string.frequency_daily));
        mua.ensureCheckBoxChecked();
        assertTrue(mua.checkTextDisplayed(R.string.frequency_daily));
    }

}
