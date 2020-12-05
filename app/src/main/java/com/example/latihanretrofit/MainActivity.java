package com.example.latihanretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.latihanretrofit.adapters.RepoListAdapter;
import com.example.latihanretrofit.databinding.ActivityMainBinding;
import com.example.latihanretrofit.models.Repo;
import com.example.latihanretrofit.services.GithubServices;
import com.example.latihanretrofit.ui.CustomLoadingDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RepoListAdapter mAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        CustomLoadingDialog mCustomLoadingDialog = new CustomLoadingDialog(MainActivity.this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        binding.btCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEtUsername()) {
                    mCustomLoadingDialog.startLoading();

                    GithubServices services = retrofit.create(GithubServices.class);
                    Call<List<Repo>> repos = services.listRepos(binding.etUsername.getText().toString());

                    repos.enqueue(new Callback<List<Repo>>() {
                        @Override
                        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                            if (response.body() != null) {
                                generateDataList(response.body());
                                mCustomLoadingDialog.dismissLoading();
                            } else {
                                mCustomLoadingDialog.dismissLoading();
                                showSnackbar(view, "Error! Username yang dicari tidak tersedia");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Repo>> call, Throwable t) {
                            mCustomLoadingDialog.dismissLoading();
                            showSnackbar(view, "Error! Coba lagi nanti");
                        }
                    });
                }
            }
        });
    }

    private void showSnackbar(View view, String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    private void generateDataList(List<Repo> reposList) {
        mAdapter = new RepoListAdapter(reposList);
        binding.recyclerview.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.recyclerview.setLayoutManager(layoutManager);
    }

    private boolean validateEtUsername() {
        boolean isError = false;
        if (TextUtils.isEmpty(binding.etUsername.getText().toString().trim())) {
            binding.etUsername.setError("Ups! Usernamenya masih kosong loh");
            isError = true;
        }
        return isError;
    }
}