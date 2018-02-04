package jp.co.e2.dogage.dialog;

import java.util.ArrayList;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.KindSelectDialog.CallbackListener;
import jp.co.e2.dogage.entity.DogMasterEntity;
import jp.co.e2.dogage.adapter.KindListAdapter;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 種類選択ダイアログ
 */
public class KindSelectDialog extends BaseDialog<CallbackListener> {
    private static final String PARAM_DATA = "data";

    /**
     * ファクトリーメソッド
     *
     * @return kindSelectDialog
     */
    public static KindSelectDialog newInstance(ArrayList<DogMasterEntity> data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_DATA, data);

        KindSelectDialog dialog = new KindSelectDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    /**
     * ${inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<DogMasterEntity> data = (ArrayList<DogMasterEntity>) getArguments().getSerializable(PARAM_DATA);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_kind_list, null);

        ListView listViewKind = view.findViewById(R.id.listViewKind);
        listViewKind.setAdapter(new KindListAdapter(getActivity(), data));
        listViewKind.setScrollingCacheEnabled(false);
        listViewKind.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> items, View view, int position, long id) {
                if (mCallbackListener != null) {
                    mCallbackListener.onClickKindSelectDialog(getTag(), data.get(position).getId(), data.get(position).getKind());
                }
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    /**
     * コールバックリスナー
     */
    public interface CallbackListener {
        /**
         * 種類選択ダイアログで何かしら選択された
         *
         * @param tag タグ
         * @param kind 種類ID
         * @param name 名称
         */
        void onClickKindSelectDialog(String tag, Integer kind, String name);
    }
}
