package com.example.revixa.presentation.ui.revision;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.revixa.R;
import com.example.revixa.domain.model.Card;
import com.example.revixa.utils.Constants;
import com.example.revixa.utils.SpacedRepetition;

import java.util.List;

public class RevisionActivity extends AppCompatActivity {

    private RevisionViewModel viewModel;

    private TextView     tvQuestion, tvAnswer, tvCategory, tvProgress,
                         tvCardCounter, tvHard, tvMed, tvEasy;
    private Button       btnReveal, btnHard, btnMedium, btnEasy, btnSkip;
    private LinearLayout llRating, llEmpty, llSession;
    private ProgressBar  progressBar;
    private View         cardView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        viewModel = new ViewModelProvider(this).get(RevisionViewModel.class);

        long categoryId = getIntent().getLongExtra(Constants.EXTRA_CATEGORY_ID, -1);

        bindViews();
        setupGestures();
        setupListeners();
        observeViewModel();

        viewModel.loadDueCards(categoryId > 0 ? categoryId : -1);
    }

    private void bindViews() {
        tvQuestion    = findViewById(R.id.tv_question);
        tvAnswer      = findViewById(R.id.tv_answer);
        tvCategory    = findViewById(R.id.tv_card_category);
        tvProgress    = findViewById(R.id.tv_progress_label);
        tvCardCounter = findViewById(R.id.tv_card_counter);
        tvHard        = findViewById(R.id.tv_hard_count);
        tvMed         = findViewById(R.id.tv_med_count);
        tvEasy        = findViewById(R.id.tv_easy_count);
        btnReveal     = findViewById(R.id.btn_reveal);
        btnHard       = findViewById(R.id.btn_hard);
        btnMedium     = findViewById(R.id.btn_medium);
        btnEasy       = findViewById(R.id.btn_easy);
        btnSkip       = findViewById(R.id.btn_skip);
        llRating      = findViewById(R.id.ll_rating);
        llEmpty       = findViewById(R.id.ll_empty);
        llSession     = findViewById(R.id.ll_session_complete);
        progressBar   = findViewById(R.id.progress_bar);
        cardView      = findViewById(R.id.card_flashcard);
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void setupGestures() {
        gestureDetector = new GestureDetector(this,
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velX, float velY) {
                    if (e1 == null || e2 == null) return false;
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > 100 && Math.abs(velX) > 100) {
                        if (diffX < 0) viewModel.skipCard();
                        else           viewModel.goToPrevious();
                        return true;
                    }
                    return false;
                }
            });
        cardView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void setupListeners() {
        btnReveal.setOnClickListener(v -> viewModel.revealAnswer());
        btnSkip.setOnClickListener(v -> viewModel.skipCard());
        btnHard.setOnClickListener(v -> {
            viewModel.rateCard(SpacedRepetition.RATING_HARD);
            updateRateCounts();
        });
        btnMedium.setOnClickListener(v -> {
            viewModel.rateCard(SpacedRepetition.RATING_MEDIUM);
            updateRateCounts();
        });
        btnEasy.setOnClickListener(v -> {
            viewModel.rateCard(SpacedRepetition.RATING_EASY);
            updateRateCounts();
        });
        findViewById(R.id.btn_finish_session).setOnClickListener(v -> finish());
    }

    private void observeViewModel() {
        viewModel.getDueCards().observe(this, cards -> {
            if (cards == null || cards.isEmpty()) showEmpty();
            else hideEmpty();
        });

        viewModel.getCurrentIndex().observe(this, index -> {
            List<Card> cards = viewModel.getDueCards().getValue();
            if (cards == null || cards.isEmpty()) return;
            int total = cards.size();
            int idx   = index != null ? index : 0;
            if (idx >= total) return;

            Card card = cards.get(idx);
            tvQuestion.setText(card.getQuestion());
            tvAnswer.setText(card.getAnswer() != null ? card.getAnswer() : "");

            String catName = card.getCategoryName();
            tvCategory.setText((catName != null && !catName.isEmpty()) ? catName : "General");
            tvCardCounter.setText((idx + 1) + " / " + total);

            int progress = (int) (((float)(idx + 1) / total) * 100);
            progressBar.setProgress(progress);
            tvProgress.setText(progress + "%");

            // Reset for new card
            tvAnswer.setVisibility(View.GONE);
            btnReveal.setVisibility(View.VISIBLE);
            llRating.setVisibility(View.GONE);

            cardView.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        });

        viewModel.getAnswerVisible().observe(this, visible -> {
            if (Boolean.TRUE.equals(visible)) {
                tvAnswer.setVisibility(View.VISIBLE);
                btnReveal.setVisibility(View.GONE);
                llRating.setVisibility(View.VISIBLE);
                tvAnswer.startAnimation(
                    AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            }
        });

        viewModel.getSessionDone().observe(this, done -> {
            if (Boolean.TRUE.equals(done)) showSessionComplete();
        });
    }

    private void updateRateCounts() {
        tvHard.setText("😖 Hard: " + viewModel.getHardCount());
        tvMed.setText( "😐 Med: "  + viewModel.getMedCount());
        tvEasy.setText("😊 Easy: " + viewModel.getEasyCount());
    }

    private void showEmpty() {
        llEmpty.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        btnSkip.setVisibility(View.GONE);
        btnReveal.setVisibility(View.GONE);
        llRating.setVisibility(View.GONE);
    }

    private void hideEmpty() {
        llEmpty.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        btnSkip.setVisibility(View.VISIBLE);
    }

    private void showSessionComplete() {
        llSession.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        btnSkip.setVisibility(View.GONE);
        llRating.setVisibility(View.GONE);
        btnReveal.setVisibility(View.GONE);

        TextView tvSummary = findViewById(R.id.tv_session_summary);
        tvSummary.setText(
            "Session Complete! 🎉\n\n"
            + "😖 Hard: "   + viewModel.getHardCount()  + "\n"
            + "😐 Medium: " + viewModel.getMedCount()   + "\n"
            + "😊 Easy: "   + viewModel.getEasyCount()  + "\n\n"
            + "+" + viewModel.getXpEarned() + " XP earned");
    }
}
