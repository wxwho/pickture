package pickture.sopt20th.com.pickutre.MainFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ksj on 2017. 6. 27..
 */
public class MainTabPageAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public MainTabPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new MyPickFragment();
            case 3:
                return new FindModelFragment();
            case 4:
                return new MyPageFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
