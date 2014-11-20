package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.test.InstrumentationTestCase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class TextDataCollectorTest extends InstrumentationTestCase {
    private boolean mScanned;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mScanned = false;
        generateTextFile();
        String textFileLocation = Environment.getExternalStorageDirectory() + "/text_file_for_text_collector_test.txt";


        MediaScannerConnection.scanFile(getInstrumentation().getTargetContext(), new String[]{textFileLocation}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String s, Uri uri) {
                mScanned = true;
            }
        });
        while (!mScanned) {}
    }

    @Override
    protected void tearDown() throws Exception {
        String toDelete =  Environment.getExternalStorageDirectory() + "/text_file_for_text_collector_test.txt";
        File file = new File(toDelete);
        file.delete();
        getInstrumentation().getTargetContext().getContentResolver().delete(MediaStore.Files.getContentUri("external"), MediaStore.Files.FileColumns.DATA + "=?", new String[]{toDelete});
        super.tearDown();
    }

    public void testFields(){
        TextDataCollector tdc = new TextDataCollector(getInstrumentation().getTargetContext());
        Map<String, Map<String, String>> m = tdc.getData();
        for(Map<String, String> r : m.values()) {
            String size = r.get("size");
            if(size == null || size.equals("39")) {
                return;
            }
        }
        fail();
    }

    public void generateTextFile(){
        File log = new File(Environment.getExternalStorageDirectory(), "text_file_for_text_collector_test.txt");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(log.getAbsolutePath(), false));
            out.write("Text file for text data collector test.");
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
}

