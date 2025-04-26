package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.enviroscan.databinding.ActivitySpeciessummarydescriptionBinding;

public class speciessummarydescription extends AppCompatActivity {
    ActivitySpeciessummarydescriptionBinding binding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speciessummarydescription);
        binding=ActivitySpeciessummarydescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        String description=intent.getStringExtra("description");
        String ecological=intent.getStringExtra("ecological");
        String fact=intent.getStringExtra("fact");
        String physical=intent.getStringExtra("physical");
        String filename=intent.getStringExtra("filename");
        String species=intent.getStringExtra("species");
        binding.description.setText(description);
        binding.ecological.setText(ecological);
        binding.fact.setText(fact);
        binding.physicalchar.setText(physical);
        binding.speciesname.setText(species);
        context=this;
        Glide.with(context)
                .load(Base_Url+"/species/"+intent.getStringExtra("filename"))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.speciesimage);


    }
}