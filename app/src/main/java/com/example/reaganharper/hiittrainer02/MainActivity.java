package com.example.reaganharper.hiittrainer02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int ADD_INTERVAL = 1;
    static final int ZERO_CLOCK = 0;
    private long endTime;
    private PausableTimer fullTimer;
    private PausableTimer intervalTimer;
    private RecyclerView intervalList;
    private RecyclerView.Adapter intervalAdapter;
    private RecyclerView.LayoutManager LayoutManager;
    private ArrayList<Interval> mIntervals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntervals = new ArrayList<>();

        intervalList = (RecyclerView) findViewById(R.id.recycler);
        intervalList.setHasFixedSize(true);

        LayoutManager = new LinearLayoutManager(this);
        intervalList.setLayoutManager(LayoutManager);

        intervalAdapter = new IntervalAdapter(this, mIntervals);
        intervalList.setAdapter(intervalAdapter);

        final ImageButton play = (ImageButton) findViewById(R.id.play);
        ImageButton stop = (ImageButton) findViewById(R.id.stop);
        final TextView intervalClock = (TextView) findViewById(R.id.intervalTimer);
        final TextView fullClock = (TextView) findViewById(R.id.fullTimer);
        Button addInterval = (Button) findViewById(R.id.addInterval);
        Button reset = (Button) findViewById(R.id.reset);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fullTimer == null) {
                    Toast.makeText(MainActivity.this, "Play", Toast.LENGTH_SHORT).show();

                    fullTimer = new PausableTimer(getFullTime(), 1000, new OnTickListener() {
                        @Override
                        public void OnTick(long timeLeft) {
                            fullClock.setText(convertTime(timeLeft));
                        }
                        @Override
                        public void OnFinish() {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                    });


                    for(int i = 0; i < mIntervals.size();){
                        long singleInterval = mIntervals.get(i).getIntervalTime();

                        intervalTimer = new PausableTimer(singleInterval, 1000, new OnTickListener() {
                            @Override
                            public void OnTick(long timeLeft) {
                                intervalClock.setText(convertTime(timeLeft));
                            }

                            @Override
                            public void OnFinish() {

                            }
                        });
                    }



                    fullTimer.start();
                    intervalTimer.start();
                    play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
                } else if (fullTimer.getIsPaused()) {
                    Toast.makeText(MainActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                    fullTimer.resume(fullTimer.getCurrentTime());
                    play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
                } else {
                    Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                    fullTimer.pause();
                    play.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24dp);
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullTimer != null) {
                    Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                    fullTimer.stop();
                    intervalTimer.stop();
                    endTime = ZERO_CLOCK;
                }
                fullTimer = null;
                play.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24dp);
            }
        });

        addInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddInterval();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetIntervalList();
            }
        });

    }

    public long getFullTime() {
        for (int i = 0; i < mIntervals.size(); i++) {
            endTime = endTime + mIntervals.get(i).getIntervalTime();
        }
        return endTime;
    }

    public String convertTime(long mills) {
        long seconds = (mills / 1000) % 60;
        long minutes = ((mills / (1000 * 60)) % 60);
        String convertedSeconds = String.format("%02d", seconds);
        String convertedMinutes = String.format("%02d", minutes);
        return convertedMinutes + ":" + convertedSeconds;
    }

    public void openAddInterval() {
        Intent addInterval = new Intent(MainActivity.this, IntervalSetupActivity.class);
        startActivityForResult(addInterval, ADD_INTERVAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_INTERVAL) {
            if (resultCode == RESULT_OK) {
                Interval returnedInterval = (Interval) data.getSerializableExtra("Interval");
                mIntervals.add(returnedInterval);
                intervalAdapter.notifyItemInserted(intervalAdapter.getItemCount());
            }
        }
    }

    public void resetIntervalList() {
        mIntervals.clear();
        intervalAdapter.notifyDataSetChanged();
    }


}
