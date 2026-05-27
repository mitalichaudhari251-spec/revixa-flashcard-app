package com.example.revixa.presentation.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revixa.R;
import com.example.revixa.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ProgressCategoryAdapter extends RecyclerView.Adapter<ProgressCategoryAdapter.ViewHolder> {

    private List<Category> categories;

    public ProgressCategoryAdapter(List<Category> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_progress_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override public int getItemCount() { return categories.size(); }

    public void updateCategories(List<Category> newList) {
        categories = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView    tvIcon, tvName, tvCardCount, tvMastery;
        ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon      = itemView.findViewById(R.id.tv_prog_cat_icon);
            tvName      = itemView.findViewById(R.id.tv_prog_cat_name);
            tvCardCount = itemView.findViewById(R.id.tv_prog_cat_count);
            tvMastery   = itemView.findViewById(R.id.tv_prog_mastery_pct);
            progressBar = itemView.findViewById(R.id.pb_mastery);
        }

        void bind(Category cat) {
            tvIcon.setText(cat.getIcon() != null ? cat.getIcon() : "📚");
            tvName.setText(cat.getName());

            int count = cat.getCardCount();
            tvCardCount.setText(count + " card" + (count == 1 ? "" : "s"));

            int pct = (int) cat.getMasteryPercentage();
            tvMastery.setText(pct + "%");
            progressBar.setProgress(pct);

            int color;
            if (pct >= 70)      color = Color.parseColor("#4CAF50");
            else if (pct >= 40) color = Color.parseColor("#FFA726");
            else                color = Color.parseColor("#EF5350");

            progressBar.getProgressDrawable()
                .setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
