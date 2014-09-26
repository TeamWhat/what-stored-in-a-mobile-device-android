package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import junit.framework.TestCase;

import java.util.HashMap;

public class DeviceDataCollectorTest extends TestCase {
    private DeviceDataCollector ddc;
    private HashMap<String,String> data;


    protected void setUp() {
        ddc = new DeviceDataCollector();
        data = (HashMap)ddc.getData();
    }

    public void testBrandHasBeenCollected() {
        assertTrue(data.containsKey("Brand"));
    }

    public void testDeviceHasBeenCollected() {
        assertTrue(data.containsKey("Device"));
    }

    public void testModelHasBeenCollected() {
        assertTrue(data.containsKey("Model"));
    }

    public void testProductHasBeenCollected() {
        assertTrue(data.containsKey("Product"));
    }

    public void testSerialHasBeenCollected() {
        assertTrue(data.containsKey("Serial"));
    }

    public void testBrandNotEmpty() {
        assertFalse(data.get("Brand").isEmpty());
    }

    public void testDeviceNotEmpty() {
        assertFalse(data.get("Device").isEmpty());
    }

    public void testModelNotEmpty() {
        assertFalse(data.get("Model").isEmpty());
    }

    public void testProductNotEmpty() {
        assertFalse(data.get("Product").isEmpty());
    }

    public void testSerialNotEmpty() {
        assertFalse(data.get("Serial").isEmpty());
    }
}
