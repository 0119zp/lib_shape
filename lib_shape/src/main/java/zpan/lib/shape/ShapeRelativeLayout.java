package zpan.lib.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @author zpan
 */
public class ShapeRelativeLayout extends RelativeLayout {

    /**
     * value空值
     */
    private static final int VALUE_NULL = -1;
    /**
     * value默认值
     */
    private static final int VALUE_DEFAULT = 0;

    /**
     * 形状
     */
    private static final int CIRCLE = 0x1;
    private static final int RECT = 0x2;

    /**
     * 从上到下
     */
    private static final int TOP_BOTTOM = 0x1;
    /**
     * 从右上到左下
     */
    private static final int TR_BL = 0x2;
    /**
     * 从右到左
     */
    private static final int RIGHT_LEFT = 0x3;
    /**
     * 从右下到左上
     */
    private static final int BR_TL = 0x4;
    /**
     * 从下到上
     */
    private static final int BOTTOM_TOP = 0x5;
    /**
     * 从左下到右上
     */
    private static final int BL_TR = 0x6;
    /**
     * 从左到右
     */
    private static final int LEFT_RIGHT = 0x7;
    /**
     * 从左上到右下
     */
    private static final int TL_BR = 0x8;

    /**
     * 默认背景颜色
     */
    private int mColorNormal = VALUE_NULL;

    /**
     * 形状
     */
    private int mShape = RECT;
    /**
     * 当背景是渐进色时，开始颜色
     */
    private int mColorStart = VALUE_NULL;
    /**
     * 当背景是渐进色时，结束颜色
     */
    private int mColorEnd = VALUE_NULL;
    /**
     * 颜色方向
     */
    private int mColorDirection = LEFT_RIGHT;
    /**
     * 所有角圆角半径
     */
    private int mCorner;
    /**
     * 四个角角度半径
     */
    private int mCornerLeftTop = VALUE_NULL;
    private int mCornerLeftBottom = VALUE_NULL;
    private int mCornerRightTop = VALUE_NULL;
    private int mCornerRightBottom = VALUE_NULL;
    /**
     * 边框颜色
     */
    private int mBorderColor;
    /**
     * 边框宽度
     */
    private int mBorderWidth;

    /**
     * 按钮背景
     */
    private GradientDrawable mShapeBackground;

    public ShapeRelativeLayout(Context context) {
        this(context, null);
    }

    public ShapeRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initShape(context, attrs);
    }

    private void initShape(Context context, AttributeSet attrs) {
        parseAttrs(context, attrs);
        mShapeBackground = new GradientDrawable();
        //设置渐变色
        if (mColorStart != VALUE_NULL && mColorEnd != VALUE_NULL) {
            if (mColorDirection == TOP_BOTTOM) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            } else if (mColorDirection == TR_BL) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.TR_BL);
            } else if (mColorDirection == RIGHT_LEFT) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
            } else if (mColorDirection == BR_TL) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.BR_TL);
            } else if (mColorDirection == BOTTOM_TOP) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
            } else if (mColorDirection == BL_TR) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.BL_TR);
            } else if (mColorDirection == LEFT_RIGHT) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            } else if (mColorDirection == TL_BR) {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.TL_BR);
            } else {
                mShapeBackground.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            }
            mShapeBackground.setColors(new int[]{mColorStart, mColorEnd});
        } else {
            //设置填充颜色
            setShapeBackgroundColor(mColorNormal);
        }

        if (mShape == CIRCLE) {
            mShapeBackground.setShape(GradientDrawable.OVAL);
        } else {
            mShapeBackground.setShape(GradientDrawable.RECTANGLE);
        }
        //ordered top-left, top-right, bottom-right, bottom-left
        mShapeBackground.setCornerRadii(new float[]{
                mCornerLeftTop != VALUE_NULL ? mCornerLeftTop : mCorner, mCornerLeftTop != VALUE_NULL ? mCornerLeftTop : mCorner,
                mCornerRightTop != VALUE_NULL ? mCornerRightTop : mCorner, mCornerRightTop != VALUE_NULL ? mCornerRightTop : mCorner,
                mCornerRightBottom != VALUE_NULL ? mCornerRightBottom : mCorner,
                mCornerRightBottom != VALUE_NULL ? mCornerRightBottom : mCorner, mCornerLeftBottom != VALUE_NULL ? mCornerLeftBottom : mCorner,
                mCornerLeftBottom != VALUE_NULL ? mCornerLeftBottom : mCorner
        });
        //设置边框颜色和边框宽度
        mShapeBackground.setStroke(mBorderWidth, mBorderColor);
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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeLayout);
        int length = typedArray.getIndexCount();
        for (int i = 0; i < length; i++) {
            int attr = typedArray.getIndex(i);
            //默认背景颜色
            if (attr == R.styleable.ShapeLayout_shape_color_normal) {
                mColorNormal = typedArray.getColor(attr, VALUE_NULL);
            }
            //形状
            if (attr == R.styleable.ShapeLayout_shape_type) {
                mShape = typedArray.getInt(attr, RECT);
            }
            //开始颜色
            if (attr == R.styleable.ShapeLayout_shape_color_start) {
                mColorStart = typedArray.getColor(attr, Color.TRANSPARENT);
            }
            //结束颜色
            if (attr == R.styleable.ShapeLayout_shape_color_end) {
                mColorEnd = typedArray.getColor(attr, Color.TRANSPARENT);
            }
            //颜色方向
            if (attr == R.styleable.ShapeLayout_shape_color_direction) {
                mColorDirection = typedArray.getInt(attr, LEFT_RIGHT);
            }
            //所有角圆角半径
            if (attr == R.styleable.ShapeLayout_shape_corner) {
                mCorner = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT);
            }
            //左上角圆角半径
            if (attr == R.styleable.ShapeLayout_shape_corner_left_top) {
                mCornerLeftTop = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //右上角圆角半径
            if (attr == R.styleable.ShapeLayout_shape_corner_right_top) {
                mCornerRightTop = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //左下角圆角半径
            if (attr == R.styleable.ShapeLayout_shape_corner_left_bottom) {
                mCornerLeftBottom = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //右下角圆角半径
            if (attr == R.styleable.ShapeLayout_shape_corner_right_bottom) {
                mCornerRightBottom = typedArray.getDimensionPixelSize(attr, VALUE_NULL);
            }
            //边框宽度
            if (attr == R.styleable.ShapeLayout_shape_border_width) {
                mBorderWidth = typedArray.getDimensionPixelSize(attr, 0);
            }
            //边框颜色
            if (attr == R.styleable.ShapeLayout_shape_border_color) {
                mBorderColor = typedArray.getColor(attr, Color.TRANSPARENT);
            }
        }
        typedArray.recycle();
    }
}
