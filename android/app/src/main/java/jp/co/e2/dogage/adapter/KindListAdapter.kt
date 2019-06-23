package jp.co.e2.dogage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.e2.dogage.R
import jp.co.e2.dogage.entity.DogMasterEntity

/**
 * 種類一覧アダプター
 */
class KindListAdapter(
        private val data: List<DogMasterEntity>,
        private val listener: View.OnClickListener
) : RecyclerView.Adapter<KindListAdapter.ViewHolder>() {

    /**
     * ${inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.parts_kind_list, parent, false)
        val holder = ViewHolder(inflate)

        holder.itemView.setOnClickListener{ view -> listener.onClick(view) }

        return holder
    }

    /**
     * ${inheritDoc}
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        if (item.category == 0) {
            //頭文字行のラベル
            holder.kind.visibility = View.GONE

            holder.label.text = item.kind
            holder.label.visibility = View.VISIBLE

            holder.itemView.isEnabled = false
        } else {
            //種類
            holder.kind.visibility = View.VISIBLE
            holder.kind.text = item.kind

            holder.label.visibility = View.GONE

            holder.itemView.isEnabled = true
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kind: TextView = view.findViewById<View>(R.id.textViewKind) as TextView
        val label: TextView = view.findViewById(R.id.textViewLabel) as TextView
    }
}