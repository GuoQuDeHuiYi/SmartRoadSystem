package com.smartcity.qiuchenly.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.qiuchenly.Adapter.iController.iPersonalEvent;

import java.util.List;

/**
 * Created by Eason on 2017/11/12.
 */

public class mPersonal_center_ViewPager extends PagerAdapter {

    List<View> list;
    iPersonalEvent event;
    public mPersonal_center_ViewPager(List<View> list, iPersonalEvent event) {
        this.list = list;
        this.event = event;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = list.get(position);
        event.PersonSetViewEvent(v,position);
        container.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
