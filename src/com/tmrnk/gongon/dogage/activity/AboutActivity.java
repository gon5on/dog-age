package com.tmrnk.gongon.dogage.activity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tmrnk.gongon.dogage.R;
import com.tmrnk.gongon.dogage.common.AndroidUtils;
import com.tmrnk.gongon.dogage.config.Config;
import com.tmrnk.gongon.dogage.entity.DogMasterEntity;

/**
 * アプリについてアクテビティ
 * 
 * @access public
 */
public class AboutActivity extends AppActivity
{
    private static final String PATTERN = "PixelKit";
    private static final String URL = "http://pixelkit.com/";

    /**
     * onCreate
     * 
     * @param Bundle savedInstanceState
     * @return void
     * @access protected
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new AboutFragment()).commit();
        }
    }

    /**
     * onCreateOptionsMenu
     * 
     * @param Menu menu
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //初回起動以外だったラ、アクションバーに戻るボタンをセット
        if (getIntent().getIntExtra("initFlag", 0) == 0) {
            getMenuInflater().inflate(R.menu.input, menu);
        }

        return true;
    }

    /**
     * onOptionsItemSelected
     * 
     * @param MenuItem item
     * @return Boolean
     * @access public
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Integer id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AboutFragment
     * 
     * @access public
     */
    public static class AboutFragment extends Fragment
    {
        private View mView = null;

        /**
         * onCreateView
         * 
         * @param LayoutInflater inflater
         * @param ViewGroup container
         * @param Bundle savedInstanceState
         * @return View
         * @access public
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // fragment再生成抑止
            setRetainInstance(true);

            mView = inflater.inflate(R.layout.fragment_about, container, false);

            //表組作成
            createDogMasterTable();

            //ライセンスにリンクを貼る
            linkLisence();

            //スクロールビューのオーバースクロールで端の色を変えないように
            container.setOverScrollMode(View.OVER_SCROLL_NEVER);

            return mView;
        }

        /**
         * ライセンスにリンクを貼る
         * 
         * @return void
         * @access private
         */
        private void linkLisence()
        {
            TextView textViewCC = (TextView) mView.findViewById(R.id.textViewCC);

            Pattern pattern = Pattern.compile(PATTERN);
            Linkify.TransformFilter filter = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return URL;
                }
            };

            Linkify.addLinks(textViewCC, pattern, URL, null, filter);
        }

        /**
         * 表組作成
         * 
         * @return void
         * @access private
         */
        private void createDogMasterTable()
        {
            String[] label = new String[] { Config.LABEL_SMALL, Config.LABEL_MEDIUM, Config.LABEL_LARGE };
            Double[] age = new Double[] { Config.BEFORE_TWO_AGE_SMALL, Config.BEFORE_TWO_AGE_MEDIUM, Config.BEFORE_TWO_AGE_LAEGE };

            TableLayout tableLayout = (TableLayout) mView.findViewById(R.id.tableLayout);

            ArrayList<DogMasterEntity> data = Config.getDogMastersList();

            TableRow.LayoutParams rowLayout = new TableRow.LayoutParams();
            rowLayout.span = 2;

            for (int i = -3; i < data.size(); i++) {
                if (i == 0) {
                    TableRow tableRow = new TableRow(getActivity());
                    tableLayout.addView(tableRow);

                    TextView textViewLabel = new TextView(getActivity());
                    textViewLabel.setTextSize(15);
                    textViewLabel.setTextColor(getResources().getColor(R.color.text));
                    textViewLabel.setText("●2歳以上");
                    textViewLabel.setPadding(0, AndroidUtils.dpToPixel(getActivity(), 10.0), 0, 0);
                    tableRow.addView(textViewLabel);
                }

                TableRow tableRow = new TableRow(getActivity());
                tableLayout.addView(tableRow);

                TextView textViewKind = new TextView(getActivity());
                textViewKind.setTextSize(12);
                textViewKind.setBackgroundResource(R.drawable.bg_table_cell);
                textViewKind.setTextColor(getResources().getColor(R.color.text));

                if (i < 0) {
                    textViewKind.setText(label[i + 3]);
                    tableRow.addView(textViewKind);
                }
                else if (data.get(i).getLabelFlag() == 1) {
                    textViewKind.setText(data.get(i).getKind());
                    textViewKind.setBackgroundResource(R.drawable.bg_table_cell_label);
                    textViewKind.setTextColor(getResources().getColor(R.color.text_highlight));
                    tableRow.addView(textViewKind, rowLayout);
                } else {
                    textViewKind.setText(data.get(i).getKind());
                    tableRow.addView(textViewKind);
                }

                if (i < 0 || data.get(i).getLabelFlag() == 0) {
                    TextView textViewAge = new TextView(getActivity());
                    textViewAge.setTextSize(12);
                    textViewAge.setTextColor(getResources().getColor(R.color.text));
                    textViewAge.setBackgroundResource(R.drawable.bg_table_cell);
                    textViewAge.setGravity(Gravity.RIGHT);

                    if (i < 0) {
                        textViewAge.setText(String.valueOf(age[i + 3]) + "歳/年");
                    } else {
                        textViewAge.setText(String.valueOf(data.get(i).getOverThreeAge()) + "歳/年");
                    }

                    tableRow.addView(textViewAge);
                }
            }
        }
    }
}
