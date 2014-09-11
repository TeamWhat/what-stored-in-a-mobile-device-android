package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import fi.hiit.whatisstoredinamobiledevice.R;

public class MainScreenTest extends ActivityUnitTestCase<MainScreen> {
    private Intent mLaunchIntent;
    private Button startQuestionsButton;

    public MainScreenTest() {
        super(MainScreen.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MainScreen.class);
        startActivity(mLaunchIntent, null, null);
        startQuestionsButton =
                (Button) getActivity()
                        .findViewById(R.id.startQuestionsButton);
    }


    @MediumTest
    public void testOpenQuestionsOpensAnActivity() {
        startQuestionsButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();

        assertNotNull("Intent was null", launchIntent);

        Intent questionsIntent = new Intent(getInstrumentation()
                .getTargetContext(), Questions.class);
        String launchName = launchIntent.getComponent().getClassName();
        String questionsName = questionsIntent.getComponent().getClassName();
        assertTrue("Expected " + questionsName + " got " + launchName, launchName.equals(questionsName));

    }

}
