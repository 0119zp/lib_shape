package zpan.lib.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class LineShape extends View {

    /**
     * value空值
     */
    private static final int VALUE_NULL = -1;
    /**
     * 边框颜色
     */
    private int mLineColor;
    /**
     * 边框宽度
     */
    private int mLineWidth;
    /**
     * 边框宽度
     */
    private int mLineDashWidth;
    /**
     * 边框宽度
     */
    private int mLineDashGap;

    /**
     * 按钮背景
     */
    private GradientDrawable mShapeBackground;

    public LineShape(Context context) {
        this(context, null);
    }

    public LineShape(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineShape(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initShape(context, attrs);
    }

    private void initShape(Context context, AttributeSet attrs) {
        parseAttrs(context, attrs);
        mShapeBackground = new GradientDrawable();

        mShapeBackground.setShape(GradientDrawable.LINE);
        //设置边框颜色和边框宽度
        mShapeBackground.setStroke(mLineWidth, mLineColor, mLineDashWidth, mLineDashGap);

        //设置背景
        setBackground(mShapeBackground);
    }

    /**
     * 设置正常状态下颜色
     */
    private void setShapeBackgroundColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mShapeBackground.setColor(ColorStateList.valueOf(color));
        } else {
            mShapeBackground.setColor(color);
        }
        setBackground(mShapeBackground);
    }

    /**
     * 属性配置
     */
    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.line_shape);
        int length = typedArray.getIndexCount();
        for (int i = 0; i < length; i++) {
            int attr = typedArray.getIndex(i);
            //线宽度
            if (attr == R.styleable.line_shape_line_width) {
                mLineWidth = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //右下角圆角半径
            if (attr == R.styleable.line_shape_line_dash_width) {
                mLineDashWidth = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //边框宽度
            if (attr == R.styleable.line_shape_line_dash_gap) {
                mLineDashGap = typedArray.getDimensionPixelSize(attr, 0);
            }
            //线颜色
            if (attr == R.styleable.line_shape_line_color) {
                mLineColor = typedArray.getColor(attr, Color.TRANSPARENT);
            }
        }
        typedArray.recycle();
    }
}
