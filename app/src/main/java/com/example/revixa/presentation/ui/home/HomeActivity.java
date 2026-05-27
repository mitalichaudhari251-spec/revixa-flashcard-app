package com.example.revixa.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.revixa.R;
import com.example.revixa.presentation.adapter.CardAdapter;
import com.example.revixa.presentation.adapter.CategoryAdapter;
import com.example.revixa.presentation.ui.community.CommunityActivity;
import com.example.revixa.presentation.ui.create.CreateCardActivity;
import com.example.revixa.presentation.ui.progress.ProgressActivity;
import com.example.revixa.presentation.ui.revision.RevisionActivity;
import com.example.revixa.utils.Constants;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel     viewModel;
    private CardAdapter       cardAdapter;
    private CategoryAdapter   categoryAdapter;

    private TextView             tvDueCount, tvStreak, tvXp, tvTotalCards;
    private RecyclerView         rvCards, rvCategories;
    private EditText             etSearch;
    private ImageButton          btnDarkMode;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        bindViews();
        setupRecyclers();
        setupListeners();
        observeData();
    }

    private void bindViews() {
        tvDueCount   = findViewById(R.id.tv_due_count);
        tvStreak     = findViewById(R.id.tv_streak);
        tvXp         = findViewById(R.id.tv_xp);
        tvTotalCards = findViewById(R.id.tv_total_cards);
        rvCards      = findViewById(R.id.rv_cards);
        rvCategories = findViewById(R.id.rv_categories);
        etSearch     = findViewById(R.id.et_search);
        btnDarkMode  = findViewById(R.id.btn_dark_mode);
        fab          = findViewById(R.id.fab_create);
        refreshStats();
    }

    private void refreshStats() {
        tvStreak.setText("🔥 " + viewModel.getStreak());
        tvXp.setText(viewModel.getTotalXp() + " XP");
    }

    private void setupRecyclers() {
        cardAdapter = new CardAdapter(new ArrayList<>(), card -> {
            Intent i = new Intent(this, CreateCardActivity.class);
            i.putExtra(Constants.EXTRA_CARD_ID, card.getId());
            i.putExtra(Constants.EXTRA_EDIT_MODE, true);
            startActivity(i);
        });
        rvCards.setLayoutManager(new LinearLayoutManager(this));
        rvCards.setAdapter(cardAdapter);
        rvCards.setNestedScrollingEnabled(false);

        categoryAdapter = new CategoryAdapter(new ArrayList<>(), cat -> {
            Intent i = new Intent(this, RevisionActivity.class);
            i.putExtra(Constants.EXTRA_CATEGORY_ID, cat.getId());
            startActivity(i);
        });
        rvCategories.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupListeners() {
        fab.setOnClickListener(v ->
            startActivity(new Intent(this, CreateCardActivity.class)));

        btnDarkMode.setOnClickListener(v -> {
            boolean dark = !viewModel.isDarkMode();
            viewModel.setDarkMode(dark);
            AppCompatDelegate.setDefaultNightMode(
                dark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        findViewById(R.id.btn_start_revision).setOnClickListener(v ->
            startActivity(new Intent(this, RevisionActivity.class)));

        findViewById(R.id.btn_progress).setOnClickListener(v ->
            startActivity(new Intent(this, ProgressActivity.class)));

        findViewById(R.id.btn_community).setOnClickListener(v ->
            startActivity(new Intent(this, CommunityActivity.class)));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                String q = s.toString().trim();
                if (q.isEmpty()) observeAllCards();
                else viewModel.searchCards(q).observe(HomeActivity.this, cards ->
                    cardAdapter.updateCards(cards != null ? cards : new ArrayList<>()));
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void observeData() {
        // Due today = all cards whose nextReviewDate <= now
        viewModel.getDueCardCount().observe(this, count -> {
            int c = count != null ? count : 0;
            tvDueCount.setText(c == 0 ? "No cards due today" : c + " card(s) due today");
        });

        viewModel.getTotalCardCount().observe(this, count ->
            tvTotalCards.setText(String.valueOf(count != null ? count : 0)));

        viewModel.getAllCategories().observe(this, categories -> {
            if (categories != null) categoryAdapter.updateCategories(categories);
        });

        observeAllCards();
    }

    private void observeAllCards() {
        viewModel.getAllCards().observe(this, cards -> {
            if (cards != null) cardAdapter.updateCards(cards);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStats();
    }
}
