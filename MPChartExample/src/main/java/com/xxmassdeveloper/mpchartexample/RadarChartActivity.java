
package com.xxmassdeveloper.mpchartexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.xxmassdeveloper.mpchartexample.custom.RadarMarkerView;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

public class RadarChartActivity extends DemoBase {

    private RadarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_radarchart);

        setTitle("RadarChartActivity");

        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);
        chart.setWebBackgroundColor(true, Color.rgb(255, 189, 0), 180);
        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(0.01f);
        chart.setWebColor(Color.LTGRAY);

        chart.setWebLineWidthInner(2f);
        chart.setWebColorInner(Color.WHITE);
        chart.setWebAlpha(100);


        RadarDataSet set1 = new RadarDataSet(null, "");
        set1.setHighlightCircleInnerRadius(0f);
        set1.setHighlightCircleOuterRadius(2f);
        set1.setHighlightCircleFillColor(Color.parseColor("#007B55"));
        set1.setHighlightCircleStrokeColor(Color.parseColor("#007B55"));
        set1.setHighlightCircleStrokeWidth(3f);

        RadarDataSet set2 = new RadarDataSet(null, "");
        set2.setHighlightCircleInnerRadius(0f);
        set2.setHighlightCircleOuterRadius(5f);
        set2.setHighlightCircleFillColor(Color.parseColor("#EB4F1B"));
        set2.setHighlightCircleStrokeColor(Color.WHITE);
        set2.setHighlightCircleStrokeWidth(1f);

        ArrayList<RadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        chart.setWebDrawCircleDotEnable(true, sets);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        setData();

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private final String[] mActivities = new String[]{"C30\nwrap\n1.05kw·h/m³", "C60\nwrap\n1.05kw·h/m³",
                    "C50\nwrap\n1.05kw·h/m³", "C40\nwrap\n1.05kw·h/m³", "C25\nwrap\n1.05kw·h/m³", "C35\nwrap\n1.05kw·h/m³"};


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });

        YAxis yAxis = chart.getYAxis();
        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(4, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(60f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.parseColor("#9B9B9B"));
    }

    private void setData() {

        float mul = 60;
        float min = 20;
        int cnt = 6;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "平均能耗");
        set1.setColor(0x80007B55);
        set1.setFillColor(0x007B55);
        set1.setDrawFilled(true);
//        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "实际能耗");
        set2.setColor(0x80FF0000);
        set2.setFillColor(0xEB4F1B);
        set2.setDrawFilled(true);
//        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(false);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setHighlightEnabled(true);
        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/RadarChartActivity.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionToggleRotate: {
                if (chart.isRotationEnabled())
                    chart.setRotationEnabled(false);
                else
                    chart.setRotationEnabled(true);
                chart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) chart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlightCircle: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) chart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    set.setDrawHighlightCircleEnabled(!set.isDrawHighlightCircleEnabled());
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleXLabels: {
                chart.getXAxis().setEnabled(!chart.getXAxis().isEnabled());
                chart.notifyDataSetChanged();
                chart.invalidate();
                break;
            }
            case R.id.actionToggleYLabels: {

                chart.getYAxis().setEnabled(!chart.getYAxis().isEnabled());
                chart.invalidate();
                break;
            }
            case R.id.animateX: {
                chart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                chart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                chart.spin(2000, chart.getRotationAngle(), chart.getRotationAngle() + 360, Easing.EaseInOutCubic);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "RadarChartActivity");
    }
}
