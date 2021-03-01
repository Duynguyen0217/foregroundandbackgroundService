package com.example.foregroundandbackgroundservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {
    String CHANNEL_ID = "APP ID";
    NotificationManager mNotificationmanager;
    NotificationCompat.Builder mBuilder;
    MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        mNotificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = createnotification();
      //  mNotificationmanager.notify(1,mBuilder.build()); //notification bị kill đc
        startForeground(1 , mBuilder.build()); // notification ko bị kill đc
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //  Log.d("NNNN","onStartCommand");
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

        //set nhạc cho app
       /* mediaPlayer = MediaPlayer.create(this, R.raw.nhachoakoloi);
        mediaPlayer.start();*/

        if (intent.getBooleanExtra("start", false)) {

            Toast.makeText(this, "vào true", Toast.LENGTH_SHORT).show();
            if(mediaPlayer != null){
                mediaPlayer.pause();

            }
        }else{
            Toast.makeText(this, "vào false", Toast.LENGTH_SHORT).show();
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, R.raw.nhachoakoloi);
            }
            else
            {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            }

            mediaPlayer.start();

        }

        return START_REDELIVER_INTENT;
    }

    private NotificationCompat.Builder createnotification(){
        Intent intentpause = new Intent(this , MyForegroundService.class);
        intentpause.putExtra("start" , true);



        Intent intentplay = new Intent(this , MyForegroundService.class);
        intentplay.putExtra("start" , false);


        //gửi dữ liệu cho activity,Broadcast, Service ...
        PendingIntent pendingIntentPause = PendingIntent.getService(this,
                123,
                intentpause,
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentPlay = PendingIntent.getService(this,
                345,
                intentplay,
                PendingIntent.FLAG_UPDATE_CURRENT);




        NotificationCompat.Builder notify = new NotificationCompat.Builder(MyForegroundService.this , CHANNEL_ID)
                /*set giao diện*/ .setSmallIcon(android.R.drawable.btn_star)
                .setShowWhen(true) //giá trị true cho phép hiện thời gian
                .setContentTitle("Thông báo mới")
                .setContentText("Có phiên bản mới")
                .addAction(R.mipmap.ic_launcher , "Pause",pendingIntentPause) //addAction : thêm action cho service
                .addAction(R.mipmap.ic_launcher , "Play",pendingIntentPlay)
                .setPriority(2); //priority càng cao thì thiết bị mau nóng





        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID ,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            mNotificationmanager.createNotificationChannel(notificationChannel);


        }
        return notify;

    }
}
