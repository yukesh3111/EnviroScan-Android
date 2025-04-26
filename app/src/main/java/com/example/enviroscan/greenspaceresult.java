package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.enviroscan.databinding.ActivityGreenspaceresultBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class greenspaceresult extends AppCompatActivity {
    ActivityGreenspaceresultBinding binding;
    private PieChart pieChart;
    Context context;
    String filename,bot;
    String str1,str2;
    int greenspace = 0, landspace = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGreenspaceresultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        context=this;
        filename=intent.getStringExtra("filename");
        bot=intent.getStringExtra("bot");
        String recommentation = intent.getStringExtra("recommentation");
        Glide.with(context)
                .load(Base_Url+"/image/"+filename)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(binding.predictionImg);

        pieChart = binding.piechart;
        setupPieChart();

        // Retrieve data from Intent extras

        try {
            str1 = intent.getStringExtra("greenpercentage");
            str2 = intent.getStringExtra("landspace");



            // Parse the values only if they are non-null
            if (str1 != null && str2 != null) {
                greenspace = Integer.parseInt(str1);
                landspace = Integer.parseInt(str2);
            } else {
                Log.e("PieChartData", "Received null values for greenpercentage or landspace");
            }
        } catch (NumberFormatException e) {
            Log.e("PieChartData", "Invalid number format in intent extras", e);
        }

        // Log values to confirm they are correct
        Log.d("PieChartData", "Green Space: " + greenspace + ", Land Space: " + landspace);

        // Load data into the pie chart after setting greenspace and landspace
        loadPieChartData();
        binding.summarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intente=new Intent(greenspaceresult.this,greenspacesummaryreport.class);
                intente.putExtra("greenpercentage",str1);
                intente.putExtra("landspace",str2);
                intente.putExtra("bot",bot);
                intente.putExtra("filename",filename);
                intente.putExtra("recommentation",recommentation);
                startActivity(intente);
            }
        });


    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Tree VS Land Population");
        pieChart.setCenterTextSize(16);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(greenspace, "Green Cover"));
        entries.add(new PieEntry(landspace, "Land Cover"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}
