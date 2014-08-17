package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.ArchiveDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * アーカイブとは？ダイアログ
 * 
 * @access public
 */
public class ArchiveDialog extends BaseDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @return SampleDialog
     * @access public
     */
    public static ArchiveDialog getInstance()
    {
        ArchiveDialog dialog = new ArchiveDialog();

        Bundle bundle = new Bundle();
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
        Dialog dialog = createDefaultDialog(R.layout.dialog_archive);

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
