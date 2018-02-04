package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.ConfirmDialog.CallbackListener;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * 確認ダイアログ
 */
public class ConfirmDialog extends BaseDialog<CallbackListener> {
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_MSG = "msg";

    /**
     * ファクトリーメソッド
     *
     * @param title タイトル
     * @param msg 本文
     * @return dialog ConfirmDialog
     */
    public static ConfirmDialog newInstance(String title, String msg) {
        ConfirmDialog dialog = new ConfirmDialog();

        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TITLE, title);
        bundle.putString(PARAM_MSG, msg);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(PARAM_TITLE));
        builder.setIcon(R.drawable.img_foot);
        builder.setMessage(getArguments().getString(PARAM_MSG));

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogOk(getTag());
                }
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogCancel(getTag());
                }
            }
        });

        return builder.create();
    }

    /**
     * コールバックリスナー
     */
    public interface CallbackListener {
        /**
         * 確認ダイアログでOKが押された
         *
         * @param tag タグ
         */
        void onClickConfirmDialogOk(String tag);

        /**
         * 確認ダイアログでキャンセルが押された
         *
         * @param tag タグ
         */
        void onClickConfirmDialogCancel(String tag);
    }
}
