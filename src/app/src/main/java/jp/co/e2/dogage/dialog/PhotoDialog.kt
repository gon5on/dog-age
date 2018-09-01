package jp.co.e2.dogage.dialog

import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.PhotoDialog.CallbackListener

import android.support.v7.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView

/**
 * 画像表示ダイアログ
 */
class PhotoDialog : BaseDialog<CallbackListener>() {

    companion object {
        private const val PARAM_BITMAP = "bitmap"

        /**
         * ファクトリーメソッド
         *
         * @param bitmap ビットマップ
         * @return SampleDialog
         */
        fun newInstance(bitmap: Bitmap): PhotoDialog {
            val dialog = PhotoDialog()

            val bundle = Bundle()
            bundle.putParcelable(PARAM_BITMAP, bitmap)
            dialog.arguments = bundle

            return dialog
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_photo, null)

        val imageViewPhoto = view.findViewById<ImageView>(R.id.imageViewPhoto)
        imageViewPhoto.setImageBitmap(arguments!!.getParcelable<Parcelable>(PARAM_BITMAP) as Bitmap)

        val builder = AlertDialog.Builder(context!!, R.style.TransparentDialogStyle)
        builder.setView(view)

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener
}
