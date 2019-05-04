package jp.co.e2.dogage.adapter

import android.app.Activity
import jp.co.e2.dogage.R
import jp.co.e2.dogage.entity.DogMasterEntity

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * 種類一覧アダプター
 */
class KindListAdapter(
        context: Context,
        objects: List<DogMasterEntity>
) : ArrayAdapter<DogMasterEntity>(context, 0, objects) {

    /**
     * ${inheritDoc}
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //view取得
        val (viewHolder, view) = when (convertView) {
            null -> {
                val inflater = (context as Activity).layoutInflater
                val view = inflater.inflate(R.layout.parts_kind_list, null)

                val textViewLabel = view.findViewById(R.id.textViewLabel) as TextView
                val textViewKind = view.findViewById(R.id.textViewKind) as TextView
                val viewHolder = ViewHolder(textViewLabel, textViewKind)

                view.tag = viewHolder
                viewHolder to view
            }
            else -> convertView.tag as ViewHolder to convertView
        }

        val item = getItem(position)

        if (item.category == 0) {
            //種類
            viewHolder.kind.visibility = View.VISIBLE
            viewHolder.kind.text = item.kind

            viewHolder.label.visibility = View.GONE
        } else {
            //頭文字行のラベル
            viewHolder.kind.visibility = View.GONE

            viewHolder.label.text = item.kind
            viewHolder.label.visibility = View.VISIBLE

            //クリックできないようにする
            isEnabled(position)
        }

        return view
    }

    /**
     * ${inheritDoc}
     */
    override fun isEnabled(position: Int): Boolean {
        val item = getItem(position)

        return (item != null && item.category != 0)
    }

    /**
     * ViewHolder
     */
    class ViewHolder(val kind: TextView, val label: TextView)
}