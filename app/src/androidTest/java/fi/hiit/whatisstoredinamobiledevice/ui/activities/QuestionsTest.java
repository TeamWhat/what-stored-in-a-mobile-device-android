package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.test.TouchUtils;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.ui.fragments.Question1;


public class QuestionsTest extends ActivityInstrumentationTestCase2<Questions> {
    private Intent mLaunchIntent;
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
        Question1 question1 = new Question1();
        assertFalse(question1.isVisible());
        assertEquals(0, getActivity().getPager().getCurrentItem());
    }

    private void swipeToRight(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TouchUtils.drag(this, width -30, 30, height/2, height/2, 5);
    }

    private void pressBack() {
        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

}