package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoTutorialActivity extends YouTubeBaseActivity {

    YouTubePlayerView videoView;
    String url,api_key = "AIzaSyAb1L4C-QMtf4b4qcAxcUt5p-yfJu9-euA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorial);

        url = getIntent().getExtras().getString("video_link");

        videoView = findViewById(R.id.video_tutorial);
        videoView.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(url);
                youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}