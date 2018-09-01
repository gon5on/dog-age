package jp.co.e2.dogage.dialog

import android.app.Activity

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment

/**
 * ダイアログ基底クラス
 */
abstract class BaseDialog<Interface> : DialogFragment() {

    companion object {
        const val PARAM_LISTENER_TYPE = "listenerType"
        const val LISTENER_ACTIVITY = 1
        const val LISTENER_FRAGMENT = 2
    }

    protected var call: Interface? = null

    /**
     * ${inheritDoc}
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listenerType = arguments!!.getInt(PARAM_LISTENER_TYPE)

        if (listenerType == BaseDialog.LISTENER_ACTIVITY) {
            call = activity as Interface
        } else if (listenerType == BaseDialog.LISTENER_FRAGMENT) {
            call = targetFragment as Interface
        }
    }

    /**
     * ダイアログのイベントリスナーを登録する
     *
     * アクテビティから呼ばれているのか、フラグメントから呼ばれているのかを判別して、
     * 適当な方法でイベントリスナーを登録する
     *
     * @param listener コールバックリスナー
     */
    fun setCallbackListener(listener: Interface) {
        val listenerType: Int

        when (listener) {
            is Activity -> {
                listenerType = BaseDialog.LISTENER_ACTIVITY
            }
            is Fragment -> {
                listenerType = BaseDialog.LISTENER_FRAGMENT
                setTargetFragment(listener as Fragment, 0)
            }
            else -> throw IllegalArgumentException(listener.toString() + " must be either an Activity or a Fragment")
        }

        arguments!!.putInt(PARAM_LISTENER_TYPE, listenerType)
    }

    /**
     * コールバックリスナーを削除
     */
    fun removeCallbackListener() {
        call = null
    }
}
