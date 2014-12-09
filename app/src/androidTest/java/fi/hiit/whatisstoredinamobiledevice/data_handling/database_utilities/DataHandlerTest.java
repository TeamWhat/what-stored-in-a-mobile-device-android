package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;


import android.test.InstrumentationTestCase;

import fi.hiit.whatisstoredinamobiledevice.data_handling.DataHandler;
import fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup;

import static org.mockito.Mockito.*;

public class DataHandlerTest extends InstrumentationTestCase {

    private DataHandler mDataHandler;
    private DatabaseAccessor databaseAccessor;

    protected void setUp() {
        TestSetup.setupMockito(this);
        databaseAccessor = mock(SQLiteDatabaseAccessor.class);
        mDataHandler = new DataHandler(getInstrumentation().getTargetContext(), databaseAccessor);
    }

    public void testSaveAllDataIsCalled() {
        mDataHandler.collectAllData();
        verify(databaseAccessor).saveAllData(anyMap());
    }

    public void testIfAccessorReturnsFalseSaveAllDataReturnsFalse() {
        when(databaseAccessor.saveAllData(anyMap())).thenReturn(false);
        assertFalse(mDataHandler.collectAllData());
    }

    public void testIfAccessorReturnsTrueSaveAllDataReturnsTrue() {
        when(databaseAccessor.saveAllData(anyMap())).thenReturn(true);
        assertTrue(mDataHandler.collectAllData());
    }
}
