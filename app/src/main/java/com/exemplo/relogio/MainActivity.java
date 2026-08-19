package com.exemplo.relogio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockView = findViewById(R.id.clockView);

        FloatingActionButton fabHourUp = findViewById(R.id.fabHourUp);
        FloatingActionButton fabHourDown = findViewById(R.id.fabHourDown);
        FloatingActionButton fabMinuteUp = findViewById(R.id.fabMinuteUp);
        FloatingActionButton fabMinuteDown = findViewById(R.id.fabMinuteDown);
        Button btnSetCurrentTime = findViewById(R.id.btnSetCurrentTime);

        fabHourUp.setOnClickListener(v -> {
            int hours = (clockView.getHours() + 1) % 24;
            clockView.setTime(hours, clockView.getMinutes(), clockView.getSeconds());
        });

        fabHourDown.setOnClickListener(v -> {
            int hours = (clockView.getHours() - 1 + 24) % 24;
            clockView.setTime(hours, clockView.getMinutes(), clockView.getSeconds());
        });

        fabMinuteUp.setOnClickListener(v -> {
            int minutes = (clockView.getMinutes() + 1) % 60;
            clockView.setTime(clockView.getHours(), minutes, clockView.getSeconds());
        });

        fabMinuteDown.setOnClickListener(v -> {
            int minutes = (clockView.getMinutes() - 1 + 60) % 60;
            clockView.setTime(clockView.getHours(), minutes, clockView.getSeconds());
        });

        btnSetCurrentTime.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            clockView.setTime(
                calendar.get(java.util.Calendar.HOUR_OF_DAY),
                calendar.get(java.util.Calendar.MINUTE),
                calendar.get(java.util.Calendar.SECOND)
            );
        });

        // Iniciar com hora atual
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        clockView.setTime(
            calendar.get(java.util.Calendar.HOUR_OF_DAY),
            calendar.get(java.util.Calendar.MINUTE),
            calendar.get(java.util.Calendar.SECOND)
        );
    }
}