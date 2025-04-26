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
import com.example.enviroscan.databinding.ActivityGreenspaceresultBinding;
import com.example.enviroscan.databinding.ActivityGreenspacesummaryreportBinding;

public class greenspacesummaryreport extends AppCompatActivity {
    ActivityGreenspacesummaryreportBinding binding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_greenspacesummaryreport);
        binding=ActivityGreenspacesummaryreportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        String filename=intent.getStringExtra("filename");
        String greenpercentage=intent.getStringExtra("greenpercentage");
        String landspace=intent.getStringExtra("landspace");
        String bot=intent.getStringExtra("bot");
        String recommentation=intent.getStringExtra("recommentation");
        context=this;
        binding.landpercentage.setText(landspace+"%");
        binding.greenpercentage.setText(greenpercentage+"%");
        binding.keyinsight.setText(bot);
        binding.recommentation.setText(recommentation);
        Glide.with(context)
                .load(Base_Url+"/image/"+filename)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.predictimg);

    }
}