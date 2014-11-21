package fi.hiit.whatisstoredinamobiledevice.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import fi.hiit.whatisstoredinamobiledevice.R;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String html = getString(R.string.about_message);

        String mime = "text/html";
        String encoding = "utf-8";

        WebView myWebView = (WebView)this.findViewById(R.id.web_view);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadDataWithBaseURL(null, html, mime, encoding, null);
    }
}
