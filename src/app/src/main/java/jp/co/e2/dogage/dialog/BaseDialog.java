package jp.co.e2.dogage.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;

/**
 * ダイアログ基底クラス
 */
public abstract class BaseDialog<Interface> extends DialogFragment {
    private static final String PARAM_LISTENER_TYPE = "listenerType";
    private static final int LISTENER_ACTIVITY = 1;
    private static final int LISTENER_FRAGMENT = 2;

    protected Interface mCallbackListener = null;

    /**
     * ${inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Integer listenerType = getArguments().getInt(PARAM_LISTENER_TYPE);

        if (listenerType == BaseDialog.LISTENER_ACTIVITY) {
            mCallbackListener = (Interface) activity;
        } else if (listenerType == BaseDialog.LISTENER_FRAGMENT) {
            mCallbackListener = (Interface) getTargetFragment();
        }
    }

    /**
     * ダイアログのイベントリスナーを登録する
     *
     * アクテビティから呼ばれているのか、フラグメントから呼ばれているのかを判別して、
     * 適当な方法でイベントリスナーを登録する
     *
     * @param listener コールバックリスナー
     */
    public void setCallbackListener(Interface listener) {
        Integer listenerType;

        if (listener instanceof Activity) {
            listenerType = BaseDialog.LISTENER_ACTIVITY;
        } else if (listener instanceof Fragment) {
            listenerType = BaseDialog.LISTENER_FRAGMENT;
            setTargetFragment((Fragment) listener, 0);
        } else {
            throw new IllegalArgumentException(listener.getClass() + " must be either an Activity or a Fragment");
        }

        Bundle bundle = getArguments();
        bundle.putInt(PARAM_LISTENER_TYPE, listenerType);
        setArguments(bundle);
    }

    /**
     * コールバックリスナーを削除
     */
    public void removeCallbackListener() {
        mCallbackListener = null;
    }
}
