package com.tmrnk.gongon.dogage.activity;

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
import android.widget.EditText;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.common.AndroidUtils;
import com.tmrnk.gongon.dogage.common.DateUtils;
import com.tmrnk.gongon.dogage.common.Utils;
import com.tmrnk.gongon.dogage.config.Config;
import com.tmrnk.gongon.dogage.dialog.DatePickerDialog;
import com.tmrnk.gongon.dogage.dialog.ErrorDialog;
import com.tmrnk.gongon.dogage.dialog.KindSelectDialog;
import com.tmrnk.gongon.dogage.entity.PetEntity;
import com.tmrnk.gongon.dogage.model.AppSQLiteOpenHelper;
import com.tmrnk.gongon.dogage.model.PetDao;
import com.tmrnk.gongon.dogage.validate.Validate;
import com.tmrnk.gongon.dogage.validate.ValidateDate;
import com.tmrnk.gongon.dogage.validate.ValidateLength;
import com.tmrnk.gongon.dogage.validate.ValidateRequire;

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
     * onCreateOptionsMenu
     * 
     * @param Menu menu
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //初回起動以外だったら、アクションバーに戻るボタンをセット
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
    public static class InputFragment extends Fragment implements KindSelectDialog.CallbackListener, DatePickerDialog.CallbackListener
    {
        private View mView = null;

        private PetEntity mSavedItem = null;

        private String mBirthday = null;
        private Integer mKind = null;

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

            // fragment再生成抑止
            setRetainInstance(true);

            mView = inflater.inflate(R.layout.fragment_input, container, false);

            //値を画面にセットする
            setItem();

            //クリックイベントをセットする
            setClickEvent();

            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
            mSavedItem = (PetEntity) getArguments().getSerializable("data");

            //編集の場合
            if (mSavedItem != null) {
                mBirthday = mSavedItem.getBirthday();
                mKind = mSavedItem.getKind();

                String[] birthday = mSavedItem.getBirthday().split("-");
                Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
                buttonBirthday.setText(String.format("%s年%s月%s日", birthday[0], birthday[1], birthday[2]));

                Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
                buttonKind.setText(mSavedItem.getKindDisp());

                EditText editTextName = (EditText) mView.findViewById(R.id.editTextName);
                editTextName.setText(mSavedItem.getName());
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
            Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
            buttonBirthday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = DatePickerDialog.getInstance(mBirthday);
                    datePickerDialog.setCallbackListener(InputFragment.this);
                    datePickerDialog.show(getFragmentManager(), "dialog");
                }
            });

            //種類
            final KindSelectDialog kindSelectDialog = KindSelectDialog.getInstance(Config.getDogMastersList());
            kindSelectDialog.setCallbackListener(this);

            Button buttonKind = (Button) mView.findViewById(R.id.buttonKind);
            buttonKind.setOnClickListener(new OnClickListener() {
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
            Validate v = new Validate();
            ValidateRequire.check(v, name, "お名前");
            ValidateLength.maxCheck(v, name, "お名前", 10);
            ValidateRequire.check(v, mBirthday, "お誕生日");
            ValidateDate.check(v, mBirthday, "お誕生日", DateUtils.FMT_DATE);
            ValidateDate.isPastAllowToday(v, mBirthday, "お誕生日");
            ValidateRequire.check(v, mKind, "種類");

            //エラーあり
            if (v.getResult() == false) {
                String errorMsg = Utils.implode(v.getErrorMsgList(), "\n");

                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", errorMsg);
                errorDialog.show(getFragmentManager(), "dialog");
                return;
            }

            //保存とページ遷移
            if (saveDb(name, mBirthday, mKind) == true) {
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
         * @param String date
         * @return void
         * @access public
         */
        @Override
        public void onClickDatePickerDialogOk(String date)
        {
            mBirthday = date;

            String[] birthday = mBirthday.split("-");
            Button buttonBirthday = (Button) mView.findViewById(R.id.buttonBirthday);
            buttonBirthday.setText(String.format("%s年%s月%s日", birthday[0], birthday[1], birthday[2]));
        }

        /**
         * DatePickerダイアログでキャンセルが押された
         * 
         * @return void
         * @access public
         */
        @Override
        public void onClickDatePickerDialogCancel()
        {
        }
    }
}