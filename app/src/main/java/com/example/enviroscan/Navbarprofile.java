package com.example.enviroscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityNavbarprofileBinding;
import com.example.enviroscan.serverresponse.Userdetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Navbarprofile extends AppCompatActivity {
    ActivityNavbarprofileBinding  binding;
    TextView username;
    TextView email;
    TextView name;
    TextView surename;
    TextView phoneno;
    TextView address;
    TextView state;
    TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityNavbarprofileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences share=getSharedPreferences("session",MODE_PRIVATE);
        String user=share.getString("username",null);


        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.homebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navbarprofile.this, HomeActivity.class));
            }
        });
        binding.viewarticlebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navbarprofile.this,ViewArticle.class));
            }
        });
        binding.logoutbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share=getSharedPreferences("session",MODE_PRIVATE);
                share.edit().putString("username",null).apply();
                String username= share.getString("username",null);
                        Log.d("Logout", "onClick: "+username);
                Toast.makeText(Navbarprofile.this, ""+username, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Navbarprofile.this,MainActivity.class));
                    finishAffinity();

            }
        });
        binding.aboutusbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navbarprofile.this, AboutusActivity.class));
            }
        });
        binding.editbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navbarprofile.this, EditProfileActivity.class));
            }
        });
        Call<Userdetails> userdetailsCall= RetroClient.getInterface().getUserDetails(user);
        userdetailsCall.enqueue(new Callback<Userdetails>() {
            @Override
            public void onResponse(Call<Userdetails> call, Response<Userdetails> response) {
                username=findViewById(R.id.username);
                email=findViewById(R.id.email);
                name=findViewById(R.id.name);
                surename=findViewById(R.id.surename);
                phoneno=findViewById(R.id.phoneno);
                address=findViewById(R.id.address);
                state=findViewById(R.id.state);
                city=findViewById(R.id.city);
                if(response.isSuccessful()){

                    if(response.body()!=null){
                        username.setText(response.body().getUsername());
                        email.setText(response.body().getEmail());
                        name.setText(response.body().getName());
                        surename.setText(response.body().getSurename());
                        phoneno.setText(response.body().getPhone_no());
                        address.setText(response.body().getAddress());
                        state.setText(response.body().getState());
                        city.setText(response.body().getCity());

                    }
                }


            }

            @Override
            public void onFailure(Call<Userdetails> call, Throwable throwable) {
                Toast.makeText(Navbarprofile.this, "Error Occur in Server Side", Toast.LENGTH_SHORT).show();

            }
        });

    }
}