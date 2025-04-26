package com.example.enviroscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityLoginBinding;
import com.example.enviroscan.databinding.ActivitySignupBinding;
import com.example.enviroscan.serverresponse.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.logintxtbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
        binding.signupbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateMyData()) {
                    Call<CommonResponse> call = RetroClient.getInterface().signup(binding.inputusername.getText().toString(), binding.inputname.getText().toString(), binding.inputsurename.getText().toString(), binding.inputphoneno.getText().toString(), binding.inputemail.getText().toString(), binding.inputpassword.getText().toString(),binding.inputaddress.getText().toString(), binding.inputcity.getText().toString(), binding.inputstate.getText().toString());
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSucces().equals("true")) {
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(SignupActivity.this, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable throwable) {
                            Toast.makeText(SignupActivity.this, "There is a error in serverside", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validateMyData() {
        String username,name,surename,phoneno,email,address,city,state,password;
        username=binding.inputusername.getText().toString();
        name=binding.inputname.getText().toString();
        surename=binding.inputsurename.getText().toString();
        phoneno=binding.inputphoneno.getText().toString();
        email=binding.inputemail.getText().toString();
        address=binding.inputaddress.getText().toString();
        city=binding.inputcity.getText().toString();
        state=binding.inputstate.getText().toString();
        password=binding.inputpassword.getText().toString();
        if(username.isEmpty()){
            binding.inputusername.setError("Please Enter Username");
            return false;
        }
        if(name.isEmpty()){
            binding.inputname.setError("Please Enter name");
            return false;
        }
        if(surename.isEmpty()){
            binding.inputsurename.setError("Please Enter Surename");
            return false;
        }
        if(phoneno.isEmpty()){
            binding.inputphoneno.setError("Please Enter Phone Number");
            return false;
        }
        if(email.isEmpty()){
            binding.inputemail.setError("Please Enter Email");
            return false;
        }
        if(address.isEmpty()){
            binding.inputaddress.setError("Please Enter Address");
            return false;
        }
        if(city.isEmpty()){
            binding.inputcity.setError("Please Enter City");
            return false;
        }
        if(state.isEmpty()){
            binding.inputstate.setError("Please Enter State");
            return false;
        }
        if(password.isEmpty()){
            binding.inputpassword.setError("Please Enter Password");
            return false;
        }
        return true;

    }
}