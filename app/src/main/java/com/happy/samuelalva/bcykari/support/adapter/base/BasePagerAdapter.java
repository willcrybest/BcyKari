package com.happy.samuelalva.bcykari.support.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Samuel.Alva on 2015/5/5.
 */
public abstract class BasePagerAdapter extends FragmentStatePagerAdapter {
    protected String[] titles;

    private SparseArray<Fragment> mPages;

    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
        mPages = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = createItem(position);
        mPages.put(position, f);
        return f;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (0 <= mPages.indexOfKey(position)) {
            mPages.remove(position);
        }
        super.destroyItem(container, position, object);
    }

    public Fragment getItemAt(int position) {
        return mPages.get(position);
    }

    protected abstract Fragment createItem(int position);
}
