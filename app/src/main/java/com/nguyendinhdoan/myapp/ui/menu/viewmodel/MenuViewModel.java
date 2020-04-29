package com.nguyendinhdoan.myapp.ui.menu.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.myapp.data.models.CategoryDTO;
import com.nguyendinhdoan.myapp.data.models.PopularCategoryDTO;
import com.nguyendinhdoan.myapp.utils.Constance;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<List<CategoryDTO>> categoryList = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public MenuViewModel() {
        loadCategoryMenu();
    }

    public LiveData<List<CategoryDTO>> getCategoryList() {
        return categoryList;
    }

    private void loadCategoryMenu() {
        List<CategoryDTO> categoryData = new ArrayList<>();
        DatabaseReference popularTable = firebaseDatabase
                .getReference(Constance.TABLE.CATEGORY_MENU);
        popularTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    CategoryDTO categoryDTO = data.getValue(CategoryDTO.class);
                    categoryData.add(categoryDTO);
                }
                categoryList.setValue(categoryData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}