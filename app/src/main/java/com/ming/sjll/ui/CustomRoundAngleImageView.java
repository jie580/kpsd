package com.ming.sjll.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.utils.TextUtil;


/**
 * @author luoming
 * created at 2019-10-14 16:18
 * 圆角图片
 */
public class CustomRoundAngleImageView extends AppCompatImageView {
    float width, height;
    private int defaultRadius = 0;
    private int radius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public CustomRoundAngleImageView(Context context) {
        this(context, null);
        init(context, null);

    }

    public CustomRoundAngleImageView(Context context, int radius) {
        this(context, null);
        this.radius = radius;
        init(context, null);


    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

            // 读取配置
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Custom_Round_Image_View);
        if(radius == 0 )
        {
            radius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_radius, defaultRadius);
            leftTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_top_radius, defaultRadius);
            rightTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_top_radius, defaultRadius);
            rightBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_bottom_radius, defaultRadius);
            leftBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_bottom_radius, defaultRadius);
        }

            //如果四个角的值没有设置，那么就使用通用的radius的值。
            if (defaultRadius == leftTopRadius) {
                leftTopRadius = radius;
            }
            if (defaultRadius == rightTopRadius) {
                rightTopRadius = radius;
            }
            if (defaultRadius == rightBottomRadius) {
                rightBottomRadius = radius;
            }
            if (defaultRadius == leftBottomRadius) {
                leftBottomRadius = radius;
            }
            array.recycle();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        int maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        int maxRight = Math.max(rightTopRadius, rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        int maxTop = Math.max(leftTopRadius, rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if (width >= minWidth && height > minHeight) {
            Path path = new Path();
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius, 0);
            path.lineTo(width - rightTopRadius, 0);
            path.quadTo(width, 0, width, rightTopRadius);

            path.lineTo(width, height - rightBottomRadius);
            path.quadTo(width, height, width - rightBottomRadius, height);

            path.lineTo(leftBottomRadius, height);
            path.quadTo(0, height, 0, height - leftBottomRadius);

            path.lineTo(0, leftTopRadius);
            path.quadTo(0, 0, leftTopRadius, 0);

            canvas.clipPath(path);
        }
        try{
            super.onDraw(canvas);
        }catch(Exception e){
            Log.e("图片绘制错误",e.getMessage());
        }

    }

}
