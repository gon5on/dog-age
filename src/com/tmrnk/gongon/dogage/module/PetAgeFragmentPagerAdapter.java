package com.tmrnk.gongon.dogage.module;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tmrnk.gongon.dogage.model.PetEntity;

/**
 * ページアダプタ―
 * 
 * @public public
 */
public class PetAgeFragmentPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<PetEntity> mList;

    /**
     * コンストラクタ
     * 
     * @param FragmentManager fm
     * @return void
     * @access public
     */
    public PetAgeFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);

        mList = new ArrayList<PetEntity>();
    }

    /**
     * getItem
     * 
     * @param int position
     * @return void
     * @access public
     */
    @Override
    public Fragment getItem(int position)
    {
        //対象ページのデータを取得
        PetEntity item = mList.get(position);

        //バンドルにデータをセット
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        //フラグメント起動
        MainFragment petAgeFragment = new MainFragment();
        petAgeFragment.setArguments(bundle);

        return petAgeFragment;
    }

    /**
     * ページ数を取得
     * 
     * @return int
     * @access public
     */
    @Override
    public int getCount()
    {
        return mList.size();
    }

    /**
     * ページを追加
     * 
     * @param PetEntity item
     * @return void
     * @access public
     */
    public void add(PetEntity item)
    {
        mList.add(item);
    }

    /**
     * ページを全て追加
     * 
     * @param ArrayList<PetEntity> list
     * @return void
     * @access public
     */
    public void addAll(ArrayList<PetEntity> list)
    {
        mList.addAll(list);
    }
}