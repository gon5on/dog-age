package jp.co.e2.dogage.config

import android.app.Application
import jp.co.e2.dogage.R

import jp.co.e2.dogage.alarm.SetAlarmManager
import jp.co.e2.dogage.entity.DogMasterEntity
import java.util.*

/**
 * アプリケーションクラス
 */
class AppApplication : Application() {

    //犬種マスタマップ
    var dogMasterMap: HashMap<Int, DogMasterEntity>? = null
        get() {
            if (field == null) {
                field = HashMap()

                val res = applicationContext.resources

                for (i in 0 until Config.KIND_NUM) {
                    val resId = res.getIdentifier("dog" + (i + 1), "array", applicationContext.packageName)
                    val array = res.getStringArray(resId)
                    val dogEntity = DogMasterEntity(Integer.parseInt(array[0]), array[1], array[2], Integer.parseInt(array[3]))

                    field!![dogEntity.id] = dogEntity
                }
            }

            return field
        }

    //犬種マスタ配列
    var dogMasterList: ArrayList<DogMasterEntity>? = null
        get() {
            if (field == null) {
                field = ArrayList()

                val initialLabelList = applicationContext.resources.getStringArray(R.array.initial_label_list)

                //犬種マスタを取得してソート
                val tmp = ArrayList(dogMasterMap!!.values)
                        .sortedWith(compareBy(DogMasterEntity::furigana, DogMasterEntity::initialLine))

                for (i in tmp.indices) {
                    //頭文字行のラベルを追加
                    if (i == 0 || tmp[i].initialLine != tmp[i - 1].initialLine) {
                        val values = DogMasterEntity(0, initialLabelList[tmp[i].initialLine], "", 0)
                        field!!.add(values)
                    }

                    // 犬種をセット
                    field!!.add(tmp[i])
                }
            }

            return field
        }

    /**
     * ${inheritDoc}
     */
    override fun onCreate() {
        super.onCreate()

        //犬情報を編集しなくても、アプリ立ち上げ時にアラームマネージャをセットする対策
        SetAlarmManager(applicationContext).set()
    }
}
