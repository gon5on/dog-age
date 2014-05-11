package com.tmrnk.gongon.dogage.activity;

import java.text.ParseException;
import java.util.ArrayList;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.common.AndroidUtils;
import com.tmrnk.gongon.dogage.common.DateUtils;
import com.tmrnk.gongon.dogage.common.Utils;
import com.tmrnk.gongon.dogage.dialog.ErrorDialog;
import com.tmrnk.gongon.dogage.dialog.KindSelectDialog;
import com.tmrnk.gongon.dogage.model.AppSQLiteOpenHelper;
import com.tmrnk.gongon.dogage.model.DogMasterDao;
import com.tmrnk.gongon.dogage.model.DogMasterEntity;
import com.tmrnk.gongon.dogage.model.PetDao;
import com.tmrnk.gongon.dogage.model.PetEntity;
import com.tmrnk.gongon.dogage.validate.Validate;

/**
 * 入力画面アクテビティ
 * 
 * @access public
 */
public class InputActivity extends AppActivity
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

        //編集の場合は値がわたってくる
        PetEntity savedItem = (PetEntity) getIntent().getSerializableExtra("item");

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new InputFragment(savedItem)).commit();
        }
    }

    /**
     * onCreateOptionsMenu
     * 
     * @param Menu menu
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //初回起動以外だったラ、アクションバーに戻るボタンをセット
        if (getIntent().getIntExtra("initFlag", 0) == 0) {
            getMenuInflater().inflate(R.menu.input, menu);
        }

        return true;
    }

    /**
     * onOptionsItemSelected
     * 
     * @param MenuItem item
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Integer id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * InputFragment
     * 
     * @access public
     */
    public static class InputFragment extends Fragment implements KindSelectDialog.CallbackListener
    {
        private View mView = null;

        private PetEntity mSavedItem = null;

        private String mBirthday = null;
        private Integer mYear = null;
        private Integer mMonth = null;
        private Integer mDay = null;
        private Integer mKind = null;

        /**
         * コンストラクタ
         * 
         * @param PetEntity item
         * @access public
         */
        public InputFragment(PetEntity item)
        {
            mSavedItem = item;
        }

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
            mView = inflater.inflate(R.layout.fragment_input, container, false);

            //値を画面にセットする
            setItem();

            //クリックイベントをセットする
            setClickEvent();

            return mView;
        }

        /**
         * 値を画面にセットする
         * 
         * @return void
         * @access private
         */
        public void setItem()
        {
            //編集の場合
            if (mSavedItem != null) {
                mBirthday = mSavedItem.getBirthday();

                String[] birthday = mSavedItem.getBirthday().split("-");
                mYear = Integer.parseInt(birthday[0]);
                mMonth = Integer.parseInt(birthday[1]);
                mDay = Integer.parseInt(birthday[2]);

                EditText editTextBirthday = (EditText) mView.findViewById(R.id.editTextBirthday);
                editTextBirthday.setText(String.format("%d年%d月%d日", mYear, mMonth, mDay));

                mKind = mSavedItem.getKind();

                EditText editTextKind = (EditText) mView.findViewById(R.id.editTextKind);
                editTextKind.setText(mSavedItem.getKindName());

                EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
                editTextName.setText(mSavedItem.getName());
            }
            //新規の場合
            else {
                DateUtils dateUtils = new DateUtils();
                mYear = Integer.parseInt(dateUtils.format("yyyy"));
                mMonth = Integer.parseInt(dateUtils.format("MM"));
                mDay = Integer.parseInt(dateUtils.format("dd"));
            }
        }

        /**
         * クリックイベントをセットする
         * 
         * @return void
         * @access private
         */
        public void setClickEvent()
        {
            //誕生日
            final EditText editTextBirthday = (EditText) mView.findViewById(R.id.editTextBirthday);
            editTextBirthday.setFocusable(false);

            final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = (monthOfYear + 1);
                    mDay = dayOfMonth;
                    mBirthday = String.format("%04d-%02d-%02d", mYear, mMonth, mDay);

                    editTextBirthday.setText(String.format("%d年%d月%d日", mYear, mMonth, mDay));
                }
            }, mYear, (mMonth - 1), mDay);

            editTextBirthday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.show();
                }
            });

            //種類
            final KindSelectDialog kindSelectDialog = KindSelectDialog.getInstance(getDogMasterList());
            kindSelectDialog.setCallbackListener(this);

            EditText editTextKind = (EditText) mView.findViewById(R.id.editTextKind);
            editTextKind.setFocusable(false);
            editTextKind.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    kindSelectDialog.show(getFragmentManager(), "dialog");
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
            Validate validate = new Validate();
            try {
                validate.set();
                validate.require.check(name, "お名前", null);
                validate.set();
                validate.require.check(mBirthday, "お誕生日", null);
                validate.date.check(mBirthday, "お誕生日", null, DateUtils.FMT_DATE);
                validate.date.isPastAllowToday(mBirthday, "お誕生日", null);
                validate.set();
                validate.require.check(mKind, "種類", null);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            //エラーあり
            if (validate.getResult() == false) {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", Utils.implode(validate.getErrorMsg(), "\n"));
                errorDialog.show(getFragmentManager(), "dialog");
                return;
            }

            //保存とページ遷移
            if (saveDb(name, mBirthday, mKind) == true) {
                AndroidUtils.showToastS(getActivity(), "保存しました。");

                Intent intent = new Intent(getActivity(), PetAgeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pageNum", getActivity().getIntent().getIntExtra("pageNum", 0));
                startActivity(intent);
                getActivity().finish();
            } else {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "保存に失敗しました。\nもう一度やり直してください。");
                errorDialog.show(getFragmentManager(), "dialog");
            }
        }

        /**
         * DBに保存する
         * 
         * @param String name
         * @param String birthday
         * @param String kind
         * @return Boolean ret
         * @access private
         */
        private Boolean saveDb(String name, String birthday, Integer kind)
        {
            Boolean ret = false;
            SQLiteDatabase db = null;

            try {
                PetEntity data = new PetEntity();
                data.setName(name);
                data.setBirthday(birthday);
                data.setKind(kind);

                if (mSavedItem != null) {
                    data.setId(mSavedItem.getId());
                }

                AppSQLiteOpenHelper helper = new AppSQLiteOpenHelper(getActivity());
                db = helper.getWritableDatabase();

                PetDao petDao = new PetDao(getActivity());
                ret = petDao.save(db, data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

            return ret;
        }

        /**
         * 種類選択ダイアログでいずれかが選択された
         * 
         * @param Integer kind
         */
        @Override
        public void onClickKindSelectDialog(Integer kind, String name)
        {
            mKind = kind;

            EditText editTextKind = (EditText) mView.findViewById(R.id.editTextKind);
            editTextKind.setText(name);
        }

        /**
         * 種類選択ダイアログ用の犬マスタ一覧を取得
         * 
         * @return ArrayList<DogMasterEntity>
         * @access private
         */
        private ArrayList<DogMasterEntity> getDogMasterList()
        {
            ArrayList<DogMasterEntity> data = null;
            SQLiteDatabase db = null;

            try {
                AppSQLiteOpenHelper helper = new AppSQLiteOpenHelper(getActivity());
                db = helper.getWritableDatabase();

                DogMasterDao dogMasterDao = new DogMasterDao(getActivity());
                data = dogMasterDao.findForKindSelectDialog(db);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

            return data;
        }
    }
}