package com.example.pandaboo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class AppLifeCycle extends Application implements Application.ActivityLifecycleCallbacks {

    private int activityReferences = 0;
    //private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate(){
        super.onCreate();
        registerActivityLifecycleCallbacks(this);

    }
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityReferences++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityReferences--;

        /*
        if (activityReferences == 0 && isOngoingTimer){
            System.out.println("Killing the app, timer ongoing.");

        }*/
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
