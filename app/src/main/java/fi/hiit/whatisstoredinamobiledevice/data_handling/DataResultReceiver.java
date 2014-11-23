package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DataResultReceiver extends ResultReceiver {

    private Receiver mDataReceiver;

    public DataResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        public void onReceiveDataCollectionResult();
    }

    public void setReceiver(Receiver receiver) {
        mDataReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mDataReceiver != null) {
            mDataReceiver.onReceiveDataCollectionResult();
        }
    }
}
