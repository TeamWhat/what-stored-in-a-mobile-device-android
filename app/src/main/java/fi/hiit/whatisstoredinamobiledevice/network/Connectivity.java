package fi.hiit.whatisstoredinamobiledevice.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

public class Connectivity {
    Context context;
    NetworkInfo activeNetwork;

    public Connectivity(Context context) {
        this.context = context;

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
    }

    public boolean isConnected() { return activeNetwork != null && activeNetwork.isConnectedOrConnecting(); }

    public boolean isWifi() {
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isConnectedAndIsWifiIfOnlyWifiSet() {
        if (isConnected()) {
            if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("settings_only_wifi", false)) {
                if (isWifi()) {
                    return true;
                }
            }else {
                return true;
            }
        }
        return false;
    }
}
