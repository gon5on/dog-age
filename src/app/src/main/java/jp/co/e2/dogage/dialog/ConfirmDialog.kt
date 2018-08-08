package jp.co.e2.dogage.dialog

import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.ConfirmDialog.CallbackListener

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog

/**
 * 確認ダイアログ
 */
class ConfirmDialog : BaseDialog<CallbackListener>() {

    companion object {
        const val PARAM_TITLE = "title"
        const val PARAM_MSG = "msg"

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
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(arguments.getString(PARAM_TITLE))
        builder.setIcon(R.drawable.img_foot)
        builder.setMessage(arguments.getString(PARAM_MSG))

        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
            mCallbackListener?.onClickConfirmDialogOk(tag)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            mCallbackListener?.onClickConfirmDialogCancel(tag)
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
