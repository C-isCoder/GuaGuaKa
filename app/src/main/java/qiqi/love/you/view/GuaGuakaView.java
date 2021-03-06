package qiqi.love.you.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import qiqi.love.you.R;

/**
 * Created by iscod on 2016/5/7.
 */
public class GuaGuakaView extends View {

    private Paint mOutterPaint = new Paint();

    private Paint mBackPaint = new Paint();

    private Canvas mCanvas;

    private Path mPath;

    private Bitmap mBitmap;
    private Bitmap mBack;

    private int mLastX;
    private int mLastY;

    private Rect mTextBound = new Rect();
    private String mText = "￥5000,000";
    private boolean isComplete;

    public GuaGuakaView(Context context) {
        super(context);
        init();
    }

    public GuaGuakaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuaGuakaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        setBackPaint();
        setOutterPaint();
    }

    private void setOutterPaint() {
        //mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setStyle(Paint.Style.FILL);
        mOutterPaint.setColor(Color.RED);
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutterPaint.setDither(true);
        mOutterPaint.setStrokeWidth(20);
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    private void setBackPaint() {
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(Color.DKGRAY);
        mBackPaint.setDither(true);
        mBackPaint.setTextSize(22);
        mBackPaint.setTextScaleX(2f);
        mBackPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30, mOutterPaint);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.bg), null, new RectF(0, 0, width, height), null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2, mBackPaint);
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }

    }

    private void drawPath() {
        mCanvas.drawPath(mPath, mOutterPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
        }
        invalidate();
        return true;
    }

    public void rest() {
        //Log.d("CID", "clean");
        isComplete = false;
        mCanvas.drawColor(Color.parseColor("#c0c0c0"), PorterDuff.Mode.DST_OVER);//清理画布
        //重置刮刮乐图标
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.bg), null, new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), null);
        invalidate();//刷新view
        mPath.reset();//把记录的点路径重置一下不然再绘制的时候会出现之前绘制过的。
        //init();//这个方法也可以重置，api提供的reset更好吧。
    }

    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totaArea = w * h;

            Bitmap bitmap = mBitmap;

            mPixels = new int[w * h];
            /**
             * 拿到所有的像素信息
             */
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
            /**
             * 便利统计擦除的区域
             */
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }
            /**
             * 根据百分比，进行一些操作
             */
            if (wipeArea > 0 && totaArea > 0) {
                int perent = (int) (wipeArea * 100 / totaArea);
                Log.e("CID", "perent:" + perent);
                if (perent > 70) {
                    isComplete = true;
                    postInvalidate();//非UI线程刷新view invalidate()Ui线程刷新。
                }
            }
        }
    };

    public void setText(String s) {
        mText = s;
    }
}
