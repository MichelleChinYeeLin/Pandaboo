package com.example.pandaboo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //String event = intent.getStringExtra("event");
        //String time = intent.getStringExtra("time");
        //int notId = intent.getIntExtra("id", 0);
        //Intent activityIntent = new Intent(context, PlannerView.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

        //String channelId = "";
        //CharSequence name = "";
        //String description = "";

        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //    channelId = "channel_id";
        //    name = "channel_name";
        //    description = "description";

        //    NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
        //    channel.setDescription(description);
        //    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        //    notificationManager.createNotificationChannel(channel);
        //}

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notificationReminder")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle("Notification")
                .setContentText("Testing")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                //.setDeleteIntent(pendingIntent)
                //.setGroup("Group_calendar_view")
                //.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
    }
}
