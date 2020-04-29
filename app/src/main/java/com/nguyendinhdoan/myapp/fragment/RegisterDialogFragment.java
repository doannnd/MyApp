package com.nguyendinhdoan.myapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyendinhdoan.myapp.base.BaseDialogFragment;
import com.nguyendinhdoan.myapp.base.OnSingleClickListener;
import com.nguyendinhdoan.myapp.databinding.DialogFragmentRegisterBinding;
import com.nguyendinhdoan.myapp.data.models.UserDTO;
import com.nguyendinhdoan.myapp.utils.Constance;
import com.nguyendinhdoan.myapp.data.sharepref.SharePref;

public class RegisterDialogFragment extends BaseDialogFragment {

    private DialogFragmentRegisterBinding binding;

    private DatabaseReference users;
    private FirebaseUser firebaseUser;
    private OnResisterSuccessListener listener;
    private SharePref sharePref;

    public static RegisterDialogFragment newInstance(FirebaseUser firebaseUser) {
        RegisterDialogFragment fragment = new RegisterDialogFragment();
        fragment.firebaseUser = firebaseUser;
        return fragment;
    }

    public void setOnResisterSuccessListener(OnResisterSuccessListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DialogFragmentRegisterBinding.inflate(inflater, container, false);
        setUpDialog(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        initEvent();
    }


    private String getName() {
        if (binding.edtName.getText() != null) {
            return binding.edtName.getText().toString();
        }
        throw new IllegalArgumentException("#getName error");
    }

    private String getAddress() {
        if (binding.edtAddress.getText() != null) {
            return binding.edtAddress.getText().toString();
        }
        throw new IllegalArgumentException("#getAddress error");
    }

    private String getPhone() {
        if (binding.edtPhone.getText() != null) {
            return binding.edtPhone.getText().toString();
        }
        throw new IllegalArgumentException("#getPhone error");
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    protected void initView() {
        if (firebaseUser != null) {
            binding.edtPhone.setText(firebaseUser.getPhoneNumber());
        }
    }

    @Override
    protected void initData() {
        users = FirebaseDatabase.getInstance().getReference(Constance.TABLE.USER);
        sharePref = new SharePref(activity);
    }

    @Override
    protected void initEvent() {
        binding.btnCancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
            }
        });

        binding.btnRegister.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if (TextUtils.isEmpty(getName())) {
            binding.edtName.setError("enter name");
            return;
        }

        if (TextUtils.isEmpty(getAddress())) {
            binding.edtAddress.setError("enter address");
            return;
        }

        if (TextUtils.isEmpty(getPhone())) {
            binding.edtPhone.setError("enter phone");
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(getName());
        userDTO.setAddress(getAddress());
        userDTO.setPhone(getPhone());

        if (firebaseUser != null) {
            userDTO.setUId(firebaseUser.getUid());
        }

        showLoading();
        // insert db
        users.child(userDTO.getUId()).setValue(userDTO)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        hideLoading();

                        if (listener != null) {
                            listener.onRegisterSuccess();
                        }
                        sharePref.saveUser(userDTO);

                        dismiss();
                    } else {
                        if (task.getException() != null) {
                            hideLoading();
                            toast(task.getException().getMessage());
                        }
                    }
                });
    }

    public interface OnResisterSuccessListener {
        void onRegisterSuccess();
    }
}
