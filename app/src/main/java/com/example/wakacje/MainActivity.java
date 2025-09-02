package com.example.wakacje;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editText = findViewById(R.id.daysToEnd);
        TextView textView = findViewById(R.id.holydaysStart);

        LocalDate holidaysStarts = lastSaturdayOfJune(LocalDate.now().getYear());
//        jeżeli moja data jest po starcie wakacji(obliczamy przyszłe wakacje)
        if(LocalDate.now().isAfter(holidaysStarts)){
            holidaysStarts = lastSaturdayOfJune(LocalDate.now().getYear()+1);
        }
        textView.setText(String.format("Wakacje zaczynają się %s", holidaysStarts.toString()));
        long days = ChronoUnit.DAYS.between(LocalDate.now(), holidaysStarts);
        editText.setText(String.valueOf(days + " dni"));
    }
    private LocalDate lastSaturdayOfJune(int year){
        LocalDate endOfJune = LocalDate.of(year, 6, 30);
        while(endOfJune.getDayOfWeek() != DayOfWeek.SATURDAY){
            endOfJune = endOfJune.minusDays(1);
        }
        return endOfJune;
    }
}