package com.tmrnk.gongon.dogage.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.dialog.ErrorDialog.CallbackListener;

/**
 * エラーダイアログ
 * 
 * @access public
 */
public class ErrorDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @String String title
     * @String String msg
     * @return SampleDialog
     * @access public
     */
    public static ErrorDialog getInstance(String title, String msg)
    {
        ErrorDialog dialog = new ErrorDialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("msg", msg);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * onCreateDialog
     * 
     * @param Bundle savedInstanceState
     * @return Dialog
     * @access public
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
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
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickErrorDialogOk();
    }
}
