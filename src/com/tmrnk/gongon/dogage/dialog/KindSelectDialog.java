package com.tmrnk.gongon.dogage.dialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.model.DogMasterEntity;
import com.tmrnk.gongon.dogage.module.KindListAdapter;

/**
 * エラーダイアログ
 * 
 * @access public
 */
public class KindSelectDialog extends DialogFragment
{
    private CallbackListener mCallbackListener = null;

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
        mCallbackListener = (CallbackListener) getTargetFragment();                 //コールバックリスナーを取り出してセット

        //犬マスタデータ取得
        final ArrayList<DogMasterEntity> data = (ArrayList<DogMasterEntity>) getArguments().getSerializable("data");

        //ダイアログ生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ワンちゃんの種類");

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_kind_list, null);
        builder.setView(layout);

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

        return builder.create();
    }

    /**
     * コールバックリスナーを追加
     * 
     * @param CallbackListener callbackListener
     * @return void
     * @access public
     */
    public void setCallbackListener(CallbackListener callbackListener)
    {
        setTargetFragment((Fragment) callbackListener, 0);      //コールバックリスナーを一時保存、第2引数は適当
    }

    /**
     * コールバックリスナーを削除
     * 
     * @return void
     * @access public
     */
    public void removeCallbackListener()
    {
        mCallbackListener = null;
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
