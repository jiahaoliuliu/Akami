package com.jiahaoliuliu.akami.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.FormattedStringCache;

import java.text.DecimalFormat;

public class CurrencyYAxisValueFormatter implements AxisValueFormatter {

    private DecimalFormat mFormat;
    private FormattedStringCache.PrimFloat mFormattedStringCache;

    public CurrencyYAxisValueFormatter() {
        mFormattedStringCache = new FormattedStringCache.PrimFloat(new DecimalFormat("###,###,###,##0"));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormattedStringCache.getFormattedValue(value) + " AED";
    }

    @Override
    public int getDecimalDigits() {
        return 1;
    }
}
