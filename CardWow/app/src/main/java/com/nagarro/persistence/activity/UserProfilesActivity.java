package com.nagarro.persistence.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nagarro.persistence.R;
import com.nagarro.persistence.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * Created by Zachary on 4/29/2018.
 */

public class UserProfilesActivity extends AppCompatActivity{
    Button button;
    TextView textView1;
    private WebView wv1;
    TextToSpeech t1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**Do you have to bind all the activities like this???**/
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.general_layout);
        Intent intent = getIntent();
        String first_name = intent.getStringExtra("FIRST_NAME");
        String last_name = intent.getStringExtra("LAST_NAME");
        String linked_link = intent.getStringExtra("LINKED_LINK");
        textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setText(first_name);
        button = (Button) findViewById(R.id.button3);
        wv1=(WebView)findViewById(R.id.webView);


        /**Setting up our get text to speech Listener**/
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        /** New myBrowser object is set as webview client for our web view**/
        wv1.setWebViewClient(new MyBrowser());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**url is the captured string in our edit test*/
                String url = linked_link;
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                t1.speak(first_name, TextToSpeech.QUEUE_FLUSH, null);
                /** We concatinate the strings url with the google query**/
                wv1.loadUrl(url);
                //
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent intent = new Intent (this, HatActivity.class);
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                shutDown(findViewById(android.R.id.content));
                return true;
            case R.id.item5:
                Intent homeintent = new Intent(this, MainActivity.class);
                startActivity(homeintent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /** Takes in current view and closes down the application  **/
    public void shutDown(View v){
        finish();
        System.exit(0);
    }
    /**MyBrowser class allows users to further browse on the device after the initial search**/
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
