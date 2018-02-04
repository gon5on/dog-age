package jp.co.e2.dogage.adapter;

import java.util.ArrayList;

import jp.co.e2.dogage.activity.PetAgeFragment;
import jp.co.e2.dogage.entity.PetEntity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * ページアダプタ―
 */
public class PetAgeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PetEntity> mList;

    /**
     * コンストラクタ
     *
     * @param fm FragmentManager
     */
    public PetAgeFragmentPagerAdapter(FragmentManager fm, ArrayList<PetEntity> list) {
        super(fm);

        mList = list;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Fragment getItem(int position) {
        return PetAgeFragment.newInstance(position, mList.get(position));
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public int getCount() {
        return mList.size();
    }
}
