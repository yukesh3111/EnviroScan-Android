package com.example.enviroscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityEditProfileBinding;
import com.example.enviroscan.serverresponse.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences share = getSharedPreferences("session", MODE_PRIVATE);
        String user = share.getString("username", null);
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.editbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateMyData()) {
                    Call<CommonResponse> call = RetroClient.getInterface().editProfile(user, binding.inputname.getText().toString(), binding.inputsurename.getText().toString(), binding.inputphoneno.getText().toString(), binding.inputemail.getText().toString(), binding.inputaddress.getText().toString(), binding.inputcity.getText().toString(), binding.inputstate.getText().toString());
                    call.enqueue(new Callback<CommonResponse>() {
                        @SuppressLint("MissingInflatedId")
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSucces().equals("true")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                                    View customView = getLayoutInflater().inflate(R.layout.dialogbox_success, null);
                                    builder.setView(customView);
                                    customView.findViewById(R.id.homebnt).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(EditProfileActivity.this, Navbarprofile.class));
                                        }
                                    });
// Customize dialog elements (e.g., buttons, text views) using `customView`
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable throwable) {
                            Toast.makeText(EditProfileActivity.this, "Error Occur in Server Side", Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            }


        });


    }

    private boolean validateMyData() {
        String  name, surename, phoneno, email, address, city, state, password;

        name = binding.inputname.getText().toString();
        surename = binding.inputsurename.getText().toString();
        phoneno = binding.inputphoneno.getText().toString();
        email = binding.inputemail.getText().toString();
        address = binding.inputaddress.getText().toString();
        city = binding.inputcity.getText().toString();
        state = binding.inputstate.getText().toString();


        if (name.isEmpty()) {
            binding.inputname.setError("Please Enter name");
            return false;
        }
        if (surename.isEmpty()) {
            binding.inputsurename.setError("Please Enter Surename");
            return false;
        }
        if (phoneno.isEmpty()) {
            binding.inputphoneno.setError("Please Enter Phone Number");
            return false;
        }
        if (email.isEmpty()) {
            binding.inputemail.setError("Please Enter Email");
            return false;
        }
        if (address.isEmpty()) {
            binding.inputaddress.setError("Please Enter Address");
            return false;
        }
        if (city.isEmpty()) {
            binding.inputcity.setError("Please Enter City");
            return false;
        }
        if (state.isEmpty()) {
            binding.inputstate.setError("Please Enter State");
            return false;
        }

        return true;
    }
}