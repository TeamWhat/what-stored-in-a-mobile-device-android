package fi.hiit.whatisstoredinamobiledevice.testhelpers;

import android.content.SharedPreferences;

import com.robotium.solo.Condition;


public class SharedPreferencesMutexHelper {


    private boolean mSharedPrefsChangeCommittedMutex;
    private Condition mSharedPrefsChangeCommittedCond;
    private SharedPreferences.OnSharedPreferenceChangeListener mSharedPrefsChangeListener;
    private SharedPreferences mSharedPrefs;


    public SharedPreferencesMutexHelper(SharedPreferences sharedPrefs) {
        this.mSharedPrefs = sharedPrefs;
        setUpSharedPrefsChangeMutex();
        setUpSharedPrefsListener();
    }

    private void setUpSharedPrefsChangeMutex() {
        mSharedPrefsChangeCommittedMutex = false;
        mSharedPrefsChangeCommittedCond = new Condition() {
            @Override
            public boolean isSatisfied() {
                return mSharedPrefsChangeCommittedMutex;
            }
        };
    }

    private void setUpSharedPrefsListener() {
        mSharedPrefsChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                mSharedPrefsChangeCommittedMutex = true;
            }
        };
        mSharedPrefs.registerOnSharedPreferenceChangeListener(mSharedPrefsChangeListener);
    }

    public Condition isSharedPrefsChangeCommitted() {
        return mSharedPrefsChangeCommittedCond;
    }

    public void setSharedPrefsChangeCommittedMutex(boolean mutexValue) {
        mSharedPrefsChangeCommittedMutex = mutexValue;
    }
}
