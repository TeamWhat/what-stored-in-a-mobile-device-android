package fi.hiit.whatisstoredinamobiledevice.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.hiit.whatisstoredinamobiledevice.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WelcomeScreen extends PreferenceFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeScreen.
     */
    public static WelcomeScreen newInstance() {
        WelcomeScreen fragment = new WelcomeScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public WelcomeScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.welcome_screen, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
