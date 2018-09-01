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
class PetAgeFragmentPagerAdapter(
        fm: FragmentManager,
        private val list: ArrayList<PetEntity>
) : FragmentStatePagerAdapter(fm) {

    /**
     * ${inheritDoc}
     */
    override fun getItem(position: Int): Fragment {
        return PetAgeFragment.newInstance(position, list[position])
    }

    /**
     * ${inheritDoc}
     */
    override fun getCount(): Int {
        return list.size
    }
}