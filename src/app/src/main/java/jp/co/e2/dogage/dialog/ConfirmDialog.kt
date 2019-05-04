package jp.co.e2.dogage.dialog

import android.app.Dialog
import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.ConfirmDialog.CallbackListener

import android.os.Bundle
import androidx.appcompat.app.AlertDialog

/**
 * 確認ダイアログ
 */
class ConfirmDialog : BaseDialog<CallbackListener>() {

    companion object {
        private const val PARAM_TITLE = "title"
        private const val PARAM_MSG = "msg"

        /**
         * ファクトリーメソッド
         *
         * @param title タイトル
         * @param msg 本文
         * @return dialog ConfirmDialog
         */
        fun newInstance(title: String, msg: String): ConfirmDialog {
            val dialog = ConfirmDialog()

            val bundle = Bundle()
            bundle.putString(PARAM_TITLE, title)
            bundle.putString(PARAM_MSG, msg)

            dialog.arguments = bundle

            return dialog
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        builder.setTitle(arguments!!.getString(PARAM_TITLE))
        builder.setIcon(R.drawable.ic_footprint)
        builder.setMessage(arguments!!.getString(PARAM_MSG))

        builder.setPositiveButton(getString(R.string.ok)) { _, _ ->
            call?.onClickConfirmDialogOk(tag!!)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            call?.onClickConfirmDialogCancel(tag!!)
        }

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener {
        /**
         * 確認ダイアログでOKが押された
         *
         * @param tag タグ
         */
        fun onClickConfirmDialogOk(tag: String)

        /**
         * 確認ダイアログでキャンセルが押された
         *
         * @param tag タグ
         */
        fun onClickConfirmDialogCancel(tag: String)
    }
}
