package com.example.money_split_f1.ui.startScreen;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.FragmentAnalysisBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;


public class AnalysisFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    AnalysisViewModel analysisViewModel;
    private FragmentAnalysisBinding binding;

    public AnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Standard set up of view
        analysisViewModel = new ViewModelProvider(this).get(AnalysisViewModel.class);
        binding = FragmentAnalysisBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setUpAllTimeChart();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, analysisViewModel.getDropdownLabels());
        binding.dropdownCharts.setAdapter(adapter);
        binding.dropdownCharts.setOnItemSelectedListener(this);
        return view;
    }

    public void setUpAllTimeChart(){
        BarDataSet allTime = new BarDataSet(analysisViewModel.getAllTimeBarEntries(), "allTime");

        //format bar data set
        allTime.setColors(Color.parseColor("#FF0000"), Color.parseColor("#005D3A"));
        allTime.setValueTextSize(20f);
        allTime.setValueFormatter(new DefaultValueFormatter(2));

        //set up labels         x:0 is not used
        final String labels[] = {"Dummy", String.format("%s %s", SuperApplication.getContext().getResources().getString(R.string.minus), SuperApplication.getContext().getResources().getString(R.string.Currency)),
                String.format("%s %s", SuperApplication.getContext().getResources().getString(R.string.plus), SuperApplication.getContext().getResources().getString(R.string.Currency))};
        ValueFormatter formatter = new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return labels[(int) value];
            }
        };

        //set up chart
        binding.barChart.setData(new BarData(allTime));
        binding.barChart.animateY(500);

        //format xAxis and labels
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.getXAxis().setDrawAxisLine(false);
        binding.barChart.getXAxis().setTextSize(20f);
        binding.barChart.getXAxis().setGranularity(1f);
        binding.barChart.getXAxis().setGranularityEnabled(true);
        binding.barChart.getXAxis().setValueFormatter(formatter);

        //format yAxis Right
        binding.barChart.getAxisRight().setDrawGridLines(false);
        binding.barChart.getAxisRight().setAxisMinimum(0);
        binding.barChart.getAxisRight().setDrawAxisLine(false);
        binding.barChart.getAxisRight().setDrawLabels(false);

        //format yAxis Left
        binding.barChart.getAxisLeft().setDrawGridLines(false);
        binding.barChart.getAxisLeft().setAxisMinimum(0);
        binding.barChart.getAxisLeft().setDrawAxisLine(false);
        binding.barChart.getAxisLeft().setDrawLabels(false);

        //format other chart elements
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setExtraBottomOffset(20f); //else labels would be cut off
    }

    public void switchToWeekChart(){
        BarDataSet week = new BarDataSet(analysisViewModel.getLimitedBarEntries(7), "week");

        //format bar data set
        week.setColors(Color.parseColor("#FF0000"), Color.parseColor("#005D3A"));
        week.setValueTextSize(20f);
        week.setValueFormatter(new DefaultValueFormatter(2));

        binding.barChart.setData(new BarData(week));
        binding.barChart.animateY(500);

    }

    public void switchToMonthChart(){
        BarDataSet month = new BarDataSet(analysisViewModel.getLimitedBarEntries(30), "week");

        //format bar data set
        month.setColors(Color.parseColor("#FF0000"), Color.parseColor("#005D3A"));
        month.setValueTextSize(20f);
        month.setValueFormatter(new DefaultValueFormatter(2));

        binding.barChart.setData(new BarData(month));
        binding.barChart.animateY(500);
    }

    public void switchToListChart(int i){
        BarDataSet list = new BarDataSet(analysisViewModel.getListBarEntries(i), "list " + i);

        //format bar data set
        list.setColors(Color.parseColor("#FF0000"), Color.parseColor("#005D3A"));
        list.setValueTextSize(20f);
        list.setValueFormatter(new DefaultValueFormatter(2));

        binding.barChart.setData(new BarData(list));
        binding.barChart.animateY(500);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                setUpAllTimeChart();
                break;
            case 1:
                switchToListChart(0);
                break;
            case 2:
                switchToListChart(1);
                break;
            case 3:
                switchToListChart(2);
                break;
            case 4:
                switchToListChart(3);
                break;
            case 5:
                switchToWeekChart();
                break;
            case 6:
                switchToMonthChart();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}