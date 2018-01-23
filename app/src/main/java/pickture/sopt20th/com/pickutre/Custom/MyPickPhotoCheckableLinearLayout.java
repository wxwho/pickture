package pickture.sopt20th.com.pickutre.Custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import pickture.sopt20th.com.pickutre.R;

/**
 * Created by ksj on 2017. 7. 2..
 */

public class MyPickPhotoCheckableLinearLayout extends LinearLayout implements Checkable {


    public MyPickPhotoCheckableLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.impl_checkbox);
        if (cb.isChecked() != checked) {
            cb.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.impl_checkbox);
        return cb.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.impl_checkbox);
        setChecked(cb.isChecked() ? false : true);
    }
}
