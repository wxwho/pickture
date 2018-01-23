package pickture.sopt20th.com.pickutre.Item;

import android.graphics.drawable.Drawable;

/**
 * Created by ksj on 2017. 6. 30..
 */

public class MyPickPhotographerListItem {
    private Drawable profile;
    private String id;
    private String category;

    public void setProfile(Drawable profile) {
        this.profile = profile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Drawable getProfile() {
        return profile;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }



}