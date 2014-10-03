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

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedWhenSelectedTwice() {
        mMua.selectMale();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        mMua.selectMale();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedAndValueNotFemale() {
        mMua.selectMale();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        assertFalse("Gender male was selected, but value was female in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testGenderSelectionFirstMaleThenChangeFemale() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        mMua.selectMale();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        mMua.selectFemale();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Changing gender selection to female was not saved to SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }


    @MediumTest
    public void testAgeSettingDefaultValueCorrect() {
        assertEquals("Age default value was not an empty string", getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE));
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSaved() {
        mMua.selectUnder18();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedWhenSelectedTwice() {
        mMua.selectUnder18();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        mMua.selectUnder18();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedAndValueNotOver35() {
        mMua.selectUnder18();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35_value);

        assertFalse("Age Under 18 was selected, but Over 35 was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testAgeSelectionFirstUnder18ThenChangeOver35() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35_value);

        mMua.selectUnder18();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        mMua.selectOver35();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Changing age selection to Over 35 was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }


    @MediumTest
    public void testCountrySettingDefaultValueCorrect() {
        assertEquals("Country default value was not an empty string", getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY));
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSaved() {
        mMua.selectTheBahamas();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedWhenSelectedTwice() {
        mMua.selectTheBahamas();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        mMua.selectTheBahamas();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedAndValueNotAlbania() {
        mMua.selectTheBahamas();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        assertFalse("Country The Bahamas was selected, but Albania was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testCountrySelectionFirstTheBahamasThenChangeAlbania() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        mMua.selectTheBahamas();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        mMua.selectAlbania();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Changing country selection to Albania was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }


    @MediumTest
    public void testDataSendingIsDisabledByDefault() {
        assertFalse("Data sending was not disabled by default (value not false in SharedPreferences)", mSharedPrefs.getBoolean(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING, true));
    }

    @MediumTest
    public void testDataSendingEnabledOptionIsSaved() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        assertTrue("SharedPreferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }

    @MediumTest
    public void testDataSendingDisabledOptionIsSaved() {
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();

        assertFalse("SharedPreferences boolean value for enable data sending is not false even though checkbox is not checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }

    @MediumTest
    public void testDataSendingFirstEnabledThanDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        assertTrue("SharedPreferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));

        clickCheckBoxAndSetSharedPrefsMutex();

        assertFalse("SharedPreferences boolean value for enable data sending is not false even though checkbox was clicked after being checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }

    @MediumTest
    public void testDataSendingCheckBoxCheckedWhenDataSendingEnabled(){
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        assertEquals("Check box state did not correspond to the value in SharedPreferences",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING), mSolo.isCheckBoxChecked(0)); // todo: Assumes only check box in view
    }

    @MediumTest
    public void testDataSendingCheckBoxUncheckedWhenDataSendingDisabled(){
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();
        assertTrue("Check box state did not correspond to the value in SharedPreferences",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING) == mSolo.isCheckBoxChecked(0)); // todo: Assumes only check box in view
    }


    @MediumTest
    public void testDataSendingFrequencySettingDefaultValueCorrect() {
        assertEquals("Data sending frequency default value was not an empty string",
                getStringValueDefinedInXML(R.string.empty_string), getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY));
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        mMua.selectMonthly();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenSelectedTwiceAndDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        mMua.selectMonthly();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);


        mMua.selectMonthly();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Selecting data sending frequency Monthly is not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedAndValueNotWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        mMua.selectMonthly();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertFalse("Data sending frequency Monthly was selected, but value Weekly was saved in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testDataSendingFrequencySelectionFirstMonthlyThenChangedToWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        mMua.selectMonthly();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Value was not Monthly in SharedPreferences after selecting Monthly", firstValueDefinedInXML, valueInSharedPrefs);

        mMua.selectWeekly();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Value was not Weekly in SharedPreferences after changing selection from Monthly to Weekly", secondValueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testDataSendingFrequencySelectionCannotBeChangedWhenDataSendingDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        mMua.selectWeekly();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String frequencyValueSetWhenDataSendingEnabled = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertEquals("Selecting data sending frequency Weekly is not saved in SharedPreferences", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefs);

        tryChangingDataSendingFreqToMonthlyWhenDataSendingDisabled();
        String valueInSharedPrefsAfterDataSendingDisabled = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Data sending frequency was changed in SharedPreferences even though data sending was disabled", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefsAfterDataSendingDisabled);

    }


    private void tryChangingDataSendingFreqToMonthlyWhenDataSendingDisabled() {
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();

        mSolo.clickOnText(getStringValueDefinedInXML(R.string.settings_data_sending_frequency_title));
        if (mMua.checkTextDisplayed(R.string.frequency_monthly)) {
            mMua.selectMonthly();
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
