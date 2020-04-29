package com.nguyendinhdoan.myapp.ui.home.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.myapp.data.models.BestDealDTO;
import com.nguyendinhdoan.myapp.data.models.PopularCategoryDTO;
import com.nguyendinhdoan.myapp.utils.Constance;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<PopularCategoryDTO>> popularCategoryList = new MutableLiveData<>();
    private MutableLiveData<List<BestDealDTO>> bestDealList = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public HomeViewModel() {
        loadPopularCategoryList();
        loadBestDealList();
    }

    public MutableLiveData<List<PopularCategoryDTO>> getPopularCategoryList() {
        return popularCategoryList;
    }

    public MutableLiveData<List<BestDealDTO>> getBestDealList() {
        return bestDealList;
    }

    private void loadPopularCategoryList() {
        List<PopularCategoryDTO> popularCategoryData = new ArrayList<>();
        DatabaseReference popularTable = firebaseDatabase
                .getReference(Constance.TABLE.POPULAR_CATEGORIES);
        popularTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    PopularCategoryDTO popularCategoryDTO = data.getValue(PopularCategoryDTO.class);
                    popularCategoryData.add(popularCategoryDTO);
                }
                popularCategoryList.setValue(popularCategoryData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadBestDealList() {
        List<BestDealDTO> bestDealData = new ArrayList<>();
        DatabaseReference popularTable = firebaseDatabase.getReference(Constance.TABLE.BEST_DEAL);
        popularTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BestDealDTO bestDealDTO = data.getValue(BestDealDTO.class);
                    bestDealData.add(bestDealDTO);
                }
                bestDealList.setValue(bestDealData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}