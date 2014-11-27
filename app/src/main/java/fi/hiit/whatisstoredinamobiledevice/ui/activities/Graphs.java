package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import fi.hiit.whatisstoredinamobiledevice.R;

public class Graphs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        setPieChart();
    }

    private void setCharts() {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        // and so on ...

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q"); xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
    }

    private void setPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(13.33F, 0));
        yVals.add(new Entry(33.33F, 1));
        yVals.add(new Entry(33.33F, 2));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Kappa");
        xVals.add("Toinen");
        xVals.add("Vaikka");

        PieDataSet pieDataSet = new PieDataSet(yVals, "Election results");
        int[] colorsi = new int[] {R.color.red, R.color.blue, R.color.yellow} ;
        pieDataSet.setColors(colorsi, this);
        PieData data = new PieData(xVals, pieDataSet);
        pieChart.setData(data);

    }
}

