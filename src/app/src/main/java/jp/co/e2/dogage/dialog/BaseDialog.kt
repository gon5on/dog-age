package jp.co.e2.dogage.dialog

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.content.Context

/**
 * ダイアログ基底クラス
 */
abstract class BaseDialog<Interface> : DialogFragment() {

    companion object {
        const val PARAM_LISTENER_TYPE = "listenerType"
        const val LISTENER_ACTIVITY = 1
        const val LISTENER_FRAGMENT = 2
    }

    protected var mCallbackListener: Interface? = null

    /**
     * ${inheritDoc}
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listenerType = arguments.getInt(PARAM_LISTENER_TYPE)

        if (listenerType == BaseDialog.LISTENER_ACTIVITY) {
            mCallbackListener = activity as Interface
        } else if (listenerType == BaseDialog.LISTENER_FRAGMENT) {
            mCallbackListener = targetFragment as Interface
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
        val listenerType: Int?

        if (listener is Activity) {
            listenerType = BaseDialog.LISTENER_ACTIVITY
        } else if (listener is Fragment) {
            listenerType = BaseDialog.LISTENER_FRAGMENT
            setTargetFragment(listener as Fragment, 0)
        } else {
            throw IllegalArgumentException(listener.toString() + " must be either an Activity or a Fragment")
        }

        val bundle = arguments
        bundle.putInt(PARAM_LISTENER_TYPE, listenerType)

        arguments = bundle
    }

    /**
     * コールバックリスナーを削除
     */
    fun removeCallbackListener() {
        mCallbackListener = null
    }
}
