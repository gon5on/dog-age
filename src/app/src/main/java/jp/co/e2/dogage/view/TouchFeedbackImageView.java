package jp.co.e2.dogage.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * タップ時に色が少し濃くなるフィードバックがついているImageView
 */
public class TouchFeedbackImageView extends AppCompatImageView implements OnTouchListener {
    /**
     * コンテキスト
     *
     * @param context コンテキスト
     */
    public TouchFeedbackImageView(Context context) {
        super(context);

        setOnTouchListener(this);
    }

    /**
     * コンテキスト
     *
     * @param context コンテキスト
     * @param attrs   指定した属性値
     */
    public TouchFeedbackImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        setOnTouchListener(this);
    }

    /**
     * コンテキスト
     *
     * @param context コンテキスト
     * @param attrs 指定した属性値
     * @param defStyle スタイルのリソースID
     */
    public TouchFeedbackImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOnTouchListener(this);
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                shadeOn(v);
                break;
            case MotionEvent.ACTION_CANCEL:
                shadeOff(v);
                break;
            case MotionEvent.ACTION_UP:
                shadeOff(v);
                break;
        }

        return false;
    }

    /**
     * 影を付ける
     *
     * @param v View
     */
    public void shadeOn(View v) {
        ((AppCompatImageView) v).setColorFilter(new LightingColorFilter(Color.LTGRAY, 0));
    }

    /**
     * 影を外す
     *
     * @param v View
     */
    public void shadeOff(View v) {
        ((AppCompatImageView) v).clearColorFilter();
    }
}