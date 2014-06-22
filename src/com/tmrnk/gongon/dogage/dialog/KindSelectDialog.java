package com.tmrnk.gongon.dogage.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.dialog.KindSelectDialog.CallbackListener;
import com.tmrnk.gongon.dogage.model.DogMasterEntity;
import com.tmrnk.gongon.dogage.module.KindListAdapter;

/**
 * エラーダイアログ
 * 
 * @access public
 */
public class KindSelectDialog extends AppDialog<CallbackListener>
{
    /**
     * インスタンスを返す
     * 
     * @String
     * @return kindSelectDialog
     * @access public
     */
    public static KindSelectDialog getInstance(ArrayList<DogMasterEntity> data)
    {
        KindSelectDialog dialog = new KindSelectDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * onCreateDialog
     * 
     * @param Bundle savedInstanceState
     * @return Dialog
     * @access public
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //犬マスタデータ取得
        final ArrayList<DogMasterEntity> data = (ArrayList<DogMasterEntity>) getArguments().getSerializable("data");

        //ダイアログ生成
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_kind_list, null);
        dialog.setContentView(layout);

        ListView listViewKind = (ListView) layout.findViewById(R.id.listViewKind);
        listViewKind.setAdapter(new KindListAdapter(getActivity(), R.layout.parts_kind_list, data));
        listViewKind.setScrollingCacheEnabled(false);
        listViewKind.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> items, View view, int position, long id) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickKindSelectDialog(data.get(position).getId(), data.get(position).getKindName());
                }
                dismiss();
            }
        });

        return dialog;
    }

    /**
     * コールバックリスナー
     * 
     * @access public
     */
    public interface CallbackListener
    {
        public void onClickKindSelectDialog(Integer kind, String name);
    }
}
