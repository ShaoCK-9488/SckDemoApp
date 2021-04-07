package com.example.sckdemoapk.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sckdemoapk.R;
import com.example.sckdemoapk.services.MainMediaPlayService;
import com.example.sckdemoapk.utils.ActivityUtils;

public class MainActivity extends Activity implements View.OnClickListener ,View.OnTouchListener{

    private static final String TAG = "MainActivity";
    private static final int MIN_DISTANCE = 200;
    private TextView main_title_text;
    private Button main_login_button;
    private Button main_gamelist_button;
    private ViewFlipper main_viewflipper;
    private AlertDialog exit_dialog;
    private ImageView imageView;
    private int id;
    private float posX = 0, currX = 0;

    private int[] imageId = new int[] {R.drawable.main_pic1, R.drawable.main_pic2, R.drawable.main_pic3,
            R.drawable.main_pic4, R.drawable.main_pic5, R.drawable.main_pic6, R.drawable.main_pic7,
            R.drawable.main_pic8, R.drawable.main_pic9, R.drawable.main_pic10,R.drawable.main_pic11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ActivityUtils.getInstance().addActivities(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, MainMediaPlayService.class);
        startService(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setViewFlipper(imageId);
    }

    private ImageView getImageViewForViewFilpper(int imageId){
        //ImageView imageView = new ImageView(this);
        imageView = new ImageView(this);
        imageView.setImageResource(imageId);

        return imageView;
    }

    private void setViewFlipper(int[] image) {
        for(int i = 0; i < image.length; i++){
            main_viewflipper.addView(getImageViewForViewFilpper(image[i]));

            getImageViewForViewFilpper(image[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                }
            });
        }

        main_viewflipper.setInAnimation(this,R.anim.anim_rightin);
        main_viewflipper.setOutAnimation(this, R.anim.anim_leftout);
        main_viewflipper.startFlipping();
    }

    private void initView() {
        main_title_text = (TextView)findViewById(R.id.mainTextTitle);
        main_login_button = (Button)findViewById(R.id.mainLoginButton);
        main_gamelist_button = (Button)findViewById(R.id.mainGameListButton);
        main_viewflipper = (ViewFlipper)findViewById(R.id.mainViewFlipper);

        main_login_button.setOnClickListener(this);
        main_gamelist_button.setOnClickListener(this);
        main_viewflipper.setOnTouchListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainLoginButton:
                Toast.makeText(this, "按下了登录按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mainGameListButton:
                Toast.makeText(this, "按下了Game列表按钮", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if(KeyCode == KeyEvent.KEYCODE_BACK){
            exit_dialog = new AlertDialog.Builder(this).create();
            exit_dialog.setTitle("退出提示：");
            exit_dialog.setMessage("确定要退出应用吗？");
            exit_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    exit_dialog.cancel();
                }
            });
            exit_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "是的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, MainMediaPlayService.class);
                    stopService(intent);
                    ActivityUtils.getInstance().finishAllActivities();
                }
            });
            exit_dialog.show();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                posX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if(currX - posX > 0 && (Math.abs(currX - posX)) > MIN_DISTANCE){
                    Toast.makeText(this,"向右滑动",Toast.LENGTH_SHORT).show();

                }
                else if(currX - posX < 0 && (Math.abs(currX - posX) > MIN_DISTANCE)){
                    Toast.makeText(this,"向左滑动",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MainActivity.this, MainMediaPlayService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}