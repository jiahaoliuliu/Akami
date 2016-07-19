package com.jiahaoliuliu.akami.ui;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jiahaoliuliu.akami.R;

import com.jiahaoliuliu.akami.utils.CurrencyYAxisValueFormatterEmpty;
import com.jiahaoliuliu.akami.utils.MonthAxisValueFormatter;
import com.jiahaoliuliu.akami.utils.CurrencyYAxisValueFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MonthlyTransactionsActivity extends AppCompatActivity {

    private static final String TAG = "MonthlyTransactionsActivity";
    public static final String INTENT_KEY_MONTHLY_TRANSACTIONS = "MonthlyTransactions";

    // Views
    private BarChart mMonthlyTransactionsBarChart;

    // Internal variables
    private HashMap<Long, Float> mMonthlyTransactions;
    private List<Long> mMonthlyDatesSorted;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_transactions);

        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the content
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(INTENT_KEY_MONTHLY_TRANSACTIONS)) {
            Log.e(TAG, "You must pass the monthly expenses as extras");
            finish();
            return;
        }

        mMonthlyTransactions = (HashMap<Long, Float>) extras.getSerializable(INTENT_KEY_MONTHLY_TRANSACTIONS);
        Log.d(TAG, "Monthly transactions get " + mMonthlyTransactions);

        mMonthlyDatesSorted = new ArrayList<>(mMonthlyTransactions.keySet());
        Collections.sort(mMonthlyDatesSorted);

        // Link the views
        mMonthlyTransactionsBarChart = (BarChart) findViewById(R.id.monthly_transactions_bar_chart);

        // Set the bar chart
        mMonthlyTransactionsBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // TODO: Implement this
            }

            @Override public void onNothingSelected() {
                // TODO: Implement this
            }
        });

        mMonthlyTransactionsBarChart.setDrawBarShadow(false);
        mMonthlyTransactionsBarChart.setDrawValueAboveBar(true);

        // Remove the description
        mMonthlyTransactionsBarChart.setDescription("");

        // Scalling can now only be done on x- and y-axis separately
        mMonthlyTransactionsBarChart.setPinchZoom(false);

        // Disable the grids
        mMonthlyTransactionsBarChart.setDrawGridBackground(false);

        // Draw the x Axis
        XAxis xAxis = mMonthlyTransactionsBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new MonthAxisValueFormatter(mMonthlyDatesSorted));

        // Draw the y Axis
        //      Hide the left Y axis
        // TODO: This causes some strange margin on the bottom. Better not use if for now
        //mMonthlyTransactionsBarChart.getAxisLeft().setDrawLabels(false);

        AxisValueFormatter currencyYAxisValueFormatterArabic = new CurrencyYAxisValueFormatterEmpty();
        YAxis leftAxis = mMonthlyTransactionsBarChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(currencyYAxisValueFormatterArabic);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        //      Show the values for the right Y axis
        AxisValueFormatter currencyYAxisValueFormatter = new CurrencyYAxisValueFormatter();
        YAxis rightAxis = mMonthlyTransactionsBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(currencyYAxisValueFormatter);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        // Legend
        // TODO: Check why legend is not working. Disable it for now
        mMonthlyTransactionsBarChart.getLegend().setEnabled(false);
        //Legend l = mMonthlyTransactionsBarChart.getLegend();
        //l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        //l.setForm(Legend.LegendForm.SQUARE);
        //l.setFormSize(9f);
        //l.setTextSize(11f);
        //l.setXEntrySpace(4f);

        fillMonthlyTransactionsChart();
    }

    @SuppressLint("LongLogTag")
    private void fillMonthlyTransactionsChart() {
        // Precondition
        if (mMonthlyTransactions == null) {
            Log.e(TAG, "Trying to fill the monthly transaction chart when the data is null");
            return;
        }

        mMonthlyTransactionsBarChart.getXAxis().setAxisMinValue(0f);
        mMonthlyTransactionsBarChart.getXAxis().setAxisMaxValue(mMonthlyTransactions.size() + 1);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < mMonthlyDatesSorted.size(); i ++) {
            long month = mMonthlyDatesSorted.get(i);
            BarEntry barEntry = new BarEntry(i + 1f, mMonthlyTransactions.get(month));
            yVals1.add(barEntry);
        }

        BarDataSet set1;

        if (mMonthlyTransactionsBarChart.getData() != null &&
            mMonthlyTransactionsBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mMonthlyTransactionsBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mMonthlyTransactionsBarChart.getData().notifyDataChanged();
            mMonthlyTransactionsBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Monthly expenses");
            set1.setColors(new int[]{getResources().getColor(R.color.colorPrimary)});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mMonthlyTransactionsBarChart.setData(data);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Capture the back action
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
