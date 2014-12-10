package fi.hiit.whatisstoredinamobiledevice.ui.fragments;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import fi.hiit.whatisstoredinamobiledevice.R;

public class FirstTimeSettingsFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AboutFragment.
     */
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FirstTimeSettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_time_settings, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set default data sending frequency as weekly
        Spinner dataSendingFrequencySpinner = (Spinner) getView().findViewById(R.id.dataSendingPeriodSelectionSpinner);
        dataSendingFrequencySpinner.setSelection(1);

        final CheckBox sendDataCheckBox = (CheckBox) getView().findViewById(R.id.sendDataCheckbox);

        sendDataCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if (!sendDataCheckBox.isChecked()) {
                            CheckBox sendDataOnWifiCheckBox = (CheckBox) getView().findViewById(R.id.sendDataOnWifiCheckbox);
                            sendDataOnWifiCheckBox.setEnabled(false);

                            Spinner dataSendingFrequencySpinner = (Spinner) getView().findViewById(R.id.dataSendingPeriodSelectionSpinner);
                            dataSendingFrequencySpinner.setEnabled(false);

                            TextView dataSendingFrequencyTextView = (TextView) getView().findViewById(R.id.dataSendingFrequencyTextView);
                            dataSendingFrequencyTextView.setEnabled(false);
                        } else {
                            CheckBox sendDataOnWifiCheckBox = (CheckBox) getView().findViewById(R.id.sendDataOnWifiCheckbox);
                            sendDataOnWifiCheckBox.setEnabled(true);

                            Spinner dataSendingFrequencySpinner = (Spinner) getView().findViewById(R.id.dataSendingPeriodSelectionSpinner);
                            dataSendingFrequencySpinner.setEnabled(true);

                            TextView dataSendingFrequencyTextView = (TextView) getView().findViewById(R.id.dataSendingFrequencyTextView);
                            dataSendingFrequencyTextView.setEnabled(true);
                        }
                    }
                }
        );

    }
}