package jp.co.e2.dogage.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.PreferenceUtils;
import jp.co.e2.dogage.config.Config;

/**
 * 設定アクテビティ
 */
public class SettingActivity extends BaseActivity {
    /**
     * ファクトリーメソッドもどき
     *
     * @param activity アクテビティ
     * @return intent
     */
    public static Intent newInstance(Activity activity) {
        return new Intent(activity, SettingActivity.class);
    }

    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        //アクションバーをセットする
        setBackArrowToolbar();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, SettingFragment.newInstance()).commit();
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
     * SettingFragment
     */
    public static class SettingFragment extends Fragment {
        private View mView = null;

        /**
         * ファクトリーメソッド
         *
         * @return SettingFragment
         */
        public static SettingFragment newInstance() {
            Bundle args = new Bundle();

            SettingFragment fragment = new SettingFragment();
            fragment.setArguments(args);

            return fragment;
        }

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_setting, container, false);

            //コンテンツセット
            setContent();

            //イベントセット
            setClickEvent();

            return mView;
        }

        /**
         * コンテンツをセットする
         */
        private void setContent() {
            //誕生日通知チェックボックス
            CheckBox checkBoxBirthNotify = mView.findViewById(R.id.checkBoxBirthNotification);
            checkBoxBirthNotify.setChecked(PreferenceUtils.get(getActivity(), Config.PREF_BIRTH_NOTIFY_FLG, true));

            //命日通知チェックボックス
            CheckBox checkBoxArchiveNotify = mView.findViewById(R.id.checkBoxArchiveNotification);
            checkBoxArchiveNotify.setChecked(PreferenceUtils.get(getActivity(), Config.PREF_ARCHIVE_NOTIFY_FLG, true));

            //アプリバージョン
            TextView textViewVer = mView.findViewById(R.id.textViewVer);
            textViewVer.setText(AndroidUtils.getVerName(getActivity()));
        }

        /**
         * クリックイベントをセットする
         */
        private void setClickEvent() {
            //誕生日通知チェックボックス
            CheckBox checkBoxBirthNotify = mView.findViewById(R.id.checkBoxBirthNotification);
            checkBoxBirthNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    changeNotifySetting(Config.PREF_BIRTH_NOTIFY_FLG, isChecked);
                }
            });

            //誕生日通知チェックボックス
            CheckBox checkBoxArchiveNotify = mView.findViewById(R.id.checkBoxArchiveNotification);
            checkBoxArchiveNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    changeNotifySetting(Config.PREF_ARCHIVE_NOTIFY_FLG, isChecked);
                }
            });

            //年齢計算について
            RelativeLayout relativeLayoutCalcAge = mView.findViewById(R.id.relativeLayoutCalcAge);
            relativeLayoutCalcAge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(AboutActivity.newInstance(getActivity()));
                }
            });

            //E2リンク
            Button buttonProducedBy = mView.findViewById(R.id.buttonProducedBy);
            buttonProducedBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(Config.OFFICIAL_LINK);
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }
            });
        }

        /**
         * チェックボックスの設定を保存する
         *
         * @param name プリファレンス保存名
         * @param value プリファレンス保存値
         */
        private void changeNotifySetting(String name, boolean value) {
            PreferenceUtils.save(getActivity(), name, value);

            AndroidUtils.showSuccessSnackBarS(mView, getString(R.string.change_settings));
        }
    }
}
