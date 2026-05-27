package com.example.revixa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.revixa.presentation.ui.splash.SplashActivity;

/**
 * Entry point that immediately delegates to SplashActivity.
 * Kept to maintain the folder structure requested in the spec.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
