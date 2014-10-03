package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import java.util.Map;

public interface DataCollector {
    // Map < tempRowIndex, Map < ColumnName, ColumnValue >>
    public Map<String, Map<String, String>> getData();
    public String getTableNameForData();
}
