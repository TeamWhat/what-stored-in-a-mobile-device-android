package fi.hiit.whatisstoredinamobiledevice.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.hiit.whatisstoredinamobiledevice.R;

public class QuestionButton extends Fragment{

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeScreen.
     */
    public static QuestionButton newInstance() {
        QuestionButton fragment = new QuestionButton();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public QuestionButton() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_button, container, false);
    }

}
