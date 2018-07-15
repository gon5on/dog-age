package jp.co.e2.dogage.activity;

import java.io.IOException;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.PhotoDialog;
import jp.co.e2.dogage.entity.PetEntity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ペット年齢フラグメント
 */
public class PetAgeFragment extends Fragment {
    private static final Integer[] BG_ABOVE = new Integer[] {
            R.drawable.img_frame_above1,
            R.drawable.img_frame_above2,
            R.drawable.img_frame_above3
    };
    private static final Integer[] BG_UNDER = new Integer[] {
            R.drawable.img_frame_under1,
            R.drawable.img_frame_under2,
            R.drawable.img_frame_under3
    };
    private static final Integer BG_CNT = BG_ABOVE.length;

    private static final String PARAM_PAGE_NUM = "page_num";
    private static final String PARAM_DATA = "data";

    private PetEntity mData = null;
    private Integer mPageNum = 0;
    private View mView = null;

    /**
     * ファクトリーメソッド
     *
     * @return PetAgeFragment
     */
    public static PetAgeFragment newInstance(int pageNum, PetEntity data) {
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_PAGE_NUM, pageNum);
        bundle.putSerializable(PARAM_DATA, data);

        PetAgeFragment fragment = new PetAgeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * onCreateView
     *
     * @return void
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_pet_age, container, false);

        // データを取得
        Bundle bundle = getArguments();
        mData = (PetEntity) bundle.getSerializable(PARAM_DATA);
        mPageNum = bundle.getInt(PARAM_PAGE_NUM);

        // 画面表示
        setDisplayItem();

        return mView;
    }

    /**
     * 画面表示
     */
    private void setDisplayItem() {
        try {
            TextView textViewName = mView.findViewById(R.id.textViewName);
            textViewName.setText(mData.getName());

            TextView textViewKind = mView.findViewById(R.id.textViewKind);
            textViewKind.setText(mData.getKindDisp(getContext()));

            TextView textViewBirthday = mView.findViewById(R.id.textViewBirthday);
            textViewBirthday.setText(mData.getDispBirthday(getActivity()));

            TextView textViewPetAge = mView.findViewById(R.id.textViewPetAge);
            textViewPetAge.setText(mData.getPetAgeDisp(getActivity()));

            TextView textViewHumanAge = mView.findViewById(R.id.textViewHumanAge);
            textViewHumanAge.setText(mData.getHumanAge(getContext()));

            TextView textViewDays = mView.findViewById(R.id.textViewDays);
            textViewDays.setText(mData.getDaysFromBorn(getActivity()));

            LinearLayout linearLayout1 = mView.findViewById(R.id.linearLayout1);
            linearLayout1.setBackgroundResource(BG_ABOVE[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout1 = mView.findViewById(R.id.relativeLayout1);
            relativeLayout1.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout2 = mView.findViewById(R.id.relativeLayout2);
            relativeLayout2.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout3 = mView.findViewById(R.id.relativeLayout3);
            relativeLayout3.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            TextView textViewArchive = mView.findViewById(R.id.textViewArchive);

            if (mData.getArchiveDate() != null) {
                textViewArchive.setVisibility(View.VISIBLE);
                textViewArchive.setText(mData.getDispArchiveDate(getActivity()));
            } else {
                textViewArchive.setVisibility(View.GONE);
            }

            ImageView imageViewPhoto = mView.findViewById(R.id.imageViewPhoto);

            if (mData.getPhotoFlg()) {
                Bitmap thumbBitmap = mData.getPhotoThumb(getActivity());
                final Bitmap bigBitmap = mData.getPhotoBig(getActivity());

                imageViewPhoto.setImageBitmap(thumbBitmap);
                imageViewPhoto.setVisibility(View.VISIBLE);

                imageViewPhoto.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getActivity() != null) {
                            PhotoDialog photoDialog = PhotoDialog.newInstance(bigBitmap);
                            photoDialog.show(getActivity().getFragmentManager(), "dialog");
                        }
                    }
                });
            } else {
                imageViewPhoto.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}