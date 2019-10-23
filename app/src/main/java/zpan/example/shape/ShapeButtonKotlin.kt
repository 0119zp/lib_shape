package zpan.example.shape

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt

/**
 * @author zpan
 * @date 2019/10/17 14:20
 *
 * description: TODO
 */
class ShapeButtonKotlin @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {

        /** value空值 */
        private const val VALUE_NULL = -1
        /** value默认值 */
        private const val VALUE_DEFAULT = 0

        /** 背景形状 */
        private const val CIRCLE = 0x1
        private const val RECT = 0x2

        /** 从上到下 */
        private const val TOP_BOTTOM = 0x1
        /** 从右上到左下 */
        private const val TR_BL = 0x2
        /** 从右到左 */
        private const val RIGHT_LEFT = 0x3
        /** d从右下到左上 */
        private const val BR_TL = 0x4
        /** 从下到上 */
        private const val BOTTOM_TOP = 0x5
        /** 从左下到右上 */
        private const val BL_TR = 0x6
        /** 从左到右 */
        private const val LEFT_RIGHT = 0x7
        /** 从左上到右下 */
        private const val TL_BR = 0x8

        /** 默认字体 */
        private const val NORMAL = 0x1
        /** 字体加粗 */
        private const val BOLD = 0x2
    }

    /** 文字内容 */
    private var text: CharSequence? = null
    /** 文字颜色 */
    private var mTextColor: Int = 0
    /** 文字大小 */
    private var mTextSize: Int = 0
    /** 字体类型 */
    private var mTextStyle = NORMAL
    /** 文字是否单行显示，默认单行 */
    private var mSingleLine = true
    /** 默认背景颜色 */
    private var mColorNormal = VALUE_NULL
    /** 默认背景颜色 */
    private var mColorPressed = VALUE_NULL
    /** 图片资源 */
    private var mDrawableLeft: Drawable? = null
    private var mDrawableRight: Drawable? = null
    private var mDrawableTop: Drawable? = null
    private var mDrawableBottom: Drawable? = null
    private var mDrawableMiddle: Drawable? = null
    private var mDrawableAuto = true
    /** 图片中间时的宽高 */
    private var mDrawableMiddleWidth = VALUE_NULL
    private var mDrawableMiddleHeight = VALUE_NULL
    /** 图片距离文字距离 */
    private var mDrawablePadding: Int = 0
    /** 形状 */
    private var mShape = RECT
    /** 当背景是渐进色时，开始颜色 */
    private var mColorStart = VALUE_NULL
    /** 当背景是渐进色时，结束颜色 */
    private var mColorEnd = VALUE_NULL
    /** 颜色方向 */
    private var mColorDirection = LEFT_RIGHT
    /** 所有角圆角半径 */
    private var mCorner: Int = 0
    /** 四个角角度半径 */
    private var mCornerLeftTop = VALUE_NULL
    private var mCornerLeftBottom = VALUE_NULL
    private var mCornerRightTop = VALUE_NULL
    private var mCornerRightBottom = VALUE_NULL
    /** 边框颜色 */
    private var mBorderColor: Int = 0
    /** 边框宽度 */
    private var mBorderWidth: Int = 0
    /** 文字和图标容器 */
    private var mTextIconContainer: TextView? = null
    /** 按钮背景 */
    private var mButtonBackground: GradientDrawable? = null
    /** 按钮是否可以点击 */
    private var mButtonClickable = true

    init {
        initButton(context, attrs)
    }

    /**
     * 初始化按钮
     */
    private fun initButton(context: Context, attrs: AttributeSet?) {
        isClickable = true
        gravity = Gravity.CENTER
        mTextIconContainer = TextView(context)
        //解析属性
        parseAttrs(context, attrs)
        //按钮背景
        mButtonBackground = GradientDrawable()
        //设置渐变色
        if (mColorStart != VALUE_NULL && mColorEnd != VALUE_NULL) {
            if (mColorDirection == TOP_BOTTOM) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.TOP_BOTTOM
            } else if (mColorDirection == TR_BL) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.TR_BL
            } else if (mColorDirection == RIGHT_LEFT) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.RIGHT_LEFT
            } else if (mColorDirection == BR_TL) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.BR_TL
            } else if (mColorDirection == BOTTOM_TOP) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.BOTTOM_TOP
            } else if (mColorDirection == BL_TR) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.BL_TR
            } else if (mColorDirection == LEFT_RIGHT) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.LEFT_RIGHT
            } else if (mColorDirection == TL_BR) {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.TL_BR
            } else {
                mButtonBackground!!.orientation = GradientDrawable.Orientation.LEFT_RIGHT
            }
            mButtonBackground!!.colors = intArrayOf(mColorStart, mColorEnd)
        } else {
            //设置填充颜色
            setButtonBackgroundColor(mColorNormal)
        }

        if (mShape == CIRCLE) {
            mButtonBackground!!.shape = GradientDrawable.OVAL
        } else {
            mButtonBackground!!.shape = GradientDrawable.RECTANGLE
        }
        //ordered top-left, top-right, bottom-right, bottom-left
        mButtonBackground!!.cornerRadii = floatArrayOf(
            (if (mCornerLeftTop != VALUE_NULL) mCornerLeftTop else mCorner).toFloat(),
            (if (mCornerLeftTop != VALUE_NULL) mCornerLeftTop else mCorner).toFloat(),
            (if (mCornerRightTop != VALUE_NULL) mCornerRightTop else mCorner).toFloat(),
            (if (mCornerRightTop != VALUE_NULL) mCornerRightTop else mCorner).toFloat(),
            (if (mCornerRightBottom != VALUE_NULL) mCornerRightBottom else mCorner).toFloat(),
            (if (mCornerRightBottom != VALUE_NULL) mCornerRightBottom else mCorner).toFloat(),
            (if (mCornerLeftBottom != VALUE_NULL) mCornerLeftBottom else mCorner).toFloat(),
            (if (mCornerLeftBottom != VALUE_NULL) mCornerLeftBottom else mCorner).toFloat()
        )

        //设置边框颜色和边框宽度
        mButtonBackground!!.setStroke(mBorderWidth, mBorderColor)
        //设置背景
        background = mButtonBackground

        //设置文字
        mTextIconContainer!!.text = text
        //设置文字大小
        mTextIconContainer!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        //设置文字颜色
        mTextIconContainer!!.setTextColor(mTextColor)
        // 设置字体类型
        if (mTextStyle == NORMAL) {
            mTextIconContainer!!.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        } else if (mTextStyle == BOLD) {
            mTextIconContainer!!.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        } else {
            mTextIconContainer!!.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
        mTextIconContainer!!.compoundDrawablePadding = mDrawablePadding
        //是否单行
        if (mSingleLine) {
            mTextIconContainer!!.setSingleLine()
        }
        mTextIconContainer!!.gravity = Gravity.CENTER

        if (mDrawableAuto) {
            //设置图标
            val iconSize = (mTextSize * 1.2f).toInt()
            setBounds(mDrawableLeft, iconSize)
            setBounds(mDrawableTop, iconSize)
            setBounds(mDrawableRight, iconSize)
            setBounds(mDrawableBottom, iconSize)
            //设置文字drawable
            mTextIconContainer!!.setCompoundDrawables(
                mDrawableLeft,
                mDrawableTop,
                mDrawableRight,
                mDrawableBottom
            )
        } else {
            mTextIconContainer!!.setCompoundDrawablesWithIntrinsicBounds(
                mDrawableLeft,
                mDrawableTop,
                mDrawableRight,
                mDrawableBottom
            )
        }

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (mDrawableMiddle != null) {
            val imageView = ImageView(getContext())
            if (mDrawableMiddleWidth == VALUE_NULL || mDrawableMiddleHeight == VALUE_NULL) {
                layoutParams.width = 40
                layoutParams.height = 40
            } else {
                layoutParams.width = mDrawableMiddleWidth
                layoutParams.height = mDrawableMiddleHeight
            }
            imageView.setImageDrawable(mDrawableMiddle)
            addView(imageView, layoutParams)
        } else {
            addView(mTextIconContainer, layoutParams)
        }
    }

    /**
     * 在自动缩放模式下设置drawable边框
     */
    private fun setBounds(drawable: Drawable?, size: Int) {
        if (drawable == null) {
            return
        }
        drawable.setBounds(0, 0, size, size)
    }

    /**
     * 设置不可点击颜色，此时按钮点击无反应
     */
    @Deprecated("")
    fun setUnableColor(@ColorInt color: Int) {
        this.mColorNormal = color
        setButtonBackgroundColor(color)
        setButtonClickable(false)
    }

    /**
     * 设置按钮是否可以点击
     */
    fun setButtonClickable(buttonClickable: Boolean) {
        this.mButtonClickable = buttonClickable
    }

    /**
     * 设置按钮颜色以及是否可以点击
     * 不影响color_pressed的值
     *
     * @param color           设置按钮的color_normal值
     * @param buttonClickable 设置按钮是否可点击
     */
    fun setButtonClickable(@ColorInt color: Int, buttonClickable: Boolean) {
        this.mColorNormal = color
        setButtonBackgroundColor(color)
        setButtonClickable(buttonClickable)
    }

    /**
     * 修改文字
     */
    fun setText(text: CharSequence?) {
        if (text == null) {
            return
        }
        mTextIconContainer!!.text = text
    }

    /**
     * 修改文字颜色
     */
    fun setTextColor(@ColorInt textColor: Int) {
        mTextIconContainer!!.setTextColor(textColor)
    }

    /**
     * 修改按钮默认背景颜色
     */
    fun setColorNormal(@ColorInt colorNormal: Int) {
        this.mColorNormal = colorNormal
        setButtonBackgroundColor(colorNormal)
    }

    /**
     * 设置边框颜色
     */
    fun setBorderColor(@ColorInt borderColor: Int) {
        this.mBorderColor = borderColor
        //设置边框颜色和边框宽度
        mButtonBackground!!.setStroke(mBorderWidth, borderColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mButtonClickable) {
            return true
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> setButtonBackgroundColor(if (mColorPressed == VALUE_NULL) mColorNormal else mColorPressed)
            MotionEvent.ACTION_UP -> setButtonBackgroundColor(mColorNormal)
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置正常状态下颜色
     */
    private fun setButtonBackgroundColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mButtonBackground!!.color = ColorStateList.valueOf(color)
        } else {
            mButtonBackground!!.setColor(color)
        }
        background = mButtonBackground
    }

    /**
     * 属性解析
     */
    private fun parseAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperButton)
        val length = typedArray.indexCount
        for (i in 0 until length) {
            val attr = typedArray.getIndex(i)
            //文字内容
            if (attr == R.styleable.SuperButton_text) {
                text = typedArray.getText(attr)
            }
            //文字颜色
            if (attr == R.styleable.SuperButton_textColor) {
                mTextColor = typedArray.getColor(attr, Color.TRANSPARENT)
            }
            //文字大小
            if (attr == R.styleable.SuperButton_textSize) {
                mTextSize = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT)
            }
            //字体类型
            if (attr == R.styleable.SuperButton_textStyle) {
                mTextStyle = typedArray.getInt(attr, NORMAL)
            }
            //默认背景颜色
            if (attr == R.styleable.SuperButton_color_normal) {
                mColorNormal = typedArray.getColor(attr, VALUE_NULL)
            }
            //按压状态颜色
            if (attr == R.styleable.SuperButton_color_pressed) {
                mColorPressed = typedArray.getColor(attr, VALUE_NULL)
            }
            //图片在文字左边
            if (attr == R.styleable.SuperButton_drawable_left) {
                mDrawableLeft = typedArray.getDrawable(attr)
            }
            //图片在文字右边
            if (attr == R.styleable.SuperButton_drawable_right) {
                mDrawableRight = typedArray.getDrawable(attr)
            }
            //图片在文字上边
            if (attr == R.styleable.SuperButton_drawable_top) {
                mDrawableTop = typedArray.getDrawable(attr)
            }
            //图片在文字下边
            if (attr == R.styleable.SuperButton_drawable_bottom) {
                mDrawableBottom = typedArray.getDrawable(attr)
            }
            //图片在中间
            if (attr == R.styleable.SuperButton_drawable_middle) {
                mDrawableMiddle = typedArray.getDrawable(attr)
            }
            //图片的宽度
            if (attr == R.styleable.SuperButton_drawable_middle_width) {
                mDrawableMiddleWidth = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT)
            }
            //图片的高度
            if (attr == R.styleable.SuperButton_drawable_middle_height) {
                mDrawableMiddleHeight = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT)
            }
            //自动适应文字的大小
            if (attr == R.styleable.SuperButton_drawable_auto) {
                mDrawableAuto = typedArray.getBoolean(attr, true)
            }
            //文字是否单行
            if (attr == R.styleable.SuperButton_singleLine) {
                mSingleLine = typedArray.getBoolean(attr, true)
            }
            //图片距离文字距离
            if (attr == R.styleable.SuperButton_drawable_padding) {
                mDrawablePadding = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT)
            }
            //形状
            if (attr == R.styleable.SuperButton_shape) {
                mShape = typedArray.getInt(attr, RECT)
            }
            //开始颜色
            if (attr == R.styleable.SuperButton_color_start) {
                mColorStart = typedArray.getColor(attr, Color.TRANSPARENT)
            }
            //结束颜色
            if (attr == R.styleable.SuperButton_color_end) {
                mColorEnd = typedArray.getColor(attr, Color.TRANSPARENT)
            }
            //颜色方向
            if (attr == R.styleable.SuperButton_color_direction) {
                mColorDirection = typedArray.getInt(attr, LEFT_RIGHT)
            }
            //所有角圆角半径
            if (attr == R.styleable.SuperButton_corner) {
                mCorner = typedArray.getDimensionPixelSize(attr, VALUE_DEFAULT)
            }
            //左上角圆角半径
            if (attr == R.styleable.SuperButton_corner_left_top) {
                mCornerLeftTop = typedArray.getDimensionPixelSize(attr, VALUE_NULL)
            }
            //右上角圆角半径
            if (attr == R.styleable.SuperButton_corner_right_top) {
                mCornerRightTop = typedArray.getDimensionPixelSize(attr, VALUE_NULL)
            }
            //左下角圆角半径
            if (attr == R.styleable.SuperButton_corner_left_bottom) {
                mCornerLeftBottom = typedArray.getDimensionPixelSize(attr, VALUE_NULL)
            }
            //右下角圆角半径
            if (attr == R.styleable.SuperButton_corner_right_bottom) {
                mCornerRightBottom = typedArray.getDimensionPixelSize(attr, VALUE_NULL)
            }
            //边框宽度
            if (attr == R.styleable.SuperButton_border_width) {
                mBorderWidth = typedArray.getDimensionPixelSize(attr, 0)
            }
            //边框颜色
            if (attr == R.styleable.SuperButton_border_color) {
                mBorderColor = typedArray.getColor(attr, Color.TRANSPARENT)
            }
            //按钮是否可以点击
            if (attr == R.styleable.SuperButton_button_click_able) {
                mButtonClickable = typedArray.getBoolean(attr, true)
            }
        }
        typedArray.recycle()
    }

}
