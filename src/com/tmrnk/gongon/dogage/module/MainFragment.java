package com.tmrnk.gongon.dogage.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.model.PetEntity;

/**
 * ペット年齢フラグメント
 * 
 * @access public
 */
public class MainFragment extends Fragment
{
    private PetEntity item = null;
    private View mView = null;

    /**
     * onCreateView
     * 
     * @return void
     * @access public
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // データを取得
        Bundle bundle = getArguments();
        item = (PetEntity) bundle.getSerializable("item");

        // レイアウトの指定
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        return mView;
    }

    /**
     * 画面表示
     * 
     * @return void
     * @access public
     */
    public void setDispItem()
    {
        TextView textViewName = (TextView) mView.findViewById(R.id.textViewName);
        textViewName.setText(item.getName());

        TextView textViewKind = (TextView) mView.findViewById(R.id.textViewKind);
        textViewKind.setText(item.getKindName());

        TextView textViewBirthday = (TextView) mView.findViewById(R.id.textViewBirthday);
        textViewBirthday.setText(item.getDispBirthday());

        TextView textViewPetAge = (TextView) mView.findViewById(R.id.textViewPetAge);
        textViewPetAge.setText(item.getPetAge());

        TextView textViewHumanAge = (TextView) mView.findViewById(R.id.textViewHumanAge);
        textViewHumanAge.setText(item.getHumanAge());

        TextView textViewDays = (TextView) mView.findViewById(R.id.textViewDays);
        textViewDays.setText(item.getDaysFromBorn());
    }
}