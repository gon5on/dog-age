package jp.co.e2.dogage.dialog

import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.dialog.DatePickerDialog.CallbackListener

import android.support.v7.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker

import java.util.Locale

/**
 * 日付選択ダイアログ
 */
class DatePickerDialog : BaseDialog<CallbackListener>() {

    companion object {
        const val PARAM_TITLE = "title"
        const val PARAM_DATE = "date"

        /**
         * ファクトリーメソッド
         *
         * @param dateTmp 日付
         * @param title タイトル
         * @return dialog DatePickerDialog
         */
        fun newInstance(dateTmp: String?, title: String): DatePickerDialog {
            val dialog = DatePickerDialog()

            val date = dateTmp?: DateHelper().format(DateHelper.FMT_DATE)

            val bundle = Bundle()
            bundle.putString(PARAM_DATE, date)
            bundle.putString(PARAM_TITLE, title)

            dialog.arguments = bundle

            return dialog
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_datepicker, null)

        val date = arguments.getString(PARAM_DATE).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        datePicker.updateDate(Integer.parseInt(date[0]), (Integer.parseInt(date[1]) - 1), Integer.parseInt(date[2]))

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(arguments.getString(PARAM_TITLE))
        builder.setIcon(R.drawable.img_foot)
        builder.setView(view)

        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            val retDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, day)

            mCallbackListener?.onClickDatePickerDialogOk(tag, retDate)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            mCallbackListener?.onClickDatePickerDialogCancel(tag)
        }

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener {
        /**
         * 日付選択ダイアログでOKが押された
         *
         * @param tag タグ
         * @param date 日付
         */
        fun onClickDatePickerDialogOk(tag: String, date: String)

        /**
         * 日付選択ダイアログでキャンセルが押された
         *
         * @param tag タグ
         */
        fun onClickDatePickerDialogCancel(tag: String)
    }
}
