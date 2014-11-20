package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import fi.hiit.whatisstoredinamobiledevice.R;

public class MainScreenTest extends ActivityUnitTestCase<MainScreen> {
    private Intent mLaunchIntent;

    public MainScreenTest() {
        super(MainScreen.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MainScreen.class);
        startActivity(mLaunchIntent, null, null);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

//    todo: write feature tests for new mainscreen
//    @MediumTest
//    public void testOpenQuestionsOpensAnActivity() {
//
//
//    }

}
