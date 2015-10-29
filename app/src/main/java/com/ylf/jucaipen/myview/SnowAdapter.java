package com.ylf.jucaipen.myview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public class SnowAdapter  extends PagerAdapter{
    private final List<View> views
            ;

    public SnowAdapter(List<View> views) {
        this.views=views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
           container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
