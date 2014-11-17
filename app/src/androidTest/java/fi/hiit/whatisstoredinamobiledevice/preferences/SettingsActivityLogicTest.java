package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;


import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockUserActions;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.SharedPreferencesMutexHelper;

public class SettingsActivityLogicTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private Solo mSolo;
    private MockUserActions mMua;
    private SharedPreferences mSharedPrefs;
    private SharedPreferencesMutexHelper mSharedPrefsMH;

    public SettingsActivityLogicTest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        setUpSharedPrefsAndItsTools();
        super.setUp();
        setActivityInitialTouchMode(true);
        mSolo = new Solo(getInstrumentation(), getActivity());
        mMua = new MockUserActions(getActivity(), mSolo);
    }

    private void setUpSharedPrefsAndItsTools() {
        setUpSharedPrefs();
        mSharedPrefsMH = new SharedPreferencesMutexHelper(mSharedPrefs);
    }

    private void setUpSharedPrefs() {
        Context context = getInstrumentation().getTargetContext();
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        clearSharedPreferences();
        PreferenceManager.setDefaultValues(context, R.xml.preferences, true);
    }

    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
        super.tearDown();
    }



    @SmallTest
    public void testDefaultSharedPreferencesExist() {
        assertTrue("Default SharedPreferences does not exist", mSharedPrefs != null);
    }


    @MediumTest
    public void testGenderSettingDefaultValueCorrect() {
        assertEquals("Gender default value was not an empty string", getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER));
    }

    @MediumTest
    public void testGenderSelectionMaleIsSaved() {
        mMua.selectMale();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedWhenSelectedTwice() {
        mMua.selectMale();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectMale();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedAndValueNotFemale() {
        mMua.selectMale();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        assertFalse("Gender male was selected, but value was female in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testGenderSelectionFirstMaleThenChangeFemale() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        mMua.selectMale();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectFemale();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Changing gender selection to female was not saved to SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }


    @MediumTest
    public void testAgeSettingDefaultValueCorrect() {
        assertEquals("Age default value was not an empty string", getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE));
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSaved() {
        mMua.selectUnder18();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedWhenSelectedTwice() {
        mMua.selectUnder18();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectUnder18();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedAndValueNotOver35() {
        mMua.selectUnder18();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35);

        assertFalse("Age Under 18 was selected, but Over 35 was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testAgeSelectionFirstUnder18ThenChangeOver35() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35);

        mMua.selectUnder18();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectOver35();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Changing age selection to Over 35 was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }


    @MediumTest
    public void testCountrySettingDefaultValueCorrect() {
        assertEquals("Country default value was not an empty string", getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY));
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSaved() {
        mMua.selectTheBahamas();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedWhenSelectedTwice() {
        mMua.selectTheBahamas();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectTheBahamas();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedAndValueNotAlbania() {
        mMua.selectTheBahamas();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        assertFalse("Country The Bahamas was selected, but Albania was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testCountrySelectionFirstTheBahamasThenChangeAlbania() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        mMua.selectTheBahamas();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectAlbania();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Changing country selection to Albania was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }


    @MediumTest
    public void testDataSendingIsDisabledByDefault() {
        assertFalse("Data sending was not disabled by default (value not false in SharedPreferences)", mSharedPrefs.getBoolean(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING, true));
    }

    @MediumTest
    public void testDataSendingEnabledOptionIsSaved() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        assertTrue("SharedPreferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingDisabledOptionIsSaved() {
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        assertFalse("SharedPreferences boolean value for enable data sending is not false even though checkbox is not checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingFirstEnabledThanDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        assertTrue("SharedPreferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
        getInstrumentation().waitForIdleSync();

        clickCheckBoxAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        assertFalse("SharedPreferences boolean value for enable data sending is not false even though checkbox was clicked after being checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingCheckBoxCheckedWhenDataSendingEnabled(){
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();
        assertEquals("Check box state did not correspond to the value in SharedPreferences",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING), mSolo.isCheckBoxChecked(0)); // todo: Assumes only check box in view
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingCheckBoxUncheckedWhenDataSendingDisabled(){
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();
        assertTrue("Check box state did not correspond to the value in SharedPreferences",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING) == mSolo.isCheckBoxChecked(0)); // todo: Assumes only check box in view
        getInstrumentation().waitForIdleSync();
    }


    @MediumTest
    public void testDataSendingFrequencySettingDefaultValueCorrect() {
        assertEquals("Data sending frequency default value was not an empty string",
                getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY));
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();
        mMua.selectMonthly();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenSelectedTwiceAndDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        mMua.selectMonthly();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();


        mMua.selectMonthly();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedAndValueNotWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();
        mMua.selectMonthly();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertFalse("Data sending frequency Monthly was selected, but value Weekly was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingFrequencySelectionFirstMonthlyThenChangedToWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        mMua.selectMonthly();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        assertEquals("Value was not Monthly in SharedPreferences after selecting Monthly", firstValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        mMua.selectWeekly();
        getInstrumentation().waitForIdleSync();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Value was not Weekly in SharedPreferences after changing selection from Monthly to Weekly", secondValueDefinedInXML, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();
    }

    @MediumTest
    public void testDataSendingFrequencySelectionCannotBeChangedWhenDataSendingDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        mMua.selectWeekly();
        getInstrumentation().waitForIdleSync();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String frequencyValueSetWhenDataSendingEnabled = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertEquals("Selecting data sending frequency Weekly is not saved in SharedPreferences", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefs);
        getInstrumentation().waitForIdleSync();

        tryChangingDataSendingFreqToMonthlyWhenDataSendingDisabled();
        getInstrumentation().waitForIdleSync();
        String valueInSharedPrefsAfterDataSendingDisabled = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Data sending frequency was changed in SharedPreferences even though data sending was disabled", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefsAfterDataSendingDisabled);
        getInstrumentation().waitForIdleSync();

    }


    private void tryChangingDataSendingFreqToMonthlyWhenDataSendingDisabled() {
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();
        getInstrumentation().waitForIdleSync();

        mSolo.clickOnText(getStringValueDefinedInXML(R.string.settings_data_sending_frequency_title));
        getInstrumentation().waitForIdleSync();
        if (mMua.checkTextDisplayed(R.string.frequency_monthly)) {
            mMua.selectMonthly();
            getInstrumentation().waitForIdleSync();
        }
    }

    private boolean getSharedPrefsBoolValue(String key) {
        mSolo.waitForCondition(mSharedPrefsMH.isSharedPrefsChangeCommitted(), 5000);
        return mSharedPrefs.getBoolean(key, false);
    }

    private String getSharedPrefsStringValue(String key) {
        return mSharedPrefs.getString(key, "String not found in SharedPreferences");
    }

    private String getStringValueDefinedInXML(int stringId) {
        return getActivity().getString(stringId);
    }

    private void ensureCheckBoxCheckedAndSetSharedPrefsMutex() {
        mSharedPrefsMH.setSharedPrefsChangeCommittedMutex(false);
        mMua.ensureCheckBoxChecked();
    }

    private void ensureCheckBoxNotCheckedAndSetSharedPrefsMutex() {
        mSharedPrefsMH.setSharedPrefsChangeCommittedMutex(false);
        mMua.ensureCheckBoxNotChecked();
    }

    private void clickCheckBoxAndSetSharedPrefsMutex() {
        mSharedPrefsMH.setSharedPrefsChangeCommittedMutex(false);
        mMua.clickOnCheckBoxText();
    }

}
