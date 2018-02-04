package jp.co.e2.dogage.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import jp.co.e2.dogage.alarm.SetAlarmManager;
import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.common.LogUtils;
import jp.co.e2.dogage.common.Utils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.dialog.DatePickerDialog;
import jp.co.e2.dogage.dialog.ErrorDialog;
import jp.co.e2.dogage.dialog.KindSelectDialog;
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
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
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
    private static final String PARAM_DATA = "data";
    private static final String PARAM_PAGE_NUM = "page_num";
    private static final String PARAM_INIT_FLG = "init_flg";

    /**
     * ファクトリーメソッドもどき
     *
     * @param activity アクテビティ
     * @param initFlg 初期フラグ
     * @param pageNum 表示対象のページ数
     * @param data エンティティ
     * @return intent
     */
    public static Intent newInstance(Activity activity, boolean initFlg, int pageNum, PetEntity data) {
        Intent intent = new Intent(activity, InputActivity.class);
        intent.putExtra(PARAM_INIT_FLG, initFlg);
        intent.putExtra(PARAM_PAGE_NUM, pageNum);
        intent.putExtra(PARAM_DATA, data);

        return intent;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        //アクションバーをセットする
        if (getIntent().getBooleanExtra(PARAM_INIT_FLG, false)) {
            setToolbar();
        } else {
            setBackArrowToolbar();
        }

        if (savedInstanceState == null) {
            PetEntity data = (PetEntity) getIntent().getSerializableExtra(PARAM_DATA);
            Integer pageNum = getIntent().getIntExtra(PARAM_PAGE_NUM, 0);

            Fragment fragment = InputFragment.newInstance(pageNum, data);
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
    public static class InputFragment extends Fragment
            implements KindSelectDialog.CallbackListener, DatePickerDialog.CallbackListener, PopupMenu.OnMenuItemClickListener {

        private static final String TAG_DIALOG_BIRTHDAY = "birthday";
        private static final String TAG_DIALOG_ARCHIVE = "archive";

        private View mView = null;
        private PetEntity mPetEntity;
        private Uri mPhotoUri = null;
        private boolean mArchiveOpenFlg = false;

        /**
         * ファクトリーメソッド
         *
         * @param pageNum 表示対象のページ数
         * @param data エンティティ
         * @return intent
         */
        public static Fragment newInstance(int pageNum, PetEntity data) {
            Bundle bundle = new Bundle();
            bundle.putInt(PARAM_PAGE_NUM, pageNum);

            if (data != null) {
                bundle.putSerializable(PARAM_DATA, data);
            }

            Fragment fragment = new InputFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_input, container, false);

            if (savedInstanceState == null) {
                if (getArguments().containsKey(PARAM_DATA)) {
                    mPetEntity = (PetEntity) getArguments().getSerializable(PARAM_DATA);
                } else {
                    mPetEntity = new PetEntity();
                }
            } else {
                mPetEntity = (PetEntity) savedInstanceState.getSerializable(PARAM_DATA);
            }

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
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            outState.putSerializable(PARAM_DATA, mPetEntity);
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //カメラ
            if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_CAMERA) {
                fromCameraGalleryIntent(data);
            }
            //ギャラリ
            else if (resultCode == RESULT_OK && (requestCode == Config.INTENT_CODE_GALLERY)) {
                fromCameraGalleryIntent(data);
            }
            //トリミング
            else if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_TRIMMING) {
                fromTrimmingIntent(data);
            }
        }

        /**
         * カメラ・ギャラリーインテントからの戻り処理
         *
         * @param data データ
         */
        private void fromCameraGalleryIntent(Intent data) {
            try {
                if (data != null && data.getData() != null) {
                    LogUtils.d(data.getData());
                    mPhotoUri = data.getData();
                }

                //写真を一時ファイルとして保存
                String savePath = Config.getImgTmpFilePath(getActivity());
                InputStream is = getActivity().getContentResolver().openInputStream(mPhotoUri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                ImgHelper imgHelper = new ImgHelper(bmp);
                imgHelper.saveJpg(savePath);

                startActivityForResult(TrimmingActivity.getInstance(getActivity(), savePath), Config.INTENT_CODE_TRIMMING);

                String msg = getString(R.string.lets_trimming_photo);
                AndroidUtils.showToastL(getActivity(), msg);

            }
            //ギャラリーから選択した画像一時保存に失敗
            catch (IOException e){
                String title = getString(R.string.error);
                String msg = getString(R.string.save_photo_fail);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                String title = getString(R.string.error);
                String msg = getString(R.string.no_trimming_app);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * トリミングインテントからの戻り処理
         *
         * @param data データ
         */
        private void fromTrimmingIntent(Intent data) {
            try {
                //トリミングインテントから帰ってきたトリミング結果がfalseだった
                if (!data.getBooleanExtra(TrimmingActivity.TRIMMING_RESULT, false)) {
                    throw new Exception();
                }

                mPetEntity.setPhotoSaveFlg(true);
                mPetEntity.setPhotoFlg(true);

                //トリミング後保存した画像を取得
                ImgHelper imgUtils = new ImgHelper(Config.getImgTmpFilePath(getActivity()));
                Integer size = AndroidUtils.dpToPixel(getActivity(), Config.PHOTO_INPUT_DP);
                Bitmap bitmap = imgUtils.getResizeKadomaruBitmap(size, size, Config.getKadomaruPixcel(getActivity()));

                //画像を表示
                ImageView imageViewPhoto = mView.findViewById(R.id.imageViewPhoto);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (Exception e) {
                String title = getString(R.string.error);
                String msg = getString(R.string.trimming_fail);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * 値を画面にセットする
         */
        private void setItem() {
            try {
                //編集の場合
                if (mPetEntity.getId() != null) {
                    setDateToButton(R.id.buttonBirthday, mPetEntity.getBirthday());

                    Button buttonKind = mView.findViewById(R.id.buttonKind);
                    buttonKind.setText(mPetEntity.getKindDisp(getActivity().getApplicationContext()));

                    EditText editTextName = mView.findViewById(R.id.editTextName);
                    editTextName.setText(mPetEntity.getName());

                    if (mPetEntity.getArchiveDate() != null) {
                        setDateToButton(R.id.buttonArchive, mPetEntity.getArchiveDate());
                        setOpenButton();
                    }

                    if (mPetEntity.getPhotoFlg()) {
                        ImageView imageViewPhoto = mView.findViewById(R.id.imageViewPhoto);
                        imageViewPhoto.setImageBitmap(mPetEntity.getPhotoInput(getActivity()));
                    }
                }

                //画像がない場合、NO PHOTOを角丸の画像にする
                if (!mPetEntity.getPhotoFlg()) {
                    setNoPhoto();
                }

                //アーカイブ関連を制御する
                if (mPetEntity.getArchiveDate() == null) {
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

                ImageView imageViewPhoto = mView.findViewById(R.id.imageViewPhoto);
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

            String dateFormat = getString(R.string.date_format);

            Button button = mView.findViewById(resId);
            button.setText(String.format(dateFormat, dateArray[0], dateArray[1], dateArray[2]));
        }

        /**
         * クリアボタン表示非表示etc処理
         *
         * @param flg 表示フラグ
         */
        private void setClearButton(boolean flg) {
            if (flg) {
                Button buttonClear = mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.VISIBLE);
            } else {
                mPetEntity.setArchiveDate(null);

                Button buttonClear = mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.GONE);

                Button buttonArchive = mView.findViewById(R.id.buttonArchive);
                buttonArchive.setText(null);
            }
        }

        /**
         * その他を開くボタン表示非表示etc処理
         */
        private void setOpenButton() {
            final LinearLayout linearLayoutArchive = mView.findViewById(R.id.linearLayoutArchive);
            final Button buttonOpen = mView.findViewById(R.id.buttonOpen);

            if (!mArchiveOpenFlg) {
                mArchiveOpenFlg = true;

                linearLayoutArchive.setVisibility(View.VISIBLE);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 0f, 1f);
                objectAnimator.setDuration(500);
                objectAnimator.start();

                buttonOpen.setText(getString(R.string.close_other));
            } else {
                mArchiveOpenFlg = false;

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 1f, 0f);
                objectAnimator.setDuration(300);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linearLayoutArchive.setVisibility(View.GONE);
                        buttonOpen.setText(getString(R.string.open_other));
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
            Button buttonBirthday = mView.findViewById(R.id.buttonBirthday);
            buttonBirthday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getString(R.string.dog_birthday);

                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(mPetEntity.getBirthday(), title);
                    datePickerDialog.setCallbackListener(InputFragment.this);
                    datePickerDialog.show(getFragmentManager(), TAG_DIALOG_BIRTHDAY);
                }
            });

            //種類
            ArrayList<DogMasterEntity> dogMasters = Config.getDogMastersList(getActivity());
            final KindSelectDialog kindSelectDialog = KindSelectDialog.newInstance(dogMasters);
            kindSelectDialog.setCallbackListener(this);

            Button buttonKind = mView.findViewById(R.id.buttonKind);
            buttonKind.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    kindSelectDialog.show(getFragmentManager(), "dialog");
                }
            });

            //写真
            ImageView imageViewPhoto = mView.findViewById(R.id.imageViewPhoto);
            final PopupMenu popup = new PopupMenu(getActivity(), imageViewPhoto);
            popup.getMenuInflater().inflate(R.menu.photo_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            imageViewPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.show();
                }
            });

            //開くボタン
            Button buttonOpen = mView.findViewById(R.id.buttonOpen);
            buttonOpen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOpenButton();
                }
            });

            //アーカイブ
            Button buttonArchive = mView.findViewById(R.id.buttonArchive);
            buttonArchive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getString(R.string.dog_die_date);

                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(mPetEntity.getArchiveDate(), title);
                    datePickerDialog.setCallbackListener(InputFragment.this);
                    datePickerDialog.show(getFragmentManager(), TAG_DIALOG_ARCHIVE);
                }
            });

            //クリアボタン
            Button buttonClear = mView.findViewById(R.id.buttonClear);
            buttonClear.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClearButton(false);
                }
            });

            //保存ボタン
            Button buttonSave = mView.findViewById(R.id.buttonSave);
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

                String title = getString(R.string.error);
                ErrorDialog errorDialog = ErrorDialog.newInstance(title, errorMsg);
                errorDialog.show(getFragmentManager(), "dialog");
                return;
            }

            //保存とページ遷移
            if (saveDb()) {
                //アラームセット
                new SetAlarmManager(getActivity()).set();

                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            } else {
                String title = getString(R.string.error);
                String msg = getString(R.string.save_fail);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
            }
        }

        /**
         * アーカイブ日付は誕生日より未来かどうか
         *
         * @return boolean
         */
        private ValidateHelper validate() {
            EditText editTextName = mView.findViewById(R.id.editTextName);
            String name = editTextName.getText().toString();

            //バリデーション
            ValidateHelper v = new ValidateHelper();

            String keyName = getString(R.string.dog_name);
            ValidateRequire.check(v, name, keyName);
            ValidateLength.maxCheck(v, name, keyName, 10);

            String keyBirthday = getString(R.string.dog_birthday);
            ValidateRequire.check(v, mPetEntity.getBirthday(), keyBirthday);
            ValidateDate.check(v, mPetEntity.getBirthday(), keyBirthday, DateHelper.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mPetEntity.getBirthday(), keyBirthday);

            String keyKind = getString(R.string.dog_kind);
            ValidateRequire.check(v, mPetEntity.getKind(), keyKind);

            String keyDieDate = getString(R.string.dog_die_date);
            ValidateDate.check(v, mPetEntity.getArchiveDate(), keyDieDate, DateHelper.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mPetEntity.getArchiveDate(), keyDieDate);

            //独自バリデーション
            if (!customValidate(mPetEntity.getArchiveDate(), mPetEntity.getBirthday())) {
                v.error(keyDieDate, getString(R.string.validate_error_archive_date));
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
                Long archiveDateMillis = new DateHelper(archiveDate, DateHelper.FMT_DATE).getMilliSecond();
                Long birthdayMillis = new DateHelper(birthday, DateHelper.FMT_DATE).getMilliSecond();

                ret = ((archiveDateMillis.compareTo(birthdayMillis)) <= 0);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return ret;
        }

        /**
         * DBに保存する
         *
         * @return boolean ret
         */
        private boolean saveDb() {
            boolean ret;
            SQLiteDatabase db = null;

            try {
                EditText editTextName = mView.findViewById(R.id.editTextName);
                String name = editTextName.getText().toString();
                mPetEntity.setName(name);

                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity());
                db = helper.getWritableDatabase();

                db.beginTransaction();

                PetDao petDao = new PetDao(getActivity());
                ret = petDao.save(db, mPetEntity);

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
         * カメラを起動する
         */
        private void startCamera() {
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
                String title = getString(R.string.error);
                String msg = getString(R.string.no_camera_app);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * ギャラリーを起動する
         */
        private void startGallery() {
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, Config.INTENT_CODE_GALLERY);
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                String title = getString(R.string.error);
                String msg = getString(R.string.no_gallery_app);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickKindSelectDialog(String tag, Integer kind, String name) {
            mPetEntity.setKind(kind);

            Button buttonKind = mView.findViewById(R.id.buttonKind);
            buttonKind.setText(name);
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickDatePickerDialogOk(String tag, String date) {
            if (tag.equals(TAG_DIALOG_BIRTHDAY)) {
                mPetEntity.setBirthday(date);
                setDateToButton(R.id.buttonBirthday, mPetEntity.getBirthday());
            }
            else if (tag.equals(TAG_DIALOG_ARCHIVE)) {
                mPetEntity.setArchiveDate(date);
                setDateToButton(R.id.buttonArchive, mPetEntity.getArchiveDate());

                Button buttonClear = mView.findViewById(R.id.buttonClear);
                buttonClear.setVisibility(View.VISIBLE);
            }
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public void onClickDatePickerDialogCancel(String tag) {
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuCamera:
                    startCamera();
                    break;
                case R.id.menuGallery:
                    startGallery();
                    break;
                case R.id.menuDelete:
                    mPetEntity.setPhotoFlg(false);
                    mPetEntity.setPhotoSaveFlg(false);

                    //NO PHOTOをセットする
                    setNoPhoto();
                    break;
            }

            return false;
        }
    }
}