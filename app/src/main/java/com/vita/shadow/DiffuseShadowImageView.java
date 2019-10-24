package com.vita.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;


/**
 * 带弥散阴影的ImageView
 */

public class DiffuseShadowImageView extends LinearLayout {

    private Bitmap originalImage;

    private View rootView = null;

    private ImageView riv_image = null;
    private ImageView iv_shadow_image = null;

    private Context context;

    public DiffuseShadowImageView(Context context) {
        this(context, null);
    }

    public DiffuseShadowImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiffuseShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        rootView = View.inflate(getContext(), R.layout.diffuse_shadow_imageview_layout, this);
        riv_image = rootView.findViewById(R.id.riv_image);
        iv_shadow_image = rootView.findViewById(R.id.iv_shadow_image);
    }


    public void loadImage(Bitmap loadedImage) {
        originalImage = loadedImage;
        setReflectedImage();
    }

    private void setReflectedImage() {
        if (originalImage == null) {
            return;
        }
        try {
            int width = ScreenUtils.getScreenWidth();

            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);

            //这里的高度，可以自己修改，我这里不做通用。
            int reflectionHeight = ConvertUtils.dp2px(194);

            //对单独的View在运行时阶段禁用硬件加速
            setLayerType(LAYER_TYPE_SOFTWARE, null);

            //快速高速模糊，倍数最大，但是效率也是最慢的
            Bitmap blur = ImageUtils.stackBlur(originalImage, 25);
            Bitmap bitmapWithReflection = Bitmap.createBitmap(width, reflectionHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(blur);
            canvas1.drawColor(ContextCompat.getColor(context, R.color.transparent5));

            Canvas canvas = new Canvas(bitmapWithReflection);
            Paint mPaint = new Paint();
            mPaint.setAlpha(150);//设置透明度
            mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));//设置发光样式,NORMAL是内外发光
            //阴影部分左右都缩小40dpi，令左右边距圆滑
            canvas.drawBitmap(blur, null, new RectF(ConvertUtils.dp2px(40), 0, width - ConvertUtils.dp2px(40), reflectionHeight - ConvertUtils.dp2px(20)), mPaint);
            //设置阴影图
            iv_shadow_image.setImageBitmap(bitmapWithReflection);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置面层图
        riv_image.setImageBitmap(originalImage);
    }


}