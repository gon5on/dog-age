package jp.co.e2.dogage.dialog;

import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.KindSelectDialog.CallbackListener;
import jp.co.e2.dogage.entity.DogMasterEntity;
import jp.co.e2.dogage.adapter.KindListAdapter;

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

/**
 * 種類選択ダイアログ
 */
public class KindSelectDialog extends BaseDialog<CallbackListener> {
    /**
     * インスタンスを返す
     *
     * @return kindSelectDialog
     */
    public static KindSelectDialog getInstance(ArrayList<DogMasterEntity> data) {
        KindSelectDialog dialog = new KindSelectDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                    mCallbackListener.onClickKindSelectDialog(data.get(position).getId(), data.get(position).getKind());
                }
                dismiss();
            }
        });

        //スクロールビューのオーバースクロールで端の色を変えないように
        listViewKind.setOverScrollMode(View.OVER_SCROLL_NEVER);

        return dialog;
    }

    /**
     * コールバックリスナー
     */
    public interface CallbackListener {
        /**
         * 種類選択ダイアログで何かしら選択された
         *
         * @param kind 種類ID
         * @param name 名称
         */
        void onClickKindSelectDialog(Integer kind, String name);
    }
}
