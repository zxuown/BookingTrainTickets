package com.example.bookingtraintickets;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> cities = new ArrayList<>(Arrays.asList("Select city", "Kyiv", "Odessa", "Uzhgorod", "Lviv", "Dnipro"));
    private String prevCity = "";
    private boolean start = true;

    private int seatbelts = 0;

    private int counter = 0;

    private Button btnPlus;

    private TextView tvCount;

    private Button btnMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        tvCount = findViewById(R.id.tvCount);

        AdapterView.OnItemSelectedListener itemSelected = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerFrom.getSelectedItem().equals(spinnerTo.getSelectedItem()) &&
                        spinnerFrom.getSelectedItem() != "Select city" &&
                        spinnerTo.getSelectedItem() != "Select city"){
                    spinnerFrom.setSelection(0);
                    spinnerTo.setSelection(0);
                    Toast.makeText(MainActivity.this , "The same cities!!!",
                           Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerFrom.setOnItemSelectedListener(itemSelected);

        spinnerTo.setOnItemSelectedListener(itemSelected);

        findViewById(R.id.pickDate).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int yearFromCalendar = c.get(Calendar.YEAR);
            int monthFromCalendar = c.get(Calendar.MONTH);
            int dayFromCalendar = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, year, month, day) -> {
                        ((Button)findViewById(R.id.pickDate))
                                .setText(day + "-" +
                                        (month + 1) +
                                        "-" + year);
                    },
                    yearFromCalendar, monthFromCalendar, dayFromCalendar);
            datePickerDialog.show();
        });

        findViewById(R.id.pickTime).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hourFromCalendar = c.get(Calendar.HOUR_OF_DAY);
            int minuteFromCalendar = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    (view, hour, minute) ->
                            ((Button)findViewById(R.id.pickTime))
                                    .setText(hour + ":" + minute),
                    hourFromCalendar, minuteFromCalendar, false);
            timePickerDialog.show();
        });

        btnPlus.setOnClickListener(v -> {
            counter++;
            tvCount.setText(String.valueOf(counter));
        });

        btnMinus.setOnClickListener(v -> {
            if(counter > 0) {
                counter--;
                tvCount.setText(String.valueOf(counter));
            }
        });

        ((Button)findViewById(R.id.btnSubmit)).setOnClickListener(v -> {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("from", spinnerFrom.getSelectedItem().toString());
            intent.putExtra("to", spinnerTo.getSelectedItem().toString());
            intent.putExtra("date", ((Button)findViewById(R.id.pickDate)).getText());
            intent.putExtra("seatbelts", tvCount.getText());
            intent.putExtra("time", ((Button)findViewById(R.id.pickTime)).getText());
            intent.putExtra("price", counter * 35);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}