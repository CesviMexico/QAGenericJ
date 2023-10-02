package com.cesvimexico.qagenericj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SgnCanvas extends View {

    Paint paint;
    Path path;

    public SgnCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    }


    public void clear(){

        invalidate();
        System.gc();

    }

        @Override
    protected  void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
float xPos = event.getX();
        float yPos = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos,yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;
                case MotionEvent.ACTION_UP:
                    break;
            default:
                return false;

        }
        invalidate();
        return false;
    }

    public Bitmap getBitmap()
    {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


}
