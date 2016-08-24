package com.example.jameschee.babystat;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {

    RelativeLayout element;
    TextView dataWelcomeText;
    TextView dataText;
    ImageView statusView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        element = (RelativeLayout)findViewById(R.id.container);
        dataWelcomeText = (TextView)findViewById(R.id.data_welcome);
        dataText = (TextView)findViewById(R.id.data_text);
        statusView = (ImageView) findViewById(R.id.statusView);

        String a1;
        Bundle bundle = getIntent().getExtras();
        a1 = bundle.getString("message_data");
        settingText(a1);
    }

    public void settingText(String a){
        int deliveredInt;
        deliveredInt = Integer.parseInt(a.replaceAll("[\\D]",""));

        if(deliveredInt==10){
            dataText.setText("아이가 서있습니다");
            statusView.setImageResource(R.drawable.standing);
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.standing);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
            element.setBackgroundColor(getResources().getColor(R.color.standing));
        }else if(deliveredInt==20){
            dataText.setText("아이가 걷고 있습니다");
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.walking);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
        }else if(deliveredInt==30){
            dataText.setText("아이가 감지할 수 없는 행동을 하고 있습니다");
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.error);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
        }else if(deliveredInt==40){
            dataText.setText("아이가 넘어졌습니다");
            statusView.setImageResource(R.drawable.falling);
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.falling);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
            element.setBackgroundColor(getResources().getColor(R.color.falling));
        }else if(deliveredInt==50){
            dataText.setText("아이가 가볍게 뛰고 있습니다");
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.walking);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
        }else if(deliveredInt==60){
            dataText.setText("아이가 활발하게 뛰고 있습니다");
            statusView.setImageResource(R.drawable.running);
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.running);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
            element.setBackgroundColor(getResources().getColor(R.color.running));
            dataWelcomeText.setTextColor(Color.BLACK);
        }else{
            dataText.setText("오류가 발생하였습니다");
            mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.error);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(ReceiveActivity.this,R.raw.restart);
                    mediaPlayer.start();
                }
            });
        }
    }
}
