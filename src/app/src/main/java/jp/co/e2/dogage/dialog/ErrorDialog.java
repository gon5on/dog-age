package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.ErrorDialog.CallbackListener;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * エラーダイアログ
 */
public class ErrorDialog extends BaseDialog<CallbackListener> {
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_MSG = "msg";

    /**
     * ファクトリーメソッド
     *
     * @param title タイトル
     * @param msg 本文
     * @return SampleDialog
     */
    public static ErrorDialog newInstance(String title, String msg) {
        ErrorDialog dialog = new ErrorDialog();

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
                    mCallbackListener.onClickErrorDialogOk(getTag());
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
         * エラーダイアログでOKが押された
         *
         * @param tag タグ
         */
        void onClickErrorDialogOk(String tag);
    }
}
