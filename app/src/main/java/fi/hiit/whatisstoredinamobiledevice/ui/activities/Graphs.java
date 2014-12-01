package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.R;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.AudioDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.ImageDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.TextDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection.VideoDataCollector;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DatabaseAccessor;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataContract;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.DeviceDataOpenHelper;
import fi.hiit.whatisstoredinamobiledevice.data_handling.database_utilities.SQLiteDatabaseAccessor;

public class Graphs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        setSizePieChart();
        setCountPieChart();
    }

    private void setSizePieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.size_pie_chart);
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(totalSize(DeviceDataContract.VideoDataEntry.TABLE_NAME, VideoDataCollector.videoColumnNames), 0));
        yVals.add(new Entry(totalSize(DeviceDataContract.ImageDataEntry.TABLE_NAME, ImageDataCollector.imageColumnNames), 1));
        yVals.add(new Entry(totalSize(DeviceDataContract.AudioDataEntry.TABLE_NAME, AudioDataCollector.audioColumnNames), 2));
        yVals.add(new Entry(totalSize(DeviceDataContract.TextDataEntry.TABLE_NAME, TextDataCollector.textColumnNames), 3));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Videos (kB)");
        xVals.add("Images (kB)");
        xVals.add("Audio (kB)");
        xVals.add("Text files (kB)");

        PieDataSet pieDataSet = new PieDataSet(yVals, "File sizes");
        int[] colorsi = new int[] {R.color.red, R.color.blue, R.color.yellow, R.color.green} ;
        pieDataSet.setColors(colorsi, this);
        PieData data = new PieData(xVals, pieDataSet);
        pieChart.setDescription("");
        pieChart.setData(data);
    }

    private void setCountPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.count_pie_chart);
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(count(DeviceDataContract.VideoDataEntry.TABLE_NAME, VideoDataCollector.videoColumnNames), 0));
        yVals.add(new Entry(count(DeviceDataContract.ImageDataEntry.TABLE_NAME, ImageDataCollector.imageColumnNames), 1));
        yVals.add(new Entry(count(DeviceDataContract.AudioDataEntry.TABLE_NAME, AudioDataCollector.audioColumnNames), 2));
        yVals.add(new Entry(count(DeviceDataContract.TextDataEntry.TABLE_NAME, TextDataCollector.textColumnNames), 3));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Videos");
        xVals.add("Images");
        xVals.add("Audio");
        xVals.add("Text files");

        PieDataSet pieDataSet = new PieDataSet(yVals, "File counts");
        int[] colorsi = new int[] {R.color.red, R.color.blue, R.color.yellow, R.color.green} ;
        pieDataSet.setColors(colorsi, this);
        PieData data = new PieData(xVals, pieDataSet);
        pieChart.setDescription("");
        pieChart.setData(data);

    }

    /**
     *
     * @param tableName
     * @param columns
     * @return Total size in kB
     */
    private float totalSize(String tableName, String[] columns) {
        float size = 0;
        DatabaseAccessor databaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(this));
        Map<String, Map<String, String>> map = databaseAccessor.getLatestData(tableName, columns);
        for(Map<String, String> m : map.values()) {
            size += Float.parseFloat(m.get("size"));
        }
        return size / 1000;
    }

    /**
     *
     * @param tableName
     * @param columns
     * @return Total size in kB
     */
    private float count(String tableName, String[] columns) {
        float count = 0;
        DatabaseAccessor databaseAccessor = new SQLiteDatabaseAccessor(new DeviceDataOpenHelper(this));
        Map<String, Map<String, String>> map = databaseAccessor.getLatestData(tableName, columns);
        for(Map<String, String> m : map.values()) {
            count++;
        }
        return count;
    }
}

