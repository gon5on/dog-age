package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.ConfirmDialog.CallbackListener;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 確認ダイアログ
 */
public class ConfirmDialog extends BaseDialog<CallbackListener> {
    /**
     * インスタンスを返す
     *
     * @param title タイトル
     * @param msg 本文
     * @return dialog ConfirmDialog
     */
    public static ConfirmDialog getInstance(String title, String msg) {
        ConfirmDialog dialog = new ConfirmDialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("msg", msg);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_confirm);

        //タイトルセット
        TextView textViewTitle = (TextView) dialog.findViewById(R.id.textViewTitle);
        textViewTitle.setText(getArguments().getString("title"));

        //テキストセット
        TextView textViewMsg = (TextView) dialog.findViewById(R.id.textViewMsg);
        textViewMsg.setText(getArguments().getString("msg"));

        //ボタンにイベントをセット
        Button buttonOk = (Button) dialog.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogOk();
                }
                dismiss();
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickConfirmDialogCancel();
                }
                dismiss();
            }
        });

        return dialog;
    }

    /**
     * コールバックリスナー
     */
    public interface CallbackListener {
        /**
         * 確認ダイアログでOKが押された
         */
        void onClickConfirmDialogOk();

        /**
         * 確認ダイアログでキャンセルが押された
         */
        void onClickConfirmDialogCancel();
    }
}