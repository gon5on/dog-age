package jp.co.e2.dogage.adapter

import java.util.ArrayList

import jp.co.e2.dogage.activity.PetAgeFragment
import jp.co.e2.dogage.entity.PetEntity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * ページアダプタ―
 */

/* ↓コンストラクタ部分を分解するとこうなる
class PetAgeFragmentPagerAdapter: FragmentStatePagerAdapter {
    private var mList: ArrayList<PetEntity>? = null;

    constructor(fm: FragmentManager, list: ArrayList<PetEntity>) : super(fm) {
        mList = list
    }
*/

class PetAgeFragmentPagerAdapter(fm: FragmentManager, private val mList: ArrayList<PetEntity>) : FragmentStatePagerAdapter(fm) {

    /**
     * ${inheritDoc}
     */
    override fun getItem(position: Int): Fragment {
        return PetAgeFragment.newInstance(position, mList[position])
    }

    /**
     * ${inheritDoc}
     */
    override fun getCount(): Int {
        return mList.size
    }
}