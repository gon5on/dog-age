package jp.co.e2.dogage.dialog;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.PhotoSelectDialog.CallbackListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 写真選択ダイアログ
 * 
 * @access public
 */
public class PhotoSelectDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @String Integer photoFlg
     * @return PhotoSelectDialog
     * @access public
     */
    public static PhotoSelectDialog getInstance(Integer photoFlg)
    {
        PhotoSelectDialog dialog = new PhotoSelectDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("buttonDelPhoto", photoFlg);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * onCreateDialog
     * 
     * @param Bundle savedInstanceState
     * @return Dialog
     * @access public
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //ダイアログ生成
        Dialog dialog = createDefaultDialog(R.layout.dialog_select_photo);

        //ボタンにイベントをセット
        Button buttonCamera = (Button) dialog.findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogCamera();
                }
                dismiss();
            }
        });

        Button buttonGallery = (Button) dialog.findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogGallery();
                }
                dismiss();
            }
        });

        Button buttonDelPhoto = (Button) dialog.findViewById(R.id.buttonDelPhoto);
        buttonDelPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickPhotoSelectDialogDelPhoto();
                }
                dismiss();
            }
        });

        Integer photoFlg = getArguments().getInt("buttonDelPhoto");
        buttonDelPhoto.setVisibility((photoFlg == 1) ? View.VISIBLE : View.GONE);

        return dialog;
    }

    /**
     * コールバックリスナー
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickPhotoSelectDialogCamera();

        public void onClickPhotoSelectDialogGallery();

        public void onClickPhotoSelectDialogDelPhoto();
    }
}
