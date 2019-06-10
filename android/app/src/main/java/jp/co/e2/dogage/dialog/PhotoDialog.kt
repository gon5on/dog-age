package jp.co.e2.dogage.dialog

import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.PhotoDialog.CallbackListener

import android.app.Dialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.config.Config

/**
 * 画像表示ダイアログ
 */
class PhotoDialog : BaseDialog<CallbackListener>() {

    companion object {
        private const val PARAM_URI = "uri"

        /**
         * ファクトリーメソッド
         *
         * @param uri URI
         * @return SampleDialog
         */
        fun newInstance(uri: Uri): PhotoDialog {
            val dialog = PhotoDialog()

            val bundle = Bundle()
            bundle.putParcelable(PARAM_URI, uri)
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

        val uri = arguments!!.getParcelable<Parcelable>(PARAM_URI) as Uri;
        val size = AndroidUtils.dpToPixel(activity, Config.PHOTO_BIG_DP)
        Picasso.get().load(uri).resize(size, size).centerCrop().into(imageViewPhoto)

        val builder = AlertDialog.Builder(context!!, R.style.TransparentDialogStyle)
        builder.setView(view)

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener
}
