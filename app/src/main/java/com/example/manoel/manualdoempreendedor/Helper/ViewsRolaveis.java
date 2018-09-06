package com.example.manoel.manualdoempreendedor.Helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

public class ViewsRolaveis extends ListView {
    public ViewsRolaveis(Context context) {
        super(context);
    }

    public ViewsRolaveis(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewsRolaveis(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
