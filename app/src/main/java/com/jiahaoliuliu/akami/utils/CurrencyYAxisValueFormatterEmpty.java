package com.jiahaoliuliu.akami.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.FormattedStringCache;
import java.text.DecimalFormat;

public class CurrencyYAxisValueFormatterEmpty implements AxisValueFormatter {

    private DecimalFormat mFormat;
    private FormattedStringCache.PrimFloat mFormattedStringCache;

    public CurrencyYAxisValueFormatterEmpty() {
        mFormattedStringCache = new FormattedStringCache.PrimFloat(new DecimalFormat("###,###,###,##0.0"));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return "";
    }

    @Override
    public int getDecimalDigits() {
        return 1;
    }
}
