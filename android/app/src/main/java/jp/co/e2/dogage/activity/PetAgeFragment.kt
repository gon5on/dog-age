package jp.co.e2.dogage.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.config.Config
import jp.co.e2.dogage.dialog.PhotoDialog
import jp.co.e2.dogage.entity.PetEntity

/**
 * ペット年齢フラグメント
 */
class PetAgeFragment : Fragment() {

    companion object {
        val BG_ABOVE = arrayOf(R.drawable.img_frame_above1, R.drawable.img_frame_above2, R.drawable.img_frame_above3)
        val BG_UNDER = arrayOf(R.drawable.img_frame_under1, R.drawable.img_frame_under2, R.drawable.img_frame_under3)

        private const val PARAM_PAGE_NUM = "page_num"
        private const val PARAM_DATA = "data"

        /**
         * ファクトリーメソッド
         *
         * @return PetAgeFragment
         */
        fun newInstance(pageNum: Int, data: PetEntity): PetAgeFragment {
            val bundle = Bundle()
            bundle.putInt(PARAM_PAGE_NUM, pageNum)
            bundle.putSerializable(PARAM_DATA, data)

            val fragment = PetAgeFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    private val data: PetEntity by lazy { arguments!!.getSerializable(PARAM_DATA) as PetEntity }
    private val pageNum: Int by lazy { arguments!!.getInt(PARAM_PAGE_NUM) }

    /**
     * onCreateView
     *
     * @return void
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_pet_age, container, false)
        
        // 画面表示
        setContent(view)

        return view
    }

    /**
     * 画面表示
     *
     * @param view
     */
    private fun setContent(view: View) {
        view.findViewById<TextView>(R.id.textViewName).apply {
            this.text = data.name
        }

        view.findViewById<TextView>(R.id.textViewKind).apply {
            this.text = data.getKindDisp(activity!!)
        }

        view.findViewById<TextView>(R.id.textViewBirthday).apply {
            this.text = data.getDispBirthday(activity!!)
        }

        view.findViewById<TextView>(R.id.textViewPetAge).apply {
            this.text = data.getPetAgeDisp(activity!!)
        }

        view.findViewById<TextView>(R.id.textViewHumanAge).apply {
            this.text = data.getHumanAge(activity!!)
        }

        view.findViewById<TextView>(R.id.textViewDays).apply {
            this.text = data.getDaysFromBorn(activity!!)
        }

        view.findViewById<LinearLayout>(R.id.linearLayout1).apply {
            this.setBackgroundResource(BG_ABOVE[pageNum % BG_ABOVE.size])
        }

        view.findViewById<ConstraintLayout>(R.id.constraintLayoutDogAge).apply {
            this.setBackgroundResource(BG_UNDER[pageNum % BG_ABOVE.size])
        }

        view.findViewById<ConstraintLayout>(R.id.constraintLayoutHumanAge).apply {
            this.setBackgroundResource(BG_UNDER[pageNum % BG_ABOVE.size])
        }

        view.findViewById<ConstraintLayout>(R.id.constraintLayoutDayCount).apply {
            this.setBackgroundResource(BG_UNDER[pageNum % BG_ABOVE.size])
        }

        view.findViewById<TextView>(R.id.textViewArchive).apply {
            if (data.archiveDate != null) {
                this.visibility = View.VISIBLE
                this.text = data.getDispArchiveDate(activity!!)
            } else {
                this.visibility = View.GONE
            }
        }

        view.findViewById<ImageView>(R.id.imageViewPhoto).apply {
            if (data.photoFlg) {
                val size = AndroidUtils.dpToPixel(activity, Config.PHOTO_THUMB_DP)
                Picasso.get().load(data.getImgFileUri(context)).resize(size, size).centerCrop().into(this)

                this.visibility = View.VISIBLE

                this.setOnClickListener {
                    val photoDialog = PhotoDialog.newInstance(data.getImgFileUri(context))
                    photoDialog.show(fragmentManager, "dialog")
                }
            } else {
                this.visibility = View.GONE
            }
        }
    }
}