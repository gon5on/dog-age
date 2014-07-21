package com.tmrnk.gongon.dogage.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.common.DateUtils;
import com.tmrnk.gongon.dogage.dialog.DatePickerDialog.CallbackListener;

/**
 * 日付選択ダイアログ
 * 
 * @access public
 */
public class DatePickerDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @String String title
     * @String String msg
     * @return ConfirmDialog
     * @access public
     */
    public static DatePickerDialog getInstance(String date)
    {
        DatePickerDialog dialog = new DatePickerDialog();

        //日付が空で渡ってきたときは、今日の日付を入れておく
        if (date == null) {
            date = new DateUtils().format(DateUtils.FMT_DATE);
        }

        Bundle bundle = new Bundle();
        bundle.putString("date", date);
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
        Dialog dialog = createDefaultDialog(R.layout.dialog_datepicker);

        //日付がわたってきていたらセット
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);

        if (getArguments().getString("date") != null) {
            String date[] = getArguments().getString("date").split("-");
            datePicker.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
        }

        //ボタンにイベントをセット
        Button buttonOk = (Button) dialog.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    Integer year = datePicker.getYear();
                    Integer month = datePicker.getMonth() + 1;
                    Integer day = datePicker.getDayOfMonth();
                    String date = String.format("%d-%02d-%02d", year, month, day);
                    mCallbackListener.onClickDatePickerDialogOk(date);
                }
                dismiss();
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickDatePickerDialogCancel();
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
        public void onClickDatePickerDialogOk(String date);

        public void onClickDatePickerDialogCancel();
    }
}
