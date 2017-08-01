package com.example.reaganharper.hiittrainer02;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.io.Serializable;

public class IntervalSetup extends AppCompatActivity{
    public NumberPicker mMinutes;
    public NumberPicker mSeconds;
    public EditText nName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_setup);

        EditText nameValue = (EditText) findViewById(R.id.name);
        nName = nameValue;

        NumberPicker minutes = (NumberPicker) findViewById(R.id.minutes);
        mMinutes = minutes;

        NumberPicker seconds = (NumberPicker) findViewById(R.id.seconds);
        mSeconds = seconds;

        Button addInterval = (Button) findViewById(R.id.add);
        Button cancel = (Button) findViewById(R.id.cancel);

        addInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInterval();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(IntervalSetup.this, MainActivity.class);
                IntervalSetup.this.startActivity(mainIntent);
            }
        });
    }

    public void confirmInterval(){
        Interval interval = new Interval(getName(), getInterval());
        Intent mainIntent = new Intent(IntervalSetup.this, MainActivity.class);
        mainIntent.putExtra("Interval", interval);
        IntervalSetup.this.startActivity(mainIntent);
    }

    public long getInterval(){
        long selectedMinutes = mMinutes.getValue() * 60000;
        long selectedSeconds = mSeconds.getValue() * 1000;
        return selectedMinutes + selectedSeconds;
    }

    public String getName(){
        String name = nName.getText().toString();
        return name;
    }

}
