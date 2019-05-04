package jp.co.e2.dogage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

import jp.co.e2.dogage.activity.PetAgeFragment
import jp.co.e2.dogage.entity.PetEntity

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