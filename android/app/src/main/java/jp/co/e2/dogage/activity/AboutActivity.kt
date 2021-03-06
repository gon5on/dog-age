package jp.co.e2.dogage.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.PreferenceUtils
import jp.co.e2.dogage.config.Config

/**
 * アプリについてアクテビティ
 */
class AboutActivity : BaseActivity() {

    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        setBackArrowToolbar()

        //タイトルをセットする
        setActionBarTitle(getString(R.string.about_app))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, AboutFragment()).commit()
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
     * AboutFragment
     */
    class AboutFragment : Fragment() {
        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreate(savedInstanceState)

            val view = inflater.inflate(R.layout.fragment_about, container, false)

            //コンテンツセット
            setContent(view)

            return view
        }

        /**
         * コンテンツをセットする
         */
        private fun setContent(view: View) {
            //アプリバージョン
            view.findViewById<TextView>(R.id.textViewVer).apply {
                this.text = AndroidUtils.getVerName(activity)
            }

            //ライセンス
            view.findViewById<TextView>(R.id.textViewLicense).apply {
                this.setOnClickListener {
                    startActivity(Intent(activity, LicenseActivity::class.java))
                }
            }

            //プライバシーポリシー
            view.findViewById<TextView>(R.id.textViewPrivacyPolicy).apply {
                this.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    builder.setToolbarColor(ContextCompat.getColor(context, R.color.darkBrown))
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(Config.PRIVACY_POLICY_URL))
                }
            }

            //E2リンク
            view.findViewById<TextView>(R.id.buttonProducedBy).apply {
                this.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    builder.setToolbarColor(ContextCompat.getColor(context, R.color.darkBrown))
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(Config.OFFICIAL_LINK))
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
