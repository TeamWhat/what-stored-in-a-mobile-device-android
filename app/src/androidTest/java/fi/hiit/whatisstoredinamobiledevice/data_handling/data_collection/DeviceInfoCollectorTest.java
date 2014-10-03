package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import junit.framework.TestCase;

import java.util.HashMap;

import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;

public class DeviceInfoCollectorTest extends TestCase {
    private DeviceInfoCollector ddc;
    private HashMap<String,String> data;


    protected void setUp() {
        ddc = new DeviceInfoCollector();
        data = (HashMap)ddc.getData();
    }

    public void testBrandHasBeenCollected() {
        assertTrue(data.containsKey(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND));
    }

    public void testDeviceHasBeenCollected() {
        assertTrue(data.containsKey(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE));
    }

    public void testModelHasBeenCollected() {
        assertTrue(data.containsKey(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL));
    }

    public void testProductHasBeenCollected() {
        assertTrue(data.containsKey(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT));
    }

    public void testSerialHasBeenCollected() {
        assertTrue(data.containsKey(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL));
    }

    public void testBrandNotEmpty() {
        assertFalse(data.get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_BRAND).isEmpty());
    }

    public void testDeviceNotEmpty() {
        assertFalse(data.get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_DEVICE).isEmpty());
    }

    public void testModelNotEmpty() {
        assertFalse(data.get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_MODEL).isEmpty());
    }

    public void testProductNotEmpty() {
        assertFalse(data.get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_PRODUCT).isEmpty());
    }

    public void testSerialNotEmpty() {
        assertFalse(data.get(DeviceDataContract.DeviceInfoEntry.COLUMN_NAME_SERIAL).isEmpty());
    }
}
