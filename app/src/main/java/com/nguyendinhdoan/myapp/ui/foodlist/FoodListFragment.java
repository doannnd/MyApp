package com.nguyendinhdoan.myapp.ui.foodlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.databinding.FragmentFoodListBinding;


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
