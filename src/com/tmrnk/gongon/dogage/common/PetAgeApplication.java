package com.tmrnk.gongon.dogage.common;

import android.app.Application;

/**
 * 変数保持用アプリケーションクラス
 * 
 * @access public
 */
public class PetAgeApplication extends Application
{
    //ペット年齢表示アクテビティを離れる際に表示していたページ数を一時保持しておく変数
    public Integer mPageNum = null;
}
