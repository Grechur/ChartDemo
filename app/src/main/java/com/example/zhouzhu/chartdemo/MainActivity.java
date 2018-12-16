package com.example.zhouzhu.chartdemo;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartGestureListener,OnChartValueSelectedListener {

    private LineChart mLineChar;
    private LineDataSet set1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineChar = (LineChart) findViewById(R.id.chart);

        //设置手势滑动事件
//        mLineChar.setOnChartGestureListener(this);
        //设置数值选择监听
        mLineChar.setOnChartValueSelectedListener(this);
        //后台绘制
        mLineChar.setDrawGridBackground(false);
        //设置描述文本
        mLineChar.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineChar.setTouchEnabled(false);
        //设置缩放
        mLineChar.setDragEnabled(true);
        //设置推动
        mLineChar.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChar.setPinchZoom(true);
        //设置是否绘制chart边框的线
        mLineChar.setDrawBorders(true);
        //设置chart边框线颜色
        mLineChar.setBorderColor(Color.GRAY);
        //设置chart边框线宽度
        mLineChar.setBorderWidth(0.3f);

//        //x轴
//        LimitLine llXAxis = new LimitLine(10f, "标记");
//        //设置线宽
//        llXAxis.setLineWidth(4f);
//        //
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        //设置
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);


        XAxis xAxis = mLineChar.getXAxis();
        xAxis.enableGridDashedLine(0f, 0f, 0f);
        xAxis.setGridColor(Color.GRAY);
        xAxis.setGridLineWidth(0.3f);
        //设置x轴的最大值
        xAxis.setAxisMaximum(100f);
        //设置x轴的最小值
        xAxis.setAxisMinimum(0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置



        YAxis leftAxis = mLineChar.getAxisLeft();
        //重置所有限制线,以避免重叠线
//        leftAxis.removeAllLimitLines();
        //设置优秀线
//        leftAxis.addLimitLine(ll1);
        //设置及格线
//        leftAxis.addLimitLine(ll2);
        //y轴最大
        leftAxis.setAxisMaximum(200f);
        //y轴最小
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(0f, 0f, 0f);
        leftAxis.setGridColor(Color.GRAY);
        leftAxis.setGridLineWidth(0.3f);
        leftAxis.setDrawZeroLine(false);

        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChar.getAxisRight().setEnabled(false);

        initData();
    }

    private void initData() {
        //这里我模拟一些数据
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0, 200));
        values.add(new Entry(5, 50));
        values.add(new Entry(10, 66));
        values.add(new Entry(15, 120));
        values.add(new Entry(20, 80));
        values.add(new Entry(35, 100));
        values.add(new Entry(40, 110));
        values.add(new Entry(45, 80));
        values.add(new Entry(50, 160));
        values.add(new Entry(100, 100));

        //设置数据
        setData(values);

        //默认动画
        mLineChar.animateX(500);
    }

    //传递数据集
    private void setData(ArrayList<Entry> values) {
        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "年度总结报告");

            // 在这里设置线
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setDrawCircles(false);//是否显示圆点
            set1.setColor(Color.RED);
//            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(0f);//设置文字大小0不显示文字
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
//                set1.setFillColor(Color.YELLOW);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            mLineChar.setData(data);


            LimitLine ll1 = new LimitLine(160, "160");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);

            LimitLine ll2 = new LimitLine(50f, "50");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);

            YAxis leftAxis = mLineChar.getAxisLeft();
            //设置优秀线
            leftAxis.addLimitLine(ll1);
            //设置及格线
            leftAxis.addLimitLine(ll2);
        }
    }

    public void showToast(CharSequence s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * GestureListener
     */
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        showToast("onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        showToast("onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        showToast("onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        showToast("onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        showToast("onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        showToast("onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        showToast("onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        showToast("onChartTranslate");
    }


    /**
     * ChartValueSelectedListener
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        showToast("onValueSelected");
    }

    @Override
    public void onNothingSelected() {
        showToast("onNothingSelected");
    }
}
