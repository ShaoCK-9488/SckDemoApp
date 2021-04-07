package com.example.sckdemoapk.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {

    //定义ActivityUtils类的对象
    private static ActivityUtils instance;
    private List<Activity> activities = new ArrayList<Activity>();

    public ActivityUtils(){
    }

    //获取对象
    public static ActivityUtils getInstance(){
        if(instance == null){
            instance = new ActivityUtils();
        }
        return instance;
    }

    //向容器添加Activity
    public List<Activity> addActivities(Activity activity){
        activities.add(activity);
        return activities;
    }

    //关闭容器中记录的所以活动
    public void finishAllActivities(){
        for(int i = 0; i < activities.size(); i++){
//            Activity activity = activities.get(i);
//            activity.finish();
            activities.get(i).finish();
        }
    }
}
