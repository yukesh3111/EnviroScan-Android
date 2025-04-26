package com.example.enviroscan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityHomeBinding;
import com.example.enviroscan.serverresponse.UserData;

import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity{

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<CarouselItem> list = new ArrayList<>();



        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ImageUploadActivity.class));
            }
        });
        binding.menubnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, Navbarprofile.class));
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, comparewithexistupload.class));
            }
        });
        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, greenspace_image_upload.class));
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, species_image_upload.class));
            }
        });
        binding.loadingAnimation.setVisibility(View.VISIBLE);
        binding.darkOverlay.setVisibility(View.VISIBLE);
        Call<UserData> userDataCall = RetroClient.getInterface().getUserData();
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                if(response.isSuccessful()){
                    if(response.body()!=null){



                        list.add(new CarouselItem("https://th.bing.com/th/id/OIP.N6Q8f_DV_Zk99VeCzU75cQHaE7?w=1600&h=1064&rs=1&pid=ImgDetMain"," "+response.body().getArticle1_title()+response.body().getMax1()));
                        list.add(new CarouselItem("https://images.unsplash.com/photo-1547140741-00d6fd251528?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mzh8fGZvcmVzdHxlbnwwfHwwfHx8MA%3D%3D"," "+response.body().getArticle2_title()+response.body().getMax2()));
                        list.add(new CarouselItem("https://images.unsplash.com/photo-1568864453925-206c927dab0a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NjB8fGZvcmVzdHxlbnwwfHwwfHx8MA%3D%3D"," "+response.body().getArticle3_title()+response.body().getMax3()));

                        binding.carousel.setData(list);
                        binding.loadingAnimation.setVisibility(View.GONE);
                        binding.darkOverlay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable throwable) {
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.darkOverlay.setVisibility(View.GONE);
                Log.d("HomeActivity", "onFailure: "+throwable.getMessage());
                Toast.makeText(HomeActivity.this, "response failed" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.carousel.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {

            }


            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {


                //Toast.makeText(HomeActivity.this, ""+carouselItem.getCaption(), Toast.LENGTH_SHORT).show();

                String caption=carouselItem.getCaption();
                int lenth = caption.length();
                String idstr = String.valueOf(caption.charAt(lenth-1));
                Intent intent = new Intent(getApplicationContext(), AritcleActivity.class);
                intent.putExtra("message_key",idstr + "");

                startActivity(intent);
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.darkOverlay.setVisibility(View.GONE);

            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });

    }

}