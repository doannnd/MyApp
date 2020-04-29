package com.nguyendinhdoan.myapp.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.databinding.ActivityLoginBinding;
import com.nguyendinhdoan.myapp.ui.main.MainActivity;
import com.nguyendinhdoan.myapp.fragment.RegisterDialogFragment;
import com.nguyendinhdoan.myapp.data.remote.ApiService;
import com.nguyendinhdoan.myapp.data.config.RetrofitClient;
import com.nguyendinhdoan.myapp.utils.Constance;

import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private static final int FIREBASE_AUTH_REQUEST_CODE = 999;

    private AlertDialog loadingDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CompositeDisposable compositeDisposable;
    private ApiService apiService;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference users;
    private List<AuthUI.IdpConfig> authConfigUI;
    private RegisterDialogFragment dialog;

    @Override
    protected void onStart() {
        super.onStart();
        authStateListener.onAuthStateChanged(firebaseAuth);
    }

    @Override
    protected void onStop() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        unSubscribe();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        event();
        initData();
    }

    /**
     * init data table from firebase database
     */
    private void initData() {
        users = firebaseDatabase.getReference(Constance.TABLE.USER);
    }

    private void init() {
        initialApiServices();
        initialFirebaseAuth();
        initialFirebaseDatabase();
        initialFirebaseAuthUI();
        initialLoadingDialog();
    }

    private void initialFirebaseAuthUI() {
        authConfigUI = Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build());
    }

    private void initialFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initialApiServices() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    private void initialLoadingDialog() {
        loadingDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .setMessage("Loading...")
                .build();
    }

    private void initialFirebaseDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void event() {
        eventAuthStateListener();
    }

    /**
     * check realtime authentication user
     * if user != null : ==> toast => main
     * else =>> login account kit => auth custom token
     * => login with firebase auth
     */
    private void eventAuthStateListener() {
        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                eventCheckUserInUserTable(firebaseUser);
            } else {
                login();
            }
        };
    }

    private void eventCheckUserInUserTable(FirebaseUser firebaseUser) {
        loadingDialog.show();
        users.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userDataSnapshot) {
                loadingDialog.dismiss();
                if (isUserRegistered(userDataSnapshot)) {
                    toast(R.string.message_user_exist_in_user_table);

                    goToHomeActivity();
                } else {

                    register(firebaseUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
                toast(databaseError.getMessage());
            }
        });
    }

    private void register(FirebaseUser firebaseUser) {
        if (dialog == null || !dialog.isVisible()) {
            dialog = RegisterDialogFragment.newInstance(firebaseUser);
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }

        dialog.setOnResisterSuccessListener(() -> {
            toast(R.string.message_register_success);

            goToHomeActivity();
        });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * check user registered
     *
     * @param user data snapshot firebase realtime database
     * @return true => exist
     * false => not exist
     */
    private boolean isUserRegistered(DataSnapshot user) {
        return user.exists();
    }

    private void login() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(authConfigUI).build(), FIREBASE_AUTH_REQUEST_CODE);
    }

    /**
     * authentication with custom token
     *
     * @param accountKitToken account kit token
     */
    private void authenticationWithCustomToken(String accountKitToken) {
        loadingDialog.show();

        Disposable disposable = apiService.getCustomToken(accountKitToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    String token = responseBody.string();
                    signInWithFirebase(token);
                }, error -> {
                    loadingDialog.dismiss();
                    toast(error.getMessage());
                });
        addSubscribe(disposable);
    }

    /**
     * sign in with authentication of firebase
     *
     * @param customToken custom token from server
     */
    private void signInWithFirebase(String customToken) {
        firebaseAuth.signInWithCustomToken(customToken)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loadingDialog.dismiss();

                        toast(R.string.message_login_firebase_success);
                    } else {
                        loadingDialog.dismiss();
                        toast(R.string.message_login_firebase_error);
                    }
                });
    }

    /**
     * show message toast ( type: res id)
     *
     * @param stringResId string res id
     */
    private void toast(@StringRes int stringResId) {
        Toast.makeText(this, getString(stringResId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * show message toast (type: string)
     *
     * @param message message
     */
    private void toast(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show();
    }

    private void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
