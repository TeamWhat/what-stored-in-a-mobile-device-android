package fi.hiit.whatisstoredinamobiledevice.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

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
    public void testGenderSelectionMaleIsSavedAndValueNotFemale() {
        selectMaleAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);

        assertFalse("Gender male was selected, but value was female in SharedPreferences", valueInSharedPrefs.equals(valueDefinedInXML));
    }

    @MediumTest
    public void testGenderSelectionFirstMaleThenChangeFemale() {
        String femaleValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_female_value);
        String maleValueDefinedInXML = getStringValueDefinedInXML(R.string.gender_male_value);

        selectMaleAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Selecting gender male was not saved to SharedPreferences", maleValueDefinedInXML, valueInSharedPrefs);

        selectFemaleAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_GENDER);

        assertEquals("Changing gender selection to female was not saved to SharedPreferences", femaleValueDefinedInXML, valueInSharedPrefs);
    }

    @MediumTest
    public void testAgeSelectionUnder18IsSaved() {
        selectUnder18AndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
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
        String under18ValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_under18_value);
        String over35ValueDefinedInXML = getStringValueDefinedInXML(R.string.age_group_over35_value);

        selectUnder18AndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Selecting age Under 18 was not saved in SharedPreferences", under18ValueDefinedInXML, valueInSharedPrefs);

        selectOver35AndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_AGE);

        assertEquals("Changing age selection to Over 35 was not saved in SharedPreferences", over35ValueDefinedInXML, valueInSharedPrefs);
    }


    @MediumTest
    public void testCountrySelectionTheBahamasIsSaved() {
        selectTheBahamasAndSetSharedPrefsMutex();

        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);
        String valueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", valueDefinedInXML, valueInSharedPrefs);
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
        String theBahamasValueDefinedInXML = getStringValueDefinedInXML(R.string.country_the_bahamas_value);
        String albaniaValueDefinedInXML = getStringValueDefinedInXML(R.string.country_albania_value);

        selectTheBahamasAndSetSharedPrefsMutex();
        String valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Selecting country The Bahamas was not saved in SharedPreferences", theBahamasValueDefinedInXML, valueInSharedPrefs);

        selectAlbaniaAndSetSharedPrefsMutex();
        valueInSharedPrefs = getSharedPrefsStringValue(SettingsFragment.KEY_SETTINGS_USER_COUNTRY);

        assertEquals("Changing country selection to Albania was not saved in SharedPreferences", albaniaValueDefinedInXML, valueInSharedPrefs);
    }

    private String getStringValueDefinedInXML(int stringId) {
        return getActivity().getString(stringId);
    }

    private String getSharedPrefsStringValue(String key) {
        solo.waitForCondition(isSharedPrefsChangeCommited, 10000);
        return sharedPrefs.getString(key, "");
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
}
