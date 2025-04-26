package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityArticleBinding;
import com.example.enviroscan.serverresponse.Articledetails;
import com.example.enviroscan.serverresponse.UserData;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AritcleActivity extends AppCompatActivity {
    ActivityArticleBinding binding;
    TextView articletitle;
    TextView articleabstraction;
    TextView articlemaincontent;
    TextView articleconclusion;
    ImageView backbnt;
    Context context;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article);
        binding=ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Context context = this;
        String str = intent.getStringExtra("message_key");
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.loadingAnimation.setVisibility(View.VISIBLE);
        binding.darkOverlay.setVisibility(View.VISIBLE);

         Call<Articledetails> articledetailsCall=RetroClient.getInterface().getarticle(str);
        articledetailsCall.enqueue(new Callback<Articledetails>() {
            @Override
            public void onResponse(Call<Articledetails> call, Response<Articledetails> response) {

                if(response.isSuccessful()){
                    if(response.body()!=null){
                        binding.articleheading.setText(response.body().getArticel_title());
                        binding.articleabstraction.setText(response.body().getArticle_abstraction());
                        binding.articleintro.setText(response.body().getArticle_maincontent());
                        binding.articleconclusion.setText(response.body().getArticle_conclusion());
                        binding.domain.setText(response.body().getArticle_domain());
                        binding.date.setText(response.body().getArticle_date());
                        Glide.with(context)
                                .load(Base_Url+"/articleimage/"+response.body().getFilename())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.errorimage)
                                .into(binding.articleimg);
                        binding.loadingAnimation.setVisibility(View.GONE);
                        binding.darkOverlay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Articledetails> call, Throwable throwable) {
                Toast.makeText(AritcleActivity.this, "Error Occur in serverside", Toast.LENGTH_SHORT).show();

            }
        });


    }


}