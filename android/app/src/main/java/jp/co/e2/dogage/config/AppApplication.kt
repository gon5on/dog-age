package jp.co.e2.dogage.config

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import jp.co.e2.dogage.R

import jp.co.e2.dogage.alarm.SetAlarmManager
import jp.co.e2.dogage.entity.DogMasterEntity
import java.util.*

/**
 * アプリケーションクラス
 */
class AppApplication : Application() {
    lateinit var firebaseAnalytics: FirebaseAnalytics

    //DBからペットデータを再取得するリロードフラグ
    var reloadFlg = false

    //犬種マスタマップ
    val dogMasterMap: HashMap<Int, DogMasterEntity> by lazy {
        HashMap<Int, DogMasterEntity>().apply {
            for (i in 0 until Config.KIND_NUM) {
                val resId = resources.getIdentifier("dog" + (i + 1), "array", applicationContext.packageName)
                val array = resources.getStringArray(resId)
                val dogEntity = DogMasterEntity(Integer.parseInt(array[0]), array[1], array[2], Integer.parseInt(array[3]))

                this[dogEntity.id] = dogEntity
            }
        }
    }

    //犬種マスタ配列
    val dogMasterList: ArrayList<DogMasterEntity> by lazy {
        ArrayList<DogMasterEntity>().apply {
            val initialLabelList = resources.getStringArray(R.array.initial_label_list)

            //犬種マスタを取得してソート
            val tmp = ArrayList(dogMasterMap.values)
                    .sortedWith(compareBy(DogMasterEntity::furigana, DogMasterEntity::initialLine))

            for (i in tmp.indices) {
                //頭文字行のラベルを追加
                if (i == 0 || tmp[i].initialLine != tmp[i - 1].initialLine) {
                    val values = DogMasterEntity(0, initialLabelList[tmp[i].initialLine], "", 0)
                    this.add(values)
                }

                // 犬種をセット
                this.add(tmp[i])
            }
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreate() {
        super.onCreate()

        //アナリティクス設定
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        //犬情報を編集しなくても、アプリ立ち上げ時にアラームマネージャをセットする対策
        SetAlarmManager(applicationContext).set()
    }
}
