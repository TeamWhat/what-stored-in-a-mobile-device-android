package fi.hiit.whatisstoredinamobiledevice.data_handling.data_collection;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import fi.hiit.whatisstoredinamobiledevice.R;

public class ImageDataCollectorTest extends InstrumentationTestCase {
    private boolean mScanned;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mScanned = false;
        Uri u = Uri.parse("android.resource://fi.hiit.whatisstoredinamobiledevice/res/raw/image.png");
        File f = Environment.getExternalStorageDirectory();
        String outLocation = f.getAbsolutePath()+ "/Pictures/image.png";
        try {
            copy(u.getEncodedPath(), outLocation);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        MediaStore.Images.Media.insertImage(getInstrumentation().getTargetContext().getContentResolver(), outLocation, "name", "kappa");

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
        String toDelete =  Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Pictures/image.png";
        File file = new File(toDelete);
        file.delete();
        getInstrumentation().getTargetContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{toDelete});
        super.tearDown();
    }

    public void testTets(){
        ImageDataCollector ic = new ImageDataCollector(getInstrumentation().getTargetContext());
        Map<String, Map<String, String>> m = ic.getData();
        Set e = m.entrySet();
    }

    public void copy(String src, String dst) throws IOException {
        InputStream in = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.image);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
