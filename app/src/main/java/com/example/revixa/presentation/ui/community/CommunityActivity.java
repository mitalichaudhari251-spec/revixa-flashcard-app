package com.example.revixa.presentation.ui.community;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.example.revixa.R;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.presentation.adapter.CommunityCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private CommunityViewModel   viewModel;
    private CommunityCardAdapter adapter;

    private RecyclerView rvDecks;
    private EditText     etSearch;
    private ChipGroup    chipGroup;
    private View         progressBar;

    private List<Card> allCards     = new ArrayList<>();
    private String     activeFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        viewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

        bindViews();
        setupRecycler();
        setupListeners();
        observeViewModel();
    }

    private void bindViews() {
        rvDecks     = findViewById(R.id.rv_community_cards);
        etSearch    = findViewById(R.id.et_search_community);
        chipGroup   = findViewById(R.id.chip_group_filter);
        progressBar = findViewById(R.id.progress_community);
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Add "All" chip by default
        addChip("All", true);
    }

    private void addChip(String label, boolean checked) {
        Chip chip = new Chip(this);
        chip.setText(label);
        chip.setCheckable(true);
        chip.setChecked(checked);
        chip.setChipBackgroundColorResource(R.color.chip_background_selector);
        chip.setOnCheckedChangeListener((v, isChecked) -> {
            if (isChecked) {
                activeFilter = label;
                applyFilter(etSearch.getText().toString().trim());
            }
        });
        chipGroup.addView(chip);
    }

    private void setupRecycler() {
        adapter = new CommunityCardAdapter(new ArrayList<>(), card ->
            viewModel.importCard(card));
        rvDecks.setLayoutManager(new LinearLayoutManager(this));
        rvDecks.setAdapter(adapter);
    }

    private void setupListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                applyFilter(s.toString().trim());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, loading ->
            progressBar.setVisibility(Boolean.TRUE.equals(loading) ? View.VISIBLE : View.GONE));

        viewModel.getCommunityCards().observe(this, cards -> {
            if (cards == null) return;
            allCards = cards;
            applyFilter(etSearch.getText().toString().trim());
        });

        // Build filter chips from real categories that have cards
        viewModel.getAllCategories().observe(this, categories -> {
            if (categories == null) return;
            // Remove old chips except "All"
            chipGroup.removeAllViews();
            addChip("All", activeFilter.equals("All"));
            for (Category cat : categories) {
                if (cat.getCardCount() > 0 || true) { // show all categories
                    addChip(cat.getName(), cat.getName().equals(activeFilter));
                }
            }
        });

        viewModel.getImportStatus().observe(this, msg -> {
            if (msg != null && !msg.isEmpty())
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void applyFilter(String query) {
        String lower = query.toLowerCase();
        List<Card> filtered = new ArrayList<>();
        for (Card c : allCards) {
            boolean subjectOk = activeFilter.equals("All") ||
                (c.getCategoryName() != null && c.getCategoryName().equals(activeFilter));
            boolean queryOk = query.isEmpty() ||
                c.getTitle().toLowerCase().contains(lower) ||
                (c.getTags() != null && c.getTags().toLowerCase().contains(lower));
            if (subjectOk && queryOk) filtered.add(c);
        }
        adapter.updateCards(filtered);
    }
}
