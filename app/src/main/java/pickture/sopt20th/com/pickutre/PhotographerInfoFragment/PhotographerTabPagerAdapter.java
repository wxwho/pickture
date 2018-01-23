package pickture.sopt20th.com.pickutre.PhotographerInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ksj on 2017. 6. 28..
 */

public class PhotographerTabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    private String pgId;

    public PhotographerTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    public PhotographerTabPagerAdapter(FragmentManager fm, int tabCount, String pgId) {
        super(fm);
        this.pgId = pgId;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("pgId", pgId);

        // Returning the current tabs
        switch (position) {
            case 0:
                fragment = new PhotographerPortfolioFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new PhotographerDetailFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new PhotographerReviewFragment();
                fragment.setArguments(bundle);
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
