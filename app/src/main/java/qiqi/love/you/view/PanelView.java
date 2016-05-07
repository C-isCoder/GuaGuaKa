package qiqi.love.you.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by iscod on 2016/5/6.
 */
public class PanelView extends View {
    /**
     * 绘制一条线paint,即用户手指绘制path
     */
    private Paint mOutterPaint = new Paint();
    /**
     * 记录用户绘制的Path
     */
    private Path mPath;
    /**
     * 内存中创建的canvas
     */
    private Canvas mCanvas;
    /**
     * mCanvas绘制的内容在这上面
     */
    private Bitmap mBitmap;

    private int mLastX;
    private int mLastY;


    public PanelView(Context context) {
        super(context);
        init();
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化mBitmap
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        //设置画笔
        mOutterPaint.setColor(Color.RED);
        mOutterPaint.setAntiAlias(true);//开启抗锯齿
        mOutterPaint.setDither(true);//开启抖动圆滑
        mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);//画出线的线头是圆角
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);//圆角
        //设置画笔宽度
        mOutterPaint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Log.d("CID", "onDraw");
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制线条
     */
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
                //int dx = Math.abs(x - mLastX);
                //int dy = Math.abs(y - mLastY);

                //if (dx > 3 && dy > 3) {
                //    mPath.lineTo(x, y);
                //}
                mPath.lineTo(x, y);
                mLastX = x;
                mLastY = y;
                break;
        }
        invalidate();
        return true;

    }

    public void clearPanel() {
        //Log.d("CID", "clean");
        mCanvas.drawColor(Color.parseColor("#FAFAFA"), PorterDuff.Mode.CLEAR);//清理画布
        mPath.reset();//把记录的点路径重置一下不然再绘制的时候会出现之前绘制过的。
        //init();//这个方法也可以重置，api提供的reset更好吧。
    }
}
