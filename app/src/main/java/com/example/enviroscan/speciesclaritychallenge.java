package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.enviroscan.databinding.ActivitySpeciesclaritychallengeBinding;

public class speciesclaritychallenge extends AppCompatActivity {
    ActivitySpeciesclaritychallengeBinding binding;
    TextView xmlconfidence1;
    TextView xmlconfidence2;
    TextView xmlconfidence3;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speciesclaritychallenge);
        binding=ActivitySpeciesclaritychallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        String species1=intent.getStringExtra("species1");
        String species2=intent.getStringExtra("species2");
        String species3=intent.getStringExtra("species3");
        String confidecens1=intent.getStringExtra("confidence1");
        String confidecens2=intent.getStringExtra("confidence2");
        String confidecens3=intent.getStringExtra("confidence3");
        binding.species1.setText(species1);
        binding.species2.setText(species2);
        binding.species3.setText(species3);
        xmlconfidence1=findViewById(R.id.confidence1);
        xmlconfidence2=findViewById(R.id.confidence2);
        xmlconfidence3=findViewById(R.id.confidence3);
        xmlconfidence1.setText("Acuracy is "+confidecens1+"%");
        xmlconfidence2.setText("Acuracy is "+confidecens2+"%");
        xmlconfidence3.setText("Acuracy is "+confidecens3+"%");
        context=this;
        Glide.with(context)
                .load(Base_Url+"/species/"+intent.getStringExtra("filename"))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.uploadedimage);
    }
}