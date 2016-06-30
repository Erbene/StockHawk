package com.sam_chordas.android.stockhawk.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.service.StockIntentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by maia on 23/06/16.
 */
public class StockHistoricActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private CandleStickChart mChart;
    private String mSymbol;
    private Intent mServiceIntent;
    private Cursor mCursor;
    private static final int CURSOR_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candlechart);
        mChart = (CandleStickChart) findViewById(R.id.chart1);

        mChart.setContentDescription(getResources().getString(R.string.historic_data_description));
        mChart.setNoDataText(getResources().getString(R.string.historic_data_empty));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        mChart.getLegend().setEnabled(false);
        mChart.resetTracking();
        //Save service instance
        mServiceIntent = new Intent(this, StockIntentService.class);
        // Get the symbol
        mSymbol = getIntent().getStringExtra("symbol");
        setTitle(mSymbol + " " + getResources().getString(R.string.historic_data));

        Cursor c = getContentResolver().query(QuoteProvider.QuoteHistoric.withSymbol(mSymbol), null, null, null, null);
        if(c.getCount() == 0 || checkLastDate(c)){
            getContentResolver().delete(QuoteProvider.QuoteHistoric.withSymbol(mSymbol),null,null);
            mServiceIntent.putExtra("tag","historic");
            mServiceIntent.putExtra("symbol", mSymbol);
            startService(mServiceIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    private boolean checkLastDate(Cursor c) {
        c.moveToLast();
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return !c.getString(c.getColumnIndex("date")).equals(simpleDateFormat.format(today));
    }

    public StockHistoricActivity() {
        super();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, QuoteProvider.QuoteHistoric.withSymbol(mSymbol),
                null, null, null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        if(cursor.getCount() > 0) {
            resetChart(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void resetChart(Cursor cursor){
        mChart.clear();
        if(cursor.getCount() <= 0) return;
        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        while(cursor.moveToNext()){
            float close = Float.parseFloat(cursor.getString(cursor.getColumnIndex("close")));
            float open = Float.parseFloat(cursor.getString(cursor.getColumnIndex("open")));
            float high = Float.parseFloat(cursor.getString(cursor.getColumnIndex("high")));
            float low = Float.parseFloat(cursor.getString(cursor.getColumnIndex("low")));
            yVals1.add(new CandleEntry(i, high, low, open, close));
            xVals.add(i,cursor.getString(cursor.getColumnIndex("date")));
            i++;
        }
        CandleDataSet set1 = new CandleDataSet(yVals1, getResources().getString(R.string.historic_data_set));
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);


        CandleData data = new CandleData(xVals, set1);

        mChart.setData(data);
        mChart.invalidate();
    }
}
