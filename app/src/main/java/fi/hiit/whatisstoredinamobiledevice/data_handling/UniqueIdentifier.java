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

    /**
     * Calculates unique identifier for the user based on Android ID, serial, and mac address
     * @return
     */
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

    /**
     * Hashes the unique identifier string with SHA-256
     * @param string
     * @return
     * @throws IOException
     */
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

        return stringify(digest);
    }

    /**
     * Converts a byte array to string
     * @param digest
     * @return
     */
    private String stringify(byte[] digest) {
        String byteArrayAsString = "";
        for(byte b : digest) {
            byteArrayAsString += byteAsString(b);
        }
        return byteArrayAsString;
    }

    /**
     * Converts a single byte to string
     * @param b
     * @return
     */
    private String byteAsString(byte b) {
        int i = Integer.parseInt("" + b);
        i += 128;
        return i + "";
    }
}
