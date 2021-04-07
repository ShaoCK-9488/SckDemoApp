package com.example.sckdemoapk.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sckdemoapk.R;
import com.example.sckdemoapk.utils.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";
    private ImageView splashimage;
    private TextView splashtextView;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        ActivityUtils.getInstance().addActivities(this);
    }

    private void initView() {
        splashimage = (ImageView)findViewById(R.id.splashImage);
        splashtextView = (TextView)findViewById(R.id.splashText);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        timer = new Timer();
        timer.schedule(task, 1000);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(timer != null){
            timer.cancel();
        }
        this.finish();
    }
}