package com.tmrnk.gongon.dogage.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.activity.PetAgeActivity;
import com.tmrnk.gongon.dogage.model.PetEntity;

/**
 * ペット年齢フラグメント
 * 
 * @access public
 */
public class PetAgeFragment extends Fragment
{
    private PetEntity mItem = null;
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
        mItem = (PetEntity) bundle.getSerializable("item");

        // レイアウトの指定
        mView = inflater.inflate(R.layout.fragment_pet_age, container, false);

        //viewPager初回表示の場合は、フラグメントを読み込んだタイミングでページを表示する
        PetAgeActivity activity = (PetAgeActivity) getActivity();
        activity.createInitPage();

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
        textViewName.setText(mItem.getName());

        TextView textViewKind = (TextView) mView.findViewById(R.id.textViewKind);
        textViewKind.setText(mItem.getKindName());

        TextView textViewBirthday = (TextView) mView.findViewById(R.id.textViewBirthday);
        textViewBirthday.setText(mItem.getDispBirthday());

        TextView textViewPetAge = (TextView) mView.findViewById(R.id.textViewPetAge);
        textViewPetAge.setText(mItem.getPetAge());

        TextView textViewHumanAge = (TextView) mView.findViewById(R.id.textViewHumanAge);
        textViewHumanAge.setText(mItem.getHumanAge());

        TextView textViewDays = (TextView) mView.findViewById(R.id.textViewDays);
        textViewDays.setText(mItem.getDaysFromBorn());
    }
}