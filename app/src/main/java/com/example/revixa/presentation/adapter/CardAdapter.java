package com.example.revixa.presentation.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revixa.R;
import com.example.revixa.domain.model.Card;
import com.example.revixa.utils.SpacedRepetition;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public interface OnCardClickListener { void onCardClick(Card card); }

    private List<Card> cards;
    private final OnCardClickListener listener;

    public CardAdapter(List<Card> cards, OnCardClickListener listener) {
        this.cards    = cards != null ? cards : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(cards.get(position), listener);
    }

    @Override public int getItemCount() { return cards.size(); }

    public void updateCards(List<Card> newCards) {
        final List<Card> updatedCards = (newCards != null) ? newCards : new ArrayList<>();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() { return cards.size(); }
            @Override public int getNewListSize() { return updatedCards.size(); }
            @Override public boolean areItemsTheSame(int o, int n) {
                return cards.get(o).getId() == updatedCards.get(n).getId();
            }
            @Override public boolean areContentsTheSame(int o, int n) {
                return cards.get(o).getTitle().equals(updatedCards.get(n).getTitle())
                    && cards.get(o).getMasteryLevel() == updatedCards.get(n).getMasteryLevel();
            }
        });
        cards = updatedCards;
        result.dispatchUpdatesTo(this);
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvDifficulty, tvMastery, tvNextReview, tvPriority;
        View     priorityBar;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle     = itemView.findViewById(R.id.tv_card_title);
            tvCategory  = itemView.findViewById(R.id.tv_card_category);
            tvDifficulty= itemView.findViewById(R.id.tv_card_difficulty);
            tvMastery   = itemView.findViewById(R.id.tv_card_mastery);
            tvNextReview= itemView.findViewById(R.id.tv_next_review);
            tvPriority  = itemView.findViewById(R.id.tv_card_priority);
            priorityBar = itemView.findViewById(R.id.view_priority_bar);
        }

        void bind(Card card, OnCardClickListener listener) {
            tvTitle.setText(card.getTitle());
            tvCategory.setText(card.getCategoryName() != null ? card.getCategoryName() : "General");
            tvDifficulty.setText(card.getDifficultyLabel());
            tvMastery.setText("Mastery: " + card.getMasteryLevel() + "%");
            tvNextReview.setText(SpacedRepetition.getNextReviewLabel(card.getIntervalDays()));
            tvPriority.setText(card.getPriorityLabel());

            // Priority bar color
            int barColor;
            switch (card.getPriority()) {
                case 2:  barColor = Color.parseColor("#EF5350"); break; // High = red
                case 1:  barColor = Color.parseColor("#FFA726"); break; // Med  = amber
                default: barColor = Color.parseColor("#66BB6A"); break; // Low  = green
            }
            priorityBar.setBackgroundColor(barColor);

            itemView.setOnClickListener(v -> { if (listener != null) listener.onCardClick(card); });
        }
    }
}
