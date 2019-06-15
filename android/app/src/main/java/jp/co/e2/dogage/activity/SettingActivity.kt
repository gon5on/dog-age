package jp.co.e2.dogage.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.PreferenceUtils
import jp.co.e2.dogage.config.Config

/**
 * 設定アクテビティ
 */
class SettingActivity : BaseActivity() {

    companion object {
        private const val PRIVACY_POLICY_URL = "https://www.e-2.co.jp/policy/"
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        setBackArrowToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, SettingFragment()).commit()
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * SettingFragment
     */
    class SettingFragment : Fragment() {
        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreate(savedInstanceState)

            val view = inflater.inflate(R.layout.fragment_setting, container, false)

            //コンテンツセット
            setContent(view)

            return view
        }

        /**
         * コンテンツをセットする
         */
        private fun setContent(view: View) {
            //誕生日通知チェックボックス
            view.findViewById<CheckBox>(R.id.checkBoxBirthNotification).apply {
                this.isChecked = PreferenceUtils.get(activity, Config.PREF_BIRTH_NOTIFY_FLG, true)

                this.setOnCheckedChangeListener { _, isChecked ->
                    changeNotifySetting(Config.PREF_BIRTH_NOTIFY_FLG, isChecked) }
            }

            //命日通知チェックボックス
            view.findViewById<CheckBox>(R.id.checkBoxArchiveNotification).apply {
                this.isChecked = PreferenceUtils.get(activity, Config.PREF_ARCHIVE_NOTIFY_FLG, true)

                this.setOnCheckedChangeListener { _, isChecked ->
                    changeNotifySetting(Config.PREF_ARCHIVE_NOTIFY_FLG, isChecked) }
            }

            //並び替え
            view.findViewById<TextView>(R.id.textViewReplaceItem).apply {
                this.setOnClickListener {
                    startActivity(Intent(activity, ReplaceItemActivity::class.java))
                }
            }

            //年齢計算について
            view.findViewById<TextView>(R.id.textViewHowToCalc).apply {
                this.setOnClickListener {
                    startActivity(Intent(activity, HowToCalcActivity::class.java))
                }
            }

            //アプリについて
            view.findViewById<TextView>(R.id.textViewAbout).apply {
                this.setOnClickListener {
                    startActivity(Intent(activity, AboutActivity::class.java))
                }
            }
        }

        /**
         * チェックボックスの設定を保存する
         *
         * @param name プリファレンス保存名
         * @param value プリファレンス保存値
         */
        private fun changeNotifySetting(name: String, value: Boolean) {
            PreferenceUtils.save(activity, name, value)

            AndroidUtils.showSuccessSnackBarS(view, getString(R.string.change_settings))
        }
    }
}
