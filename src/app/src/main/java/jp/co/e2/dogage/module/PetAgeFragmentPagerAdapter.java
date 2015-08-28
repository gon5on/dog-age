package jp.co.e2.dogage.module;

import java.util.ArrayList;

import jp.co.e2.dogage.entity.PetEntity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * ページアダプタ―
 */
public class PetAgeFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<PetEntity> mList;

    /**
     * コンストラクタ
     *
     * @param fm
     */
    public PetAgeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        mList = new ArrayList<PetEntity>();
    }

    /**
     * getItem
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        //対象ページのデータを取得
        PetEntity item = mList.get(position);

        //バンドルにデータをセット
        Bundle bundle = new Bundle();
        bundle.putInt("pageNum", position);
        bundle.putSerializable("item", item);

        //フラグメント起動
        PetAgeFragment petAgeFragment = new PetAgeFragment();
        petAgeFragment.setArguments(bundle);

        return petAgeFragment;
    }

    /**
     * ページ数を取得
     *
     * @return int
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * ページを追加
     *
     * @param item
     */
    public void add(PetEntity item) {
        mList.add(item);
    }

    /**
     * ページを一気に追加
     *
     * @param list
     */
    public void addAll(ArrayList<PetEntity> list) {
        mList.addAll(list);
    }
}
