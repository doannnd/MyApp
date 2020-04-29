package com.nguyendinhdoan.myapp.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nguyendinhdoan.myapp.databinding.ItemPopularCategoryBinding;
import com.nguyendinhdoan.myapp.data.models.PopularCategoryDTO;

import java.util.List;

public class PopularCategoryAdapter extends RecyclerView.Adapter<PopularCategoryAdapter
        .PopularCategoryViewHolder> {

    private List<PopularCategoryDTO> items;

    private ItemPopularCategoryBinding binding;

    public PopularCategoryAdapter(List<PopularCategoryDTO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemPopularCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()));
        return new PopularCategoryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PopularCategoryViewHolder holder, int position) {
        PopularCategoryDTO item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PopularCategoryViewHolder extends RecyclerView.ViewHolder {
        PopularCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(PopularCategoryDTO item) {
            // image
            Glide.with(itemView.getContext())
                    .load(item.getImage())
                    .into(binding.ivCategoryImage);

            // content
            binding.tvCategoryName.setText(item.getName());
        }
    }
}
