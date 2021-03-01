package com.example.foregroundandbackgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnforeground,btnbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btnforeground = findViewById(R.id.buttonforegroundservice);
        btnbackground = findViewById(R.id.buttonbackgroundservice);

        btnbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MybackgroundService.class);
                intent.putExtra("chuoi","Hello");
                startService(intent);
            }
        });
        btnforeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyForegroundService.class);
                ContextCompat.startForegroundService(MainActivity.this,intent);


            }
        });
    }
}
