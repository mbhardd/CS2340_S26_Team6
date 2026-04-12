package com.example.sprintproject.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.AnalyticsViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class AnalyticsFragment extends Fragment {
    private AnalyticsViewModel mViewModel;

    private PieChart pieChart;
    private BarChart barChart;
    private LineChart lineChart;

    public AnalyticsFragment() {
        super(R.layout.fragment_analytics);
    }

    public static AnalyticsFragment newInstance() {
        return new AnalyticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = view.findViewById(R.id.pieChart);
        barChart = view.findViewById(R.id.barChart);
        lineChart = view.findViewById(R.id.lineChart);

        mViewModel = new ViewModelProvider(this).get(AnalyticsViewModel.class);

        setupCharts();
        observeAnalyticsData();
    }

    private void setupCharts() {
        pieChart.setNoDataText("No category data available");
        barChart.setNoDataText("No status data available");
        lineChart.setNoDataText("No issue timeline data available");

        pieChart.getDescription().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        pieChart.setUsePercentValues(false);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setCenterText("Categories");

        barChart.getAxisRight().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private void observeAnalyticsData() {
        mViewModel.getCategoryCounts().observe(getViewLifecycleOwner(), this::updatePieChart);
        mViewModel.getStatusCounts().observe(getViewLifecycleOwner(), this::updateBarChart);
        mViewModel.getIssuesOverTime().observe(getViewLifecycleOwner(), this::updateLineChart);
    }

    private void updatePieChart(Map<String, Integer> categoryData) {
        List<PieEntry> entries = new ArrayList<>();

        if (categoryData != null) {
            for (Map.Entry<String, Integer> entry : categoryData.entrySet()) {
                entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
        }

        if (entries.isEmpty()) {
            pieChart.clear();
            pieChart.setNoDataText("No category data available");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Issues by Category");
        dataSet.setColors(com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.animateY(800);
        pieChart.invalidate();
    }

    private void updateBarChart(Map<String, Integer> statusData) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        if (statusData != null) {
            int index = 0;
            for (Map.Entry<String, Integer> entry : statusData.entrySet()) {
                entries.add(new BarEntry(index, entry.getValue()));
                labels.add(entry.getKey());
                index++;
            }
        }

        if (entries.isEmpty()) {
            barChart.clear();
            barChart.setNoDataText("No status data available");
            return;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Issues by Status");
        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        if (!labels.isEmpty()) {
            barChart.getXAxis().setLabelCount(labels.size());
        }
        barChart.animateY(800);
        barChart.invalidate();
    }

    private void updateLineChart(Map<String, Integer> timeData) {
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        if (timeData != null) {
            int index = 0;
            for (Map.Entry<String, Integer> entry : timeData.entrySet()) {
                entries.add(new Entry(index, entry.getValue()));
                labels.add(entry.getKey());
                index++;
            }
        }

        if (entries.isEmpty()) {
            lineChart.clear();
            lineChart.setNoDataText("No issue timeline data available");
            return;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Issues Created Over Time");
        LineData data = new LineData(dataSet);

        lineChart.setData(data);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        lineChart.getXAxis().setGranularity(1f);
        if (!labels.isEmpty()) {
            lineChart.getXAxis().setLabelCount(labels.size());
        }
        lineChart.animateX(800);
        lineChart.invalidate();
    }
}