package com.example.jameschee.babystat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentSender;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements
        MessageApi.MessageListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private TextView welcomeText;

    private GoogleApiClient mGoogleApiClient;
    private MediaPlayer mediaPlayer;
    private BroadcastReceiver mReceiver;


    private static final String TAG = "Mainactivity";
    private static final String SEND_DATA = "/send-data";
    private static final int CONNECTION_TIME_OUT_MS = 5000;

    private String deviceName = "smart watch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing needed components
        welcomeText = (TextView)findViewById(R.id.welcome_text);
        //initializing GoogleAPI_client

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonsendMessage();
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.welcome);
        mediaPlayer.start();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.MessageApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        LOGD(TAG, "Connection to Google API client was suspended");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try {
                connectionResult.startResolutionForResult(this, 1000);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }else{
            Log.e(TAG, "Connection to Google API client has failed");
            Wearable.MessageApi.removeListener(mGoogleApiClient, this);
        }
    }


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        LOGD(TAG, "onMessageReceived() A message from watch was received:");
    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mGoogleApiClient, node.getId(), SEND_DATA, new byte[0]).await();
                }

            }
        }).start();
    }

    public void buttonsendMessage(){
        retrieveDeviceNode();
        Context context = getApplicationContext();
        Toast succeed = Toast.makeText(context,"Finding out my Child's Status",Toast.LENGTH_SHORT);
        succeed.show();
    }

    private static void LOGD(final String tag, String message) {
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public void audioAssistant(String path, String fileName){

    }

}