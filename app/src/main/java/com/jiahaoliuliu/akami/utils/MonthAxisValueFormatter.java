package com.jiahaoliuliu.akami.utils;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by philipp on 02/06/16.
 */
public class MonthAxisValueFormatter implements AxisValueFormatter {

    // Date to be displayed as header
    private static final String LABEL_DATE_FORMAT = "MMM yy";

    private List<Long> mMonthlyDates;
    private SimpleDateFormat mDateLabelFormatter;

    public MonthAxisValueFormatter(List<Long> monthlyDates) {
        this.mMonthlyDates = monthlyDates;
        // Initiate internal variables
        mDateLabelFormatter = new SimpleDateFormat(LABEL_DATE_FORMAT);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value - 1;
        if (position < 0 || position >= mMonthlyDates.size()) {
            return "";
        } else {
            return mDateLabelFormatter.format(mMonthlyDates.get(position));
        }

    //    int days = (int) value;
    //
    //    int year = determineYear(days);
    //
    //    int month = determineMonth(days);
    //    String monthName = mMonths[month % mMonths.length];
    //    String yearName = String.valueOf(year);
    //
    //    if (chart.getVisibleXRange() > 30 * axis.getLabelCount()) {
    //
    //        return monthName + " " + yearName;
    //    } else {
    //
    //        int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));
    //
    //        String appendix = "th";
    //
    //        switch (dayOfMonth) {
    //            case 1:
    //                appendix = "st";
    //                break;
    //            case 2:
    //                appendix = "nd";
    //                break;
    //            case 3:
    //                appendix = "rd";
    //                break;
    //            case 21:
    //                appendix = "st";
    //                break;
    //            case 22:
    //                appendix = "nd";
    //                break;
    //            case 23:
    //                appendix = "rd";
    //                break;
    //            case 31:
    //                appendix = "st";
    //                break;
    //        }
    //
    //        return dayOfMonth == 0 ? "" : dayOfMonth + appendix + " " + monthName;
    //    }
    //}
    //
    //private int getDaysForMonth(int month, int year) {
    //
    //    if (month == 1) {
    //
    //        if (year == 2016 || year == 2020)
    //            return 29;
    //        else
    //            return 28;
    //    }
    //
    //    if (month == 3 || month == 5 || month == 8 || month == 10)
    //        return 30;
    //    else
    //        return 31;
    //}
    //
    //private int determineMonth(int dayOfYear) {
    //
    //    int month = -1;
    //    int days = 0;
    //
    //    while (days < dayOfYear) {
    //        month = month + 1;
    //
    //        if (month >= 12)
    //            month = 0;
    //
    //        int year = determineYear(days);
    //        days += getDaysForMonth(month, year);
    //    }
    //
    //    return Math.max(month, 0);
    //}
    //
    //private int determineDayOfMonth(int dayOfYear, int month) {
    //
    //    int count = 0;
    //    int days = 0;
    //
    //    while (count < month) {
    //
    //        int year = determineYear(days);
    //        days += getDaysForMonth(count % 12, year);
    //        count++;
    //    }
    //
    //    return dayOfYear - days;
    //}
    //
    //private int determineYear(int days) {
    //
    //    if (days <= 366)
    //        return 2016;
    //    else if (days <= 730)
    //        return 2017;
    //    else if (days <= 1094)
    //        return 2018;
    //    else if (days <= 1458)
    //        return 2019;
    //    else
    //        return 2020;
    //
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
