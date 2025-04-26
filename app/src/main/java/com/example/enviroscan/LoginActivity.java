package com.example.enviroscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityLoginBinding;
import com.example.enviroscan.serverresponse.CommonResponse;
import com.example.enviroscan.serverresponse.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signuptxtbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        SharedPreferences share=getSharedPreferences("session",MODE_PRIVATE);
        String username= share.getString("username",null);
        if(username!=null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
        binding.loginbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateMyData()) {
                    Call<CommonResponse> call = RetroClient.getInterface().login(binding.loginUsernameInput.getText().toString(), binding.loginPasswordInput.getText().toString());
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSucces().equals("true")) {
                                    SharedPreferences sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
                                    SharedPreferences.Editor edit=sharedPreferences.edit();
                                    edit.putString("username",response.body().getUsername());
                                    edit.commit();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Credientials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable throwable) {

                        }
                    });
                }
            }
        });



    }

    private boolean validateMyData() {
        String username,password;

        username = binding.loginUsernameInput.getText().toString();
        password = binding.loginPasswordInput.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            binding.loginUsernameInput.setError("Please Enter Username and password");
            return false;
        }

        return true;
    }

}