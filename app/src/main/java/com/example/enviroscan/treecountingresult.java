package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.enviroscan.databinding.ActivityTreecountingresultBinding;

public class treecountingresult extends AppCompatActivity {
    ActivityTreecountingresultBinding binding;
    TextView treecounts;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_treecountingresult);
        binding = ActivityTreecountingresultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        context = this;

        Intent intent=getIntent();
        String str=intent.getStringExtra("message_key");
        String filename=intent.getStringExtra("filename");

        binding.treecount.setText(str);
        Toast.makeText(this, " "+str, Toast.LENGTH_SHORT).show();

        Glide.with(context)
                .load(Base_Url+"/image/"+filename)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.predictionImg);




    }
}