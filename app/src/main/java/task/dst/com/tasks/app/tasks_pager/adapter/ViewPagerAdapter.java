package task.dst.com.tasks.app.tasks_pager.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> tasks;
    private List<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {
        super(fm);
        this.tasks = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
