package com.nguyendinhdoan.myapp.ui.menu.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.data.models.CategoryDTO;
import com.nguyendinhdoan.myapp.databinding.FragmentMenuBinding;
import com.nguyendinhdoan.myapp.ui.menu.adapter.CategoryMenuAdapter;
import com.nguyendinhdoan.myapp.ui.menu.custom.SpaceItemDecoration;
import com.nguyendinhdoan.myapp.ui.menu.viewmodel.MenuViewModel;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private FragmentMenuBinding binding;
    private AlertDialog loadingDialog;
    private LayoutAnimationController animationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initialViewModel();
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        initialLoading();
        initialAnimation();
        initialLoadCategory();
        return binding.getRoot();
    }

    private void initialViewModel() {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
    }

    private void initialLoadCategory() {
        loadingDialog.show();
        menuViewModel.getCategoryList().observe(getViewLifecycleOwner(), list -> {
            binding.recyclerMenu.setHasFixedSize(true);
            CategoryMenuAdapter adapter = new CategoryMenuAdapter(list);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (adapter.getItemViewType(position) == CategoryMenuAdapter.NORMAL) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            });

            binding.recyclerMenu.setLayoutManager(layoutManager);
            binding.recyclerMenu.addItemDecoration(new SpaceItemDecoration(8));
            binding.recyclerMenu.setAdapter(adapter);
            binding.recyclerMenu.setLayoutAnimation(animationController);

        });

        loadingDialog.dismiss();
    }

    private void initialAnimation() {
        animationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_item_from_to_left);
    }

    private void initialLoading() {
        loadingDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .setMessage("Loading...")
                .build();
    }
}
