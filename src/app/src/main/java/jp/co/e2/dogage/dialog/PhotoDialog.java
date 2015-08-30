package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.PhotoDialog.CallbackListener;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 画像表示ダイアログ
 */
public class PhotoDialog extends BaseDialog<CallbackListener> {
    /**
     * インスタンスを返す
     *
     * @param bitmap ビットマップ
     * @return SampleDialog
     */
    public static PhotoDialog getInstance(Bitmap bitmap) {
        PhotoDialog dialog = new PhotoDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", bitmap);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_photo);

        //画像セット
        ImageView imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setImageBitmap((Bitmap) getArguments().getParcelable("bitmap"));

        //ボタンにイベントをセット
        Button buttonClose = (Button) dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoDialogClose();
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
         * 写真表示ダイアログで閉じるが押された
         */
        void onClickPhotoDialogClose();
    }
}
