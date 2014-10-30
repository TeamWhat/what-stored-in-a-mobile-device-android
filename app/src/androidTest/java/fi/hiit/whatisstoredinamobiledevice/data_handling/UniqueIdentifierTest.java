package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import fi.hiit.whatisstoredinamobiledevice.testhelpers.TestSetup;

import static org.mockito.Mockito.*;

public class UniqueIdentifierTest extends InstrumentationTestCase{
    private UniqueIdentifier identifier;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestSetup.setupMockito(this);
        Context mockContext = mock(MockContext.class);
        WifiManager wm = mock(WifiManager.class);
        when(mockContext.getSystemService(Context.WIFI_SERVICE)).thenReturn(wm);
        WifiInfo info = mock(WifiInfo.class);
        when(info.getMacAddress())
                .thenReturn("1")
                .thenReturn("2");
        when(wm.getConnectionInfo()).thenReturn(info);
        identifier = new UniqueIdentifier(mockContext);
    }

    public void testUniqueIdentifierIsDifferentIfMacAddressChanges() {
        assertFalse(identifier.identifier().equals(identifier.identifier()));
    }
}
