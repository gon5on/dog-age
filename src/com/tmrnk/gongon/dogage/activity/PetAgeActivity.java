package com.tmrnk.gongon.dogage.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.common.AndroidUtils;
import com.tmrnk.gongon.dogage.dialog.AppDialog;
import com.tmrnk.gongon.dogage.dialog.ConfirmDialog;
import com.tmrnk.gongon.dogage.dialog.ErrorDialog;
import com.tmrnk.gongon.dogage.model.AppSQLiteOpenHelper;
import com.tmrnk.gongon.dogage.model.PetDao;
import com.tmrnk.gongon.dogage.model.PetEntity;
import com.tmrnk.gongon.dogage.module.PetAgeFragment;
import com.tmrnk.gongon.dogage.module.PetAgeFragmentPagerAdapter;

/**
 * ペット年齢アクテビティ
 * 
 * @access public
 */
public class PetAgeActivity extends AppActivity implements ConfirmDialog.CallbackListener
{
    private ViewPager viewPager = null;                     // ViewPager
    private PetAgeFragmentPagerAdapter adapter = null;      // ページアダプター
    private ArrayList<PetEntity> mData = null;              // ペット情報一覧
    private Integer mPageNum = 0;                           // 現在表示中のページ数

    private Integer mInitFlag = 1;                          //viewPager初回表示フラグ

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
        setContentView(R.layout.activity_pet_age);

        if (savedInstanceState == null) {
            //ペット情報一覧を取得
            getPetList();

            //データがない場合は入力画面に飛ばす
            if (mData.size() == 0) {
                Intent intent = new Intent(PetAgeActivity.this, InputActivity.class);
                intent.putExtra("initFlag", 1);
                startActivity(intent);
                finish();
                return;
            }

            createViewPager();
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
        getMenuInflater().inflate(R.menu.pet_age, menu);

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

        //追加
        if (id == R.id.action_add) {
            Intent intent = new Intent(PetAgeActivity.this, InputActivity.class);
            startActivity(intent);
        }
        //編集
        else if (id == R.id.action_edit) {
            Intent intent = new Intent(PetAgeActivity.this, InputActivity.class);
            intent.putExtra("pageNum", mPageNum);
            intent.putExtra("item", mData.get(mPageNum));
            startActivity(intent);
        }
        //削除
        else if (id == R.id.action_delete) {
            ConfirmDialog confirmDialog = ConfirmDialog.getInstance(AppDialog.LISTENER_ACTIVITY, "確認", "本当に削除してよろしいですか？");
            confirmDialog.show(getFragmentManager(), "dialog");
        }
        //アプリについて
        else if (id == R.id.action_about) {
            Intent intent = new Intent(PetAgeActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * viewPager初回表示の場合は、ページを生成する
     * フラグメントから呼ばれる
     * 
     * @return void
     * @access public
     */
    public void createInitPage()
    {
        if (mInitFlag == 1) {
            mInitFlag = 0;
            flickEvent();
        }
    }

    /**
     * ページャ作成
     * 
     * @return void
     * @access private
     */
    private void createViewPager()
    {
        // ページングの○を動的にページ分生成
        createPaging();

        // ページアダプタ生成
        adapter = new PetAgeFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addAll(mData);

        // ページアダプタをセット
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        //前画面からページ数がわたってくれば、そのページを表示する（0がくれば最終ページを表示）
        if (getIntent().hasExtra("pageNum")) {
            Integer pageNum = getIntent().getIntExtra("pageNum", 0);
            mPageNum = (pageNum != 0) ? pageNum : (mData.size() - 1);

            viewPager.setCurrentItem(mPageNum);
        }

        // ページフリック遷移のイベントを受け取る
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPageNum = position;
                flickEvent();
            }
        });
    }

    /**
     * ペット情報一覧を取得
     * 
     * @return void
     * @access private
     */
    private void getPetList()
    {
        SQLiteDatabase db = null;

        try {
            AppSQLiteOpenHelper helper = new AppSQLiteOpenHelper(getApplicationContext());
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(getApplicationContext());
            mData = petDao.findAll(db);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * ページングの○を動的にページ分生成
     * 
     * @return void
     * @access private
     */
    private void createPaging()
    {
        LinearLayout linearLayoutPager = (LinearLayout) findViewById(R.id.linearLayoutPager);
        linearLayoutPager.removeAllViewsInLayout();

        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                ImageView imageViewPager = (ImageView) getLayoutInflater().inflate(R.layout.parts_pager, null);
                imageViewPager.setId(i);
                linearLayoutPager.addView(imageViewPager);
            }
        }
    }

    /**
     * ページがフリックされた時のイベント
     * 
     * @return void
     * @access public
     */
    public void flickEvent()
    {
        // ページング
        ImageView now = (ImageView) findViewById(mPageNum);
        now.setImageResource(R.drawable.img_page_on);

        if (mData.size() != 1) {
            if (mPageNum == 0) {
                ImageView next = (ImageView) findViewById(mPageNum + 1);
                next.setImageResource(R.drawable.img_page_off);
            }
            else if (mPageNum == adapter.getCount() - 1) {
                ImageView back = (ImageView) findViewById(mPageNum - 1);
                back.setImageResource(R.drawable.img_page_off);
            }
            else {
                ImageView next = (ImageView) findViewById(mPageNum + 1);
                ImageView back = (ImageView) findViewById(mPageNum - 1);
                next.setImageResource(R.drawable.img_page_off);
                back.setImageResource(R.drawable.img_page_off);
            }
        }

        // フラグメント内の処理
        PetAgeFragment fragment = (PetAgeFragment) adapter.instantiateItem(viewPager, mPageNum);
        fragment.setDispItem();
    }

    /**
     * 削除確認ダイアログでOKが押された
     * 
     * @return void
     * @access public
     */
    @Override
    public void onClickConfirmDialogOk()
    {
        SQLiteDatabase db = null;

        try {
            AppSQLiteOpenHelper helper = new AppSQLiteOpenHelper(getApplicationContext());
            db = helper.getWritableDatabase();

            PetDao petDao = new PetDao(getApplicationContext());

            if (petDao.deleteById(db, mData.get(mPageNum).getId()) == true) {
                AndroidUtils.showToastS(getApplicationContext(), "削除しました。");

                Intent intent = new Intent(this, PetAgeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pageNum", (mPageNum != 0) ? (mPageNum - 1) : 0);
                startActivity(intent);
            } else {
                ErrorDialog errorDialog = ErrorDialog.getInstance("エラー", "削除に失敗しました。もう一度やり直してください。");
                errorDialog.show(getFragmentManager(), "dialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 削除確認ダイアログでキャンセルが押された
     * 
     * @return void
     * @access public
     */
    @Override
    public void onClickConfirmDialogCancel()
    {
    }
}