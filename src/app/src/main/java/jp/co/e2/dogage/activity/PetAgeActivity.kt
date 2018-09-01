package jp.co.e2.dogage.activity

import java.util.ArrayList

import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.dialog.ConfirmDialog
import jp.co.e2.dogage.dialog.NoticeDialog
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao
import jp.co.e2.dogage.adapter.PetAgeFragmentPagerAdapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.database.sqlite.transaction

/**
 * ペット年齢アクテビティ
 */
class PetAgeActivity : BaseActivity(), ConfirmDialog.CallbackListener {

    companion object {
        private const val PARAM_PAGE_NUM = "page_num"
        private const val PARAM_PET_DATA = "pet_data"
        private const val PARAM_RADIO_RES_IDS = "radio_res_ids"

        private const val REQUEST_CODE_ADD = 1
        private const val REQUEST_CODE_EDIT = 2

        /**
         * ファクトリーメソッドもどき
         *
         * @param context コンテキスト
         * @param pageNum 表示対象のページ数
         * @return intent
         */
        fun newInstance(context: Context, pageNum: Int): Intent {
            val intent = Intent(context, InputActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(PARAM_PAGE_NUM, pageNum)

            return intent
        }
    }

    private var pageNum: Int = 0                            // 現在表示中のページ数
    private lateinit var petData: ArrayList<PetEntity>      // ペット情報一覧
    private lateinit var radioResIds: IntArray              // ページャのラジオボタンリソースID

    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pet_age)

        //アクションバーをセットする
        setToolbar()

        //ページ数が渡ってきたら取得する
        pageNum = intent.getIntExtra(PARAM_PAGE_NUM, 0)

        if (savedInstanceState == null) {
            //通知を消す
            val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
            notificationManagerCompat.cancelAll()

            //ペット情報一覧を取得
            getPetList()

            //データがない場合は入力画面に飛ばす
            if (petData.size == 0) {
                val intent = InputActivity.newInstance(this@PetAgeActivity, true, 0, null)
                startActivityForResult(intent, REQUEST_CODE_ADD)
            }
        } else {
            petData = savedInstanceState.getSerializable(PARAM_PET_DATA) as ArrayList<PetEntity>
            radioResIds = savedInstanceState.getIntArray(PARAM_RADIO_RES_IDS)
        }

        createViewPager()
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pet_age, menu)

        return true
    }

    /**
     * ${inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val intent: Intent

        when (id) {
            //追加
            R.id.action_add -> {
                intent = InputActivity.newInstance(this@PetAgeActivity, false, 0, null)
                startActivityForResult(intent, REQUEST_CODE_ADD)
            }
            //編集
            R.id.action_edit -> {
                intent = InputActivity.newInstance(this@PetAgeActivity, false, pageNum, petData[pageNum])
                startActivityForResult(intent, REQUEST_CODE_EDIT)
            }
            //削除
            R.id.action_delete -> {
                val title = getString(R.string.confirm)
                val msg = getString(R.string.before_del)

                val confirmDialog = ConfirmDialog.newInstance(title, msg)
                confirmDialog.setCallbackListener(this)
                confirmDialog.show(supportFragmentManager, "dialog")
            }
            //設定
            R.id.action_setting -> startActivity(Intent(this@PetAgeActivity, SettingActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * ${inheritDoc}
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(PARAM_PET_DATA, petData)
        outState.putIntArray(PARAM_RADIO_RES_IDS, radioResIds)
    }

    /**
     * ${inheritDoc}
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDIT) {
            if (resultCode != Activity.RESULT_OK) {
                //データが存在しない場合はアプリ終了
                if (petData.size == 0) {
                    finish()
                    return
                }
                return
            }

            val view = findViewById<View>(android.R.id.content)
            AndroidUtils.showSuccessSnackBarS(view, getString(R.string.save_success))

            //追加の場合は、1ページ目を表示する
            if (requestCode == REQUEST_CODE_ADD) {
                pageNum = 0
            }

            getPetList()
            createViewPager()
        }
    }

    /**
     * ペット情報一覧を取得
     */
    private fun getPetList() {
        BaseSQLiteOpenHelper(this).writableDatabase.use {
            val petDao = PetDao(this)
            petData = petDao.findAll(it)
        }
    }

    /**
     * ページャ作成
     */
    private fun createViewPager() {
        // ページングを動的に生成
        createPaging()

        val adapter = PetAgeFragmentPagerAdapter(supportFragmentManager, petData)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.currentItem = pageNum

        // ページフリック遷移のイベントを受け取る
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                pageNum = position
                flickEvent()
            }
        })
    }

    /**
     * ページングの○を動的にページ分生成
     */
    private fun createPaging() {
        if (petData.size == 0) {
            return
        }

        val radioGroupPager = findViewById<RadioGroup>(R.id.radioGroupPager)
        radioGroupPager.removeAllViewsInLayout()

        radioResIds = IntArray(petData.size)

        if (petData.size == 1) {
            //1匹の場合はページングを表示しない
            radioGroupPager.visibility = View.GONE
        } else {
            radioGroupPager.visibility = View.VISIBLE

            for (i in petData.indices) {
                //2匹以上の場合はページングを表示する
                radioResIds[i] = View.generateViewId()

                val radioButton = layoutInflater.inflate(R.layout.parts_pager, null) as RadioButton
                radioButton.id = radioResIds[i]
                radioGroupPager.addView(radioButton)

                //表示ページはONにしておく
                if (i == pageNum) {
                    radioGroupPager.check(radioResIds[i])
                }
            }
        }
    }

    /**
     * ページがフリックされた時のイベント
     */
    fun flickEvent() {
        // 1匹の場合はページング非表示
        if (petData.size <= 1) {
            return
        }

        val radioGroupPager = findViewById<RadioGroup>(R.id.radioGroupPager)
        radioGroupPager.check(radioResIds[pageNum])
    }

    /**
     * ${inheritDoc}
     */
    override fun onClickConfirmDialogOk(tag: String) {
        var ret = false
        val petDao = PetDao(this)

        BaseSQLiteOpenHelper(this).writableDatabase.use {
            it.transaction {
                ret = petDao.deleteById(it, petData[pageNum].id)
            }
        }

        if (ret) {
            val view = findViewById<View>(android.R.id.content)
            AndroidUtils.showSuccessSnackBarS(view, getString(R.string.delete_success))

            getPetList()
            createViewPager()
        } else {
            val title = getString(R.string.error)
            val msg = getString(R.string.delete_fail)

            val noticeDialog = NoticeDialog.newInstance(title, msg)
            noticeDialog.show(supportFragmentManager, "dialog")
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onClickConfirmDialogCancel(tag: String) {
    }
}