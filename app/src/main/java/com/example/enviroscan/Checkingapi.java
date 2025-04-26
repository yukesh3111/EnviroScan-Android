package com.example.enviroscan;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityCheckingapiBinding;
import com.example.enviroscan.serverresponse.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkingapi extends AppCompatActivity {
    ActivityCheckingapiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCheckingapiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Submit button click listener
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure that username and password are not empty
                if (validateFields()) {
                    // Send login request using Retrofit
                    Call<CommonResponse> call = RetroClient.getInterface().login(
                            binding.username.getText().toString(),
                            binding.password.getText().toString()
                    );

                    // Make API call
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.isSuccessful()) {
                                // Handle success, checking the message response
                                CommonResponse responseBody = response.body();
                                if (responseBody != null) {
                                    if (response.body().getSucces().equals("true")) {
                                        // Successful login, display username
                                        binding.check.setText("Welcome, " + responseBody.getUsername());
                                    } else {
                                        // Invalid credentials
                                        Toast.makeText(Checkingapi.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // Handle unsuccessful response, possibly due to network issues or wrong server response
                                Toast.makeText(Checkingapi.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable throwable) {
                            // Handle failure to connect (e.g., network issues)
                            binding.check.setText(""+throwable.getMessage());
                        }
                    });
                }
            }
        });
    }

    // Method to validate input fields (username and password)
    private boolean validateFields() {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        if (username.isEmpty()) {
            binding.username.setError("Username is required.");
            return false;
        }

        if (password.isEmpty()) {
            binding.password.setError("Password is required.");
            return false;
        }

        return true;
    }
}
