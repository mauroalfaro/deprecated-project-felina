package com.example.mauro.projectfelina;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.Button;

import javax.swing.text.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    String url = "";
    WebView webView;
    public EditText editText;
    ProgressBar progressBar;
    ActionBar actionBar;
    NotificationManager mNotifyMgr;
    TextView tv;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.felinaicon);

        tv=(TextView)findViewById(R.id.textView9);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        this.webView= (WebView)findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Button b = (Button)findViewById(R.id.btnBuscar);
        b.setOnClickListener(this);

        this.editText=(EditText)findViewById(R.id.txtURL);
        editText.setOnKeyListener(this);
        this.editText.setText("http://");
        this.webView.requestFocus();
        //this.textView.setText(this.webView.getUrl());

        this.progressBar=(ProgressBar)findViewById(R.id.progressBar);

        /*if(this.webView.getUrl().equals("")){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }*/

        if(url.equals("")){
            webView.setWebViewClient(new MyWebViewClient(this));
            webView.loadUrl("http://www.google.com.ar");
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.felinaicon)
                        .setContentTitle("Project Felina")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true)
                        .setContentText("Base 0.0.1.2 running--database OK");
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        this.mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        final MainActivity a = this;
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Project Felina")
                    .setMessage("¿Seguro que desea salir?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            a.finish();
                            a.mNotifyMgr.cancel(001);
                        }
                    });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (id == android.R.id.home){
            this.webView.goBack();
        }

        if(id==R.id.acercaDe){
            Intent i = new Intent(this,AcercaDeActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Cargando...",Toast.LENGTH_SHORT).show();

        String urlAux="http://";
        this.editText = (EditText)findViewById(R.id.txtURL);
        this.url=editText.getText().toString();

        this.webView.setWebViewClient(new MyWebViewClient(this));
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress); }
        });

        if(!this.url.startsWith("http://")){
            this.url=urlAux+url;
        }

        this.webView.loadUrl(url);
    }


    public void onProgressChanged(WebView view, int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            if ((i == KeyEvent.KEYCODE_DPAD_CENTER) ||
                    (i == KeyEvent.KEYCODE_ENTER)) {
                // Process the entered text here
                //editText.setText("");
                this.onClick(this.webView);
                return true;
            }
        return false;
    }
}
