package com.tmrnk.gongon.dogage.module;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.entity.DogMasterEntity;

/**
 * 種類一覧アダプター
 * 
 * @access public
 */
public class KindListAdapter extends ArrayAdapter<DogMasterEntity>
{
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * 
     * @param Context context
     * @param Integer layoutId
     * @param List<DogEntity> objects
     */
    public KindListAdapter(Context context, int layoutId, List<DogMasterEntity> objects)
    {
        super(context, 0, objects);

        if (mInflater == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    /**
     * getView
     * 
     * @param int position
     * @param View convertView
     * @param ViewGroup parent
     * @return View convertView
     * @access public
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // 特定の行(position)のデータを得る
        DogMasterEntity item = getItem(position);

        convertView = mInflater.inflate(R.layout.parts_kind_list, null);

        //ラベル
        if (item.getLabelFlag() == 1) {
            TextView textViewKind = (TextView) convertView.findViewById(R.id.textViewKind);
            textViewKind.setVisibility(View.GONE);

            LinearLayout LinearLayoutLabel = (LinearLayout) convertView.findViewById(R.id.LinearLayoutLabel);
            LinearLayoutLabel.setVisibility(View.VISIBLE);

            TextView textViewLabel = (TextView) convertView.findViewById(R.id.textViewLabel);
            textViewLabel.setText(item.getKind());

            isEnabled(position);	//クリックできないように
        }
        //種類
        else {
            TextView textViewKind = (TextView) convertView.findViewById(R.id.textViewKind);
            textViewKind.setVisibility(View.VISIBLE);
            textViewKind.setText(item.getKind());

            LinearLayout LinearLayoutLabel = (LinearLayout) convertView.findViewById(R.id.LinearLayoutLabel);
            LinearLayoutLabel.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     * 行のクリックを無効にするかどうか
     * 
     * @param Integer position
     * @return boolean
     * @access public
     */
    @Override
    public boolean isEnabled(int position)
    {
        DogMasterEntity item = getItem(position);

        if (item.getLabelFlag() == 1) {
            return false;
        } else {
            return true;
        }
    }
}