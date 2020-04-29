package com.nguyendinhdoan.myapp.ui.foodlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nguyendinhdoan.myapp.databinding.FragmentFoodListBinding;
import com.nguyendinhdoan.myapp.ui.foodlist.viewmodel.FoodListViewModel;


public class FoodListFragment extends Fragment {

    private FoodListViewModel foodListViewModel;
    private FragmentFoodListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initialViewModel();
        binding = FragmentFoodListBinding.inflate(inflater, container, false);
        setUpFoodList();
        return binding.getRoot();
    }

    private void initialViewModel() {
        foodListViewModel = new ViewModelProvider(this).get(FoodListViewModel.class);
    }

    private void setUpFoodList() {

    }
}
