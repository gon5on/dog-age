package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.ErrorDialog.CallbackListener;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * エラーダイアログ
 */
public class ErrorDialog extends BaseDialog<CallbackListener> {
    /**
     * インスタンスを返す
     *
     * @return SampleDialog
     * @param title タイトル
     * @param msg 本文
     */
    public static ErrorDialog getInstance(String title, String msg) {
        ErrorDialog dialog = new ErrorDialog();

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
        Dialog dialog = createDefaultDialog(R.layout.dialog_error);

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
                    mCallbackListener.onClickErrorDialogOk();
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
         * エラーダイアログでOKが押された
         */
        void onClickErrorDialogOk();
    }
}
