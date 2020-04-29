package com.nguyendinhdoan.myapp.ui.menu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nguyendinhdoan.myapp.data.models.CategoryDTO;
import com.nguyendinhdoan.myapp.data.models.CategoryEvent;
import com.nguyendinhdoan.myapp.databinding.ItemCategoryMenuBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CategoryMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NORMAL = 0;
    public static final int FULL_WIDTH = 1;

    private List<CategoryDTO> categorylist;
    private ItemCategoryMenuBinding binding;

    public CategoryMenuAdapter(List<CategoryDTO> categorylist) {
        this.categorylist = categorylist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCategoryMenuBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CategoryMenuViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryDTO categoryDTO = categorylist.get(position);
        if (categoryDTO != null) {
            Glide.with(holder.itemView.getContext())
                    .load(categoryDTO.getImage())
                    .into(binding.ivCategoryMenu);

            binding.tvCategoryMenu.setText(categoryDTO.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     EventBus.getDefault().postSticky(new CategoryEvent(categoryDTO));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (categorylist.size() == 1) {
            return NORMAL;
        } else {
            if (position == categorylist.size() - 1) {
                return FULL_WIDTH;
            } else {
                return NORMAL;
            }
        }
    }

    class CategoryMenuViewHolder extends RecyclerView.ViewHolder {

        public CategoryMenuViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CategoryDTO categoryDTO);
    }
}
