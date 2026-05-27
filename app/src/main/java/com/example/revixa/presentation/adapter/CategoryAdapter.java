package com.example.revixa.presentation.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revixa.R;
import com.example.revixa.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryClickListener { void onCategoryClick(Category category); }

    private List<Category> categories;
    private final OnCategoryClickListener listener;

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories != null ? categories : new ArrayList<>();
        this.listener   = listener;
    }

    @NonNull @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position), listener);
    }

    @Override public int getItemCount() { return categories.size(); }

    public void updateCategories(List<Category> newList) {
        categories = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvName, tvCount;
        CardView cardView;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon   = itemView.findViewById(R.id.tv_category_icon);
            tvName   = itemView.findViewById(R.id.tv_category_name);
            tvCount  = itemView.findViewById(R.id.tv_category_count);
            cardView = itemView.findViewById(R.id.card_category);
        }

        void bind(Category category, OnCategoryClickListener listener) {
            tvIcon.setText(category.getIcon() != null ? category.getIcon() : "📚");
            tvName.setText(category.getName());
            // Show real card count
            int count = category.getCardCount();
            tvCount.setText(count == 1 ? "1 card" : count + " cards");

            try {
                if (category.getColor() != null && !category.getColor().isEmpty()) {
                    int color = Color.parseColor(category.getColor());
                    int tinted = Color.argb(40,
                        Color.red(color), Color.green(color), Color.blue(color));
                    cardView.setCardBackgroundColor(tinted);
                }
            } catch (IllegalArgumentException ignored) {}

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onCategoryClick(category);
            });
        }
    }
}
