package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import fi.hiit.whatisstoredinamobiledevice.R;

public class AudioDataCollectorTest extends InstrumentationTestCase {
    private boolean mScanned;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mScanned = false;
        Uri u = Uri.parse("android.resource://fi.hiit.whatisstoredinamobiledevice/res/raw/audio_for_audio_collector_test.ogg");
        File f = Environment.getExternalStorageDirectory();
        String outLocation = f.getAbsolutePath()+ "/Music/audio_for_audio_collector_test.ogg";
        try {
            copy(outLocation);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        MediaScannerConnection.scanFile(getInstrumentation().getTargetContext(), new String[]{outLocation}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String s, Uri uri) {
                mScanned = true;
            }
        });
        while (!mScanned) {}
    }

    @Override
    protected void tearDown() throws Exception {
        String toDelete =  Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Music/audio_for_audio_collector_test.ogg";
        File file = new File(toDelete);
        file.delete();
        getInstrumentation().getTargetContext().getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.DATA + "=?", new String[]{toDelete});
        super.tearDown();
    }

    public void testFields(){
        AudioDataCollector adc = new AudioDataCollector(getInstrumentation().getTargetContext());
        Map<String, Map<String, String>> m = adc.getData();
        for(Map<String, String> r : m.values()) {
            String size = r.get("size");
            if(size == null || size.equals("4006")) {
                if (r.get("artist").equals("Tester")) {
                    return;
                }
            }
        }
        fail();
    }

    public void copy(String dst) throws IOException {
        InputStream in = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.audio_for_audio_collector_test);
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
