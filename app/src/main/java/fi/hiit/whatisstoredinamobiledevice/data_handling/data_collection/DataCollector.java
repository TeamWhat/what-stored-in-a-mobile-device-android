package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import java.util.Map;

public interface DataCollector {
    public Map getData();
    public String getTableNameForData();
}
