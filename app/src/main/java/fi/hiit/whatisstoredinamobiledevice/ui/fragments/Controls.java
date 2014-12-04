package fi.hiit.whatisstoredinamobiledevice.ui.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagerindicator.CirclePageIndicator;
import fi.hiit.whatisstoredinamobiledevice.R;


public class Controls extends Fragment{
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    public void setPager(ViewPager mPager) {
        this.mPager = mPager;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeScreen.
     */
    public static Controls newInstance() {
        Controls fragment = new Controls();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public Controls() {
// Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.controls, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIndicator = (CirclePageIndicator)getView().findViewById(R.id.circles_indicator);
        mIndicator.setViewPager(mPager);
    }
}