package jp.co.e2.dogage.activity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.common.MediaUtils;
import jp.co.e2.dogage.common.Utils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.dialog.DatePickerDialog;
import jp.co.e2.dogage.dialog.ErrorDialog;
import jp.co.e2.dogage.dialog.KindSelectDialog;
import jp.co.e2.dogage.dialog.PhotoSelectDialog;
import jp.co.e2.dogage.entity.DogMasterEntity;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;
import jp.co.e2.dogage.validate.ValidateDate;
import jp.co.e2.dogage.validate.ValidateHelper;
import jp.co.e2.dogage.validate.ValidateLength;
import jp.co.e2.dogage.validate.ValidateRequire;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 入力画面アクテビティ
 */
public class InputActivity extends BaseActivity {
    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //初回起動以外だったら、アクションバーに戻るボタンをセット
        if (getIntent().getIntExtra("initFlag", 0) == 0) {
            if (getActionBar() != null) {
                getActionBar().setDisplayShowHomeEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
                getActionBar().setLogo(R.drawable.ic_back);
            }
        }

        //編集の場合は値がわたってくる
        PetEntity savedItem = (PetEntity) getIntent().getSerializableExtra("item");
        Integer pageNum = (Integer) getIntent().getIntExtra("pageNum", 0);

        if (savedInstanceState == null) {
            InputFragment fragment = new InputFragment();
            Bundle args = new Bundle();
            args.putSerializable("data", savedItem);
            args.putInt("pageNum", pageNum);
            fragment.setArguments(args);

            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * InputFragment
     */
    public static class InputFragment extends Fragment implements KindSelectDialog.CallbackListener, DatePickerDialog.CallbackListener, PhotoSelectDialog.CallbackListener {
        private static Integer DIALOG_BIRTHDAY_FLG = 1;
        private static Integer DIALOG_ARCHIVE_FLG = 2;

        private View mView = null;

        private PetEntity mSavedItem = null;
        private String mBirthday = null;
        private Integer mKind = null;
        private Integer mPhotoFlg = 0;
        private Uri mPhotoUri = null;
        private String mArchiveDate = null;
        private Boolean mArchiveOpenFlg = false;

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_input, container, false);

            // fragment再生成抑止
            setRetainInstance(true);

            //値を画面にセットする
            setItem();

            //クリックイベントをセットする
            setClickEvent();

            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

            return mView;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //カメラ
            if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_CAMERA) {
                //ギャラリーに追加
                MediaScannerConnection.scanFile(
                        getActivity(),
                        new String[] {MediaUtils.getPathFromUri(getActivity(), mPhotoUri)},
                        new String[] {"image/jpeg"},
                        null
                );

                fromCameraGalleryIntent(data);
            }
            //ギャラリ
            else if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_GALLERY) {
                fromCameraGalleryIntent(data);
            }
            //トリミング
            else if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_TRIMMING) {
                fromTrimmingIntent();
            }
        }

        /**
         * カメラ・ギャラリーインテントからの戻り処理
         *
         * @param data データ
         */
        private void fromCameraGalleryIntent(Intent data) {
            try {
                //元画像（カメラで写真を撮った場合はdataが空なので、事前に保持していたURIを採用する）
                Uri inputData;
                if (data != null && data.getData() != null) {
                    inputData = data.getData();
                } else {
                    inputData = mPhotoUri;
                }

                //画像保存先のURIを取得
                mPhotoUri = Uri.fromFile(new File(Config.getImgTmpFilePath(getActivity())));

                //トリミングインテントを投げる
                Intent intent = new Intent();
                intent.setAction(Config.INTENT_TRIMMING);
                intent.setData(inputData);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(intent, Config.INTENT_CODE_TRIMMING);

                String msg = getResources().getString(R.string.lets_trimming_photo);
                AndroidUtils.showToastL(getActivity(), msg);
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                String title = getResources().getString(R.string.error);
                String msg = getResources().getString(R.string.no_trimming_app);

                ErrorDialog errorDialog = ErrorDialog.getInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * トリミングインテントからの戻り処理
         */
        private void fromTrimmingIntent() {
            try {
                mPhotoFlg = 1;

                //トリミング後保存した画像を取得
                ImgHelper imgUtils = new ImgHelper(getActivity(), mPhotoUri);
                Integer size = AndroidUtils.dpToPixel(getActivity(), Config.PHOTO_INPUT_DP);
                Bitmap bitmap = imgUtils.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(getActivity()));

                imgUtils = null;

                //画像を表示
                ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (Exception e) {
                String title = getResources().getString(R.string.error);
                String msg = getResources().getString(R.string.trimming_fail);

                ErrorDialog errorDialog = ErrorDialog.getInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * 値を画面にセットする
         */
        private void setItem() {
            mSavedItem = (PetEntity) getArguments().getSerializable("data");

            try {
                //編集の場合
                if (mSavedItem != null) {
                    mBirthday = mSavedItem.getBirthday();
                    mKind = mSavedItem.getKind();
                    mPhotoFlg = mSavedItem.getPhotoFlg();
                    mArchiveDate = mSavedItem.getArchiveDate();

                    setDateToButton(R.id.buttonBirthday, mSavedItem.getBirthday());

                    Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
                    buttonKind.setText(mSavedItem.getKindDisp(getActivity().getApplicationContext()));

                    EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
                    editTextName.setText(mSavedItem.getName());

                    if (mSavedItem.getArchiveDate() != null) {
                        setDateToButton(R.id.buttonArchive, mSavedItem.getArchiveDate());
                        setOpenButton();
                    }

                    if (mSavedItem.getPhotoFlg() == 1) {
                        ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
                        imageViewPhoto.setImageBitmap(mSavedItem.getPhotoInput(getActivity()));
                    }
                }

                //画像がない場合、NO PHOTOを角丸の画像にする
                if (mSavedItem == null || mSavedItem.getPhotoFlg() == 0) {
                    setNoPhoto();
                }

                //アーカイブ関連を制御する
                if (mSavedItem == null || mSavedItem.getArchiveDate() == null) {
                    mArchiveOpenFlg = true;

                    setClearButton(false);
                    setOpenButton();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * NO PHOTOをセットする
         */
        private void setNoPhoto() {
            try {
                ImgHelper imgUtils = new ImgHelper(getActivity(), R.drawable.img_no_photo);
                Bitmap bitmap = imgUtils.getKadomaruBitmap(Config.getKadomaruPixcel(getActivity()));

                imgUtils = null;

                ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * ボタン上に日付を表示する
         *
         * @param resId リソースID
         * @param date 日付
         */
        private void setDateToButton(Integer resId, String date) {
            String[] dateArray = date.split("-");

            String dateFormat = getResources().getString(R.string.date_format);

            Button button = (Button) mView.findViewById(resId);
            button.setText(String.format(dateFormat, dateArray[0], dateArray[1], dateArray[2]));
        }

        /**
         * クリアボタン表示非表示etc処理
         *
         * @param flg 表示フラグ
         */
        private void setClearButton(Boolean flg) {
            if (flg) {
                Button buttonClear = (Button) mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.VISIBLE);
            } else {
                mArchiveDate = null;

                Button buttonClear = (Button) mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.GONE);

                Button buttonArchive = (Button) mView.findViewById(R.id.buttonArchive);
                buttonArchive.setText(null);
            }
        }

        /**
         * その他を開くボタン表示非表示etc処理
         */
        private void setOpenButton() {
            final LinearLayout linearLayoutArchive = (LinearLayout) mView.findViewById(R.id.linearLayoutArchive);
            final Button buttonOpen = (Button) mView.findViewById(R.id.buttonOpen);

            if (!mArchiveOpenFlg) {
                mArchiveOpenFlg = true;

                linearLayoutArchive.setVisibility(View.VISIBLE);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 0f, 1f);
                objectAnimator.setDuration(500);
                objectAnimator.start();

                buttonOpen.setText(getResources().getString(R.string.close_other));
            } else {
                mArchiveOpenFlg = false;

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 1f, 0f);
                objectAnimator.setDuration(300);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linearLayoutArchive.setVisibility(View.GONE);
                        buttonOpen.setText(getResources().getString(R.string.open_other));
                    }
                });
                objectAnimator.start();
            }
        }

        /**
         * クリックイベントをセットする
         */
        private void setClickEvent() {
            //誕生日
            Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
            buttonBirthday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getResources().getString(R.string.dog_birthday);

                    DatePickerDialog datePickerDialog = DatePickerDialog.getInstance(DIALOG_BIRTHDAY_FLG, mBirthday, title);
                    datePickerDialog.setCallbackListener(InputFragment.this);
                    datePickerDialog.show(getFragmentManager(), "dialog");
                }
            });

            //種類
            ArrayList<DogMasterEntity> dogMasters = Config.getDogMastersList(getActivity());
            final KindSelectDialog kindSelectDialog = KindSelectDialog.getInstance(dogMasters);
            kindSelectDialog.setCallbackListener(this);

            Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
            buttonKind.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    kindSelectDialog.show(getFragmentManager(), "dialog");
                }
            });

            //写真
            ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoSelectDialog photoSelectDialog = PhotoSelectDialog.getInstance(mPhotoFlg);
                    photoSelectDialog.setCallbackListener(InputFragment.this);
                    photoSelectDialog.show(getFragmentManager(), "dialog");
                }
            });

            //開くボタン
            Button buttonOpen = (Button) mView.findViewById(R.id.buttonOpen);
            buttonOpen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOpenButton();
                }
            });

            //アーカイブ
            Button buttonArchive = (Button) mView.findViewById(R.id.buttonArchive);
            buttonArchive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getResources().getString(R.string.dog_die_date);

                    DatePickerDialog datePickerDialog = DatePickerDialog.getInstance(DIALOG_ARCHIVE_FLG, mArchiveDate, title);
                    datePickerDialog.setCallbackListener(InputFragment.this);
                    datePickerDialog.show(getFragmentManager(), "dialog");
                }
            });

            //クリアボタン
            Button buttonClear = (Button) mView.findViewById(R.id.buttonClear);
            buttonClear.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClearButton(false);
                }
            });

            //保存ボタン
            Button buttonSave = (Button) mView.findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    save();
                }
            });
        }

        /**
         * 保存する
         */
        private void save() {
            //バリデーション
            ValidateHelper v = validate();

            //エラーあり
            if (!v.getResult()) {
                String errorMsg = Utils.implode(v.getErrorMsgList(), "\n");

                String title = getResources().getString(R.string.error);
                ErrorDialog errorDialog = ErrorDialog.getInstance(title, errorMsg);
                errorDialog.show(getFragmentManager(), "dialog");
                return;
            }

            //保存とページ遷移
            if (saveDb()) {
                String msg = getResources().getString(R.string.save_success);
                AndroidUtils.showToastS(getActivity(), msg);

                Intent intent = new Intent(getActivity(), PetAgeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pageNum", getArguments().getInt("pageNum", 0));
                startActivity(intent);
                getActivity().finish();
            } else {
                String title = getResources().getString(R.string.error);
                String msg = getResources().getString(R.string.save_fail);

                ErrorDialog errorDialog = ErrorDialog.getInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
            }
        }

        /**
         * アーカイブ日付は誕生日より未来かどうか
         *
         * @return boolean
         */
        private ValidateHelper validate() {
            EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
            String name = editTextName.getText().toString();

            //バリデーション
            ValidateHelper v = new ValidateHelper();

            String keyName = getResources().getString(R.string.dog_name);
            ValidateRequire.check(v, name, keyName);
            ValidateLength.maxCheck(v, name, keyName, 10);

            String keyBirthday = getResources().getString(R.string.dog_birthday);
            ValidateRequire.check(v, mBirthday, keyBirthday);
            ValidateDate.check(v, mBirthday, keyBirthday, DateHelper.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mBirthday, keyBirthday);

            String keyKind = getResources().getString(R.string.dog_kind);
            ValidateRequire.check(v, mKind, keyKind);

            String keyDieDate = getResources().getString(R.string.dog_birthday);
            ValidateDate.check(v, mArchiveDate, keyDieDate, DateHelper.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mArchiveDate, keyDieDate);

            //独自バリデーション
            if (!customValidate(mArchiveDate, mBirthday)) {
                v.error(keyDieDate, getResources().getString(R.string.validate_error_archive_date));
            }

            return v;
        }

        /**
         * アーカイブ日付は誕生日より未来かどうか
         *
         * @param birthday 誕生日
         * @param archiveDate アーカイブ日付（亡くなった日）
         * @return boolean
         */
        private Boolean customValidate(String birthday, String archiveDate) {
            Boolean ret = true;

            if (birthday == null || archiveDate == null) {
                return true;
            }

            try {
                Long archiveDateMillis = new DateHelper(archiveDate, DateHelper.FMT_DATE).get().getTimeInMillis();
                Long birthdayMillis = new DateHelper(birthday, DateHelper.FMT_DATE).get().getTimeInMillis();

                ret = ((archiveDateMillis.compareTo(birthdayMillis)) <= 0);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return ret;
        }

        /**
         * DBに保存する
         *
         * @return Boolean ret
         */
        private Boolean saveDb() {
            Boolean ret = false;
            SQLiteDatabase db = null;

            try {
                EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
                String name = editTextName.getText().toString();

                PetEntity data = new PetEntity();
                data.setName(name);
                data.setBirthday(mBirthday);
                data.setKind(mKind);
                data.setPhotoFlg(mPhotoFlg);
                data.setPhotoUri(mPhotoUri);
                data.setArchiveDate(mArchiveDate);

                if (mSavedItem != null) {
                    data.setId(mSavedItem.getId());
                }

                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity());
                db = helper.getWritableDatabase();

                db.beginTransaction();

                PetDao petDao = new PetDao(getActivity());
                ret = petDao.save(db, data);

                if (ret) {
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                ret = false;
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }

            return ret;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickKindSelectDialog(Integer kind, String name) {
            mKind = kind;

            Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
            buttonKind.setText(name);
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickDatePickerDialogOk(Integer flg, String date) {
            if (flg.equals(DIALOG_BIRTHDAY_FLG)) {
                mBirthday = date;

                setDateToButton(R.id.buttonBirthday, mBirthday);
            } else if (flg.equals(DIALOG_ARCHIVE_FLG)) {
                mArchiveDate = date;

                setDateToButton(R.id.buttonArchive, mArchiveDate);

                Button buttonClear = (Button) mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.VISIBLE);
            }
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickDatePickerDialogCancel(Integer flg) {
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickPhotoSelectDialogCamera() {
            try {
                String filename = System.currentTimeMillis() + ".jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                mPhotoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(intent, Config.INTENT_CODE_CAMERA);
            }
            //カメラアプリなくてエラー
            catch (ActivityNotFoundException e) {
                String title = getResources().getString(R.string.error);
                String msg = getResources().getString(R.string.no_camera_app);

                ErrorDialog errorDialog = ErrorDialog.getInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickPhotoSelectDialogGallery() {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Config.INTENT_CODE_GALLERY);
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                String title = getResources().getString(R.string.error);
                String msg = getResources().getString(R.string.no_gallery_app);

                ErrorDialog errorDialog = ErrorDialog.getInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickPhotoSelectDialogDelPhoto() {
            mPhotoFlg = 0;

            //NO PHOTOをセットする
            setNoPhoto();
        }
    }
}