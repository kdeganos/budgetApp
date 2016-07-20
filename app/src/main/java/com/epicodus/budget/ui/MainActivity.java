package com.epicodus.budget.ui;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.epicodus.budget.Constants;
import com.epicodus.budget.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private double mTotal;
    private double mTotalMonthly;
    private double mTotalWeekly;
    private double mTotalDaily;

    @Bind(R.id.monthlyBudget) TextView mMonthlyBudget;
    @Bind(R.id.weeklyBudget) TextView mWeeklyBudget;
    @Bind(R.id.dailyBudget) TextView mDailyBudget;
    @Bind(R.id.totalBudget) TextView mTotalBudget;
    @Bind(R.id.chart) PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        displayPieChart(1d);

        mMonthlyBudget.setOnClickListener(this);
        mWeeklyBudget.setOnClickListener(this);
        mDailyBudget.setOnClickListener(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

    }

    public void displayPieChart(double args) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        // creating data values

        DecimalFormat decComma = new DecimalFormat();
        DecimalFormat dec = new DecimalFormat();

        decComma.setMinimumFractionDigits(2);
        decComma.setMaximumFractionDigits(2);

        dec.setGroupingUsed(false);
        dec.setMinimumFractionDigits(2);
        dec.setMaximumFractionDigits(2);

        float groceries = Float.parseFloat(dec.format(args * 0.1d));
        float entertainment = Float.parseFloat(dec.format(args * 0.2d));
        float housing = Float.parseFloat(dec.format(args * 0.2d));
        float clothing = Float.parseFloat(dec.format(args * 0.1d));
        float transportation = Float.parseFloat(dec.format(args * 0.1d));
        float shopping = Float.parseFloat(dec.format(args * 0.1d));
        float nightlife = Float.parseFloat(dec.format(args * 0.2d));

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(groceries, 0));
        entries.add(new Entry(entertainment, 1));
        entries.add(new Entry(housing, 2));
        entries.add(new Entry(clothing, 3));
        entries.add(new Entry(transportation, 4));
        entries.add(new Entry(shopping, 5));
        entries.add(new Entry(nightlife, 6));

        PieDataSet dataset = new PieDataSet(entries, "in $");
        dataset.setValueTextSize(12f);

        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Groceries");
        labels.add("Entertainment");
        labels.add("Housing");
        labels.add("Clothing");
        labels.add("Transportation");
        labels.add("Shopping");
        labels.add("Nightlife");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(labels, dataset); // initialize Piedata
        mPieChart.setData(data);

        mPieChart.setDescription("Budget Breakdown");  // set the description

        mMonthlyBudget.setText("Monthly Budget: $" + decComma.format(mTotalMonthly));
        mWeeklyBudget.setText("Weekly Budget: $" + decComma.format(mTotalWeekly));
        mDailyBudget.setText("Daily Budget: $" + decComma.format(mTotalDaily));

        mTotalBudget.setText("Total Budget: $" + decComma.format(mTotal));

        mMonthlyBudget.setOnClickListener(this);
        mWeeklyBudget.setOnClickListener(this);
        mDailyBudget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mMonthlyBudget) {
            displayPieChart(mTotalMonthly);
        }
        if (view == mWeeklyBudget) {
            displayPieChart(mTotalWeekly);
        }
        if (view == mDailyBudget) {
            displayPieChart(mTotalDaily);
        }
//        if (view == mDailyExpenditure) {
//            String dailyExpenditure =
//
//            Intent intent = new Intent(MainActivity.this, InputActivity.class);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query) {
                mTotal = Double.parseDouble(query);
                DecimalFormat dec = new DecimalFormat();

                dec.setMinimumFractionDigits(2);
                dec.setMaximumFractionDigits(2);

                mTotalMonthly = (mTotal / 12);
                mTotalWeekly = (mTotal / 52);
                mTotalDaily = (mTotal / 365);

                mMonthlyBudget.setText("Monthly Budget: $" + dec.format(mTotalMonthly));
                mWeeklyBudget.setText("Weekly Budget: $" + dec.format(mTotalWeekly));
                mDailyBudget.setText("Daily Budget: $" + dec.format(mTotalDaily));

                mTotalBudget.setText("Total Budget: $" + dec.format(mTotal));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    private void addToSharedPreferences(String location) {
//        mEditor.putString(Constants.PREFERENCES_INCOME_KEY, income).apply();
    }
}
