package skinsenor.jcgf.com.heartmpchartdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by wenbaohe on 2018/1/16.
 */

public class HeartRateChartConfig {
    private static HeartRateChartConfig instance;

    public static HeartRateChartConfig getInstance() {
        if (instance == null) {
            synchronized (HeartRateChartConfig.class) {
                if (instance == null) {
                    instance = new HeartRateChartConfig();
                }
            }
        }
        return instance;
    }

    private WeakReference<Context> weakReference;
    private LineChart lineChart;
    private int color;

    public void initChart(Context context, LineChart lineChart) {
        weakReference = new WeakReference<Context>(context);
        this.lineChart = lineChart;
        color = Color.parseColor("#e401d7bb");
        initlineChart();
    }

    /**
     * 初始化图表
     */
    private void initlineChart() {
        if (lineChart != null) {
            Description description = new Description();
            description.setText("");
            description.setEnabled(false);
            lineChart.setDescription(description);

            lineChart.setNoDataText("");   //没有数据时显示在中央的字符串，参数是String对象
            lineChart.setDrawGridBackground(false);//设置图表内格子背景是否显示，默认是false
            lineChart.getAxisRight().setEnabled(false);//右边不显示刻度值
            //Interaction with the Chart 图表的交互
            lineChart.setTouchEnabled(true); // 设置是否可以触摸
            lineChart.setDragEnabled(true);// 是否可以拖拽
            lineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true
            lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
            lineChart.setScaleYEnabled(false); //是否可以缩放 仅y轴

            lineChart.setPinchZoom(false);  //设置x轴和y轴能否同时缩放。默认是否
            lineChart.setDoubleTapToZoomEnabled(false);//设置是否可以通过双击屏幕放大图表。默认是true
            lineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
            // Chart fling / deceleration
            lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
            lineChart.setDragDecelerationFrictionCoef(0.95f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
            lineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
            lineChart.setHighlightPerTapEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true


            LineData data = new LineData();
            lineChart.setData(data);

            Legend legend = lineChart.getLegend();
            legend.setEnabled(false);
            setX(lineChart.getXAxis());
            setY(lineChart.getAxisLeft());
            lineChart.setBackgroundColor(Color.BLACK);
            lineChart.invalidate();
        }
    }

    /**
     *
     */
    private void setY(YAxis yAxis) {
        yAxis.setTextColor(color);
        yAxis.setAxisLineColor(color);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0);//从0坐标开始
        yAxis.setAxisMaximum(300);//最大值
        yAxis.setSpaceMin(50);
        yAxis.setSpaceMax(50);
//        yAxis.enableGridDashedLine(10f, 10f, 10f);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) Math.abs(value) % 50 == 0) {
                    return "" + (int) value;
                }
                return "";
            }
        });

        for (int i = 1; i < 7; i++) {
            LimitLine limitLine1 = new LimitLine(i * 50f);
            limitLine1.setLineWidth(0.5f);
            limitLine1.enableDashedLine(5f, 5f, 0);
            limitLine1.setLineColor(color);
            yAxis.addLimitLine(limitLine1);
        }

    }

    private void setX(XAxis xAxis) {
        xAxis.setAxisLineColor(color);
        xAxis.setTextColor(color);
        xAxis.setDrawGridLines(false);
        xAxis.enableGridDashedLine(5, 5, 0);
        xAxis.setGridColor(color);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);
        xAxis.setDrawLabels(true);
        xAxis.setSpaceMin(0.01f);
        xAxis.setAxisMinimum(0.01f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }


    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setCircleColor(Color.TRANSPARENT);
        set.setLineWidth(1f);
        set.setCircleRadius(0f);
        set.setFillAlpha(65);
        set.setDrawCircleHole(false); //启用圆心
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(color);
        set.setValueTextColor(color);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    /**
     * 浮点型相加
     */
    private static float floatsum(float a, float b) {
        if (a > 99999 || b > 99999) {
            return 0;
        }
        BigDecimal da = new BigDecimal(a);
        BigDecimal db = new BigDecimal(b);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return Float.parseFloat(fnum.format(da.add(db)));
    }

    private float rx = 0;

    public synchronized void addPoints(final Activity activity, final ArrayList<Float> floats) {
        isup = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (float y : floats) {
                        if (isup) {
                            uptate(activity, rx, y);
                            rx = floatsum(rx, 0.02f);//0.02f表示0.02秒，即1300/64（包）=20毫秒，每隔0.02画一个值
                            Log.e("rx轴--->", "" + rx);
                            try {
                                Thread.sleep(20);//0.02f表示0.02秒，即1300/64（包）=20毫秒
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else break;
                    }
                }
            }
        }).start();
    }

    private void uptate(Activity activity, final float xv, final float yv) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isup){
                        addEntry(xv, yv);
                    }
                }
            });
        }
    }

    public void addEntry(float x, float y) {
        if (lineChart != null) {
            LineData data = lineChart.getData();
            if (data != null) {
                ILineDataSet set = data.getDataSetByIndex(0);
                if (set == null) {
                    set = createSet();
                    data.addDataSet(set);
                }
                data.addEntry(new Entry(x, y), 0);
                data.notifyDataChanged();
                lineChart.notifyDataSetChanged();
//            lineChart.setVisibleXRangeMaximum(7.5f);
                lineChart.setVisibleXRangeMaximum(7.8f);
                lineChart.moveViewToX(data.getEntryCount());
            }
        }
    }


    private boolean isup = true;

    public void clearValuse() {
        isup = false;
        if (lineChart != null) {
            LineData lineData = lineChart.getLineData();
            if (lineData != null) {
                lineData.clearValues();
            }
            lineChart.getXAxis().resetAxisMinimum();
            lineChart.notifyDataSetChanged();
            rx = 0;
            lineChart.setScaleMinima(0, 0);
            lineChart.getViewPortHandler().refresh(new Matrix(), lineChart, true);
        }
    }
}
