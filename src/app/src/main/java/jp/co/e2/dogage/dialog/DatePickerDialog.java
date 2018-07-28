package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.dialog.DatePickerDialog.CallbackListener;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Locale;

/**
 * 日付選択ダイアログ
 */
public class DatePickerDialog extends BaseDialog<CallbackListener> {
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_DATE = "date";

    /**
     * ファクトリーメソッド
     *
     * @param date 日付
     * @param title タイトル
     * @return dialog DatePickerDialog
     */
    public static DatePickerDialog newInstance(String date, String title) {
        DatePickerDialog dialog = new DatePickerDialog();

        //日付が空で渡ってきたときは、今日の日付を入れておく
        if (date == null) {
            date = new DateHelper().format(DateHelper.FMT_DATE);
        }

        Bundle bundle = new Bundle();
        bundle.putString(PARAM_DATE, date);
        bundle.putString(PARAM_TITLE, title);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_datepicker, null);

        final DatePicker datePicker = view.findViewById(R.id.datePicker);
        if (getArguments().containsKey(PARAM_DATE)) {
            String date[] = getArguments().getString(PARAM_DATE).split("-");
            datePicker.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(PARAM_TITLE));
        builder.setIcon(R.drawable.img_foot);
        builder.setView(view);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mCallbackListener != null) {
                    Integer year = datePicker.getYear();
                    Integer month = datePicker.getMonth() + 1;
                    Integer day = datePicker.getDayOfMonth();
                    String date = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, day);
                    mCallbackListener.onClickDatePickerDialogOk(getTag(), date);
                }
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickDatePickerDialogCancel(getTag());
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
         * 日付選択ダイアログでOKが押された
         *
         * @param tag タグ
         * @param date 日付
         */
        void onClickDatePickerDialogOk(String tag, String date);

        /**
         * 日付選択ダイアログでキャンセルが押された
         *
         * @param tag タグ
         */
        void onClickDatePickerDialogCancel(String tag);
    }
}
