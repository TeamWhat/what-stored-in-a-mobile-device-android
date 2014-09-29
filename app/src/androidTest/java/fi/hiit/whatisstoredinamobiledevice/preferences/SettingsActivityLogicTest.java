package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;


import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockUserActions;

public class SettingsActivityLogicTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private Solo solo;
    private MockUserActions mua;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Condition isSharedPrefsChangeCommited;
    private boolean isSharedPrefsChangeCommitedMutex;

    public SettingsActivityLogicTest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        solo = new Solo(getInstrumentation(), getActivity());
        mua = new MockUserActions(getActivity(), solo);

        // TODO: Muuta sharedPrefs listener/mutex systeemi omaksi helper-luokaksi
        setUpSharedPrefsAndItsTools();
    }

    private void setUpSharedPrefsAndItsTools() {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        setUpSharedPrefsChangeMutex();
        setUpSharedPrefsListener();
    }

    private void setUpSharedPrefsChangeMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        isSharedPrefsChangeCommited = new Condition() {
            @Override
            public boolean isSatisfied() {
                return isSharedPrefsChangeCommitedMutex;
            }
        };
    }

    private void setUpSharedPrefsListener() {
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                isSharedPrefsChangeCommitedMutex = true;
                assertTrue("SharedPrefsChangeMutex wasnt changed to true in the listener", isSharedPrefsChangeCommitedMutex);
            }
        };
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }



    @SmallTest
    public void testDefaultSharedPreferencesExist() {
        assertTrue("Default SharedPreferences does not exist", sharedPrefs != null);
    }



    @MediumTest
    public void testGenderSelectionMaleIsSaved() {
        selectMaleAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedWhenSelectedTwice() {
        selectMaleAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        assertEquals("Selecting gender male was not saved to SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        selectMaleAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testGenderSelectionMaleIsSavedAndValueNotFemale() {
        selectMaleAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        assertFalse("Gender male was selected, but value was female in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testGenderSelectionFirstMaleThenChangeFemale() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        selectMaleAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        selectFemaleAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Changing gender selection to female was not saved to SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }



    @MediumTest
    public void testAgeSelectionUnder18IsSaved() {
        selectUnder18AndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedWhenSelectedTwice() {
        selectUnder18AndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        selectUnder18AndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSavedAndValueNotOver35() {
        selectUnder18AndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35_value);

        assertFalse("Age Under 18 was selected, but Over 35 was saved in SharedPreferences", valueInSharedPrefs == valueDefinedInXML);
    }

    @MediumTest
    public void testAgeSelectionFirstUnder18ThenChangeOver35() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35_value);

        selectUnder18AndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        selectOver35AndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Changing age selection to Over 35 was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }



    @MediumTest
    public void testCountrySelectionTheBahamasIsSaved() {
        selectTheBahamasAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedWhenSelectedTwice() {
        selectTheBahamasAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);

        selectTheBahamasAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testCountrySelectionTheBahamasIsSavedAndValueNotAlbania() {
        selectTheBahamasAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        assertFalse("Country The Bahamas was selected, but Albania was saved in SharedPreferences", valueInSharedPrefs == valueDefinedInXML);
    }

    @MediumTest
    public void testCountrySelectionFirstTheBahamasThenChangeAlbania() {
        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        selectTheBahamasAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", firstValueDefinedInXML, valueInSharedPrefs);

        selectAlbaniaAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Changing country selection to Albania was not saved in SharedPreferences", secondValueDefinedInXML, valueInSharedPrefs);
    }



    @MediumTest
    public void testDataSendingEnabledOptionIsSaved() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        assertTrue("Shared Preferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }

    @MediumTest
    public void testDataSendingDisabledOptionIsSaved() {
        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();

        assertFalse("Shared Preferences boolean value for enable data sending is not false even though checkbox is not checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }

    @MediumTest
    public void testDataSendingFirstEnabledThanDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        assertTrue("Shared Preferences boolean value for enable data sending is not true even though checkbox is checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));

        clickCheckBoxAndSetSharedPrefsMutex();

        assertFalse("Shared Preferences boolean value for enable data sending is not false even though checkbox was clicked after being checked",
                getSharedPrefsBoolValue(SettingsFragment.KEY_SETTINGS_ENABLE_DATA_SENDING));
    }



    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        selectMonthlyAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in Shared Preferences", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedWhenSelectedTwiceAndDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        selectMonthlyAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);

        assertEquals("Selecting data sending frequency Monthly is not saved in Shared Preferences", valueDefinedInXML, valueInSharedPrefs);


        selectMonthlyAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Selecting data sending frequency Monthly is not saved in Shared Preferences when selected again", valueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testDataSendingFrequencySelectionMonthlyIsSavedAndValueNotWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();
        selectMonthlyAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertFalse("Data sending frequency Monthly was selected, but value Weekly was saved in Shared Preferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testDataSendingFrequencySelectionFirstMonthlyThanChangedToWeeklyWhenDataSendingEnabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        String firstValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_monthly_value);
        String secondValueDefinedInXML = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        selectMonthlyAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Value was not Monthly in Shared Preferences after selecting Monthly", firstValueDefinedInXML, valueInSharedPrefs);

        selectWeeklyAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Value was not Weekly in Shared Preferences after changing selection from Monthly to Weekly", secondValueDefinedInXML, valueInSharedPrefs);
    }

    // TODO: Siivoa tama testi
    @LargeTest
    public void testDataSendingFrequencySelectionCannotBeChangedWhenDataSendingDisabled() {
        ensureCheckBoxCheckedAndSetSharedPrefsMutex();

        selectWeeklyAndSetSharedPrefsMutex();

        solo.waitForDialogToClose();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);
        String frequencyValueSetWhenDataSendingEnabled = getStringValueDefinedInXML(R.string.frequency_weekly_value);

        assertEquals("Selecting data sending frequency Weekly is not saved in Shared Preferences", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefs);


        ensureCheckBoxNotCheckedAndSetSharedPrefsMutex();

        solo.clickOnText(getActivity().getString(R.string.settings_data_sending_frequency_title));
        if (mua.checkTextDisplayed(R.string.frequency_monthly)) {
            isSharedPrefsChangeCommitedMutex = false;
            mua.selectMonthly();
        }

        String valueInSharedPrefsAfterDataSendingDisabled = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_DATA_SENDING_FREQUENCY);

        assertEquals("Data sending frequency was changed in Shared Preferences even though data sending was disabled", frequencyValueSetWhenDataSendingEnabled, valueInSharedPrefsAfterDataSendingDisabled);

    }


    private boolean getSharedPrefsBoolValue(String key) {
        solo.waitForCondition(isSharedPrefsChangeCommited, 10000);
        return sharedPrefs.getBoolean(key, false);
    }

    private String getSharedPrefsStringValue(String key) {
        assertTrue("waiting for shared prefs mutex timed out", solo.waitForCondition(isSharedPrefsChangeCommited, 60000));
        return sharedPrefs.getString(key, "String not found in Shared Preferences");
    }

    private String getStringValueDefinedInXML(int stringId) {
        return getActivity().getString(stringId);
    }

    private void selectMaleAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectMale();
    }

    private void selectFemaleAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectFemale();
    }

    private void selectUnder18AndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectUnder18();
    }

    private void selectOver35AndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectOver35();
    }

    private void selectTheBahamasAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectTheBahamas();
    }

    private void selectAlbaniaAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectAlbania();
    }

    private void selectMonthlyAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectMonthly();
    }

    private void selectWeeklyAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.selectWeekly();
    }

    private void ensureCheckBoxCheckedAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.ensureCheckBoxChecked();
    }

    private void ensureCheckBoxNotCheckedAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        mua.ensureCheckBoxNotChecked();
    }

    private void clickCheckBoxAndSetSharedPrefsMutex() {
        isSharedPrefsChangeCommitedMutex = false;
        // TODO: Tama klikkaus olettaa etta Settings nakymassa on vain yksi checkbox
        solo.clickOnCheckBox(0);
    }

}
