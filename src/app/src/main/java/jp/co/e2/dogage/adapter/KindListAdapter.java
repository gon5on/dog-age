package jp.co.e2.dogage.adapter;

import java.util.List;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.entity.DogMasterEntity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 種類一覧アダプター
 */
public class KindListAdapter extends ArrayAdapter<DogMasterEntity> {
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     * @param objects 犬マスタデータ
     */
    public KindListAdapter(Context context, List<DogMasterEntity> objects) {
        super(context, 0, objects);

        if (mInflater == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    /**
     * ${inheritDoc}
     */
    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // 特定の行(position)のデータを得る
        DogMasterEntity item = getItem(position);

        convertView = mInflater.inflate(R.layout.parts_kind_list, null);

        //頭文字行のラベル
        if (item.getCategory() == null) {
            TextView textViewKind = convertView.findViewById(R.id.textViewKind);
            textViewKind.setVisibility(View.GONE);

            TextView textViewLabel = convertView.findViewById(R.id.textViewLabel);
            textViewLabel.setText(item.getKind());
            textViewLabel.setVisibility(View.VISIBLE);

            isEnabled(position);    //クリックできないように
        }
        //種類
        else {
            TextView textViewKind = convertView.findViewById(R.id.textViewKind);
            textViewKind.setVisibility(View.VISIBLE);
            textViewKind.setText(item.getKind());

            TextView textViewLabel = convertView.findViewById(R.id.textViewLabel);
            textViewLabel.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public boolean isEnabled(int position) {
        DogMasterEntity item = getItem(position);

        return (item != null && item.getCategory() != null);
    }
}