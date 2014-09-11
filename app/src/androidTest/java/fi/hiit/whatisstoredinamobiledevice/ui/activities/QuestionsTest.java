package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;


public class QuestionsTest extends ActivityInstrumentationTestCase2<Questions> {
    private Questions questions;

    public QuestionsTest() {
        super(Questions.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        questions = getActivity();
    }

    @MediumTest
    public void testOnBackPressedReturnsToFirstFragmentWhenOnSecondFragment() {
        swipeToRight();
        pressBack();
        assertEquals(0, getActivity().getPager().getCurrentItem());
    }

    @MediumTest
    public void testGetPagerReturnsPager() {
        assertTrue(questions.getPager().getClass() == ViewPager.class);
    }

    @MediumTest
    public void testOnBackPressedReturnsToMainScreenWhenOnFirstFragment() {

        pressBack();

        // need to wait until back press is finished
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Log.wtf("Questions", "Too many energy drinks son, couldn't sleep");
            e.printStackTrace();
        }

        assertFalse(questions.hasWindowFocus());
    }

    public void pressBack() {
        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

    public void swipeToRight(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TouchUtils.drag(this, width - 30, 30, height / 2, height / 2, 5);
    }

}
