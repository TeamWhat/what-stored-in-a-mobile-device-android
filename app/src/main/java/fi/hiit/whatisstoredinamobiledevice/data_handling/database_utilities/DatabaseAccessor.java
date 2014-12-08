package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import java.util.Map;

public interface DatabaseAccessor {
    // Map< tablename, Map< tempRowIndex, Map < ColumnName, ColumnValue >>>
    public boolean saveAllData(Map<String, Map<String, Map<String, String>>> map);
    public Map<String, Map<String, String>> getData(String tablename, String[] columnNames, String sortOrder);
    public Map<String, Map<String, String>> getLatestData(String tablename, String[] columnNames);
    public void setAllSent();
}
