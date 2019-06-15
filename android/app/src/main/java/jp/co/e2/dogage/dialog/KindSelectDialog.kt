package jp.co.e2.dogage.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.e2.dogage.R
import jp.co.e2.dogage.adapter.KindListAdapter
import jp.co.e2.dogage.dialog.KindSelectDialog.CallbackListener
import jp.co.e2.dogage.entity.DogMasterEntity
import java.util.*

/**
 * 種類選択ダイアログ
 */
class KindSelectDialog : BaseDialog<CallbackListener>() {

    companion object {
        private const val PARAM_DATA = "data"

        /**
         * ファクトリーメソッド
         *
         * @return kindSelectDialog
         */
        fun newInstance(data: ArrayList<DogMasterEntity>): KindSelectDialog {
            val bundle = Bundle()
            bundle.putSerializable(PARAM_DATA, data)

            val dialog = KindSelectDialog()
            dialog.arguments = bundle

            return dialog
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val data = arguments!!.getSerializable(PARAM_DATA) as ArrayList<DogMasterEntity>

        val layout = activity!!.layoutInflater.inflate(R.layout.dialog_kind_list, null)

        val recyclerViewKind = layout!!.findViewById<RecyclerView>(R.id.recyclerViewKind)
        recyclerViewKind.adapter = KindListAdapter(data, View.OnClickListener {
            val position = recyclerViewKind.getChildAdapterPosition(it)
            call?.onClickKindSelectDialog(tag!!, data[position].id, data[position].kind)
            dismiss()
        })
        recyclerViewKind.layoutManager = LinearLayoutManager(context)

        val builder = AlertDialog.Builder(context!!)
        builder.setView(layout)

        return builder.create()
    }

    /**
     * コールバックリスナー
     */
    interface CallbackListener {
        /**
         * 種類選択ダイアログで何かしら選択された
         *
         * @param tag タグ
         * @param kind 種類ID
         * @param name 名称
         */
        fun onClickKindSelectDialog(tag: String, kind: Int, name: String)
    }
}
