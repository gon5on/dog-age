package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.PhotoDialog.CallbackListener;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 画像表示ダイアログ
 */
public class PhotoDialog extends BaseDialog<CallbackListener> {
    private static final String PARAM_BITMAP = "bitmap";

    /**
     * ファクトリーメソッド
     *
     * @param bitmap ビットマップ
     * @return SampleDialog
     */
    public static PhotoDialog newInstance(Bitmap bitmap) {
        PhotoDialog dialog = new PhotoDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_BITMAP, bitmap);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_photo, null);

        ImageView imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setImageBitmap((Bitmap) getArguments().getParcelable(PARAM_BITMAP));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener {
    }
}
