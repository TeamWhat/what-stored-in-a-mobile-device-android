package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.database.Cursor;
import android.test.InstrumentationTestCase;

import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MediaDataCollectorTest extends InstrumentationTestCase {
    MediaDataCollector imageDataCollector;
    MediaDataCollector spyDC;
    protected void setUp() {
        TestSetup.setupMockito(this);
        imageDataCollector = new ImageDataCollector(getInstrumentation().getTargetContext());
        spyDC = spy(imageDataCollector);
        Cursor cursor = mock(Cursor.class);
        when(spyDC.getCursor()).thenReturn(cursor);

        String[] columns = {"Kappa"};
        when(cursor.getColumnNames()).thenReturn(columns);
        when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(false);
        when(cursor.getColumnIndex(anyString())).thenReturn(1);
        when(cursor.getString(anyInt())).thenReturn("Test string");


    }

    public void testGetDataGivesDataInCorrectFormat() {
        Map<String, Map<String, String>> map = spyDC.getData();
        assertEquals("Test string", map.get("0").get(ImageDataCollector.imageColumnNames[0]));
    }
}
