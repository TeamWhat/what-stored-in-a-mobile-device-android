package fi.hiit.whatisstoredinamobiledevice.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.preferences.SettingsFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalQuestions#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PersonalQuestions extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PersonalQuestions.
     */
    public static PersonalQuestions newInstance() {
        PersonalQuestions fragment = new PersonalQuestions();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public PersonalQuestions() {
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
        View view = inflater.inflate(R.layout.personal_questions, container, false);
        SettingsFragment settingsFragment = new SettingsFragment();
        QuestionButton questionButton = new QuestionButton();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.preference_fragment, settingsFragment);
        //transaction.add(R.id.button_fragment, questionButton);
        transaction.commit();
        return view;
    }


}
