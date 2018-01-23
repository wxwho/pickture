package pickture.sopt20th.com.pickutre.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import pickture.sopt20th.com.pickutre.MainFragment.MainTabPageAdapter;
import pickture.sopt20th.com.pickutre.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView toolbarImageview;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding Toolbar to the activity
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // 이미지 배열에 저장
        final int[][] tabImage = {
                {R.drawable.home_gray, R.drawable.home_black},
                {R.drawable.magnifier_gray, R.drawable.magnifier_black},
                {R.drawable.check_gray, R.drawable.check_black},
                {R.drawable.friends_gray, R.drawable.friends_black},
                {R.drawable.drawer_gray, R.drawable.drawer_black},
        };

        final int[] toolbarImage = {
                R.drawable.logo,
                R.drawable.search_txt,
                R.drawable.category_txt,
                R.drawable.model_txt,
                R.drawable.mypage_txt,
        };

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabImage[0][0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabImage[1][0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabImage[2][0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabImage[3][0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabImage[4][0]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        toolbarImageview = (ImageView) findViewById(R.id.am_imageview_toolbar);

        // Creating TabPagerAdapter adapter
        MainTabPageAdapter pagerAdapter = new MainTabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabImage[tab.getPosition()][1]);
                toolbarImageview.setImageResource(toolbarImage[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabImage[tab.getPosition()][0]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "뒤로가기를 한 번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}