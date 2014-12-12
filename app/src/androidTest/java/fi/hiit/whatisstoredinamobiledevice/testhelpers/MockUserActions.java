package fi.hiit.whatisstoredinamobiledevice.testhelpers;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.DisplayMetrics;

import com.robotium.solo.Solo;

import fi.hiit.whatisstoredinamobiledevice.R;


public class MockUserActions {
    private Activity testActivity;
    private Solo testSoloObject;

    public MockUserActions(Activity testActivity, Solo testSoloObject) {
        this.testActivity = testActivity;
        this.testSoloObject = testSoloObject;
    }

    public void swipeToRight(ActivityInstrumentationTestCase2 aitc2){
        DisplayMetrics dm = new DisplayMetrics();
        testActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TouchUtils.drag(aitc2, width - 30, 30, height / 2, height / 2, 5);
    }

    public void selectOption(int categoryId, int optionId) {
        testSoloObject.clickOnText(testActivity.getString(categoryId));
        testSoloObject.waitForText("Cancel"); // todo: HARDCODE
        testSoloObject.scrollListToTop(0);
        testSoloObject.clickOnText(testActivity.getString(optionId));
        testSoloObject.waitForText(testActivity.getString(R.string.title_activity_settings));
    }

    public void selectMonthly() {
        selectOption(R.string.settings_data_sending_frequency_title, R.string.frequency_monthly);
    }

    public void selectWeekly() {
        selectOption(R.string.settings_data_sending_frequency_title, R.string.frequency_weekly);
    }

    public void selectFemale() {
        selectOption(R.string.settings_gender_title, R.string.gender_female);
    }

    public void selectMale() {
        selectOption(R.string.settings_gender_title, R.string.gender_male);
    }

    public void selectUnder18() {
        selectOption(R.string.settings_age_title, R.string.age_group_under18);
    }

    public void selectOver35() {
        selectOption(R.string.settings_age_title, R.string.age_group_over35);
    }

    public void selectTheBahamas() {
        selectOption(R.string.settings_country_title, R.string.country_the_bahamas);
    }

    public void selectAlbania() {
        selectOption(R.string.settings_country_title, R.string.country_albania);
    }

    public void clickOnCheckBoxText() {
        testSoloObject.clickOnText(testActivity.getString(R.string.settings_enable_data_sending_title));
    }

    // todo: Assumes only one check box in view
    public void ensureCheckBoxNotChecked() {
        if (testSoloObject.isCheckBoxChecked(0)) {
            clickOnCheckBoxText();
        }
    }

    // todo: Assumes only one check box in view
    public void ensureCheckBoxChecked() {
        if (!testSoloObject.isCheckBoxChecked(0)) {
            clickOnCheckBoxText();
        }
    }

    public boolean checkTextDisplayed(int stringId) {
        return testSoloObject.searchText(testActivity.getString(stringId));
    }

}
