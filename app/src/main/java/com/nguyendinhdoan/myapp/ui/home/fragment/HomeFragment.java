package com.nguyendinhdoan.myapp.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.databinding.FragmentHomeBinding;
import com.nguyendinhdoan.myapp.ui.home.adapter.BestDealAdapter;
import com.nguyendinhdoan.myapp.ui.home.adapter.PopularCategoryAdapter;
import com.nguyendinhdoan.myapp.ui.home.viewmodel.HomeViewModel;
import com.zhpan.indicator.enums.IndicatorStyle;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private PopularCategoryAdapter adapter;

    private LayoutAnimationController animationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initialViewModel();
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initialLayoutAnimation();
        initialPopularCategory();
        initialBestDeal();

        return binding.getRoot();
    }

    private void initialBestDeal() {
        homeViewModel.getBestDealList().observe(getViewLifecycleOwner(), list -> {
            BestDealAdapter bestDealAdapter = new BestDealAdapter();
            binding.bannerBestDeal.setAutoPlay(true)
                    .setScrollDuration(800)
                    .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                    .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dimen_4))
                    .setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.dimen_4),
                            getResources().getDimensionPixelOffset(R.dimen.dimen_10))
                    .setIndicatorSliderColor(getResources().getColor(R.color.white),
                            getResources().getColor(R.color.gray))
                    .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                    .setInterval(2000)
                    .setRoundCorner(30)
                    .setAdapter(bestDealAdapter).create(list);
        });
    }

    private void initialPopularCategory() {
        homeViewModel.getPopularCategoryList().observe(getViewLifecycleOwner(), list -> {
            binding.rvPopularCategories.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            binding.rvPopularCategories.setLayoutManager(layoutManager);

            adapter = new PopularCategoryAdapter(list);
            binding.rvPopularCategories.setAdapter(adapter);
            binding.rvPopularCategories.setLayoutAnimation(animationController);
        });
    }

    private void initialViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initialLayoutAnimation() {
        animationController = AnimationUtils.loadLayoutAnimation(getActivity(),
                R.anim.layout_item_from_to_left);
    }
}
