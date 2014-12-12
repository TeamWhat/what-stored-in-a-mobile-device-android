package fi.hiit.whatisstoredinamobiledevice.data_handling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DataResultReceiver extends ResultReceiver {

    private Receiver mDataReceiver;


    public DataResultReceiver(Handler handler) {
        super(handler);
    }

    /**
     * A class implementing this interface receives a broadcast when the dataHandlerIntentService has finished
     */
    public interface Receiver {
        // this method is called when the dataHandlerIntentService has finished data collection
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
