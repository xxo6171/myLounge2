package com.example.mylounge2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "channel2";

    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }

    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(
                channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT
        );
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);

        NotificationChannel channel2 = new NotificationChannel(
                channel2ID, channel2Name, NotificationManager.IMPORTANCE_DEFAULT
        );
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);


    }

    public NotificationManager getManager(){
    if(mManager == null){
        mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }
    return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(){
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle("AUL")
                .setContentText("사용이 시작되었습니다.")
                .setSmallIcon(R.drawable.lounge_system);
    }

    public NotificationCompat.Builder getChannel2Notification(){
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle("AUL")
                .setContentText("사용이 종료되었습니다")
                .setSmallIcon(R.drawable.lounge_system);
    }
}
