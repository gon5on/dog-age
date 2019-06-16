package jp.co.e2.dogage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.e2.dogage.R
import jp.co.e2.dogage.entity.PetEntity

/**
 * 入れ替えアダプタ
 */
class ReplaceItemAdapter(val data: ArrayList<PetEntity>) : RecyclerView.Adapter<ReplaceItemAdapter.ViewHolder>() {
    /**
     * ${inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textViewName = LayoutInflater.from(parent.context).inflate(R.layout.parts_replace_item, parent, false) as TextView

        return ViewHolder(textViewName)
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
    class ViewHolder(val name: TextView) : RecyclerView.ViewHolder(name)
}