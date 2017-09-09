package com.francis.gitme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String image_url = intent.getStringExtra("image_url");
        String dev_url = intent.getStringExtra("dev_url");
    }
    /**
     * TODO: Design your profile xml UI the way you want it
     * Follow the steps from the google doc i sent to you via whatsapp to design the UI
     * To display an image on this profile screen check {@link DeveloperAdapter} for Glide usage.
     * You might want to use Glide.into(<The ImageView object>) instead of creating a
     * new BitmapImageViewTarget as that was to create a circular image.
     * Best of luck. Remember Deadline is 16th September.
     */
 }
