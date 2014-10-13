package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import java.util.Map;

public interface DatabaseAccessor {
    // Map< tablename, Map< tempRowIndex, Map < ColumnName, ColumnValue >>>
    public boolean saveAllData(Map<String, Map<String, Map<String, String>>> map);
}
