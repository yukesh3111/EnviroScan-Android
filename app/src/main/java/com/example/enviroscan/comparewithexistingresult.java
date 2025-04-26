package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.enviroscan.databinding.ActivityComparewithexistingresultBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class comparewithexistingresult extends AppCompatActivity {
    ActivityComparewithexistingresultBinding binding;
    Context context;
    private List<String> xvalues= Arrays.asList("Present","Previous");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding=ActivityComparewithexistingresultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        context=this;
        Intent intent=getIntent();
        String str1=intent.getStringExtra("presentcount");
        String str2=intent.getStringExtra("previouscount");
        String filename=intent.getStringExtra("filename");
        String new_filename=intent.getStringExtra("newfilename");
        Glide.with(context)
                .load(Base_Url+"/image/"+filename)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.predictionImg);
        binding.presenttreecount.setText(str1);
        binding.existingtreecount.setText(str2);
        binding.existingfilename.setText(filename);
        binding.presentfilename.setText(new_filename);
        int presentcount=Integer.parseInt(str1);
        int previouscount=Integer.parseInt(str2);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        barChart.getAxisRight().setDrawLabels(false);
        ArrayList<BarEntry> entries=new ArrayList<>();
        entries.add(new BarEntry(0,presentcount));
        entries.add(new BarEntry(1,previouscount));
        int greater=Math.max(presentcount,previouscount);
        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setAxisMaximum(greater+10);  // Adjust maximum
        yAxis.setAxisMinimum(0);
        //yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(getColor(android.R.color.black));
        yAxis.setLabelCount(5, true);
        BarDataSet dataSet=new BarDataSet(entries,"Area Tree Population");
        int[] colors = {Color.parseColor("#20c997"), Color.parseColor("#6f42c1")};
        dataSet.setColors(colors);
        BarData barData=new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xvalues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.animateY(1400, com.github.mikephil.charting.animation.Easing.EaseInOutQuad);
    }
}