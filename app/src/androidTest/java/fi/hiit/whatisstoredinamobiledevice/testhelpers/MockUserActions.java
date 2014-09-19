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

    public void selectOption(Solo solo, Activity activity, int categoryId, int optionId) {
        solo.clickOnText(activity.getString(categoryId));
        solo.scrollListToTop(0);
        solo.clickOnText(activity.getString(optionId));
    }

    private void mockSelections(int categoryId, int optionId) {
        selectOption(testSoloObject, testActivity, categoryId, optionId);
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

    public void selectUnder18() {
        mockSelections(R.string.settings_age_title, R.string.age_group_under18);
    }

    public void selectOver35() {
        mockSelections(R.string.settings_age_title, R.string.age_group_over35);
    }

    public void selectTheBahamas() {
        mockSelections(R.string.settings_country_title, R.string.country_the_bahamas);
    }

    public void selectAlbania() {
        mockSelections(R.string.settings_country_title, R.string.country_albania);
    }


    public void ensureCheckBoxNotChecked() {
        if (testSoloObject.isCheckBoxChecked(0)) {
            testSoloObject.clickOnCheckBox(0);
        }
    }

    public void ensureCheckBoxChecked() {
        if (!testSoloObject.isCheckBoxChecked(0)) {
            testSoloObject.clickOnCheckBox(0);
        }
    }


    public boolean checkTextDisplayed(int stringId) {
        return testSoloObject.searchText(testActivity.getString(stringId));
    }

}
