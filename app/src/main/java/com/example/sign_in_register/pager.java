//  use viewpager here so let the change between mutil pages smoother

package com.example.sign_in_register;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class pager extends ViewPager {

    public pager(@NonNull Context context) { super(context);}
    public pager(@NonNull Context context, @Nullable AttributeSet attr) { super(context,attr);}

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

}

