package jp.co.e2.dogage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.e2.dogage.R
import jp.co.e2.dogage.entity.PetEntity

/**
 * 入れ替えアダプタ
 */
class ReplaceItemAdapter(
        val data: ArrayList<PetEntity>,
        private val listener: View.OnClickListener
) : RecyclerView.Adapter<ReplaceItemAdapter.ViewHolder>() {

    /**
     * ${inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.parts_replace_item, parent, false)
        val holder = ViewHolder(inflate)

        holder.itemView.setOnClickListener{ view -> listener.onClick(view) }

        return holder
    }

    /**
     * ${inheritDoc}
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
    }

    /**
     * ${inheritDoc}
     */
    override fun getItemCount() = data.size

    /**
     * ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view as TextView
    }
}