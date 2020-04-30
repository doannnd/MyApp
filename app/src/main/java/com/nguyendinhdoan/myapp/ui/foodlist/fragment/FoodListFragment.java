package com.nguyendinhdoan.myapp.ui.foodlist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.data.models.CategoryDTO;
import com.nguyendinhdoan.myapp.data.models.FoodDTO;
import com.nguyendinhdoan.myapp.databinding.FragmentFoodListBinding;
import com.nguyendinhdoan.myapp.ui.foodlist.adapter.FoodAdapter;
import com.nguyendinhdoan.myapp.ui.foodlist.viewmodel.FoodListViewModel;

import java.util.ArrayList;
import java.util.List;


public class FoodListFragment extends Fragment {

    private FoodListViewModel foodListViewModel;
    private FragmentFoodListBinding binding;
    private LayoutAnimationController animationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initialViewModel();
        binding = FragmentFoodListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

   private CategoryDTO categoryDTO = new CategoryDTO();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            categoryDTO = getArguments().getParcelable("foodlist");
            setUpFoodList();

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(categoryDTO.getName());
        }
    }

    private void initialViewModel() {
        animationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_item_from_to_left);
        foodListViewModel = new ViewModelProvider(this).get(FoodListViewModel.class);
    }

    private void setUpFoodList() {
        binding.recyclerFoodList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerFoodList.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(categoryDTO.getFoods());
        binding.recyclerFoodList.setAdapter(adapter);
        binding.recyclerFoodList.setLayoutAnimation(animationController);
    }
}
