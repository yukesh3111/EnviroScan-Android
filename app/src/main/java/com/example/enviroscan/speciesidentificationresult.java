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
import com.example.enviroscan.databinding.ActivitySpeciesidentificationresultBinding;

public class speciesidentificationresult extends AppCompatActivity {
    ActivitySpeciesidentificationresultBinding binding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speciesidentificationresult);
        binding=ActivitySpeciesidentificationresultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        context=this;
        Glide.with(context)
                .load(Base_Url+"/species/"+intent.getStringExtra("filename"))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.predictionImg);

        String str1=intent.getStringExtra("message_key1");
        String str2=intent.getStringExtra("message_key2");
        String ecological=intent.getStringExtra("ecological");
        String fact=intent.getStringExtra("fact");
        String physical=intent.getStringExtra("physical");
        binding.speciessummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(speciesidentificationresult.this,speciessummarydescription.class);
                intent1.putExtra("description",str2);
                intent1.putExtra("ecological",ecological);
                intent1.putExtra("fact",fact);
                intent1.putExtra("physical",physical);
                intent1.putExtra("filename",str1);
                intent1.putExtra("species",str1);
                intent1.putExtra("filename",intent.getStringExtra("filename"));
                startActivity(intent1);
            }
        });
        binding.species.setText(str1);
        binding.description1.setText(str2);

    }
}