package jp.co.e2.dogage.activity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.DateHelper;
import jp.co.e2.dogage.common.ImgHelper;
import jp.co.e2.dogage.common.Utils;
import jp.co.e2.dogage.config.Config;
import jp.co.e2.dogage.dialog.ArchiveDialog;
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
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 入力画面アクテビティ
 * 
 * @access public
 */
public class InputActivity extends BaseActivity
{
    /**
     * onCreate
     * 
     * @param Bundle savedInstanceState
     * @return void
     * @access protected
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //初回起動以外だったら、アクションバーに戻るボタンをセット
        if (getIntent().getIntExtra("initFlag", 0) == 0) {
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setLogo(R.drawable.ic_back);
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
     * onOptionsItemSelected
     * 
     * @param MenuItem item
     * @return void
     * @access protected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * InputFragment
     * 
     * @access public
     */
    public static class InputFragment extends Fragment
            implements KindSelectDialog.CallbackListener, DatePickerDialog.CallbackListener, PhotoSelectDialog.CallbackListener
    {
        private static Integer DIALOG_BIRTHDAY_FLG = 1;
        private static Integer DIALOG_ARCHIVE_FLG = 2;

        private View mView = null;

        private PetEntity mSavedItem = null;

        private String mBirthday = null;
        private Integer mKind = null;
        private Integer mPhotoFlg = 0;
        private Uri mPhotoUri = null;
        private Boolean mArchiveFlg = false;
        private String mArchiveDate = null;

        /**
         * onCreateView
         * 
         * @param LayoutInflater inflater
         * @param ViewGroup container
         * @param Bundle savedInstanceState
         * @return View
         * @access public
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
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
         * onActivityResult
         * 
         * @param int requestCode
         * @param int resultCode
         * @param Intent data
         * @return void
         * @access protected
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            //カメラ
            if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_CAMERA) {
                fromCameraGalleryIntent(data);
            }
            //ギャラリ
            else if (resultCode == RESULT_OK && requestCode == Config.INTENT_CODE_GALLERY) {
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
         * @param Intent data
         * @return void
         * @access private
         */
        private void fromCameraGalleryIntent(Intent data)
        {
            try {
                //画像保存先のURIを取得
                mPhotoUri = Uri.fromFile(new File(Config.getImgTmpFilePath(getActivity())));

                //トリミングインテントを投げる
                Intent intent = new Intent();
                intent.setAction(Config.INTENT_TRIMMING);
                intent.setData(data.getData());
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(intent, Config.INTENT_CODE_TRIMMING);

                AndroidUtils.showToastL(getActivity(), "ワンちゃんの顔が中心になるようにトリミングしてください。");
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "トリミング可能なアプリがインストールされていません。\nもしくは、先程選択した写真が読み込めません、別の写真を選択してください。");
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * トリミングインテントからの戻り処理
         * 
         * @param Intent data
         * @return void
         * @access private
         */
        private void fromTrimmingIntent(Intent data)
        {
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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 値を画面にセットする
         * 
         * @return void
         * @access private
         */
        private void setItem()
        {
            mSavedItem = (PetEntity) getArguments().getSerializable("data");

            try {
                //編集の場合
                if (mSavedItem != null) {
                    mBirthday = mSavedItem.getBirthday();
                    mKind = mSavedItem.getKind();
                    mPhotoFlg = mSavedItem.getPhotoFlg();
                    mArchiveDate = mSavedItem.getArchiveDate();

                    String[] birthday = mSavedItem.getBirthday().split("-");
                    Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
                    buttonBirthday.setText(String.format("%s年%s月%s日", birthday[0], birthday[1], birthday[2]));

                    Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
                    buttonKind.setText(mSavedItem.getKindDisp(getActivity().getApplicationContext()));

                    EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
                    editTextName.setText(mSavedItem.getName());

                    CheckBox checkBoxArchive = (CheckBox) mView.findViewById(R.id.checkBoxArchive);
                    if (mSavedItem.getArchiveDate() != null) {
                        mArchiveDate = mSavedItem.getArchiveDate();
                        mArchiveFlg = true;

                        String[] archiveDate = mSavedItem.getArchiveDate().split("-");
                        checkBoxArchive.setText(" アーカイブする (" + String.format("%s/%s/%s", archiveDate[0], archiveDate[1], archiveDate[2]) + ")");
                        checkBoxArchive.setChecked(true);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * NO PHOTOをセットする
         * 
         * @return void
         * @access private
         */
        private void setNoPhoto()
        {
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
         * クリックイベントをセットする
         * 
         * @return void
         * @access private
         */
        private void setClickEvent()
        {
            //誕生日
            Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
            buttonBirthday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = DatePickerDialog.getInstance(DIALOG_BIRTHDAY_FLG, mBirthday, "お誕生日");
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

            //アーカイブ
            final CheckBox checkBoxArchive = (CheckBox) mView.findViewById(R.id.checkBoxArchive);
            checkBoxArchive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mArchiveFlg == false) {
                        DatePickerDialog datePickerDialog = DatePickerDialog.getInstance(DIALOG_ARCHIVE_FLG, mArchiveDate, "アーカイブ日付");
                        datePickerDialog.setCallbackListener(InputFragment.this);
                        datePickerDialog.show(getFragmentManager(), "dialog");

                        //アーカイブ日付を選択し終わって始めてチェックを入れたいので、チェックをはずしておく
                        checkBoxArchive.setChecked(false);
                    } else {
                        checkBoxArchive.setText(" アーカイブする");
                        mArchiveFlg = false;
                    }
                }
            });

            //アーカイブとは？
            final ArchiveDialog archiveDialog = ArchiveDialog.getInstance();

            Button buttonArchive = (Button) mView.findViewById(R.id.buttonArchive);
            buttonArchive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    archiveDialog.show(getFragmentManager(), "dialog");
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
         * 
         * @return void
         * @access private
         */
        private void save()
        {
            EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
            String name = editTextName.getText().toString();

            //バリデーション
            ValidateHelper v = new ValidateHelper();
            ValidateRequire.check(v, name, "お名前");
            ValidateLength.maxCheck(v, name, "お名前", 10);
            ValidateRequire.check(v, mBirthday, "お誕生日");
            ValidateDate.check(v, mBirthday, "お誕生日", DateHelper.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mBirthday, "お誕生日");
            ValidateRequire.check(v, mKind, "種類");

            if (mArchiveFlg == true) {
                ValidateRequire.check(v, mArchiveDate, "アーカイブの日付");
                ValidateDate.check(v, mArchiveDate, "アーカイブの日付", DateHelper.FMT_DATE);
                ValidateDate.isPastAllowToday(v, mBirthday, "アーカイブの日付");

                //独自バリデーション
                if (customValidate(mArchiveDate, mBirthday) == false) {
                    v.error("アーカイブ日付", "アーカイブ日付はお誕生日より過去は指定できません。");
                }
            }

            //エラーあり
            if (v.getResult() == false) {
                String errorMsg = Utils.implode(v.getErrorMsgList(), "\n");

                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", errorMsg);
                errorDialog.show(getFragmentManager(), "dialog");
                return;
            }

            //保存とページ遷移
            if (saveDb(name) == true) {
                AndroidUtils.showToastS(getActivity(), "保存しました。");

                Intent intent = new Intent(getActivity(), PetAgeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pageNum", getArguments().getInt("pageNum", 0));
                startActivity(intent);
                getActivity().finish();
            } else {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "保存に失敗しました。\nもう一度やり直してください。");
                errorDialog.show(getFragmentManager(), "dialog");
            }
        }

        /**
         * アーカイブ日付は誕生日より未来かどうか
         * 
         * @param String birthday
         * @param String archiveDate
         * @return boolean
         * @access private
         */
        private Boolean customValidate(String birthday, String archiveDate)
        {
            Boolean ret = true;

            if (birthday == null || archiveDate == null) {
                return ret;
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
         * @param String name
         * @return Boolean ret
         * @access private
         */
        private Boolean saveDb(String name)
        {
            Boolean ret = false;
            SQLiteDatabase db = null;

            try {
                PetEntity data = new PetEntity();
                data.setName(name);
                data.setBirthday(mBirthday);
                data.setKind(mKind);
                data.setPhotoFlg(mPhotoFlg);
                data.setPhotoUri(mPhotoUri);

                if (mArchiveFlg) {
                    data.setArchiveDate(mArchiveDate);
                }

                if (mSavedItem != null) {
                    data.setId(mSavedItem.getId());
                }

                BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getActivity());
                db = helper.getWritableDatabase();

                db.beginTransaction();

                PetDao petDao = new PetDao(getActivity());
                ret = petDao.save(db, data);

                if (ret == true) {
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                ret = false;
                e.printStackTrace();
            } finally {
                db.endTransaction();
                db.close();
            }

            return ret;
        }

        /**
         * 種類選択ダイアログでいずれかが選択された
         * 
         * @param Integer kind
         * @return void
         * @access public
         */
        @Override
        public void onClickKindSelectDialog(Integer kind, String name)
        {
            mKind = kind;

            Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
            buttonKind.setText(name);
        }

        /**
         * DatePickerダイアログでOKが押された
         * 
         * @param Integer flg
         * @param String date
         * @return void
         * @access public
         */
        @Override
        public void onClickDatePickerDialogOk(Integer flg, String date)
        {
            if (flg == DIALOG_BIRTHDAY_FLG) {
                mBirthday = date;

                String[] birthday = mBirthday.split("-");
                Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
                buttonBirthday.setText(String.format("%s年%s月%s日", birthday[0], birthday[1], birthday[2]));
            }
            else if (flg == DIALOG_ARCHIVE_FLG) {
                mArchiveDate = date;
                mArchiveFlg = true;

                String[] archiveDate = mArchiveDate.split("-");
                CheckBox checkBoxArchive = (CheckBox) mView.findViewById(R.id.checkBoxArchive);
                checkBoxArchive.setText(" アーカイブする (" + String.format("%s/%s/%s", archiveDate[0], archiveDate[1], archiveDate[2]) + ")");
                checkBoxArchive.setChecked(mArchiveFlg);
            }
        }

        /**
         * DatePickerダイアログでキャンセルが押された
         * 
         * @param Integer flg
         * @return void
         * @access public
         */
        @Override
        public void onClickDatePickerDialogCancel(Integer flg)
        {
        }

        /**
         * 写真選択ダイアログでカメラ起動が押された
         * 
         * @return void
         * @access public
         */
        @Override
        public void onClickPhotoSelectDialogCamera()
        {
            try {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Config.getImgTmpDirPath(getActivity()));
                startActivityForResult(intent, Config.INTENT_CODE_CAMERA);
            }
            //カメラアプリなくてエラー
            catch (ActivityNotFoundException e) {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "カメラアプリがインストールされていません。");
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * 写真選択ダイアログでギャラリーが押された
         * 
         * @return void
         * @access public
         */
        @Override
        public void onClickPhotoSelectDialogGallery()
        {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Config.INTENT_CODE_GALLERY);
            }
            //トリミングインテントに反応するアクテビティがなくてエラー
            catch (ActivityNotFoundException e) {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "ギャラリーアプリがインストールされていません。");
                errorDialog.show(getFragmentManager(), "dialog");
                e.printStackTrace();
            }
        }

        /**
         * 写真選択ダイアログで削除が押された
         * 
         * @return void
         * @access public
         */
        @Override
        public void onClickPhotoSelectDialogDelPhoto()
        {
            mPhotoFlg = 0;

            //NO PHOTOをセットする
            setNoPhoto();
        }
    }
}