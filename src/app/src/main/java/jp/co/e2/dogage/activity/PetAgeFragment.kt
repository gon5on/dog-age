package jp.co.e2.dogage.activity

import java.io.IOException

import jp.co.e2.dogage.R
import jp.co.e2.dogage.dialog.PhotoDialog
import jp.co.e2.dogage.entity.PetEntity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * ペット年齢フラグメント
 */
class PetAgeFragment : Fragment() {

    companion object {
        val BG_ABOVE = arrayOf(R.drawable.img_frame_above1, R.drawable.img_frame_above2, R.drawable.img_frame_above3)
        val BG_UNDER = arrayOf(R.drawable.img_frame_under1, R.drawable.img_frame_under2, R.drawable.img_frame_under3)

        const val PARAM_PAGE_NUM = "page_num"
        const val PARAM_DATA = "data"

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

    private var mData: PetEntity? = null
    private var mPageNum: Int? = 0
    private var mView: View? = null

    /**
     * onCreateView
     *
     * @return void
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mView = inflater.inflate(R.layout.fragment_pet_age, container, false)

        // データを取得
        val bundle = arguments
        mData = bundle!!.getSerializable(PARAM_DATA) as PetEntity
        mPageNum = bundle.getInt(PARAM_PAGE_NUM)

        // 画面表示
        setDisplayItem()

        return mView
    }

    /**
     * 画面表示
     */
    private fun setDisplayItem() {
        try {
            val textViewName = mView!!.findViewById<TextView>(R.id.textViewName)
            textViewName.text = mData!!.name

            val textViewKind = mView!!.findViewById<TextView>(R.id.textViewKind)
            textViewKind.text = mData!!.getKindDisp(context)

            val textViewBirthday = mView!!.findViewById<TextView>(R.id.textViewBirthday)
            textViewBirthday.text = mData!!.getDispBirthday(activity)

            val textViewPetAge = mView!!.findViewById<TextView>(R.id.textViewPetAge)
            textViewPetAge.text = mData!!.getPetAgeDisp(activity)

            val textViewHumanAge = mView!!.findViewById<TextView>(R.id.textViewHumanAge)
            textViewHumanAge.text = mData!!.getHumanAge(context)

            val textViewDays = mView!!.findViewById<TextView>(R.id.textViewDays)
            textViewDays.text = mData!!.getDaysFromBorn(activity)

            val linearLayout1 = mView!!.findViewById<LinearLayout>(R.id.linearLayout1)
            linearLayout1.setBackgroundResource(BG_ABOVE[mPageNum!! % BG_ABOVE.size])

            val constraintLayoutDogAge = mView!!.findViewById<ConstraintLayout>(R.id.constraintLayoutDogAge)
            constraintLayoutDogAge.setBackgroundResource(BG_UNDER[mPageNum!! % BG_ABOVE.size])

            val constraintLayoutHumanAge = mView!!.findViewById<ConstraintLayout>(R.id.constraintLayoutHumanAge)
            constraintLayoutHumanAge.setBackgroundResource(BG_UNDER[mPageNum!! % BG_ABOVE.size])

            val constraintLayoutDayCount = mView!!.findViewById<ConstraintLayout>(R.id.constraintLayoutDayCount)
            constraintLayoutDayCount.setBackgroundResource(BG_UNDER[mPageNum!! % BG_ABOVE.size])

            val textViewArchive = mView!!.findViewById<TextView>(R.id.textViewArchive)

            if (mData!!.archiveDate != null) {
                textViewArchive.visibility = View.VISIBLE
                textViewArchive.text = mData!!.getDispArchiveDate(activity)
            } else {
                textViewArchive.visibility = View.GONE
            }

            val imageViewPhoto = mView!!.findViewById<ImageView>(R.id.imageViewPhoto)

            if (mData!!.photoFlg) {
                val thumbBitmap = mData!!.getPhotoThumb(activity)
                val bigBitmap = mData!!.getPhotoBig(activity)

                imageViewPhoto.setImageBitmap(thumbBitmap)
                imageViewPhoto.visibility = View.VISIBLE

                imageViewPhoto.setOnClickListener {
                    if (activity != null) {
                        val photoDialog = PhotoDialog.newInstance(bigBitmap)
                        photoDialog.show(activity!!.fragmentManager, "dialog")
                    }
                }
            } else {
                imageViewPhoto.visibility = View.GONE
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}