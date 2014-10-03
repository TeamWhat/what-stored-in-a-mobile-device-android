package fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities;

import java.util.Map;

public interface DatabaseAccessor {
    public boolean saveAllData(Map<String, Map<String, String>> map);
}
