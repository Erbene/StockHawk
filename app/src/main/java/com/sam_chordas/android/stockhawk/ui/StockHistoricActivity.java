package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.sam_chordas.android.stockhawk.R;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;

/**
 * Created by maia on 23/06/16.
 */
public class StockHistoricActivity extends AppCompatActivity {
    private CandleStickChart mChart;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candlechart);

        mChart = (CandleStickChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.WHITE);

        mChart.setDescription("");


        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setStartAtZero(false);


        mChart.getLegend().setEnabled(false);

        // Legend l = mChart.getLegend();
        // l.setPosition(LegendPosition.BELOW_CHART_CENTER);
        // l.setFormSize(8f);
        // l.setFormToTextSpace(4f);
        // l.setXEntrySpace(6f);

        // mChart.setDrawLegend(false);


        mChart.resetTracking();

        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

//        for (int i = 0; i < prog; i++) {
//            float mult = (mSeekBarY.getProgress() + 1);
//            float val = (float) (Math.random() * 40) + mult;
//
//            float high = (float) (Math.random() * 9) + 8f;
//            float low = (float) (Math.random() * 9) + 8f;
//
//            float open = (float) (Math.random() * 6) + 1f;
//            float close = (float) (Math.random() * 6) + 1f;
//
//            boolean even = i % 2 == 0;
//
//            yVals1.add(new CandleEntry(i, val + high, val - low, even ? val + open : val - open,
//                    even ? val - close : val + close));
//        }
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < prog; i++) {
//            xVals.add("" + (1990 + i));
//        }

        CandleDataSet set1 = new CandleDataSet(yVals1, "Data Set");
        set1.setAxisDependency(AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);
        //set1.setHighlightLineWidth(1f);

//        CandleData data = new CandleData(xVals, set1);

//        mChart.setData(data);
        mChart.invalidate();

    }

    }
