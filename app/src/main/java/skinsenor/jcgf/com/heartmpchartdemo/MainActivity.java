package skinsenor.jcgf.com.heartmpchartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LineChart heart_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heart_chart = (LineChart) findViewById(R.id.heart_chart);
        HeartRateChartConfig.getInstance().initChart(this, heart_chart);
    }

    ArrayList<Float> floats;
    private boolean isrun=true;

    public void clickBtn1(View view) {
        start();
    }

    public void clickBtn2(View view) {
        isrun=false;
        HeartRateChartConfig.getInstance().clearValuse();
    }

    public void clickBtn3(View view) {
        isrun=true;
        HeartRateChartConfig.getInstance().clearValuse();
        start();
    }


    private void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (isrun) {
                    floats = new ArrayList<Float>();
                    for (int i = 0; i < 64; i++) {
                        float f=(float) new Random().nextInt(300);
                        Log.e("f---->",""+f);
                        floats.add(f);
                    }
                    HeartRateChartConfig.getInstance().addPoints(MainActivity.this, floats);
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

}
