
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class XAxisRendererRadarChart extends XAxisRenderer {

    private RadarChart mChart;

    public XAxisRendererRadarChart(ViewPortHandler viewPortHandler, XAxis xAxis, RadarChart chart) {
        super(viewPortHandler, xAxis, null);

        mChart = chart;
    }

    @Override
    public void renderAxisLabels(Canvas c) {

        if (!mXAxis.isEnabled() || !mXAxis.isDrawLabelsEnabled())
            return;

        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        final MPPointF drawLabelAnchor = MPPointF.getInstance(0.5f, 0.25f);

        mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mXAxis.getTextSize());
        mAxisLabelPaint.setColor(mXAxis.getTextColor());

        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();

        MPPointF center = mChart.getCenterOffsets();
        MPPointF pOut = MPPointF.getInstance(0,0);
        for (int i = 0; i < mChart.getData().getMaxEntryCountSet().getEntryCount(); i++) {

            String label = mXAxis.getValueFormatter().getFormattedValue(i, mXAxis);

            float angle = (sliceangle * i + mChart.getRotationAngle()) % 360f;

            Utils.getPosition(center, mChart.getYRange() * factor
                    + mXAxis.mLabelRotatedWidth / 2f, angle, pOut);

            /**因需求而修改*/
            /**顶部两个 l255,r285*/
            /**底部部两个 l105,r75*/
            /**左边部度数 225、195、165、135*/
            /**右边部度数 315、345、15、45*/
            switch ((int) angle) {
                /**顶部*/
                case 255:
                case 285:
                    drawLabel(c, label, pOut.x, pOut.y+ mXAxis.mLabelRotatedHeight+ mXAxis.mLabelRotatedHeight/3, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                /**底部*/
                case 105:
                    drawLabel(c, label, pOut.x-mXAxis.mLabelRotatedWidth/6, pOut.y-mXAxis.mLabelRotatedHeight*2, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 75:
                    drawLabel(c, label, pOut.x+mXAxis.mLabelRotatedWidth/6, pOut.y-mXAxis.mLabelRotatedHeight*2, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                /**左边*/
                case 225:
                    drawLabel(c, label, pOut.x,pOut.y+ mXAxis.mLabelRotatedHeight+ mXAxis.mLabelRotatedHeight/3, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 195:
                    drawLabel(c, label, pOut.x,pOut.y+ mXAxis.mLabelRotatedHeight/2f, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 165:
                    drawLabel(c, label, pOut.x, pOut.y - mXAxis.mLabelRotatedHeight, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 135:
                    drawLabel(c, label, pOut.x-mXAxis.mLabelRotatedWidth/8, pOut.y - mXAxis.mLabelRotatedHeight*2, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                /**右边*/
                case 315:
                    drawLabel(c, label, pOut.x,pOut.y+ mXAxis.mLabelRotatedHeight+ mXAxis.mLabelRotatedHeight/3, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 345:
                    drawLabel(c, label, pOut.x,pOut.y+ mXAxis.mLabelRotatedHeight/2f, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 15:
                    drawLabel(c, label, pOut.x, pOut.y - mXAxis.mLabelRotatedHeight, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                case 45:
                    drawLabel(c, label, pOut.x+mXAxis.mLabelRotatedWidth/8, pOut.y - mXAxis.mLabelRotatedHeight*2, drawLabelAnchor, labelRotationAngleDegrees);
                    break;
                default:
                    drawLabel(c, label, pOut.x, pOut.y - mXAxis.mLabelRotatedHeight / 2.f,
                            drawLabelAnchor, labelRotationAngleDegrees);//原始的写法
            }
        }

        MPPointF.recycleInstance(center);
        MPPointF.recycleInstance(pOut);
        MPPointF.recycleInstance(drawLabelAnchor);
    }

	/**
	 * XAxis LimitLines on RadarChart not yet supported.
	 *
	 * @param c
	 */
	@Override
	public void renderLimitLines(Canvas c) {
		// this space intentionally left blank
	}
}
