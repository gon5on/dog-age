package jp.co.e2.dogage.dialog;

import android.app.Activity;
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
    public static final int LISTENER_ACTIVITY = 1;
    public static final int LISTENER_FRAGMENT = 2;

    protected Interface mCallbackListener = null;

    /**
     * 基本ダイアログ作成
     *
     * @param layoutId レイアウトリソースID
     * @return Dialog dialog
     */
    protected Dialog createDefaultDialog(int layoutId) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layoutId);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Integer listenerType = getArguments().getInt("listenerType");

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
        bundle.putInt("listenerType", listenerType);
        setArguments(bundle);
    }

    /**
     * コールバックリスナーを削除
     */
    public void removeCallbackListener() {
        mCallbackListener = null;
    }
}
