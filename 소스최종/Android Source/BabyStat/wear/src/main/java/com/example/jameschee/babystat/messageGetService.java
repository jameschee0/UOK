package com.example.jameschee.babystat;

import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

public class messageGetService extends WearableListenerService {
    GoogleApiClient mGoogleApiClient;

    private static final String SENDing_DATA = "/send-data";


    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        // Check to see if the message is to start an activity
        if (messageEvent.getPath().equals(SENDing_DATA)) {
            Intent startIntent = new Intent(this, MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
        }
    }
}
