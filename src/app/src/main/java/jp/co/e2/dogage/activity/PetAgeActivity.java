package jp.co.e2.dogage.activity;

import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.dialog.ConfirmDialog;
import jp.co.e2.dogage.dialog.ErrorDialog;
import jp.co.e2.dogage.entity.PetEntity;
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper;
import jp.co.e2.dogage.model.PetDao;
import jp.co.e2.dogage.adapter.PetAgeFragmentPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * ペット年齢アクテビティ
 */
public class PetAgeActivity extends BaseActivity implements ConfirmDialog.CallbackListener {
    public static final String PARAM_ID = "id";
    private static final String PARAM_PAGE_NUM = "page_num";
    private static final String PARAM_DATA = "data";
    private static final String PARAM_RADIO_RES_IDS = "radio_res_ids";

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    private Integer mPageNum = 0;                            // 現在表示中のページ数
    private ArrayList<PetEntity> mData = null;               // ペット情報一覧
    private int[] mRadioResIds = null;                     // ページャのラジオボタンリソースID

    /**
     * ファクトリーメソッドもどき
     *
     * @param activity アクテビティ
     * @param pageNum 表示対象のページ数
     * @return intent
     */
    public static Intent newInstance(Activity activity, int pageNum) {
        Intent intent = new Intent(activity, InputActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PARAM_PAGE_NUM, pageNum);

        return intent;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_age);

        //アクションバーをセットする
        setToolbar();

        //ページ数が渡ってきたら取得する
        mPageNum = getIntent().getIntExtra(PARAM_PAGE_NUM, 0);

        if (savedInstanceState == null) {
            //通知を消す
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.cancelAll();

            //ペット情報一覧を取得
            getPetList();

            //データがない場合は入力画面に飛ばす
            if (mData.size() == 0) {
                Intent intent = InputActivity.newInstance(PetAgeActivity.this, true, 0, null);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        } else {
            mData = (ArrayList<PetEntity>) savedInstanceState.getSerializable(PARAM_DATA);
            mRadioResIds = savedInstanceState.getIntArray(PARAM_RADIO_RES_IDS);
        }

        createViewPager();
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_age, menu);

        return true;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();

        //追加
        if (id == R.id.action_add) {
            Intent intent = InputActivity.newInstance(PetAgeActivity.this, false, 0, null);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        }
        //編集
        else if (id == R.id.action_edit) {
            Intent intent = InputActivity.newInstance(PetAgeActivity.this, false, mPageNum, mData.get(mPageNum));
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        }
        //削除
        else if (id == R.id.action_delete) {
            String title = getString(R.string.confirm);
            String msg = getString(R.string.before_del);

            ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, msg);
            confirmDialog.setCallbackListener(this);
            confirmDialog.show(getFragmentManager(), "dialog");
        }
        //設定
        else if (id == R.id.action_setting) {
            startActivity(SettingActivity.newInstance(PetAgeActivity.this));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(PARAM_DATA, mData);
        outState.putIntArray(PARAM_RADIO_RES_IDS, mRadioResIds);
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDIT) {
            if (resultCode != RESULT_OK) {
                return;
            }

            View view = findViewById(android.R.id.content);
            AndroidUtils.showSuccessSnackBarS(view, getString(R.string.save_success));

            //追加の場合は、1ページ目を表示する
            if (requestCode == REQUEST_CODE_ADD) {
                mPageNum = 0;
            }

            getPetList();
            createViewPager();
        }
    }

    /**
     * ペット情報一覧を取得
     */
    private void getPetList() {
        SQLiteDatabase db = null;

        try {
            BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getApplicationContext());
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(getApplicationContext());
            mData = petDao.findAll(db);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * ページャ作成
     */
    private void createViewPager() {
        // ページングを動的に生成
        createPaging();

        PetAgeFragmentPagerAdapter adapter = new PetAgeFragmentPagerAdapter(getSupportFragmentManager(), mData);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mPageNum);

        // ページフリック遷移のイベントを受け取る
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPageNum = position;
                flickEvent();
            }
        });
    }

    /**
     * ページングの○を動的にページ分生成
     */
    private void createPaging() {
        if (mData == null) {
            return;
        }

        RadioGroup radioGroupPager = findViewById(R.id.radioGroupPager);
        radioGroupPager.removeAllViewsInLayout();

        mRadioResIds = new int[mData.size()];

        //1匹の場合はページングを表示しない
        if (mData.size() == 1) {
            radioGroupPager.setVisibility(View.GONE);
        }
        //2匹以上の場合はページングを表示する
        else {
            radioGroupPager.setVisibility(View.VISIBLE);

            for (int i = 0; i < mData.size(); i++) {
                mRadioResIds[i] = View.generateViewId();

                RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.parts_pager, null);
                radioButton.setId(mRadioResIds[i]);
                radioGroupPager.addView(radioButton);

                //表示ページはONにしておく
                if (i == mPageNum) {
                    radioGroupPager.check(mRadioResIds[i]);
                }
            }
        }
    }

    /**
     * ページがフリックされた時のイベント
     */
    public void flickEvent() {
        // 1匹の場合はページング非表示
        if (mData.size() == 1) {
            return;
        }

        RadioGroup radioGroupPager = findViewById(R.id.radioGroupPager);
        radioGroupPager.check(mRadioResIds[mPageNum]);
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public void onClickConfirmDialogOk(String tag) {
        SQLiteDatabase db = null;

        try {
            BaseSQLiteOpenHelper helper = new BaseSQLiteOpenHelper(getApplicationContext());
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(getApplicationContext());

            if (petDao.deleteById(db, mData.get(mPageNum).getId())) {
                View view = findViewById(android.R.id.content);
                AndroidUtils.showSuccessSnackBarS(view, getString(R.string.delete_success));

                getPetList();
                createViewPager();
            } else {
                String title = getString(R.string.error);
                String msg = getString(R.string.delete_fail);

                ErrorDialog errorDialog = ErrorDialog.newInstance(title, msg);
                errorDialog.show(getFragmentManager(), "dialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public void onClickConfirmDialogCancel(String tag) {
    }
}