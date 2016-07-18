package com.jiahaoliuliu.akami.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jiahaoliuliu.akami.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MonthlyTransactionsActivity extends AppCompatActivity {

    private static final String TAG = "MonthlyTransactionsActivity";
    public static final String INTENT_KEY_MONTHLY_TRANSACTIONS = "MonthlyTransactions";

    // Date to be displayed as header
    private static final String LABEL_DATE_FORMAT = "MMMM yyyy";

    // Views
    private BarChart mMonthlyTransactionsBarChart;

    // Internal variables
    private HashMap<Long, Float> mMonthlyTransactions;
    private SimpleDateFormat mDateLabelFormatter;

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

        // Initiate internal variables
        mDateLabelFormatter = new SimpleDateFormat(LABEL_DATE_FORMAT);

        // Link the views
        mMonthlyTransactionsBarChart = (BarChart) findViewById(R.id.monthly_transactions_bar_chart);

        fillMonthlyTransactionsChart();
    }

    private void fillMonthlyTransactionsChart() {
        // Precondition
        if (mMonthlyTransactions == null) {
            Log.e(TAG, "Trying to fill the monthly transaction chart when the data is null");
            return;
        }

        ArrayList<BarEntry> monthlyBars = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int position = 0;
        for (Long key : mMonthlyTransactions.keySet()) {
            // Create the label
            labels.add(mDateLabelFormatter.format(new Date(key)));

            // Set the value
            Log.v(TAG, "Value get " + mMonthlyTransactions.get(key) + " for the position " + position);
            monthlyBars.add(new BarEntry(mMonthlyTransactions.get(key), position));
            position++;
        }

        BarDataSet barDataSet = new BarDataSet(monthlyBars, "Transactions");
        BarData barData = new BarData(barDataSet);
        mMonthlyTransactionsBarChart.setData(barData);
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
