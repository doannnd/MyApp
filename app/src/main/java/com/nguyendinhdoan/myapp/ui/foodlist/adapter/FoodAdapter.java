package com.nguyendinhdoan.myapp.ui.foodlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nguyendinhdoan.myapp.data.models.FoodDTO;
import com.nguyendinhdoan.myapp.databinding.ItemFoodListBinding;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodDTO> foodList;
    private ItemFoodListBinding binding;

    public FoodAdapter(List<FoodDTO> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemFoodListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodDTO foodDTO = foodList.get(position);
        if (foodDTO != null) {
            Glide.with(holder.itemView.getContext())
                    .load(foodDTO.getImage())
                    .into(binding.ivFoodImage);

            binding.tvFoodPrice.setText("$" + foodDTO.getPrice() + "");
            binding.tvFoodName.setText(foodDTO.getName());
        }
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
