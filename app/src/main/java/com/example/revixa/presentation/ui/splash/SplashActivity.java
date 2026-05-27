package com.example.revixa.presentation.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.example.revixa.R;
import com.example.revixa.presentation.ui.home.HomeActivity;
import com.example.revixa.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION_MS = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply dark/light mode before super.onCreate
        PreferenceManager prefs = PreferenceManager.getInstance(this);
        if (prefs.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // All views are fully visible (no alpha=0)
        // Just add a subtle scale+fade animation for polish
        CardView logoCard  = findViewById(R.id.iv_logo) != null
                ? (CardView) ((View) findViewById(R.id.iv_logo).getParent()) : null;
        TextView tvAppName = findViewById(R.id.tv_app_name);
        TextView tvTagline = findViewById(R.id.tv_tagline);

        animateIn(tvAppName, 0);
        animateIn(tvTagline, 150);

        // Navigate to Home after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_DURATION_MS);
    }

    private void animateIn(View view, long startOffset) {
        if (view == null) return;
        AlphaAnimation fade = new AlphaAnimation(0f, 1f);
        fade.setDuration(600);
        fade.setStartOffset(startOffset);
        fade.setFillAfter(true);
        view.startAnimation(fade);
    }
}