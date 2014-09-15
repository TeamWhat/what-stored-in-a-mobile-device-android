package fi.hiit.whatisstoredinamobiledevice.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.hiit.whatisstoredinamobiledevice.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Question1#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Question1 extends PreferenceFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Question1.
     */
    public static Question1 newInstance() {
        Question1 fragment = new Question1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public Question1() {
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
        return inflater.inflate(R.layout.fragment_question1, container, false);
    }
}
