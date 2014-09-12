package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockUserActions;


public class QuestionsTest extends ActivityInstrumentationTestCase2<Questions> {
    private Questions questions;
    private MockUserActions mockUserActions;

    public QuestionsTest() {
        super(Questions.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        questions = getActivity();
        mockUserActions = new MockUserActions();
    }

    @MediumTest
    public void testOnBackPressedReturnsToFirstFragmentWhenOnSecondFragment() {
        mockUserActions.swipeToRight(questions, this);
        mockUserActions.pressBack(this);
        assertEquals(0, getActivity().getPager().getCurrentItem());
    }

    @MediumTest
    public void testGetPagerReturnsPager() {
        assertTrue(questions.getPager().getClass() == ViewPager.class);
    }

    @MediumTest
    public void testOnBackPressedReturnsToMainScreenWhenOnFirstFragment() {
        mockUserActions.pressBack(this);
        assertFalse(questions.hasWindowFocus());
    }

}
