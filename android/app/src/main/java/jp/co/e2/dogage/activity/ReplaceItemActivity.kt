package jp.co.e2.dogage.activity

import jp.co.e2.dogage.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.database.sqlite.transaction
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.e2.dogage.adapter.ReplaceItemAdapter
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao
import java.util.ArrayList
import androidx.recyclerview.widget.ItemTouchHelper
import jp.co.e2.dogage.common.AndroidUtils


/**
 * 入れ替えアクテビティ
 */
class ReplaceItemActivity : BaseActivity() {
    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        setBackArrowToolbar()

        //タイトルをセットする
        setActionBarTitle(getString(R.string.replace))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, ReplaceItemFragment()).commit()
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * ReplaceItemFragment
     */
    class ReplaceItemFragment : Fragment() {
        private lateinit var petData: ArrayList<PetEntity>      // ペット情報一覧

        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            super.onCreate(savedInstanceState)

            val view = inflater.inflate(R.layout.fragment_replace_item, container, false)

            //ペット情報一覧を取得
            setPetList()

            //コンテンツをセットする
            setContent()

            return view
        }

        /**
         * ペット情報一覧を取得
         */
        private fun setPetList() {
            BaseSQLiteOpenHelper(context!!).writableDatabase.use {
                val petDao = PetDao(context!!)
                petData = petDao.findAll(it)
            }
        }

        /**
         * コンテンツをセットする
         */
        private fun setContent() {
            //ペット一覧
            view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {
                val adapter = ReplaceItemAdapter(petData)

                //ドラドラで並び替え
                val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val data = adapter.data.removeAt(viewHolder.adapterPosition)
                        adapter.data.add(target.adapterPosition, data)
                        adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)

                        saveDb(adapter.data)
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    }
                })
                touchHelper.attachToRecyclerView(this)

                this.layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
                this.addItemDecoration(touchHelper)
            }

            //アラート
            val msg = getString(R.string.how_to_replace)
            AndroidUtils.showToastL(context, msg)
        }

        /**
         * DBに保存する
         *
         * @param data データ
         * @return ret 結果
         */
        private fun saveDb(data: ArrayList<PetEntity>): Boolean {
            var ret = false

            BaseSQLiteOpenHelper(context!!).writableDatabase.use {
                it.transaction {
                    val petDao = PetDao(context!!)
                    ret = petDao.updateOrder(it, data)
                }
            }

            return ret
        }
    }
}
