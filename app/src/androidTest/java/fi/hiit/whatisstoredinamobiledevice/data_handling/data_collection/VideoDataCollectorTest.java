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

public class VideoDataCollectorTest extends InstrumentationTestCase {
    private boolean mScanned;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mScanned = false;
        Uri u = Uri.parse("android.resource://fi.hiit.whatisstoredinamobiledevice/res/raw/video_collector_test_video.mpg");
        File f = Environment.getExternalStorageDirectory();
        String outLocation = f.getAbsolutePath()+ "/video_collector_test_video.mpg";
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
        String toDelete =  Environment.getExternalStorageDirectory().getAbsolutePath()+ "/video_collector_test_video.mpg";
        File file = new File(toDelete);
        file.delete();
        getInstrumentation().getTargetContext().getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media.DATA + "=?", new String[]{toDelete});
        super.tearDown();
    }

    public void testFields(){
        VideoDataCollector vdc = new VideoDataCollector(getInstrumentation().getTargetContext());
        Map<String, Map<String, String>> m = vdc.getData();
        for(Map<String, String> r : m.values()) {
            String size = r.get("size");
            if(size == null || size.equals("20480")) {
                return;
            }
        }
        fail();
    }

    public void copy(String dst) throws IOException {
        InputStream in = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.video_collector_test_video);
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

