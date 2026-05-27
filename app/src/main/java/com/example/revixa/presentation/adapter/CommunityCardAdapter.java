package com.example.revixa.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revixa.R;
import com.example.revixa.domain.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CommunityCardAdapter extends RecyclerView.Adapter<CommunityCardAdapter.ViewHolder> {

    public interface OnImportClickListener { void onImport(Card card); }

    private List<Card> cards;
    private final OnImportClickListener listener;

    public CommunityCardAdapter(List<Card> cards, OnImportClickListener listener) {
        this.cards    = cards != null ? cards : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_community_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cards.get(position), listener);
    }

    @Override public int getItemCount() { return cards.size(); }

    public void updateCards(List<Card> newCards) {
        cards = newCards != null ? newCards : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvQuestion, tvTags, tvDifficulty;
        Button btnImport;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle      = itemView.findViewById(R.id.tv_community_title);
            tvCategory   = itemView.findViewById(R.id.tv_community_category);
            tvQuestion   = itemView.findViewById(R.id.tv_community_question);
            tvTags       = itemView.findViewById(R.id.tv_community_tags);
            tvDifficulty = itemView.findViewById(R.id.tv_community_difficulty);
            btnImport    = itemView.findViewById(R.id.btn_import_card);
        }

        void bind(Card card, OnImportClickListener listener) {
            tvTitle.setText(card.getTitle());
            tvCategory.setText(card.getCategoryName() != null ? card.getCategoryName() : "General");
            tvQuestion.setText(card.getQuestion());
            tvTags.setText(card.getTags() != null ? "#" + card.getTags().replace(",", "  #") : "");
            tvDifficulty.setText(card.getDifficultyLabel());
            btnImport.setOnClickListener(v -> { if (listener != null) listener.onImport(card); });
        }
    }
}
