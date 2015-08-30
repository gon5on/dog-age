package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.dialog.DatePickerDialog.CallbackListener;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * 日付選択ダイアログ
 */
public class DatePickerDialog extends BaseDialog<CallbackListener> {
    private Integer mFlg = 0;               //呼び出し元判別用フラグ

    /**
     * インスタンスを返す
     *
     * @param flg 呼び出し元判別用フラグ
     * @param date 日付
     * @param title タイトル
     * @return dialog DatePickerDialog
     */
    public static DatePickerDialog getInstance(Integer flg, String date, String title) {
        DatePickerDialog dialog = new DatePickerDialog();

        //日付が空で渡ってきたときは、今日の日付を入れておく
        if (date == null) {
            date = new DateHelper().format(DateHelper.FMT_DATE);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("flg", flg);
        bundle.putString("date", date);
        bundle.putString("title", title);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mFlg = getArguments().getInt("flg");

        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_datepicker);

        //タイトルセット
        TextView textViewTitle = (TextView) dialog.findViewById(R.id.textViewTitle);
        textViewTitle.setText(getArguments().getString("title"));

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
                    mCallbackListener.onClickDatePickerDialogOk(mFlg, date);
                }
                dismiss();
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickDatePickerDialogCancel(mFlg);
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
         * 日付選択ダイアログでOKが押された
         *
         * @param flg 呼び出し元判別フラグ
         * @param date 日付
         */
        void onClickDatePickerDialogOk(Integer flg, String date);

        /**
         * 日付選択ダイアログでキャンセルが押された
         *
         * @param flg 呼び出し元判別フラグ
         */
        void onClickDatePickerDialogCancel(Integer flg);
    }
}
