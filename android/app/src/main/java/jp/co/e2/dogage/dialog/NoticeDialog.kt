package jp.co.e2.dogage.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.NoticeDialog.CallbackListener

/**
 * お知らせダイアログ
 */
class NoticeDialog : BaseDialog<CallbackListener>() {

    companion object {
        private const val PARAM_TITLE = "title"
        private const val PARAM_MSG = "msg"

        /**
         * ファクトリーメソッド
         *
         * @param title タイトル
         * @param msg 本文
         * @return SampleDialog
         */
        fun newInstance(title: String, msg: String): NoticeDialog {
            val bundle = Bundle()
            bundle.putString(PARAM_TITLE, title)
            bundle.putString(PARAM_MSG, msg)

            val dialog = NoticeDialog()
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
            call?.onClickErrorDialogOk(tag!!)
        }

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener {
        /**
         * エラーダイアログでOKが押された
         *
         * @param tag タグ
         */
        fun onClickErrorDialogOk(tag: String)
    }
}
