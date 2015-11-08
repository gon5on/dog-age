package jp.co.e2.dogage.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import jp.co.e2.dogage.alarm.SetAlarmManager;
import jp.co.e2.dogage.R;
import jp.co.e2.dogage.common.AndroidUtils;
import jp.co.e2.dogage.common.PreferenceUtils;
import jp.co.e2.dogage.config.Config;

/**
 * アプリについてアクテビティ
 */
public class SettingActivity extends BaseActivity {
    /**
     * ${inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        if (savedInstanceState == null) {
            //アクションバーをセットする
            setActionbar(true);

            getFragmentManager().beginTransaction().add(R.id.container, new AboutFragment()).commit();
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
     * AboutFragment
     */
    public static class AboutFragment extends Fragment {
        private View mView = null;

        /**
         * ${inheritDoc}
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mView = inflater.inflate(R.layout.fragment_setting, container, false);

            // fragment再生成抑止
            setRetainInstance(true);

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
            //通知チェックボックス
            boolean value = PreferenceUtils.get(getActivity(), Config.PREF_NOTIFICATION, true);
            CheckBox checkBoxNotification = (CheckBox) mView.findViewById(R.id.checkBoxNotification);
            checkBoxNotification.setChecked(value);
        }

        /**
         * クリックイベントをセットする
         */
        private void setClickEvent() {
            //保存ボタン
            Button buttonSave = (Button) mView.findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //値保存
                    CheckBox checkBoxNotification = (CheckBox) mView.findViewById(R.id.checkBoxNotification);
                    boolean value = checkBoxNotification.isChecked();
                    PreferenceUtils.save(getActivity(), Config.PREF_NOTIFICATION, value);

                    //アラームセット
                    new SetAlarmManager(getActivity()).set();

                    String msg = getResources().getString(R.string.save_success);
                    AndroidUtils.showToastS(getActivity(), msg);

                    getActivity().finish();
                }
            });
        }
    }
}
