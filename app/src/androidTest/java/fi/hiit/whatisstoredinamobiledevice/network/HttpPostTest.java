package fi.hiit.whatisstoredinamobiledevice.network;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.volley.toolbox.BasicNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import fi.hiit.whatisstoredinamobiledevice.testhelpers.MockHttpStack;


public class HttpPostTest extends InstrumentationTestCase {

    private MockHttpStack mMockHttpStack;
    private HttpPostHandler mHttpPostHandler;

    private static String TEST_JSON_STRING = "{\"0\":{\"3\":\"4\",\"1\":\"2\"}}";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockHttpStack = new MockHttpStack();
        mHttpPostHandler = new HttpPostHandler(getInstrumentation().getTargetContext(), mMockHttpStack);
    }

    @LargeTest
    public void testHttpPostJSONSendsCorrectJSON() {
        JSONObject testPostJSON = null;
        try {
            testPostJSON = new JSONObject(TEST_JSON_STRING);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttpPostHandler.postJSON(testPostJSON);
        // Wait here for response?
        getInstrumentation().waitForIdleSync();
        assertEquals(testPostJSON.toString(), new String(mMockHttpStack.getLastPostBody()));
    }

}
