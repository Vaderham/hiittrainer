package com.example.reaganharper.hiittrainer02;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnTickListener {

    private long endTime;
    private PausableTimer mainTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker minutes = (NumberPicker) findViewById(R.id.minutes);
        pickerSetup(minutes);
        NumberPicker seconds = (NumberPicker) findViewById(R.id.seconds);
        pickerSetup(seconds);


        final ImageButton play = (ImageButton) findViewById(R.id.play);
        ImageButton stop = (ImageButton) findViewById(R.id.stop);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = getTime();

                if (mainTimer == null){
                    Toast.makeText(MainActivity.this, "Play", Toast.LENGTH_SHORT).show();
                    mainTimer = new PausableTimer(endTime, 1000, MainActivity.this);
                    mainTimer.start();
                    play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
                }else if(mainTimer.getIsPaused() == true){
                    Toast.makeText(MainActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                    mainTimer.resume();
                }else{
                    Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                    mainTimer.pause();
                    play.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24dp);
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainTimer != null){
                    mainTimer.stop();
                }
                mainTimer = null;
            }
        });

    }

    public void pickerSetup(NumberPicker pickerId){
        pickerId.setMaxValue(60);
        pickerId.setMinValue(0);
        pickerId.setWrapSelectorWheel(true);
    }

    @Override
    public void OnTick(long timeLeft) {
        TextView clock = (TextView) findViewById(R.id.intervalTimer);
        clock.setText(convertTime(timeLeft));
    }

    public long getTime(){
        //Get value of Number pickers and convert to milliseconds from minutes and seconds.
        NumberPicker minutes = (NumberPicker) findViewById(R.id.minutes);
        long selectedMinutes = minutes.getValue() * 60000;

        NumberPicker seconds = (NumberPicker) findViewById(R.id.seconds);
        long selectedSeconds = seconds.getValue() * 1000;

        //Add selected Minutes and seconds together
        endTime = selectedMinutes + selectedSeconds;

        return endTime;
    }

    public String convertTime(long mills){

        long seconds = (mills / 1000) % 60;
        long minutes = ((mills / (1000 * 60)) % 60);

        return String.valueOf(minutes) + ":" + String.valueOf(seconds);

    }


}
