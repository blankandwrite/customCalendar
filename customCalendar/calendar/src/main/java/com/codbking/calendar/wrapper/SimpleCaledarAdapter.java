package com.codbking.calendar.wrapper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.R;

/**
 * Created by THINK on 2017-3-7.
 */
public abstract class SimpleCaledarAdapter extends CaledarAdapter implements Cloneable {
    @Override
    public View getView(View convertView, ViewGroup parentView, int selectPosition, int position, CalendarBean bean) {
        TextView view;
        ImageView indicator;
        if (convertView == null) {
            convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.wrapper_item_calendar, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MyUtils.px(48), MyUtils.px(48));
            convertView.setLayoutParams(params);
        }
        view = (TextView) convertView.findViewById(R.id.text);
        indicator = (ImageView) convertView.findViewById(R.id.indicatorImg);
        view.setText("" + bean.day);
        if (isEnabled(position, bean)) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.INVISIBLE);
        }
        if (bean.mothFlag != 0) {
            view.setTextColor(0xff9299a1);
        } else {
            view.setTextColor(0xffffffff);
        }
        if (selectPosition == position) {
            System.out.println("#select:" + bean);
            indicator.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CaledarAdapter obj = null;
        try {
            obj = (CaledarAdapter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
