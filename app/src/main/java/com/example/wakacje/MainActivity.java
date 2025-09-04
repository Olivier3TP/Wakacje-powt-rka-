package com.example.wakacje;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
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
        TextView holidaysTime = findViewById(R.id.holidaysTime);

        LocalDate holidaysStarts = lastSaturdayOfJune(LocalDate.now().getYear());
        LocalDate schoolStarts = firstDayOfSchool(LocalDate.now().getYear());
//        jeżeli moja data jest po starcie wakacji(obliczamy przyszłe wakacje)
        if(LocalDate.now().isAfter(holidaysStarts)){
            holidaysStarts = lastSaturdayOfJune(LocalDate.now().getYear()+1);
        }
        if(LocalDate.now().isAfter(schoolStarts)){
            schoolStarts = firstDayOfSchool(LocalDate.now().getYear()+1);
        }

        if(LocalDate.now().isAfter(holidaysStarts) && LocalDate.now().isBefore(schoolStarts)){
            textView.setText("są wakacje");
            editText.setText("0");
        }else{
            textView.setText(String.format("Wakacje zaczynają się %s", holidaysStarts.toString()));
            long days = ChronoUnit.DAYS.between(LocalDate.now(), holidaysStarts);
            editText.setText(String.valueOf(days + " dni"));
        }
        holidaysTime.setText(String.format("Wakacje od %s do %s", holidaysStarts.toString(), schoolStarts.toString()));
    }

    private LocalDate lastSaturdayOfJune(int year){
        LocalDate endOfJune = LocalDate.of(year, 6, 30);
        while(endOfJune.getDayOfWeek() != DayOfWeek.SATURDAY){
            endOfJune = endOfJune.minusDays(1);
        }
        return endOfJune;
    }
    private LocalDate firstDayOfSchool(int year){
        LocalDate firstOfSeptember = LocalDate.of(year,9,1);
        while(firstOfSeptember.getDayOfWeek() == DayOfWeek.SATURDAY){
            firstOfSeptember = firstOfSeptember.plusDays(2);
        }
        while(firstOfSeptember.getDayOfWeek() == DayOfWeek.SUNDAY){
            firstOfSeptember = firstOfSeptember.plusDays(1);
        }
        return firstOfSeptember;
    }
//    jak bys sie kiedys z tego uczył to troche zepsułem xd
}