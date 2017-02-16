package jp.co.e2.dogage.activity;

import java.io.IOException;

import jp.co.e2.dogage.R;
import jp.co.e2.dogage.dialog.PhotoDialog;
import jp.co.e2.dogage.entity.PetEntity;

import android.graphics.Bitmap;
import android.os.Bundle;
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
    private static final Integer[] BG_ABOVE = new Integer[]{R.drawable.img_frame_above1, R.drawable.img_frame_above2, R.drawable.img_frame_above3};
    private static final Integer[] BG_UNDER = new Integer[]{R.drawable.img_frame_under1, R.drawable.img_frame_under2, R.drawable.img_frame_under3};
    private static final Integer BG_CNT = BG_ABOVE.length;

    private PetEntity mItem = null;
    private Integer mPageNum = 0;
    private View mView = null;

    /**
     * onCreateView
     *
     * @return void
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_pet_age, container, false);

        // データを取得
        Bundle bundle = getArguments();
        mItem = (PetEntity) bundle.getSerializable("item");
        mPageNum = bundle.getInt("pageNum");

        // 画面表示
        setDisplayItem();

        return mView;
    }

    /**
     * 画面表示
     */
    private void setDisplayItem() {
        try {
            TextView textViewName = (TextView) mView.findViewById(R.id.textViewName);
            textViewName.setText(mItem.getName());

            TextView textViewKind = (TextView) mView.findViewById(R.id.textViewKind);
            textViewKind.setText(mItem.getKindDisp(getActivity().getApplicationContext()));

            TextView textViewBirthday = (TextView) mView.findViewById(R.id.textViewBirthday);
            textViewBirthday.setText(mItem.getDispBirthday(getActivity()));

            TextView textViewPetAge = (TextView) mView.findViewById(R.id.textViewPetAge);
            textViewPetAge.setText(mItem.getPetAgeDisp(getActivity()));

            TextView textViewHumanAge = (TextView) mView.findViewById(R.id.textViewHumanAge);
            textViewHumanAge.setText(mItem.getHumanAge(getActivity().getApplicationContext()));

            TextView textViewDays = (TextView) mView.findViewById(R.id.textViewDays);
            textViewDays.setText(mItem.getDaysFromBorn(getActivity()));

            LinearLayout linearLayout1 = (LinearLayout) mView.findViewById(R.id.linearLayout1);
            linearLayout1.setBackgroundResource(BG_ABOVE[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout1 = (RelativeLayout) mView.findViewById(R.id.relativeLayout1);
            relativeLayout1.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout2 = (RelativeLayout) mView.findViewById(R.id.relativeLayout2);
            relativeLayout2.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            RelativeLayout relativeLayout3 = (RelativeLayout) mView.findViewById(R.id.relativeLayout3);
            relativeLayout3.setBackgroundResource(BG_UNDER[mPageNum % BG_CNT]);

            TextView textViewArchive = (TextView) mView.findViewById(R.id.textViewArchive);

            if (mItem.getArchiveDate() != null) {
                textViewArchive.setVisibility(View.VISIBLE);
                textViewArchive.setText(mItem.getDispArchiveDate(getActivity()));
            } else {
                textViewArchive.setVisibility(View.GONE);
            }

            ImageView imageViewPhoto = (ImageView) mView.findViewById(R.id.imageViewPhoto);

            if (mItem.getPhotoFlg()) {
                Bitmap thumbBitmap = mItem.getPhotoThumb(getActivity());
                final Bitmap bigBitmap = mItem.getPhotoBig(getActivity());

                imageViewPhoto.setImageBitmap(thumbBitmap);
                imageViewPhoto.setVisibility(View.VISIBLE);

                imageViewPhoto.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoDialog photoDialog = PhotoDialog.getInstance(bigBitmap);
                        photoDialog.show(getActivity().getFragmentManager(), "dialog");
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