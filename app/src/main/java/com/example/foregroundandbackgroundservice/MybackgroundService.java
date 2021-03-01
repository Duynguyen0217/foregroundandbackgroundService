package com.example.foregroundandbackgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;

public class MybackgroundService extends Service {

    Handler handler;
    int i = 0;

    // chỉ dùng khi dùng hàm bound service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
   //     Log.d("BBBB", "onCreate Service");
        Toast.makeText(this, "onCreate Service", Toast.LENGTH_SHORT).show();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
   //     Log.d("BBBB", "onStartCommand");
   //     Toast.makeText(this, "onStartCommand"+intent.getStringExtra("chuoi"), Toast.LENGTH_SHORT).show();
     /*   try {
            Toast.makeText(this, "onStartCommand: "+intent.getStringExtra("chuoi"), Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "onStartCommand: null value", Toast.LENGTH_SHORT).show();
        }*/
     handler.postDelayed(new Runnable() {
         @Override
         public void run() {
             if(i<=100){
                 Toast.makeText(MybackgroundService.this, "Giá trị của i là: "+i, Toast.LENGTH_SHORT).show();
                 i++;
                 handler.postDelayed(this,2000);

             }else {
                 Toast.makeText(MybackgroundService.this, "Kết Thúc", Toast.LENGTH_SHORT).show();
             }
         }
     },2000);
        return START_NOT_STICKY;
        //hàm onStartCommand giúp xử lý Task vd: download ....
        //return START_NOT_STICKY: giúp khi mà kill service thì service chết luôn ko hỗ trợ service nữa(ko start lại từ đầu)
        //return START_STICKY:Nếu service bị kill và trong hàm onStartCommand không có một intent nào chờ nó nữa thì Service sẽ được hệ thống khởi động lại với một Intent null.
        //return START_REDELIVER_INTENT:Nếu Service bị kill thì nó sẽ được khởi động lại với một Intent là Intent cuối cùng mà Service được nhận. Điều này thích hợp với các service đang thực hiện công việc muốn tiếp tục ngay tức thì như download fie chẳng hạn
        //return START_STICKY_COMPATIBILITY:Giá trị này cũng giống như START_STICKY nhưng nó không chắc chắn, đảm bảo khởi động lại service.
        //return DEFAULT: Là một sự lựa chọn giữa START_STICKY_COMPATIBILITY hoặc START_STICKY
    }

}
