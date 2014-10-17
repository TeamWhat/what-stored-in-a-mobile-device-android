package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UniqueIdentifier {

    private Context mContext;

    public UniqueIdentifier(Context mContext) {
        this.mContext = mContext;
    }

    public String identifier() {
        String androidId = Settings.Secure.ANDROID_ID;
        String serial = Build.SERIAL;
        String mac = mac();
        try {
            return hash(androidId+serial+mac);
        } catch (IOException e) {
            e.printStackTrace();
            return "4"; // chosen by fair dice roll, guaranteed to be random
        }
    }

    private String mac() {
        WifiManager wm = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    private String hash(String string) throws IOException{
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("SHA-256 not supported. Please contact your system administrator.");
        }
        byte[] bytes = string.getBytes();
        int byteCount;
        ByteArrayInputStream in = new  ByteArrayInputStream(bytes);
        while ((byteCount = in.read(bytes)) > 0) {
            digester.update(bytes, 0, byteCount);
        }
        byte[] digest = digester.digest();

        return new String(digest);
    }
}
