package jp.co.e2.dogage.config;

import android.app.Application;

import jp.co.e2.dogage.alarm.SetAlarmManager;

/**
 * アプリケーションクラス
 */
public class AppApplication extends Application {
    /**
     * ${inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //犬情報を編集しなくても、アプリ立ち上げ時にアラームマネージャをセットする対策
        new SetAlarmManager(getApplicationContext()).set();
    }
}
