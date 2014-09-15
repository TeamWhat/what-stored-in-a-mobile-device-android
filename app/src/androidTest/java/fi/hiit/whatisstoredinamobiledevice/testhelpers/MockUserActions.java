package fi.hiit.whatisstoredinamobiledevice.testhelpers;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.robotium.solo.Solo;

import fi.hiit.whatisstoredinamobiledevice.R;

public class MockUserActions {

    public void pressBack(ActivityInstrumentationTestCase2 aitc2) {
        aitc2.sendKeys(KeyEvent.KEYCODE_BACK);
        waitUntilActionFinished();
    }

    public void swipeToRight(Activity activity, ActivityInstrumentationTestCase2 aitc2){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TouchUtils.drag(aitc2, width - 30, 30, height / 2, height / 2, 5);
    }

    /**
     * Waits for 500ms to let emulator finish the mock action
     */
    private void waitUntilActionFinished() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Log.wtf("MockUserActions", "Thread sleep failed for some reason");
            e.printStackTrace();
        }
    }

    public void selectOption(Solo solo, Activity activity, int categoryId, int optionId) {
        solo.clickOnText(activity.getString(categoryId));
        solo.clickOnText(activity.getString(optionId));
    }
}
