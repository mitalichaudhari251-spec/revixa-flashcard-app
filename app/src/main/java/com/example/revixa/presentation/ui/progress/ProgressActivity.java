package com.example.revixa.presentation.ui.progress;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.example.revixa.R;
import com.example.revixa.domain.model.Category;
import com.example.revixa.presentation.adapter.ProgressCategoryAdapter;
import com.example.revixa.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private ProgressViewModel viewModel;

    private TextView tvStreak, tvXp, tvTotal, tvMastered, tvWeak;
    private BarChart barChart;
    private PieChart pieChart;
    private RecyclerView rvSubjectProgress;
    private ProgressCategoryAdapter progressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        viewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        bindViews();
        setupCharts();
        observeData();

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void bindViews() {
        tvStreak  = findViewById(R.id.tv_streak_value);
        tvXp      = findViewById(R.id.tv_xp_value);
        tvTotal   = findViewById(R.id.tv_total_cards);
        tvMastered= findViewById(R.id.tv_mastered_count);
        tvWeak    = findViewById(R.id.tv_weak_count);
        barChart  = findViewById(R.id.bar_chart);
        pieChart  = findViewById(R.id.pie_chart);
        rvSubjectProgress = findViewById(R.id.rv_subject_progress);

        tvStreak.setText("🔥 " + viewModel.getStreak() + " day streak");
        tvXp.setText(viewModel.getTotalXp() + " XP");

        progressAdapter = new ProgressCategoryAdapter(new ArrayList<>());
        rvSubjectProgress.setLayoutManager(new LinearLayoutManager(this));
        rvSubjectProgress.setAdapter(progressAdapter);
        rvSubjectProgress.setNestedScrollingEnabled(false);
    }

    private void setupCharts() {
        setupBarChart();
        setupPieChart(0, 0, 0);
    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setNoDataText("No revision sessions yet");
        barChart.setNoDataTextColor(Color.GRAY);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(11f);

        String[] days = new String[7];
        for (int i = 6; i >= 0; i--) {
            days[6 - i] = DateUtils.getDayLabel(i);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        barChart.getAxisRight().setEnabled(false);
    }

    private void updateBarChart(List<Integer> dayCounts) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dayCounts.size(); i++) {
            entries.add(new BarEntry(i, dayCounts.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Sessions");
        dataSet.setColor(Color.parseColor("#5C6BC0"));
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.GRAY);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);
        barChart.setData(barData);
        barChart.animateY(500);
        barChart.invalidate();
    }

    private void setupPieChart(int mastered, int weak, int total) {
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(52f);
        pieChart.getLegend().setEnabled(true);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setNoDataText("No cards yet");

        refreshPieChart(mastered, weak, total);
    }

    private void refreshPieChart(int mastered, int weak, int total) {
        int inProgress = Math.max(0, total - mastered - weak);

        List<PieEntry> entries = new ArrayList<>();
        if (mastered > 0)   entries.add(new PieEntry(mastered,   "Mastered"));
        if (inProgress > 0) entries.add(new PieEntry(inProgress, "In Progress"));
        if (weak > 0)       entries.add(new PieEntry(weak,       "Needs Work"));
        if (entries.isEmpty()) {
            entries.add(new PieEntry(1f, "No Cards Yet"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
            Color.parseColor("#4CAF50"),
            Color.parseColor("#5C6BC0"),
            Color.parseColor("#EF5350")
        );
        dataSet.setSliceSpace(3f);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.WHITE);

        pieChart.setData(new PieData(dataSet));
        pieChart.animateY(600);
        pieChart.invalidate();
    }

    private void observeData() {
        viewModel.getTotalCount().observe(this, total -> {
            int t = total != null ? total : 0;
            tvTotal.setText(String.valueOf(t));
        });

        viewModel.getMasteredCount().observe(this, mastered -> {
            int m = mastered != null ? mastered : 0;
            tvMastered.setText(String.valueOf(m));
            Integer weak  = viewModel.getWeakCount().getValue();
            Integer total = viewModel.getTotalCount().getValue();
            int w = weak != null ? weak : 0;
            int t = total != null ? total : 0;
            refreshPieChart(m, w, t);
        });

        viewModel.getWeakCount().observe(this, weak -> {
            int w = weak != null ? weak : 0;
            tvWeak.setText(String.valueOf(w));
        });

        // Real per-category data with actual card counts
        viewModel.getAllCategories().observe(this, categories -> {
            if (categories == null) return;
            // Only show categories that have at least 1 card
            List<Category> withCards = new ArrayList<>();
            for (Category c : categories) {
                if (c.getCardCount() > 0) withCards.add(c);
            }
            progressAdapter.updateCategories(withCards);
        });

        // Build bar chart from real review data
        viewModel.getDayRevisionCounts().observe(this, dayCounts -> {
            if (dayCounts != null && !dayCounts.isEmpty()) {
                updateBarChart(dayCounts);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refresh();
        tvStreak.setText("🔥 " + viewModel.getStreak() + " day streak");
        tvXp.setText(viewModel.getTotalXp() + " XP");
    }
}
